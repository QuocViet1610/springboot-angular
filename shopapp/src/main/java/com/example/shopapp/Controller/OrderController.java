package com.example.shopapp.Controller;

import com.example.shopapp.Repository.OrderRepository;
import com.example.shopapp.Respone.OrderRespone;
import com.example.shopapp.Service.imp.IOrderService;
import com.example.shopapp.dtos.OrderDTO;
import com.example.shopapp.exception.DataNotFoundException;
import com.example.shopapp.model.Order;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("${api.prefix}/orders")
@RequiredArgsConstructor
public class OrderController {

    private final IOrderService orderService;

    @PostMapping("")
    public ResponseEntity<?> createOrder(@Valid @RequestBody OrderDTO orderDTO, BindingResult result){
        try {
            if (result.hasErrors()) {
                List<String> ErroMessage = result.getFieldErrors().stream().map(FieldError::getDefaultMessage).toList();
                String errorMessage = String.join(", ", ErroMessage);
                return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
            }
            Order order = orderService.createOrder(orderDTO);
            return new ResponseEntity<>(OrderRespone.fromOrderBuilder(order), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>( e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/user/{user_id}")
    public ResponseEntity<?> getOrderByIdUser(@PathVariable("user_id") Long id){
        List<OrderRespone> orderList = orderService.getOrderByUser(id).stream()
                .map(OrderRespone::fromOrderBuilder).toList();

        return new ResponseEntity<>(orderList,HttpStatus.OK);

    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOrderById(@PathVariable("id") Long id){
        try {
            Order order = orderService.getOrderById(id);
            return new ResponseEntity<>(OrderRespone.fromOrderBuilder(order),HttpStatus.OK);
        } catch (DataNotFoundException e) {
            return new ResponseEntity<>( e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }


    @PutMapping("/{id}")
    public ResponseEntity<?> updateOrder(@PathVariable("id") Long id, @RequestBody OrderDTO orderDTO){
        try {
            Order order = orderService.updateOrder(id,orderDTO);
            return new ResponseEntity<>(OrderRespone.fromOrderBuilder(order),HttpStatus.OK);
        } catch (DataNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }

    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> Delete(@PathVariable("id") Long id){
        // xoá mền
        orderService.deleteOder(id);
        return new ResponseEntity<>("",HttpStatus.OK);
    }

}
