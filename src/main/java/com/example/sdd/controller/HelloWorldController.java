package com.example.sdd.controller;

import com.example.sdd.MessageClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {

    private final MessageClient messageClient;

    @Value("${greeting}")
    private String currentGreeting;

    public HelloWorldController(MessageClient messageClient) {
        this.messageClient = messageClient;
    }

    @GetMapping("/hello-worlds/{name}")
    public String getHelloWorld(@PathVariable String name) {
        return "Hello from Balancer. My message to You is " + currentGreeting + ". Embedded message is^ "
                + messageClient.getRestTemplateGreeting(name);
    }
}