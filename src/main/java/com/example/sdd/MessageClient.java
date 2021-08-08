package com.example.sdd;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

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

        List<ServiceInstance> instanceList = discoveryClient.getInstances("ex-014-spring-cloud-client");

        if(instanceList==null || instanceList.size()==0)
            throw new IllegalStateException("!!! ex-014-spring-cloud-client service unavailable");

        System.out.print("\nWE HAVE: ");
        for(ServiceInstance instance : instanceList){
            System.out.print(instance.getUri()+", ");
        }

        int i = ThreadLocalRandom.current().nextInt(0, instanceList.size());
        System.out.println("\nWE HAVE CHOSEN #" + i);
        ServiceInstance instance = instanceList.get(i);

        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder
                .fromHttpUrl(instance.getUri().toString() + "/hello-worlds/" + name);

        System.out.println("WE REQUEST: " + uriComponentsBuilder.toUriString());
        return restTemplate.getForObject(uriComponentsBuilder.toUriString(), String.class);
    }
}
