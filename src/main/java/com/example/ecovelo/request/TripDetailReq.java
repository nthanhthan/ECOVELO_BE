package com.example.ecovelo.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class TripDetailReq {
	private String lat;
	private String lng;
	private int id_rent;
}
