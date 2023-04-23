package com.example.ecovelo.response;

import com.example.ecovelo.entity.UserModel;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class AuthResponse {
	  private String accessToken;
	  private String refreshToken;
	  private Long expired;
	  private UserResponse userResponse;
}

