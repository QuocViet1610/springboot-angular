package com.example.shopapp.Service.imp;

import com.example.shopapp.dtos.UserDTO;
import com.example.shopapp.exception.DataNotFoundException;
import com.example.shopapp.model.User;

public interface IUserService {

    User createUser(UserDTO userDTO) throws DataNotFoundException;

    String Login(String numberPhone, String password) throws Exception;
}
