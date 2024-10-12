package com.orderize.backoffice_api.mapper.enterprise;

import org.springframework.stereotype.Component;

import com.orderize.backoffice_api.dto.enterprise.EnterpriseRequestDto;
import com.orderize.backoffice_api.mapper.Mapper;
import com.orderize.backoffice_api.model.Enterprise;

@Component
public class EnterpriseRequestToEnterprise implements Mapper<EnterpriseRequestDto, Enterprise> {
    @Override
    public Enterprise map(EnterpriseRequestDto enterpriseRequestDto) {
        return new Enterprise(
                enterpriseRequestDto.name(),
                enterpriseRequestDto.cnpj()
        );
    }
}
