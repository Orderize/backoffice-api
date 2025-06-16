package com.orderize.backoffice_api.enumeration;

public enum OrderStatusEnumeration {
    EM_PREPARO("EM PREPARO"),
    DISPONIVEL("DISPONIVEL"),
    PENDENTE("PENDENTE"); 

    private String value;

    OrderStatusEnumeration(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
