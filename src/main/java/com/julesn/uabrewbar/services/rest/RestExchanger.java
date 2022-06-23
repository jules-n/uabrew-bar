package com.julesn.uabrewbar.services.rest;

import org.bson.types.ObjectId;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class RestExchanger {

    //private static final String URL_LOGS = "http://localhost:8080/logs";
    private final RestTemplate restTemplate;

    public RestExchanger(){
        restTemplate = new RestTemplate();
    }

    public <T, O> T fetch(String host, String port, Class<T> requestClass, @Nullable List<String> pathParams, O request) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("http://");
        stringBuilder.append(host);
        stringBuilder.append(":");
        stringBuilder.append(port);
        if(pathParams != null && !pathParams.isEmpty()) {
            pathParams.forEach(param -> {
                stringBuilder.append("/");
                stringBuilder.append(param);
            });
        }
        return restTemplate.getForObject(stringBuilder.toString(), requestClass, request);
    }
}
