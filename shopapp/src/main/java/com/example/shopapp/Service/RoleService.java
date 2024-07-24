package com.example.shopapp.Service;

import com.example.shopapp.Repository.RoleRepository;
import com.example.shopapp.Service.imp.IRoleService;
import com.example.shopapp.model.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleService implements IRoleService {

    private final RoleRepository roleRepository;

    @Override
    public List<Role> getAllRole() {
        return roleRepository.findAll();
    }
}
