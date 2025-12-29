package com.rps.bookstore.controller;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;


@RestController
@RequestMapping("/health")
public class Health {


    @GetMapping
    public  ResponseEntity<String> checkHealth() {
        return ResponseEntity.ok("OK");
    }

}