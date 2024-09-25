package com.orderize.backoffice_api.repository;

import com.orderize.backoffice_api.model.Enterprise;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnterpriseRepository extends JpaRepository<Enterprise, Long> {

    boolean existsByName(String name);

    boolean existsByCnpj(String cnpj);

}
