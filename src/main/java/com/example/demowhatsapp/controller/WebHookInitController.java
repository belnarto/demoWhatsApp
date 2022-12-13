package com.example.demowhatsapp.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebHookInitController {

    @GetMapping("init-webhook")
    public ResponseEntity<Void> setupWebhook(
        @RequestParam(name = "hub_mode", required = false) String hubMode,
        @RequestParam(name = "hub_challenge", required = false) String hubChallenge,
        @RequestParam(name = "hub_verify_token", required = false) String hubVerifyToken) {

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Baeldung-Example-Header",
            "Value-ResponseEntityBuilderWithHttpHeaders");

        return ResponseEntity.ok()
            .headers(responseHeaders)
            .build();
    }
}
