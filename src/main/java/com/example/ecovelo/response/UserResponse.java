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
	private String phoneNumber;
	private String email;
	private float money;
	private String nameUser;
	private boolean sex;
	private boolean verify;
}
