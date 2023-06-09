package com.example.ecovelo.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class DetailTripResponse {
	private int id;

	private String lat;

	private String lng;

	private int rentBicycleModel;
}
