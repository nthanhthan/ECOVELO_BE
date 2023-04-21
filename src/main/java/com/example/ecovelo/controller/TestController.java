package com.example.ecovelo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class TestController {
	@GetMapping("/xinchao")
	public ResponseEntity<?> index() {
		return ResponseEntity.ok("xin chao");
	}
}
