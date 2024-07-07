package com.example.shopapp.Service;

import com.example.shopapp.Repository.OrderDetailRepository;
import com.example.shopapp.Repository.OrderRepository;
import com.example.shopapp.Repository.ProductRepository;
import com.example.shopapp.Repository.UserRepository;
import com.example.shopapp.Service.imp.IOrderDetailService;
import com.example.shopapp.dtos.OrderDetailDTO;
import com.example.shopapp.exception.DataNotFoundException;
import com.example.shopapp.model.Order;
import com.example.shopapp.model.OrderDetail;
import com.example.shopapp.model.Product;
import com.example.shopapp.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderDetailService implements IOrderDetailService {

    private final OrderRepository orderRepository;

    private final ProductRepository productRepository;

    private final OrderDetailRepository orderDetailRepository;

    @Override
    public OrderDetail createOrderDetail(OrderDetailDTO orderDetailDTO) throws DataNotFoundException {
        Order order = orderRepository.findById(orderDetailDTO.getOrderId())
                .orElseThrow(() -> new DataNotFoundException("cannot find order id: "+orderDetailDTO.getOrderId() ));

        Product product = productRepository.findById(orderDetailDTO.getProductId())
                .orElseThrow(() -> new DataNotFoundException("cannot find prduct id: "+orderDetailDTO.getOrderId() ));

       OrderDetail orderDetail = OrderDetail.builder()
               .orderId(order)
               .productId(product)
               .price(orderDetailDTO.getPrice())
               .numberOfProduct(orderDetailDTO.getNumberOfProduct())
               .Color(orderDetailDTO.getColor())
               .totalMoney(orderDetailDTO.getTotalMoney())
               .build();


        return orderDetail;
    }

    @Override
    public OrderDetail updateOrderDetail(Long id, OrderDetailDTO orderDetailDTO) throws Exception {
        Order exsitOrder = orderRepository.findById(orderDetailDTO.getOrderId())
                .orElseThrow(() -> new DataNotFoundException("cannot find order id: "+orderDetailDTO.getOrderId() ));

        Product  exsitProduct = productRepository.findById(orderDetailDTO.getProductId())
                .orElseThrow(() -> new DataNotFoundException("cannot find product id: "+orderDetailDTO.getOrderId() ));

        OrderDetail orderDetail = orderDetailRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("cannot find Order Details id: "+id ));


        orderDetail.setPrice(orderDetailDTO.getPrice());
        orderDetail.setColor(orderDetailDTO.getColor());
        orderDetail.setNumberOfProduct(orderDetailDTO.getNumberOfProduct());
        orderDetail.setTotalMoney(orderDetailDTO.getTotalMoney());
        orderDetail.setOrderId(exsitOrder);
        orderDetail.setProductId(exsitProduct);

        return orderDetailRepository.save(orderDetail);
    }

    @Override
    public OrderDetail getOrderDetail(Long id) throws DataNotFoundException {
        OrderDetail orderDetail = orderDetailRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("cannot find order detail with id :" +id ));
        return orderDetail;
    }

    @Override
    public List<OrderDetail> getAllOrderDetail(Long idOrder) throws DataNotFoundException {
        Order exsitOrder = orderRepository.findById(idOrder)
                .orElseThrow(() -> new DataNotFoundException("cannot find order id: "+idOrder ));

        List<OrderDetail> orderDetail = orderDetailRepository.findByOrderId(exsitOrder);
        return orderDetail;
    }

    @Override
    public void deleleOrderDetail(Long id) {
        OrderDetail orderDetail = orderDetailRepository.findById(id).orElse(null);
        if (orderDetail != null){
            orderDetailRepository.delete(orderDetail);
        }
    }
}
