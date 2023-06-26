package com.example.ecovelo.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class VerifyAccountReq {
	String urlFront;
	String urlBack;
	int idLegal;
	String token;
}