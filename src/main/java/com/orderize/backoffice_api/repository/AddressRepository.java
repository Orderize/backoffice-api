package com.orderize.backoffice_api.repository;

import com.orderize.backoffice_api.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
