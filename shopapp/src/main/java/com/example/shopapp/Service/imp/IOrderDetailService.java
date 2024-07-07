package com.example.shopapp.Service.imp;

import com.example.shopapp.dtos.OrderDetailDTO;
import com.example.shopapp.exception.DataNotFoundException;
import com.example.shopapp.model.OrderDetail;

import java.util.List;

public interface IOrderDetailService {
    OrderDetail createOrderDetail(OrderDetailDTO orderDetailDTO) throws DataNotFoundException;
    OrderDetail updateOrderDetail(Long id, OrderDetailDTO orderDetailDTO) throws Exception;
    OrderDetail getOrderDetail(Long id) throws DataNotFoundException;

    List<OrderDetail> getAllOrderDetail(Long idOrder) throws DataNotFoundException;

    void deleleOrderDetail(Long id);
}
