package com.example.ecovelo.service;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;

import com.example.ecovelo.entity.UserModel;
import com.example.ecovelo.jwt.JwtTokenProvider;
import com.example.ecovelo.repository.AccountModelRepository;
import com.example.ecovelo.repository.TransactionModelRepository;
import com.example.ecovelo.repository.UserModelRepository;
import com.example.ecovelo.request.TransactionRequest;
import com.example.ecovelo.response.HistoryTransactionResp;
import com.example.ecovelo.response.TransactionResp;

import lombok.RequiredArgsConstructor;
import java.util.List;
import java.util.Optional;

import com.example.ecovelo.entity.TransactionHistoryModel;

@Service
@RequiredArgsConstructor
public class TransactionHistoryService {
	final private TransactionModelRepository transactionRepo;
	private final AccountModelRepository accountRepository;
	private final UserModelRepository userRepository;

	private final JwtTokenProvider jwtService;

	
	public TransactionResp createTransactionHistory(UserModel user, TransactionRequest transactionReq) {
		System.out.print(transactionReq);
		var transactionHistory = TransactionHistoryModel.builder()
				.datetimeTransaction(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli())
				.mainAccount(transactionReq.isMainPoint()).money(transactionReq.getMoney())
				.statusTransaction(transactionReq.isStatus()).titleTransaction(transactionReq.getTitleTransaction())
				.userModelTransaction(user).build();
		TransactionHistoryModel transaction = transactionRepo.save(transactionHistory);
		var transactionResp = TransactionResp.builder().id(transaction.getId())
				.dateTimeTransaction(transaction.getDatetimeTransaction()).point(transaction.getMoney()).build();
		return transactionResp;
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

	public List<HistoryTransactionResp> getListTransactionHistory(String token) {
		UserModel user = getUserByToken(token);
		if (user != null) {
			var userResp = UserModel.builder().id(user.getId()).nameUser(user.getNameUser()).email(user.getEmail())
					.mainPoint(user.getMainPoint()).proPoint(user.getProPoint()).verify(user.isVerify()).build();
			List<TransactionHistoryModel> listTransaction= transactionRepo.findByUserModelTransaction(userResp);
			List<HistoryTransactionResp> historyTransactionResp = new ArrayList<>();
			for(TransactionHistoryModel item :listTransaction) {
				boolean isEcovelo=false;
				if(item.getTitleTransaction().contains("Riding")){
					isEcovelo=true;
				}
				HistoryTransactionResp history= HistoryTransactionResp.builder()
						.id(item.getId())
						.dateTimeTransaction(item.getDatetimeTransaction())
						.point(item.getMoney())
						.isEcovelo(isEcovelo)
						.titleTransaction(item.getTitleTransaction()).build();
				historyTransactionResp.add(history);
			}
			return historyTransactionResp;
		}
		return null;
	}
}

