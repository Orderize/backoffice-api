package com.orderize.backoffice_api.util.observer;

import com.orderize.backoffice_api.service.OrderService;
import com.orderize.backoffice_api.service.attestation.AttestationService;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

@Component
public class ObserversInitializer {

    public ObserversInitializer(OrderService orderService, AttestationService attestationService) {
        orderService.addObserver(attestationService);
    }

}
