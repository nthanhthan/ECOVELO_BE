package com.example.ecovelo.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.ecovelo.request.AuthRequest;
import com.example.ecovelo.request.RegisterRequest;
import com.example.ecovelo.request.TransactionRequest;
import com.example.ecovelo.request.VerifyAccountReq;
import com.example.ecovelo.response.AuthResponse;
import com.example.ecovelo.response.TransactionResp;
import com.example.ecovelo.response.UserAdminResponse;
import com.example.ecovelo.response.UserResponse;
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
	public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
		return ResponseEntity.ok(service.register(request));
	}

	@PostMapping("/login")
	public ResponseEntity<AuthResponse> authenticate(@RequestBody AuthRequest request) {
		return ResponseEntity.ok(service.authenticate(request));
	}

	@PostMapping("/refresh-token")
	public ResponseEntity<AuthResponse> refreshToken(HttpServletRequest request, HttpServletResponse response,
			@RequestBody String refreshToken) throws IOException {
		return ResponseEntity.ok(service.refreshToken(request, response, refreshToken));
	}

	@DeleteMapping("logout")
	public void logout() {
		logoutService.logout(null, null, null);
	}

	@GetMapping("/checkPoint")
	public ResponseEntity<Boolean> checkPontUser(@RequestHeader(name = "Authorization") String token) {
		final String jwt = token.substring(7);
		return ResponseEntity.ok(service.checkPointUser(jwt));
	}

	@GetMapping("/getUser")	
	public ResponseEntity<UserResponse> getPointUser(@RequestHeader(name = "Authorization") String token) {
		final String jwt = token.substring(7);
		return ResponseEntity.ok(service.getUser(jwt,null));
	}
	
	@PostMapping("/addMoney")
	public ResponseEntity<TransactionResp> register(@RequestHeader(name = "Authorization") String token,
			@RequestBody TransactionRequest request) {
		final String jwt = token.substring(7);
		return ResponseEntity.ok(service.addPointUser(jwt, request));
	}
	
	@PostMapping("/verify")
	public ResponseEntity<Boolean> verifyAccount(
			@RequestBody VerifyAccountReq verifyAcc) {
		return ResponseEntity.ok(service.verifyAccount(verifyAcc));
	}
	@GetMapping("/getAllUser")
	public ResponseEntity<List<UserAdminResponse>> getAllUser(@RequestHeader(name = "Authorization") String token) {
		final String jwt = token.substring(7);
		return ResponseEntity.ok(service.getListUser(jwt));
	}
	
	@GetMapping("/confirm-authencation/{idUser}")
	public ResponseEntity<?> fallBicycle(@PathVariable("idUser") int  idUser) {
		service.confirmAuthencation(idUser);
		return ResponseEntity.ok().build();
	}
	@GetMapping("/getDetailUser/{idUser}")
	public ResponseEntity<UserAdminResponse> getDetailUser(@PathVariable("idUser") int  idUser) {
		return ResponseEntity.ok(service.getDetaiUser(idUser));
	}

}
