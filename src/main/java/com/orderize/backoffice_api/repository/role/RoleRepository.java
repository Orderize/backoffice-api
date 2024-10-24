package com.orderize.backoffice_api.repository.role;

import com.orderize.backoffice_api.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {

    boolean existsByName(String name);

}
