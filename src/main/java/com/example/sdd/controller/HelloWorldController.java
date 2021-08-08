package com.example.sdd.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {

    @Value("${greeting}")
    private String currentGreeting;

    @GetMapping("/hello-worlds/{name}")
    public String getHelloWorld(@PathVariable String name) {
        return "Hello World " + name + ". My message to You is " + currentGreeting;
    }
}