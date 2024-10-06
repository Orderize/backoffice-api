package com.orderize.backoffice_api.repository;

import com.orderize.backoffice_api.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoleRepository extends JpaRepository<UserRole, UserRole.UserRoleId> {
}
