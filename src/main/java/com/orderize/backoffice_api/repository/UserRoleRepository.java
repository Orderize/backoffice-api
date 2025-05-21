package com.orderize.backoffice_api.repository;

import com.orderize.backoffice_api.model.UserRole;
import com.orderize.backoffice_api.model.UserRoleId;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoleRepository extends JpaRepository<UserRole, UserRoleId> {
}
