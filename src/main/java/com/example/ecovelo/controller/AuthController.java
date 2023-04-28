package com.example.ecovelo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ecovelo.request.AuthRequest;
import com.example.ecovelo.request.RegisterRequest;
import com.example.ecovelo.response.AuthResponse;
import com.example.ecovelo.service.AuthService;
import com.example.ecovelo.service.LogoutService;

import io.jsonwebtoken.io.IOException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {
	
	  private final AuthService service;
	  private final LogoutService logoutService;

	  @PostMapping("/register")
	  public ResponseEntity<AuthResponse> register(
	      @RequestBody RegisterRequest request
	  ) {
	    return ResponseEntity.ok(service.register(request));
	  }
	  @PostMapping("/login")
	  public ResponseEntity<AuthResponse> authenticate(
	      @RequestBody AuthRequest request
	  ) {
	    return ResponseEntity.ok(service.authenticate(request));
	  }

	  @PostMapping("/refresh-token")
	  public ResponseEntity<AuthResponse> refreshToken(
	      HttpServletRequest request,
	      HttpServletResponse response,
	      @RequestBody String refreshToken
	  ) throws IOException {
		return ResponseEntity.ok(service.refreshToken(request, response, refreshToken))  ;
	  }
	  
	  @DeleteMapping("logout")
	  public void logout() {
		  logoutService.logout(null, null, null);
	  }
}
