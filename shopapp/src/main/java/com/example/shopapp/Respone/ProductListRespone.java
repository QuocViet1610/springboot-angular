package com.example.shopapp.Respone;


import lombok.*;

import java.util.List;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductListRespone {
    List<ProductRespone> ListProductRespone;
    int totalPage;
}
