package com.example.shopapp.Controller;

import com.example.shopapp.Repository.ProductRepository;
import com.example.shopapp.Respone.ProductListRespone;
import com.example.shopapp.Respone.ProductRespone;
import com.example.shopapp.Service.imp.IProductService;
import com.example.shopapp.Utils.MessageKeys;
import com.example.shopapp.compoments.LocalizationUtils;
import com.example.shopapp.dtos.ProductDTO;
import com.example.shopapp.dtos.ProductImageDTO;
import com.example.shopapp.exception.DataNotFoundException;
import com.example.shopapp.model.Product;
import com.example.shopapp.model.ProductImage;
import com.github.javafaker.Faker;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("${api.prefix}/products")
@RequiredArgsConstructor
public class ProductController {

    private final IProductService productService;
    private final LocalizationUtils localizationUtils;


    @GetMapping("")
    public ResponseEntity<?> getAllCategory(
            @RequestParam("Page") int page,
            @RequestParam("limit") int limit
    ){

        PageRequest pageRequest = PageRequest.of(page, limit, Sort.by("createdAt").descending());
        Page<ProductRespone> productPage = productService.getAllProduct(pageRequest);

        int totalPage = productPage.getTotalPages();
        // danh sách trang hiện tại
        List<ProductRespone> productList = productPage.getContent();

        return new ResponseEntity<>(ProductListRespone.builder()
                .ListProductRespone(productList)
                .totalPage(totalPage)
                .build()
                , HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<?> CreateProduct(@Valid
//                  @ModelAttribute ProductDTO productDTO,
//                  @RequestPart("file") MultipartFile file,
                  @RequestBody ProductDTO productDTO,
                  BindingResult result){
        try{
            List<MultipartFile> files = productDTO.getFiles();

            // nếu file bằng null sẽ tạo một mảng rỗng để có thể chạy dc for

                if(result.hasErrors()){
                    List<String> messageErrors= result.getFieldErrors().stream().map(FieldError::getDefaultMessage).toList();
                     String messageError = String.join(",", messageErrors );
                    return new ResponseEntity<>(messageError, HttpStatus.BAD_REQUEST);
                }

                Product product = productService.createProduct(productDTO);



            return new ResponseEntity<>(product, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

//    {
//        "name" : "viet",
//            "price" : 123.3,
//            "thumnail" : "img",
//            "description" : "description",
//            "category_id" : "1"
//    }

    @PostMapping(value = "uplodaFile/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    private ResponseEntity<?> uploadFile(@ModelAttribute("files") List<MultipartFile> files, @PathVariable("id") Long id){
        try{
            // nếu file bằng null sẽ tạo một mảng rỗng để có thể chạy dc for
            files = files == null ? new ArrayList<MultipartFile>() : files;
            Product existProduct = productService.getById(id);

            List<ProductImage> productImage = new ArrayList<>();

            if(files.size() > ProductImage.MAX_IMAGE_PER_PRODUCT){
                return ResponseEntity.badRequest().body(localizationUtils
                        .getLocalizedMessage(MessageKeys.UPLOAD_IMAGES_MAX_5));
            }

            for(MultipartFile file : files){
                // kiểm tra kích thước của size
                if(file.getSize() == 0){
                    continue;
                }
                if(file.getSize() > 10 * 1024 * 1024){
                    return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE)
                            .body(localizationUtils
                                    .getLocalizedMessage(MessageKeys.UPLOAD_IMAGES_FILE_LARGE));
                }
                // kiểm tra loại file là file ảnh
                String contentType = file.getContentType();
                if(contentType == null || !contentType.startsWith("image/")){
                    return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
                            .body(localizationUtils.getLocalizedMessage(MessageKeys.UPLOAD_IMAGES_FILE_MUST_BE_IMAGE));
                }
                String namefile = storeFile(file);

                ProductImageDTO productImageDTO = ProductImageDTO.builder()
                        .imageUrl(namefile)
                        .build();

                ProductImage saveImage = productService.createProductImage(
                        existProduct.getId(),
                        productImageDTO
                );
                productImage.add(saveImage);
            }
            return new ResponseEntity<>(productImage, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    private String storeFile(MultipartFile file) throws IOException{
        // lấy tên file
        String filename = StringUtils.cleanPath(file.getOriginalFilename());
        // thêm thời gian trước tên file đảm bảo file là duy nhất

        String uniqueFileName = LocalDateTime.now().toString().replaceAll(":", "") + filename;

        // đường dẫn đến thư mục muốn lưu
        Path uploadFile = Paths.get("uploads");
        // kiểm tra thư và tạo thư mục nếu không tồn tại
        if(!Files.exists(uploadFile)){
            Files.createDirectory(uploadFile);
        }
        Path destination = Paths.get(uploadFile.toString(),uniqueFileName );
        Files.copy(file.getInputStream(),destination, StandardCopyOption.REPLACE_EXISTING);
        return uniqueFileName;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable int id ){
        return new ResponseEntity<>("delete", HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable int id ){
        return new ResponseEntity<>("update: " + id , HttpStatus.OK);
    }

    @PostMapping("/genarateFakeProducts")
    public ResponseEntity<?> genarateFakeProducts(){
        Faker faker = new Faker();
        for (int i = 0; i < 1000; i++) {
            String title = faker.commerce().productName();
            if (productService.existNameProduct(title)) {
                continue; // Nếu title đã tồn tại, bỏ qua và tạo sản phẩm mới
            }

            ProductDTO productDTO = ProductDTO.builder()
                    .name(title)
                    .price((float)faker.number().numberBetween(18, 90_088_000))
                    .thumnail("")
                    .description(faker.lorem().sentence())
                    .categoryId((long) faker.number().numberBetween(1,4))
                    .build();

            try {
                productService.createProduct(productDTO);
            } catch (DataNotFoundException e) {
                return new ResponseEntity<>(e.getMessage() , HttpStatus.BAD_REQUEST);
            }
        }
        return new ResponseEntity<>("generate successfull" , HttpStatus.OK);
    }

    @GetMapping ("/{id}")
    public ResponseEntity<?> getDetailProduct(@PathVariable("id") Long id){
        try {
            Product product = productService.getById(id);
            ProductRespone productRespone = ProductRespone.fromProduct(product);
            return new ResponseEntity<>(productRespone , HttpStatus.OK);
        } catch (DataNotFoundException e) {
            return new ResponseEntity<>(e.getMessage() , HttpStatus.OK);
        }


    }

}
