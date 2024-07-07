package com.example.shopapp.Repository;

import com.example.shopapp.model.Order;
import com.example.shopapp.model.User;
import org.aspectj.weaver.ast.Or;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    // tìm ca đơn hàng của 1 user nào đó
    List<Order> findByUserId(User userId);
}
