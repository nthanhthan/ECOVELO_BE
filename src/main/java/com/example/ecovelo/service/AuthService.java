package com.example.ecovelo.service;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.ecovelo.entity.AccountModel;
import com.example.ecovelo.entity.RefreshToken;
import com.example.ecovelo.entity.UserModel;
import com.example.ecovelo.enums.Role;
import com.example.ecovelo.exception.UnAuthorizeException;
import com.example.ecovelo.jwt.JwtTokenProvider;
import com.example.ecovelo.repository.AccountModelRepository;
import com.example.ecovelo.repository.RefreshTokenRepository;
import com.example.ecovelo.repository.UserModelRepository;
import com.example.ecovelo.request.AuthRequest;
import com.example.ecovelo.request.RegisterRequest;
import com.example.ecovelo.response.AuthResponse;
import com.example.ecovelo.response.UserResponse;
import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.io.IOException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
	private final AccountModelRepository accountRepository;
	private final RefreshTokenRepository refreshTokenRepository;
	private final UserModelRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtTokenProvider jwtService;
	private final AuthenticationManager authenticationManager;

	private UserModel saveUserModel(RegisterRequest request) {
		var userModel = UserModel.builder()
				.nameUser(request.getNameUser())
				.email(request.getEmail())
				.verify(false)
				.build();
		userRepository.save(userModel);
		return userModel;
	}

	public AuthResponse register(RegisterRequest request) {
		var userModel = saveUserModel(request);
		var accountModel = AccountModel.builder().phoneNumber(request.getPhoneNumber())
				.password(passwordEncoder.encode(request.getPassword())).role(Role.USER).userModel(userModel).build();
		var savedAccountModel = accountRepository.save(accountModel);
		var jwtToken = jwtService.generateToken(accountModel);
		var refreshToken = jwtService.generateRefreshToken(accountModel);
		saveUserToken(savedAccountModel, jwtToken);
		return AuthResponse.builder().accessToken(jwtToken).refreshToken(refreshToken).build();
	}

	public AuthResponse authenticate(AuthRequest request) {
		Authentication authentication = new UsernamePasswordAuthenticationToken(request.getPhoneNumber(), request.getPassword());
		try {
			authenticationManager.authenticate(authentication);
		}catch(BadCredentialsException ex) {
			throw new UnAuthorizeException("Invalid UserName or Password");
		}
		var accountModel = accountRepository.findByPhoneNumber(request.getPhoneNumber()).orElseThrow();
		var jwtToken = jwtService.generateToken(accountModel);
		var refreshToken = jwtService.generateRefreshToken(accountModel);
		revokeAllUserTokens(accountModel);
		saveUserToken(accountModel, jwtToken);
//	    var expired = jwtService.extractExpiration(jwtToken).getTime();
		UserResponse userResponse = new UserResponse();
		if(accountModel != null) {
			if(accountModel.getUserModel() != null) {
				userResponse.setPhoneNumber(accountModel.getPhoneNumber());
				userResponse.setEmail(accountModel.getUserModel().getEmail());
				userResponse.setMoney(accountModel.getUserModel().getMoney());
				userResponse.setNameUser(accountModel.getUserModel().getNameUser());
				userResponse.setVerify(accountModel.getUserModel().isVerify());
			}
		}
		return AuthResponse.builder().accessToken(jwtToken).refreshToken(refreshToken).userResponse(userResponse)
				.expired(jwtService.extractExpiration(jwtToken).getTime()).build();
	}

	private void saveUserToken(AccountModel accountModel, String jwtToken) {
		var token = RefreshToken.builder().accountModel(accountModel).token(jwtToken).expired(false).revoked(false)
				.build();
		refreshTokenRepository.save(token);
	}

	private void revokeAllUserTokens(AccountModel accountModel) {
		var validUserTokens = refreshTokenRepository.findAllValidTokenByUser(accountModel.getUsername());
		if (validUserTokens.isEmpty())
			return;
		validUserTokens.forEach(token -> {
			token.setExpired(true);
			token.setRevoked(true);
		});
		refreshTokenRepository.saveAll(validUserTokens);
	}

	public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
		final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
		final String refreshToken;
		final String phoneNumber;
		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
			return;
		}
		refreshToken = authHeader.substring(7);
		phoneNumber = jwtService.extractUsername(refreshToken);
		if (phoneNumber != null) {
			var user = this.accountRepository.findByPhoneNumber(phoneNumber).orElseThrow();
			if (jwtService.isTokenValid(refreshToken, user)) {
				var accessToken = jwtService.generateToken(user);
				revokeAllUserTokens(user);
				saveUserToken(user, accessToken);
				var authResponse = AuthResponse.builder().accessToken(accessToken).refreshToken(refreshToken).build();
				try {
					new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
				} catch (StreamWriteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (DatabindException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (java.io.IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}
