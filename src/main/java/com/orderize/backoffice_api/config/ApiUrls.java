package com.orderize.backoffice_api.config;

public enum ApiUrls {
    VIA_CEP("https://viacep.com.br/ws/");

    private final String baseUrl;

    ApiUrls(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getViaCepRequestUrl(String cep) {
        return VIA_CEP.baseUrl + cep + "/json";
    }
}
