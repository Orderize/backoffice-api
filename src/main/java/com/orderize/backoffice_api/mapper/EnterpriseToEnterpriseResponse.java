package com.orderize.backoffice_api.mapper;

import com.orderize.backoffice_api.dto.enterprise.EnterpriseResponseDto;
import com.orderize.backoffice_api.model.Enterprise;

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
