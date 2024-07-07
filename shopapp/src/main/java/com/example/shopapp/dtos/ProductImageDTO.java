package com.example.shopapp.dtos;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ProductImageDTO {

    @JsonProperty( "product_id")
    @Min(value = 1, message = "product id must be least 1")
    private Long productId;

    @JsonProperty("image_url")
    @Size(min = 1, max = 250, message = "title must be between 1 and 250 character")
    private String imageUrl;


}
