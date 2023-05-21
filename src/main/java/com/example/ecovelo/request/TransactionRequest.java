package com.example.ecovelo.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class TransactionRequest {
	private  float money;
	private String titleTransaction;
	private boolean isMainPoint;
	private boolean isStatus;
}
