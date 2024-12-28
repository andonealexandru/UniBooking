package com.unibooking.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

    @GetMapping("/hi")
    private String helloWorld() {
        return "Hello World!";
    }

}
