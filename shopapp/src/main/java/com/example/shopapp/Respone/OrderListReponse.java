package com.example.shopapp.Respone;

import com.example.shopapp.model.Order;
import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderListReponse {
    private List<OrderRespone> orderRespones;
}
