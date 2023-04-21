package com.example.ecovelo.request;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
	private String phoneNumber;
	  private String password;
	  private String name;
	  private LocalDate birthDay;
	  private String address;
}
