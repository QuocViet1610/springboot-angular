package com.example.shopapp.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailDTO {
    @JsonProperty("order_id")
    @Min(value = 1, message = "Order's ID must be > 0")
    private Long orderId;

    @JsonProperty("product_id")
    @Min(value = 1, message = "Product's ID must be > 0")
    private Long productId;

    @Min(value = 1, message = "Price must be > 0")
    private Float price;

    @JsonProperty("number_of_product")
    @Min(value = 1, message = "Number of product must be > 1")
    private Long numberOfProduct;

    @JsonProperty("total_money")
    @Min(value = 1, message = "Number of product must be > 0")
    private Float totalMoney;

    @NotBlank(message = "color is not blank")
    private String color;
}
