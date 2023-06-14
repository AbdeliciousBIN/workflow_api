package com.i2s.worfklow_api_final.service;

import com.i2s.worfklow_api_final.dto.RoleDTO;
import com.i2s.worfklow_api_final.model.Role;
import com.i2s.worfklow_api_final.repository.RoleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RoleService {
    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;

    @Autowired

    public RoleService(RoleRepository roleRepository, ModelMapper modelMapper) {
        this.roleRepository = roleRepository;
        this.modelMapper = modelMapper;
    }

    public List<RoleDTO> getAllRoles() {
//        return userRepository.findAll().stream().map(UserDTO::new).collect(Collectors.toList());
        return roleRepository.findAll().stream().map(role -> modelMapper.map(role, RoleDTO.class)).collect(Collectors.toList());
    }

    public Optional<RoleDTO> getRoleById(long id) {
        return roleRepository.findById(id).map(role -> modelMapper.map(role, RoleDTO.class));
    }

    public RoleDTO createRole(@Valid RoleDTO roleDTO) {
        Role role = modelMapper.map(roleDTO, Role.class);
        Role savedRole = roleRepository.save(role);
        return modelMapper.map(savedRole, RoleDTO.class);

    }

    public void deleteRole(long id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("ROLE with ID " + id + " not found."));
        roleRepository.deleteById(id);
    }

    public RoleDTO getRoleByName(String roleName) {
        Role role = roleRepository.findRoleByName(roleName)
                .orElseThrow(() -> new EntityNotFoundException("Role with name '" + roleName + "' not found."));
        return modelMapper.map(role, RoleDTO.class);
    }


}
