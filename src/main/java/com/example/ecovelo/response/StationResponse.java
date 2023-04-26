package com.example.ecovelo.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class StationResponse {
	private int id;
	private String address;
	private double lat;
    private double lng;
    private int numBicycle;


}
