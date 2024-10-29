package com.orderize.backoffice_api.util.observer.order_attestation;

import com.orderize.backoffice_api.model.Order;

public interface OrderObserverSubject {

    void addObserver(OrderObserver observer);

    void removeObserver(OrderObserver observer);

    void notifyObservers(Order order);

}
