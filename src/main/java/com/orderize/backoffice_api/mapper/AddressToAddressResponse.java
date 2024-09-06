package com.orderize.backoffice_api.mapper;

import com.orderize.backoffice_api.dto.address.AddressResponseDto;
import com.orderize.backoffice_api.model.Address;
import org.springframework.stereotype.Component;

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
