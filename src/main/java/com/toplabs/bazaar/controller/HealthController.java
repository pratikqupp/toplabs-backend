package com.toplabs.bazaar.controller;

import org.springframework.web.bind.annotation.GetMapping;

public class HealthController {
    @GetMapping("/")
    public String healthCheck() {
        return "Application is running ";
    }
}
