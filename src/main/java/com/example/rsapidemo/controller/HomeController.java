package com.example.rsapidemo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/")
    public String homepage() {
        return "<p>Redisson</p>"
                + "<a href='/redisson/init'>Load data</a><br/>"
                + "<a href='/redisson/employees'>Data</a><br/>"
                + "<p>RedisJSON</p>"
                + "<a href='/redisjson/init'>Load data</a><br/>"
                + "<a href='/redisjson/employees'>Data</a><br/>";
    }
}
