package com.orderize.backoffice_api.service;

import com.orderize.backoffice_api.dto.address.AddressRequestDto;
import com.orderize.backoffice_api.dto.address.AddressResponseDto;
import com.orderize.backoffice_api.mapper.AddressRequestToAddress;
import com.orderize.backoffice_api.mapper.AddressToAddressResponse;
import com.orderize.backoffice_api.model.Address;
import com.orderize.backoffice_api.repository.AddressRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AddressService {

    private final AddressRepository repository;
    private final AddressToAddressResponse mapperAddressToAddressResponse;
    private final AddressRequestToAddress mapperAddressRequestToAddress;

    public AddressService(AddressRepository repository, AddressToAddressResponse mapperAddressToAddressResponse, AddressRequestToAddress mapperAddressRequestToAddress) {
        this.repository = repository;
        this.mapperAddressToAddressResponse = mapperAddressToAddressResponse;
        this.mapperAddressRequestToAddress = mapperAddressRequestToAddress;
    }

    public List<AddressResponseDto> getAllAddresses() {
        List<Address> repAddresses = repository.findAll();
        return repAddresses.stream().map(mapperAddressToAddressResponse::map).toList();
    }

    public AddressResponseDto saveAddress(AddressRequestDto address) {
        Optional<Address> savedAddress = Optional.of(repository.save(mapperAddressRequestToAddress.map(address)));

        if (savedAddress.isPresent()) {
            return mapperAddressToAddressResponse.map(savedAddress.get());
        } else {
            throw new RuntimeException("Error creating Address");
        }
    }

    public AddressResponseDto putAddress(AddressRequestDto reqAddress) {
        Optional<Address> ad = repository.findById(reqAddress.id());

        if (ad.isPresent()) {
            Address saving = mapperAddressRequestToAddress.map(reqAddress);
            saving.setId(ad.get().getId());
            return mapperAddressToAddressResponse.map(repository.save(saving));
        } else {
            return null;
        }
    }

    public Boolean deleteAddress(Long id) {
        Optional<Address> ads = repository.findById(id);

        if (ads.isPresent()) {
            repository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}
