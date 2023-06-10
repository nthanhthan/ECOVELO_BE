package com.example.ecovelo.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserResponse {
	private int userId;
	private String phoneNumber;
	private String email;
	private float mainPoint;
	private float proPoint;
	private String nameUser;
	private boolean verify;
	private boolean isProccessing;
}
