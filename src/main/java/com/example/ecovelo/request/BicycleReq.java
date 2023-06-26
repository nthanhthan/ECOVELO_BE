package com.example.ecovelo.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class BicycleReq {
	String id;
	boolean status;
	boolean using;
	int id_bicycle_station;
	int coordinate_id;
}