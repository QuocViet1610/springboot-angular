package com.example.shopapp.Repository;

import com.example.shopapp.model.Product;
import com.example.shopapp.model.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {

    List<ProductImage> findByProductId(Product productId);
    //tìm khoá ngoại sẽ trả về 1 list
    // khi tìm bằng khoá ngoại sẽ tương ứng với trường trong cột không cần phải tạo ra một đối tượng

}
