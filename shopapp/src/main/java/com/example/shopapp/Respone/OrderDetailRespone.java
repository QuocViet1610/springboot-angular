package com.example.shopapp.Respone;

import com.example.shopapp.Repository.OrderDetailRepository;
import com.example.shopapp.model.Order;
import com.example.shopapp.model.OrderDetail;
import com.example.shopapp.model.Product;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDetailRespone {

    private Long id;

    private Long orderId;

    private Long productId;

    private Float price;

    private Long numberOfProduct;

    private Float totalMoney;

    private String color;

    public static OrderDetailRespone fromOrderDetailResponeBuider(OrderDetail orderDetail){
        return OrderDetailRespone.builder()
                .id(orderDetail.getId())
                .orderId(orderDetail.getOrderId().getId())
                .color(orderDetail.getColor())
                .numberOfProduct(orderDetail.getNumberOfProduct())
                .price(orderDetail.getPrice())
                .productId(orderDetail.getProductId().getId())
                .totalMoney(orderDetail.getTotalMoney())
                .build();
    }
}
