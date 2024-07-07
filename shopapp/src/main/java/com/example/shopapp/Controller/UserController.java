package com.example.shopapp.Controller;

import com.example.shopapp.Service.imp.IUserService;
import com.example.shopapp.compoments.JwtTokenUtil;
import com.example.shopapp.dtos.requestDTO.UserLoginDTO;
import com.example.shopapp.model.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import com.example.shopapp.dtos.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/User")
@RequiredArgsConstructor
public class UserController {

    private final IUserService userService;



    @PostMapping("/register")
    public ResponseEntity<?> createUser(@Valid @RequestBody UserDTO userDTO, BindingResult result){
        try{
            if(result.hasErrors()){
                List<String> messageErrors= result.getFieldErrors().stream().map(FieldError::getDefaultMessage).toList();
                String messageError = String.join(",", messageErrors );
                return new ResponseEntity<>(messageError, HttpStatus.BAD_REQUEST);
            }
            if(!userDTO.getPassword().equals(userDTO.getRetypePassword())){
                return new ResponseEntity<>("Password does not match", HttpStatus.OK);

            }
            User user= userService.createUser(userDTO);
            return new ResponseEntity<>("create user is success",HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody UserLoginDTO UserLoginDTO){
        try{

            return new ResponseEntity<>("SecreKey", HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
