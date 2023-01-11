package com.fyllo.interview.controller;

import com.fyllo.interview.service.WebClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ServerWebExchange;

@Controller
@RequestMapping("/v0")
@RequiredArgsConstructor
public class AppController {

    private final WebClientService service;

    @PostMapping("/accept")
    public ResponseEntity accept(ServerWebExchange exchange) {
        service.doAsyncCall();
        return ResponseEntity.ok().build();
    }

}
