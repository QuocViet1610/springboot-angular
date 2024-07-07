package com.example.shopapp.Service;

import com.example.shopapp.Repository.CategoryRepository;
import com.example.shopapp.Repository.ProductImageRepository;
import com.example.shopapp.Repository.ProductRepository;
import com.example.shopapp.Respone.ProductRespone;
import com.example.shopapp.Service.imp.IProductService;
import com.example.shopapp.dtos.ProductDTO;
import com.example.shopapp.dtos.ProductImageDTO;
import com.example.shopapp.exception.DataNotFoundException;
import com.example.shopapp.exception.InvalidPramaterExpection;
import com.example.shopapp.model.Category;
import com.example.shopapp.model.Product;
import com.example.shopapp.model.ProductImage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductService implements IProductService {

    private final CategoryRepository categoryRepository;

    private final ProductRepository productRepository;

    private final ProductImageRepository productImageRepository;

    public ProductService(CategoryRepository categoryRepository, ProductRepository productRepository, ProductImageRepository productImageRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
        this.productImageRepository = productImageRepository;
    }

    @Override
    public Product createProduct(ProductDTO productDTO) throws DataNotFoundException {
        Category category = categoryRepository.findById(productDTO.getCategoryId())
                .orElseThrow(() -> new DataNotFoundException("Category not found"));

        Product newProduct = Product.builder()
                .name(productDTO.getName())
                .price(productDTO.getPrice())
                .thumbnail(productDTO.getThumnail())
                .description(productDTO.getDescription())
                .CategoryId(category)
                .build();

        return productRepository.save(newProduct);
    }

    @Override
    public Product getById(Long id) throws DataNotFoundException {
        return productRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("cannot find product with id+" + id));
    }

    @Override
    public Page<ProductRespone> getAllProduct(PageRequest pageRequest) {
        // lấy sản phẩm theo trang (page) và giới hạn limited
        return productRepository.findAll(pageRequest)
                .map(product -> ProductRespone.fromProduct(product));
    }

    @Override
    public Product updateProduct(Long id, ProductDTO productDTO) throws DataNotFoundException {
        Product existProduct = getById(id);
        if (existProduct != null){
            Category category = categoryRepository.
                    findById(productDTO.getCategoryId())
                    .orElseThrow(() -> new DataNotFoundException("cant not find category id = "+productDTO.getCategoryId()));
            existProduct.setName(productDTO.getName());
            existProduct.setPrice(productDTO.getPrice());
            existProduct.setDescription(productDTO.getDescription());
            existProduct.setThumbnail(productDTO.getThumnail());
            existProduct.setCategoryId(category);
        }
        return productRepository.save(existProduct);
    }

    @Override
    public void deleteProduct(Long id) {
        Optional<Product> product = productRepository.findById(id);
        if(product.isPresent()){
            productRepository.delete(product.get());
        }
    }

    @Override
    public boolean existNameProduct(String name) {
        return productRepository.existsByName(name);
    }

    @Override
    public ProductImage createProductImage(Long id, ProductImageDTO productImageDTO) throws Exception {
        Product existProduct = productRepository.findById(id)
                .orElseThrow(() ->
                        new DataNotFoundException("cannot found Product ID = "+ id));
        ProductImage newProductImage = ProductImage.builder()
                .imageUrl(productImageDTO.getImageUrl())
                .productId(existProduct)
                .build();

        //không cho insert lớn hơn 5 ảnh trong cùng môt sản phẩm
        int size =productImageRepository.findByProductId(existProduct).size();
        if(size > 5){
            throw new InvalidPramaterExpection("number of images must be <5  ");
        }

        return productImageRepository.save(newProductImage);
    }

}
