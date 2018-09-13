package com.renuka.repository;

import com.renuka.entity.Role;
import com.renuka.entity.RoleType;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RoleRepository extends CrudRepository<Role, Long> {
    Optional<Role> getRoleByRoleType(RoleType roleType);
}
