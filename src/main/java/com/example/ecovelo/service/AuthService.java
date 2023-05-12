package com.example.ecovelo.service;
import java.util.Optional;

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
		var userModel = UserModel.builder().nameUser(request.getNameUser()).email(request.getEmail()).verify(false)
				.build();
		userRepository.save(userModel);
		return userModel;
	}
	public UserModel getUserByToken(String token) {
		final String phoneNumber;
		phoneNumber = jwtService.extractUsername(token);
		var accountModel = accountRepository.findByPhoneNumber(phoneNumber).orElseThrow();
		Optional<UserModel> user = userRepository.findById(accountModel.getIdUser());
		if(user.isPresent()) {
			return user.get();
		}
		return null;
		
	}
	
	public boolean checkPointUser(String token) {
		UserModel user= getUserByToken(token);
		if(user!=null) {
			if(user.getMainPoint()+user.getProPoint()>=5000) {
				return true;
			}
		}
		return false;
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
		Authentication authentication = new UsernamePasswordAuthenticationToken(request.getPhoneNumber(),
				request.getPassword());
		try {
			authenticationManager.authenticate(authentication);
		} catch (BadCredentialsException ex) {
			throw new UnAuthorizeException("Invalid UserName or Password");
		}
		
		var accountModel = accountRepository.findByPhoneNumber(request.getPhoneNumber()).orElseThrow();
		var jwtToken = jwtService.generateToken(accountModel);
		var refreshToken = jwtService.generateRefreshToken(accountModel);
		saveUserToken(accountModel, jwtToken);
//	    var expired = jwtService.extractExpiration(jwtToken).getTime();
		UserResponse userResponse = new UserResponse();
		if (accountModel != null) {
			if (accountModel.getUserModel() != null) {
				userResponse.setUserId(accountModel.getIdUser());
				userResponse.setPhoneNumber(accountModel.getPhoneNumber());
				userResponse.setEmail(accountModel.getUserModel().getEmail());
				userResponse.setMainPoint(accountModel.getUserModel().getMainPoint());
				userResponse.setNameUser(accountModel.getUserModel().getNameUser());
				userResponse.setVerify(accountModel.getUserModel().isVerify());
				userResponse.setProPoint(accountModel.getUserModel().getProPoint());
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

//	private void revokeAllUserTokens(AccountModel accountModel) {
//		var validUserTokens = refreshTokenRepository.findAllValidTokenByUser(accountModel.getUsername());
//		if (validUserTokens.isEmpty())
//			return;
//		validUserTokens.forEach(token -> {
//			token.setExpired(true);
//			token.setRevoked(true);
//		});
//		refreshTokenRepository.saveAll(validUserTokens);
//	}

	public AuthResponse refreshToken(HttpServletRequest request, HttpServletResponse response, String refreshToken)
			throws IOException {

		final String phoneNumber;
		phoneNumber = jwtService.extractUsername(refreshToken);
		if (phoneNumber != null) {
			var user = this.accountRepository.findByPhoneNumber(phoneNumber).orElseThrow();
			if (jwtService.isTokenValid(refreshToken, user)) {
				var accessToken = jwtService.generateToken(user);
				var newRefreshToken = jwtService.generateRefreshToken(user);
				saveUserToken(user, accessToken);
				var authResponse = AuthResponse.builder().accessToken(accessToken).refreshToken(newRefreshToken)
						.expired(jwtService.extractExpiration(accessToken).getTime()).build();
				return authResponse;

			}else {
				return null;
			}
		}
		return null;
	}
	public UserModel getPointUser(String token) {
		UserModel user= getUserByToken(token);
		if(user!=null) {
			var userModel = UserModel.builder().id(user.getId())
					.nameUser(user.getNameUser())
					.email(user.getEmail())
					.mainPoint(user.getMainPoint())
					.proPoint(user.getProPoint())
					.verify(user.isVerify())
					.build();
			return userModel;
		}
		return null;
		
	}
}
