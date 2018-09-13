package com.renuka.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/greeting")
public class Greeting {
    @GetMapping("/{name}")
    public String greet(@PathVariable("name") String name) {
        return "Hello " + name;
    }

    @GetMapping("/{name}/protected")
    public String greetProtected(@PathVariable("name") String name) {
        return "Hello " + name + ", we have a secret for you. That is 4445-998 රේණුක!";
    }
}
