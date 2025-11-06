package com.TopLabBazaar2909.TLBnew2909.controller;

import org.springframework.web.bind.annotation.GetMapping;

public class HealthController {
    @GetMapping("/")
    public String healthCheck() {
        return "Application is running ";
    }
}
