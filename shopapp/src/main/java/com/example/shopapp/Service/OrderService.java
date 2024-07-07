package com.example.shopapp.Service;

import com.example.shopapp.Repository.OrderRepository;
import com.example.shopapp.Repository.UserRepository;
import com.example.shopapp.Service.imp.IOrderService;
import com.example.shopapp.dtos.OrderDTO;
import com.example.shopapp.exception.DataNotFoundException;
import com.example.shopapp.model.Order;
import com.example.shopapp.model.OrderStatus;
import com.example.shopapp.model.User;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class OrderService implements IOrderService {

    private final UserRepository userRepository;
    private final OrderRepository orderRepository;

    private final ModelMapper modelMapper;

    public OrderService(UserRepository userRepository, OrderRepository orderRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Order createOrder(OrderDTO orderDTO) throws DataNotFoundException {
        // tim usser co ton tai khong
        User user =userRepository.findById(orderDTO.getUserId())
                .orElseThrow(() -> new DataNotFoundException("can't find user's ID = "+orderDTO.getUserId() ));

        modelMapper.typeMap(OrderDTO.class, Order.class)
                .addMappings(mapper -> mapper.skip(Order::setId));

        Order order = new Order();
        modelMapper.map(orderDTO, order);

        order.setUserId(user);
        order.setOrderDate(new Date());
        order.setStatus(OrderStatus.PENDING);
        //Kiểm tra shipping date phải >= ngày hôm nay
        LocalDate shippingDate = orderDTO.getShippingDate() == null
                ? LocalDate.now() : orderDTO.getShippingDate();

        // shippingDate phải sau giờ đặt hàng
        if (shippingDate.isBefore(LocalDate.now())) {
            throw new DataNotFoundException("Date must be at least today !");
        }
        order.setShippingDate(shippingDate);
        order.setActive(true);
        orderRepository.save(order);
        return order;
    }

    @Override
    public Order updateOrder(Long id, OrderDTO oderDTO) throws DataNotFoundException {
        Order order = getOrderById(id);
        User existUser =userRepository.findById(oderDTO.getUserId()).orElseThrow(
                () -> new DataNotFoundException("cannot find User with id :" + oderDTO.getUserId())
        );

        modelMapper.typeMap(OrderDTO.class, Order.class)
                .addMappings(mapper -> mapper.skip(Order::setId));

        //cập nhập lại cac trường order
        modelMapper.map(oderDTO, order);
        order.setUserId(existUser);
        return orderRepository.save(order);
    }

    @Override
    public void deleteOder(Long id) {
      Order order = orderRepository.findById(id).orElse(null);
        if(order != null){
//            orderRepository.delete(order.get());
            // soft delete
           order.setActive(false);
           orderRepository.save(order);
        }
    }

    @Override
    public Order getOrderById(Long id) throws DataNotFoundException {
        Order order = orderRepository.findById(id).orElseThrow(
                () -> new DataNotFoundException("cannot find Order id: "+ id)
        );

        return order;
    }

    @Override
    public List<Order> getOrderByUser(Long id) {
        User user = new User();
        user.setId(id);
        List<Order> order = orderRepository.findByUserId(user);
        return order;
    }
}
