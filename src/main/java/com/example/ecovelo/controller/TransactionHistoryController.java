package com.example.ecovelo.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.ecovelo.response.HistoryTransactionResp;
import com.example.ecovelo.service.TransactionHistoryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/transaction")
public class TransactionHistoryController {
	final private TransactionHistoryService transactionService;

	@GetMapping("/getTransaction")
	public ResponseEntity<List<HistoryTransactionResp>> getListTransaction(
			@RequestHeader(name = "Authorization") String token
	) {
		final String jwt = token.substring(7);
		return ResponseEntity.ok(transactionService.getListTransactionHistory(jwt));
	}
}
