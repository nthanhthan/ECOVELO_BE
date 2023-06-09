package com.example.ecovelo.response;

import java.util.List;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class DetailJourneyResponse {
	private int id;
	private Long beginTimeRent;
	private Long endTimeRent;
	private CoordinateResponse coordinateBeginStaion;
	private CoordinateResponse coordinateEndStaion;
	private float money;
	private List<CoordinateResponse> detailTrip;
}
