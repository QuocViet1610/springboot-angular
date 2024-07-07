package com.example.shopapp.Service.imp;

import com.example.shopapp.Respone.ProductRespone;
import com.example.shopapp.dtos.ProductDTO;
import com.example.shopapp.dtos.ProductImageDTO;
import com.example.shopapp.exception.DataNotFoundException;
import com.example.shopapp.model.Product;
import com.example.shopapp.model.ProductImage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface IProductService {

    Product createProduct(ProductDTO productDTO) throws DataNotFoundException;

    Product getById(Long id) throws DataNotFoundException;

    Page<ProductRespone> getAllProduct(PageRequest pageRequest);

    Product updateProduct(Long id, ProductDTO productDTO) throws DataNotFoundException;

    void deleteProduct(Long id);

    boolean existNameProduct(String name);

    ProductImage createProductImage(Long id , ProductImageDTO productImageDTO) throws Exception;
}