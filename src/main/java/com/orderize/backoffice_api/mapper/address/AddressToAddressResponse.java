package com.orderize.backoffice_api.mapper.address;

import org.springframework.stereotype.Component;

import com.orderize.backoffice_api.dto.address.AddressResponseDto;
import com.orderize.backoffice_api.mapper.Mapper;
import com.orderize.backoffice_api.model.Address;

@Component
public class AddressToAddressResponse implements Mapper<Address, AddressResponseDto> {

    @Override
    public AddressResponseDto map(Address address) {
        return new AddressResponseDto(
                address.getId(),
                address.getCep(),
                address.getState(),
                address.getNumber(),
                address.getStreet(),
                address.getCity(),
                address.getNeighborhood()
        );
    }
}
