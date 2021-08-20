package com.deroahe.gallerybe.service.impl;

import com.deroahe.gallerybe.model.ERole;
import com.deroahe.gallerybe.model.Role;
import com.deroahe.gallerybe.repository.RoleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleServiceImpl {

    RoleRepository roleRepository;
    Logger logger = LoggerFactory.getLogger(RoleServiceImpl.class);

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role findRoleById(int id) {
        return roleRepository.findByRoleId(id);
    }

    public Optional<Role> findRoleByName(String name) {
        ERole eRole = ERole.valueOf(name);
        Optional<Role> role = roleRepository.findByRoleName(eRole);
        if (role == null) {
            logger.info("Role name not present in DB");
        }
        return role;
    }

    public List<Role> findAllRoles() {
        return roleRepository.findAll();
    }

    public Role saveRole(Role role) {
        if (!roleRepository.existsByRoleName(role.getRoleName())) {
            return roleRepository.save(role);
        }
        logger.info("Role name already present in DB");
        return null;
    }

    public void saveAllRoles(List<Role> roles) {
        for (Role role : roles) {
            saveRole(role);
        }
    }
}
