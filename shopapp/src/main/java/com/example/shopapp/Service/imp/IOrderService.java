package com.example.shopapp.Service.imp;

import com.example.shopapp.dtos.OrderDTO;
import com.example.shopapp.exception.DataNotFoundException;
import com.example.shopapp.model.Order;

import java.util.List;

public interface IOrderService {
    Order createOrder(OrderDTO oderDTO) throws DataNotFoundException;

    Order updateOrder(Long id, OrderDTO oderDTO) throws DataNotFoundException;
    void deleteOder(Long id);

    Order getOrderById(Long id) throws DataNotFoundException;

    List<Order> getOrderByUser(Long id);




}
