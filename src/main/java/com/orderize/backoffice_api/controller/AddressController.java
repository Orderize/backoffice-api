package com.orderize.backoffice_api.controller;

import com.orderize.backoffice_api.dto.address.AddressRequestDto;
import com.orderize.backoffice_api.dto.address.AddressResponseDto;
import com.orderize.backoffice_api.dto.viaCep.ViaCepRequestDto;
import com.orderize.backoffice_api.service.AddressService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/addresses", produces = {"application/json"})
@Tag(name = "/addresses")
public class AddressController {

    private final AddressService service;

    public AddressController(AddressService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Lista todos os endereços", method = "GET")
    public ResponseEntity<List<AddressResponseDto>> getAllAddresses() {
        List<AddressResponseDto> addresses = service.getAllAddresses();

        if (!addresses.isEmpty()) {
            return ResponseEntity.status(200).body(addresses);
        } else {
            return ResponseEntity.status(204).build();
        }
    }

    @GetMapping("/cep")
    @Operation(
            summary = "Busca um endereço utilizando a API ViaCep",
            method = "GET",
            description = "Recebe um body com o cep e o número do endereço, retorna o endereço completo buscando " +
                    "dados na API ViaCep, retorna um id null porque o Endereço buscado não é inserido no banco"
    )
    public ResponseEntity<AddressResponseDto>  getAddressByViaCep(
            @RequestBody @Valid ViaCepRequestDto request
    ) {
        AddressResponseDto response = service.getAddressByViaCep(request);

        return ResponseEntity.status(200).body(response);
    }

    @PostMapping
    @Operation(summary = "Salva um novo endereço", method = "POST")
    public ResponseEntity<AddressResponseDto> postAddAddress(
            @RequestBody @Valid AddressRequestDto address
    ) {
        AddressResponseDto res = service.saveAddress(address);

        return ResponseEntity.status(201).body(res);
    }

    @PutMapping
    @Operation(summary = "Atualiza um endereço", method = "PUT")
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
    @Operation(summary = "Deleta um endereço", method = "DELETE")
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
