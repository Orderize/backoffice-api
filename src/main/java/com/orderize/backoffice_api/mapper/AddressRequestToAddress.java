package com.orderize.backoffice_api.mapper;

import com.orderize.backoffice_api.dto.address.AddressRequestDto;
import com.orderize.backoffice_api.model.Address;
import org.springframework.stereotype.Component;

@Component
public class AddressRequestToAddress implements Mapper<AddressRequestDto, Address> {

    @Override
    public Address map(AddressRequestDto addressRequestDto) {
        return new Address(
                addressRequestDto.cep(),
                addressRequestDto.state(),
                addressRequestDto.number(),
                addressRequestDto.street(),
                addressRequestDto.city(),
                addressRequestDto.neighborhood()
        );
    }

}
