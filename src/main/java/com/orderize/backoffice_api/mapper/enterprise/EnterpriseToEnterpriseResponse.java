package com.orderize.backoffice_api.mapper.enterprise;

import org.springframework.stereotype.Component;

import com.orderize.backoffice_api.dto.enterprise.EnterpriseResponseDto;
import com.orderize.backoffice_api.mapper.Mapper;
import com.orderize.backoffice_api.model.Enterprise;
import org.springframework.stereotype.Component;

@Component
public class EnterpriseToEnterpriseResponse implements Mapper<Enterprise, EnterpriseResponseDto> {
    @Override
    public EnterpriseResponseDto map(Enterprise enterprise) {
        return new EnterpriseResponseDto(
                enterprise.getId(),
                enterprise.getName(),
                enterprise.getCnpj()
        );
    }
}
