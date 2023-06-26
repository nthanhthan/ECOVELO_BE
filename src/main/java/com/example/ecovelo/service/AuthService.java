package com.example.ecovelo.service;
import java.util.Optional;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.ecovelo.entity.AccountModel;
import com.example.ecovelo.entity.PersonalLegalModel;
import com.example.ecovelo.entity.PersonalLegalUserModel;
import com.example.ecovelo.entity.RefreshToken;
import com.example.ecovelo.entity.UserModel;
import com.example.ecovelo.enums.Role;
import com.example.ecovelo.exception.UnAuthorizeException;
import com.example.ecovelo.jwt.JwtTokenProvider;
import com.example.ecovelo.repository.AccountModelRepository;
import com.example.ecovelo.repository.LegalRepository;
import com.example.ecovelo.repository.RefreshTokenRepository;
import com.example.ecovelo.repository.UserModelRepository;
import com.example.ecovelo.repository.VerifyAccountRepository;
import com.example.ecovelo.request.AuthRequest;
import com.example.ecovelo.request.RegisterRequest;
import com.example.ecovelo.request.TransactionRequest;
import com.example.ecovelo.request.VerifyAccountReq;
import com.example.ecovelo.response.AuthResponse;
import com.example.ecovelo.response.TransactionResp;
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
	private final TransactionHistoryService transactionService;
	private final VerifyAccountRepository verifyRepo;
	private final LegalRepository legalRepo;

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
		if (user.isPresent()) {
			return user.get();
		}
		return null;
	}

	public boolean checkPointUser(String token) {
		UserModel user = getUserByToken(token);
		if (user != null) {
			if (user.getMainPoint() + user.getProPoint() >= 5000) {
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
				.admin(accountModel.getRole().equals(Role.ADMIN))
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

			} else {
				return null;
			}
		}
		return null;
	}

	public UserResponse getUser(String token, UserModel userModel) {
		UserModel user;
		if (userModel != null) {
			user = userModel;
		} else {
			user = getUserByToken(token);
		}
		if (user != null) {
	     	boolean checkLegal=verifyRepo.existsByUserModelLegal(user);
			var userResp = UserResponse.builder()
					.userId(user.getId())
					.nameUser(user.getNameUser())
					.email(user.getEmail())
					.mainPoint(user.getMainPoint())
					.proPoint(user.getProPoint())
					.verify(user.isVerify())
					.isProccessing(checkLegal)
					.build();
			return userResp;
			
		}
		return null;

	}

	public void payRentBicycle(float point, UserModel user) {
		float mainPoint = user.getMainPoint();
		float proPoint = user.getProPoint();
		float result = proPoint - point;
		if (result < 0) {
			proPoint = 0;
			result = -result;
			mainPoint = mainPoint - result;
		} else {
			proPoint = result;
		}
		UserModel updateMoneyUser = UserModel.builder().id(user.getId()).email(user.getEmail())
				.nameUser(user.getNameUser()).mainPoint(mainPoint).proPoint(proPoint)
				.accountModel(user.getAccountModel()).verify(user.isVerify()).build();
		userRepository.save(updateMoneyUser);
	}


	public TransactionResp addPointUser(String token, TransactionRequest transactionReq) {
		UserModel user = getUserByToken(token);
		if (user != null) {
			var updateUser = UserModel.builder().accountModel(user.getAccountModel()).id(user.getId())
					.email(user.getEmail()).mainPoint(user.getMainPoint() + transactionReq.getMoney())
					.proPoint(user.getProPoint()).verify(user.isVerify()).nameUser(user.getNameUser())
					.personalLegalUserModel(user.getPersonalLegalUserModel())
					.listReportProblems(user.getListReportProblems()).rentBicycleModels(user.getRentBicycleModels())
					.transactionHistories(user.getTransactionHistories()).userVouchers(user.getUserVouchers()).build();
			var userUpdate = userRepository.save(updateUser);
			TransactionResp transaction = transactionService.createTransactionHistory(userUpdate, transactionReq);
			return transaction;
		} else {
			return null;
		}
	}
	public boolean verifyAccount(VerifyAccountReq verifyReq) {
		Optional<PersonalLegalModel> legal=legalRepo.findById(verifyReq.getIdLegal());
		UserModel usermodel= getUserByToken(verifyReq.getToken());
		if(legal.isPresent() && usermodel!=null) {
		PersonalLegalUserModel personalLegal= PersonalLegalUserModel.builder()
				.identifyBackside(verifyReq.getUrlBack())
				.identifyFront(verifyReq.getUrlFront())
				.personalLegalModel(legal.get())
				.userModelLegal(usermodel)
				.build();
		verifyRepo.save(personalLegal);
		return true;
		}
		return false;
	}
}
