package com.example.shopapp.Respone;

import com.example.shopapp.model.Order;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderRespone {

    private Long id;

    private String fullname;

    private String email;

    private String phoneNumber;

    private String address;

    private String note;

    private Date orderDate;

    private String shippingMethod;

    private String shippingAddress;

    private LocalDate shippingDate;

    private String trackingNumber;

    private String paymentMethod;

    private boolean active;

    private String Status;

    private Long userId;

    public static OrderRespone fromOrderBuilder(Order order) {
        return OrderRespone.builder()
                .id(order.getId())
                .fullname(order.getFullname())
                .email(order.getEmail())
                .phoneNumber(order.getPhoneNumber())
                .address(order.getAddress())
                .note(order.getNote())
                .orderDate(order.getOrderDate())
                .shippingMethod(order.getShippingMethod())
                .shippingAddress(order.getShippingAddress())
                .shippingDate(order.getShippingDate())
                .trackingNumber(order.getTrackingNumber())
                .paymentMethod(order.getPaymentMethod())
                .active(order.isActive())
                .Status(order.getStatus())
                .userId(order.getUserId().getId())
                .build();
    }
}
