package com.orderize.backoffice_api.mapper;

import com.orderize.backoffice_api.dto.enterprise.EnterpriseRequestDto;
import com.orderize.backoffice_api.dto.enterprise.EnterpriseResponseDto;
import com.orderize.backoffice_api.model.Enterprise;
import org.springframework.stereotype.Component;

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
