package com.example.ecovelo.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ReviewRequest {
	int idRent;
	String comment;
	String reasonFall;
	int numStar;
}
