package com.orderize.backoffice_api.controller;

import com.orderize.backoffice_api.dto.address.AddressRequestDto;
import com.orderize.backoffice_api.dto.address.AddressResponseDto;
import com.orderize.backoffice_api.service.AddressService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/addresses")
public class AddressController {

    private final AddressService service;

    public AddressController(AddressService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<AddressResponseDto>> getAllAddresses() {
        List<AddressResponseDto> addresses = service.getAllAddresses();

        if (!addresses.isEmpty()) {
            return ResponseEntity.status(200).body(addresses);
        } else {
            return ResponseEntity.status(204).build();
        }
    }

    @PostMapping
    public ResponseEntity<AddressResponseDto> postAddAddress(
            @RequestBody @Valid AddressRequestDto address
    ) {
        AddressResponseDto res = service.saveAddress(address);

        return ResponseEntity.status(201).body(res);
    }

    @PutMapping
    public ResponseEntity<AddressResponseDto> putAddress(
            @RequestBody @Valid AddressRequestDto reqAddress
    ) {
        AddressResponseDto res = service.putAddress(reqAddress);

        if (res != null) {
            return ResponseEntity.status(200).body(res);
        } else {
            return ResponseEntity.status(404).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteAddress(
            @PathVariable Long id
    ) {
        Boolean canBeDeleted = service.deleteAddress(id);

        if (canBeDeleted) {
            return ResponseEntity.status(204).build();
        } else {
            return ResponseEntity.status(404).build();
        }
    }

}
