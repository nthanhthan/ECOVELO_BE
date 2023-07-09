package com.example.ecovelo.response;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class UserAdminResponse {
	UserResponse userModel;
	int  totalRent;
	int numFall;
	String frontSide;
	String backSide;
}
