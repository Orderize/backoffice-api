package com.orderize.backoffice_api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.orderize.backoffice_api.config.ApiUrls;
import com.orderize.backoffice_api.dto.address.AddressRequestDto;
import com.orderize.backoffice_api.dto.address.AddressResponseDto;
import com.orderize.backoffice_api.dto.viaCep.ViaCepApiResponse;
import com.orderize.backoffice_api.dto.viaCep.ViaCepRequestDto;
import com.orderize.backoffice_api.mapper.address.AddressRequestToAddress;
import com.orderize.backoffice_api.mapper.address.AddressToAddressResponse;
import com.orderize.backoffice_api.model.Address;
import com.orderize.backoffice_api.repository.AddressRepository;

@Service
public class AddressService {

    private final AddressRepository repository;
    private final AddressToAddressResponse mapperAddressToAddressResponse;
    private final AddressRequestToAddress mapperAddressRequestToAddress;
    private final RestTemplate restTemplate;

    public AddressService(AddressRepository repository, AddressToAddressResponse mapperAddressToAddressResponse, AddressRequestToAddress mapperAddressRequestToAddress, RestTemplate restTemplate) {
        this.repository = repository;
        this.mapperAddressToAddressResponse = mapperAddressToAddressResponse;
        this.mapperAddressRequestToAddress = mapperAddressRequestToAddress;
        this.restTemplate = restTemplate;
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

    public AddressResponseDto getAddressByViaCep(ViaCepRequestDto request) {
        String urlRequest = ApiUrls.VIA_CEP.getViaCepRequestUrl(request.cep());
        ViaCepApiResponse apiResponse = restTemplate.getForObject(urlRequest, ViaCepApiResponse.class);

        /*
        Má prática sim, mas achei melhor deixar assim do que Criar um novo mapper, aumentar o número de classes
        no projeto e a dificuldade de manutenção sendo que essa conversão só vai acontecer nesse endpoint
        se decidirmos fazer mais algo com a ViaCep tipo um post eu mudo prometo :)
         */
        return new AddressResponseDto(
                null,
                apiResponse.cep(),
                apiResponse.state(),
                request.number(),
                apiResponse.street(),
                apiResponse.city(),
                apiResponse.neighborhood()
        );
    }
}
