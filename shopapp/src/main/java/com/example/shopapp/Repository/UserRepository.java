package com.example.shopapp.Repository;

import com.example.shopapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {

    boolean existsByPhoneNumber(String phongNumber);

    Optional<User> findByPhoneNumber(String phoneNumber);
    //Select * from users where phone_number = ?
}
