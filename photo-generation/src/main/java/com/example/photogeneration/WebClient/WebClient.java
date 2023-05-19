package com.example.photogeneration.WebClient;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Map;

@Component
@Slf4j
public class WebClient {

    public <T> T postForObject(URI uri, Class<T> clazz, Map<String, String> headers, Map<String, String> body) {
        HttpHeaders httpHeaders = createHeaders(headers);
        MultiValueMap httpBody = createBody(body);
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<Object> request = new HttpEntity<>(httpBody, httpHeaders);
        return restTemplate.exchange(uri, HttpMethod.POST, request, clazz).getBody();
    }

    protected HttpHeaders createHeaders(Map<String, String> headers) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.forEach((key, value) -> {
            httpHeaders.set(key, value);
        });
        return httpHeaders;
    }

    private MultiValueMap createBody(Map<String, String> body) {
        MultipartBodyBuilder multipartBodyBuilder = new MultipartBodyBuilder();
        body.forEach((key, value) -> {
            multipartBodyBuilder.part(key, value);
        });
        return multipartBodyBuilder.build();
    }
}
