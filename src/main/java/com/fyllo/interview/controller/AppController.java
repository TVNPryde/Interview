package com.fyllo.interview.controller;

import com.fyllo.interview.service.WebClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;

@RestController
@RequestMapping("/v0")
@RequiredArgsConstructor
public class AppController {

    private final WebClientService service;

    @GetMapping("/accept")
    public ResponseEntity get(ServerWebExchange exchange) {
        service.doAsyncCall();
        return ResponseEntity.ok().build();
    }

    @PostMapping("/accept")
    public ResponseEntity accept(ServerWebExchange exchange) {
        service.doAsyncCall();
        return ResponseEntity.ok().build();
    }

}
