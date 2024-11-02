package com.orderize.backoffice_api.util.observer.order_attestation;

import com.orderize.backoffice_api.model.Order;

public interface OrderObserver {

    void onOrderCreated(Order order);

}
