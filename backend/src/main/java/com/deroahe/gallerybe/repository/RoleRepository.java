package com.deroahe.gallerybe.repository;

import com.deroahe.gallerybe.model.ERole;
import com.deroahe.gallerybe.model.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {

    Role findByRoleId(int id);

    Optional<Role> findByRoleName(ERole name);

    List<Role> findAll();

    Boolean existsByRoleName(ERole eRole);

    void deleteByRoleId(int id);

}
