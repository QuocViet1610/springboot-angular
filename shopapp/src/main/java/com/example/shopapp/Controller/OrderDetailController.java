package com.example.shopapp.Controller;

import com.example.shopapp.Repository.OrderDetailRepository;
import com.example.shopapp.Respone.OrderDetailRespone;
import com.example.shopapp.Respone.OrderRespone;
import com.example.shopapp.Service.OrderDetailService;
import com.example.shopapp.dtos.OrderDetailDTO;
import com.example.shopapp.exception.DataNotFoundException;
import com.example.shopapp.model.Order;
import com.example.shopapp.model.OrderDetail;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.security.Provider;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("${api.prefix}/order_details")
@RequiredArgsConstructor
public class OrderDetailController {

    private final OrderDetailService orderDetailService;

    @PostMapping("")
    public ResponseEntity<?> createOderDetail(@Valid @RequestBody OrderDetailDTO orderDetailDTO,  BindingResult result){
        try {
            if (result.hasErrors()) {
                List<String> ErroMessage = result.getFieldErrors().stream().map(FieldError::getDefaultMessage).toList();
                String errorMessage = String.join(", ", ErroMessage);
                return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);

            }
            OrderDetail orderDetail = orderDetailService.createOrderDetail(orderDetailDTO);
            return new ResponseEntity<>("", HttpStatus.OK);
        } catch (DataNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }


    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOderDetail(@Valid @PathVariable("id") Long id){
        try {
            OrderDetail orderDetail = orderDetailService.getOrderDetail(id);
            return new ResponseEntity<>(OrderDetailRespone.fromOrderDetailResponeBuider(orderDetail), HttpStatus.OK);
        } catch (DataNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }


    }


    @GetMapping("/order/{id}")
    public ResponseEntity<?> getOderDetailByOder(@Valid @PathVariable("id") Long id){
        try {
            List<OrderDetailRespone> orderDetailDTOs = orderDetailService.getAllOrderDetail(id).stream().
                    map(orderDetail ->
                                    OrderDetailRespone.fromOrderDetailResponeBuider(orderDetail)
                            ).collect(Collectors.toList());
            return new ResponseEntity<>(orderDetailDTOs, HttpStatus.OK);
        } catch (DataNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOder(@Valid @PathVariable("id") Long id){
            orderDetailService.deleleOrderDetail(id);
        return new ResponseEntity<>("delete order Detail success  "+ id, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateOrder(@Valid @PathVariable("id") Long id , @RequestBody OrderDetailDTO orderDetailDTO, BindingResult result){
        try {
            if (result.hasErrors()) {
                List<String> ErroMessage = result.getFieldErrors().stream().map(FieldError::getDefaultMessage).toList();
                String errorMessage = String.join(", ", ErroMessage);
                return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
            }
            OrderDetail orderDetail = orderDetailService.updateOrderDetail(id,orderDetailDTO);
            return new ResponseEntity<>(OrderDetailRespone.fromOrderDetailResponeBuider(orderDetail), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }
}
