package com.example.ecovelo.service;
import java.util.List;
import java.util.ArrayList;


import org.springframework.stereotype.Service;

import com.example.ecovelo.repository.BicycleStationModelRepository;
import com.example.ecovelo.response.StationResponse;

import lombok.RequiredArgsConstructor;
import com.example.ecovelo.entity.BicycleStationModel;

@Service
@RequiredArgsConstructor
public class StationService {
	private final BicycleStationModelRepository bicycleStationModelRepository;
	
	public List<StationResponse> getListStation(){
		List<BicycleStationModel> listStation= bicycleStationModelRepository.findAll();
		List<StationResponse> listStationResponse= new ArrayList<StationResponse>();
		if(listStation!=null && !listStation.isEmpty()) {
			for(BicycleStationModel station :listStation){
				listStationResponse.add(StationResponse.builder().id(station.getId()).lat(station.getLat()).lng(station.getLng()).address(station.getAddressStation()).numBicycle(station.getNumBicycle()).build());
			}
			
		}
		return listStationResponse;
	}

}
