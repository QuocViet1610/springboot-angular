package com.example.shopapp.Controller;

import com.example.shopapp.Respone.RoleResponse;
import com.example.shopapp.Service.RoleService;
import com.example.shopapp.Service.imp.IRoleService;
import com.example.shopapp.model.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("${api.prefix}/roles")
@RequiredArgsConstructor
public class RoleController {

    private final IRoleService roleService;

    @GetMapping("")
    public ResponseEntity<?> getAllRole(){
        List<Role> roleList = roleService.getAllRole();

        List<RoleResponse> roleDTOs = roleList.stream()
                .map(role -> new RoleResponse(role.getId(), role.getName())) // Chuyển đổi User thành UserDTO
                .collect(Collectors.toList());
        return  ResponseEntity.ok(roleDTOs);
    }
}
