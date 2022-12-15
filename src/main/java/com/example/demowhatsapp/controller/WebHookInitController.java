package com.example.demowhatsapp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class WebHookInitController {

    private final ObjectMapper objectMapper;

    @GetMapping("/")
    public ResponseEntity<String> setupWebhook(
        @RequestParam(name = "hub.mode", required = false) String hubMode,
        @RequestParam(name = "hub.challenge", required = false) String hubChallenge,
        @RequestParam(name = "hub.verify.token", required = false) String hubVerifyToken
    ) {
        return ResponseEntity.ok().body(Objects.requireNonNullElse(hubChallenge, ""));
    }

    @SneakyThrows
    @PostMapping("/")
    public ResponseEntity<Void> defaultHandler(@RequestBody(required = false) Object body) {
        log.info("Received webhook with body: \n" + objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(body));
        return ResponseEntity.ok().build();
    }

}
