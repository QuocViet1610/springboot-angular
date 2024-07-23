package com.example.shopapp.Controller;

import com.example.shopapp.Respone.LoginResponse;
import com.example.shopapp.Respone.RegisterResponse;
import com.example.shopapp.Service.imp.IUserService;
import com.example.shopapp.Utils.MessageKeys;
import com.example.shopapp.compoments.JwtTokenUtil;
import com.example.shopapp.compoments.LocalizationUtils;
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
import java.util.Map;

@RestController
@RequestMapping("${api.prefix}/User")
@RequiredArgsConstructor
public class UserController {

    private final IUserService userService;

    private final LocalizationUtils localizationUtils;


    @PostMapping("/register")
    public ResponseEntity<?> createUser(@Valid @RequestBody UserDTO userDTO, BindingResult result){
        RegisterResponse registerResponse = new RegisterResponse();
        try{
            if(result.hasErrors()){
                List<String> messageErrors= result.getFieldErrors().stream().map(FieldError::getDefaultMessage).toList();
                String messageError = String.join(",", messageErrors );
                return new ResponseEntity<>(messageError, HttpStatus.BAD_REQUEST);
            }
            if(!userDTO.getPassword().equals(userDTO.getRetypePassword())){
                registerResponse.setMessage(localizationUtils.getLocalizedMessage(MessageKeys.PASSWORD_NOT_MATCH));
                return ResponseEntity.badRequest().body(registerResponse);

            }
            User user= userService.createUser(userDTO);
            registerResponse.setMessage(localizationUtils.getLocalizedMessage(MessageKeys.REGISTER_SUCCESSFULLY));
            registerResponse.setUser(user);
            return ResponseEntity.ok(registerResponse);

        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody UserLoginDTO userLoginDTO){
        try{
            String token = userService.Login(userLoginDTO.getPhoneNumber(), userLoginDTO.getPassword());
            return ResponseEntity.ok(LoginResponse.builder()
                    .message(localizationUtils.getLocalizedMessage(MessageKeys.LOGIN_SUCCESSFULLY))
                    .token(token)
                    .build());
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
