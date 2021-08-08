package com.example.sdd;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class MessageClient {

    private final RestTemplate restTemplate;
    private final DiscoveryClient discoveryClient;

    MessageClient(RestTemplate restTemplate, DiscoveryClient discoveryClient) {
        this.restTemplate = restTemplate;
        this.discoveryClient = discoveryClient;
    }

    public String getRestTemplateGreeting(String name) {
        return restTemplate.getForObject("http://ex-014-spring-cloud-client/hello-worlds/" + name, String.class);
    }

    public String getDiscoveryClientGreeting(String name) {

        ServiceInstance instance = discoveryClient.getInstances("ex-014-spring-cloud-client")
                .stream().findAny()
                .orElseThrow(() -> new IllegalStateException("!!! ex-014-spring-cloud-client service unavailable"));

        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder
                .fromHttpUrl(instance.getUri().toString() + "/hello-worlds/" + name);
        return restTemplate.getForObject("http://ex-014-spring-cloud-client/hello-worlds/" + name, String.class);
    }
}
