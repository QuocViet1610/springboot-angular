package com.example.shopapp.Service;

import com.example.shopapp.Repository.RoleRepository;
import com.example.shopapp.Repository.UserRepository;
import com.example.shopapp.Service.imp.IUserService;
import com.example.shopapp.compoments.JwtTokenUtil;
import com.example.shopapp.dtos.UserDTO;
import com.example.shopapp.exception.DataNotFoundException;
import com.example.shopapp.model.Role;
import com.example.shopapp.model.User;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements IUserService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    private final JwtTokenUtil jwtTokenUtil;


    public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtTokenUtil jwtTokenUtil) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @Override
    public User createUser(UserDTO userDTO) throws DataNotFoundException {
        String phone = userDTO.getPhoneNumber();
        if(userRepository.existsByPhoneNumber(phone)){
            throw new DataIntegrityViolationException("phone number already exist");
        }
        User newUser = User.builder()
                        .fullname(userDTO.getFullName())
                        .phoneNumber(userDTO.getPhoneNumber())
                .password(userDTO.getPassword())
                .address(userDTO.getAddress())
                .dateOfBirth(userDTO.getDateOfBirth())
                .googleAccountId(userDTO.getGoogleAccountId())
                .facebookAccountId(userDTO.getFacebookAccountId())
        .build();

        Role role =roleRepository.findById(userDTO.getRoleId())
                .orElseThrow(() -> new DataNotFoundException("Role not exist"));
        newUser.setRoleId(role);
        //kiểm tra nếu có account ID, không yêu cầu password
        if(userDTO.getGoogleAccountId() == 0 && userDTO.getFacebookAccountId() == 0){
            String password = userDTO.getPassword();
            String encodedPassword = passwordEncoder.encode(password);
            newUser.setPassword(encodedPassword);

        }
        return userRepository.save(newUser);
    }

    @Override
    public String Login(String numberPhone, String password) throws Exception {
        Optional<User> existingUser = userRepository.findByPhoneNumber(numberPhone);
        if(!existingUser.isPresent()) {
            throw new DataNotFoundException("Invalid phone number / password");
        }

        if (existingUser.get().getFacebookAccountId() == 0
                && existingUser.get().getGoogleAccountId() == 0) {
            // ham matches kiem tra mat khau ma hoa cung nhau
            if(!passwordEncoder.matches(password, existingUser.get().getPassword())) {
                throw new BadCredentialsException("Wrong phone number or password");
            }
        }

        //convert request to object authentication
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                numberPhone, password,
                existingUser.get().getAuthorities()
        );
        authenticationManager.authenticate(authenticationToken);


        return jwtTokenUtil.generateToken(existingUser.get());
    }
}
