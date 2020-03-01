package com.pro.it.sdms.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/user/hello")
    public String hello() {
        return "hello, User";
    }

    @GetMapping("/admin/hello")
    public String helloAdmin() {
        return "hello, Admin";
    }
}
