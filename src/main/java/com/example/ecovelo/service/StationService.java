package com.example.ecovelo.service;
import java.util.List;
import java.util.Optional;
import java.util.ArrayList;


import org.springframework.stereotype.Service;

import com.example.ecovelo.repository.BicycleStationModelRepository;
import com.example.ecovelo.repository.CoordinateModelRepository;
import com.example.ecovelo.request.StationReq;
import com.example.ecovelo.response.CoordinateResponse;
import com.example.ecovelo.response.StationResponse;

import lombok.RequiredArgsConstructor;

import com.example.ecovelo.entity.BicycleModel;
import com.example.ecovelo.entity.BicycleStationModel;

@Service
@RequiredArgsConstructor
public class StationService {
	private final BicycleStationModelRepository bicycleStationModelRepository;
	private final CoordinateModelRepository coordinateRepo;
	
	public List<StationResponse> getListStation(){
		List<BicycleStationModel> listStation= bicycleStationModelRepository.findAll();
		List<StationResponse> listStationResponse= new ArrayList<StationResponse>();
		if(listStation!=null && !listStation.isEmpty()) {
			for(BicycleStationModel station :listStation){
				List<BicycleModel>  bicycles= station.getBicycleModels();
				int num=0;
				for(BicycleModel bicycle :bicycles ) {
					if(bicycle.isStatus() && !bicycle.isUsing()) {
						num+=1;
					}
				}			
				listStationResponse.add(StationResponse.builder()
						.id(station.getId())
						.lat(station.getCoordinate().getLat())
						.lng(station.getCoordinate().getLng())
						.address(station.getCoordinate().getAddress())
						.numBicycle(num)
						.build());
			}
			
		}
		return listStationResponse;
	}
	public void createStation(List<StationReq> stations) {
		List<BicycleStationModel> listStation= new ArrayList<BicycleStationModel>();
		for(int i=1;i<=30;i++) {
			var coordinate = coordinateRepo.findById(i);
			if(coordinate.isPresent()) {
				listStation.add(BicycleStationModel
						.builder()
						.coordinate(coordinate.get())
						.numBicycle(stations.get(i-1).getNumBicycle())
						.build());
			}
		}
		bicycleStationModelRepository.saveAll(listStation);	
	}
	public CoordinateResponse getCoordinateByIdStation(int id) {
		Optional<BicycleStationModel> station=  bicycleStationModelRepository.findById(id);
		if(station.isPresent()) {
			CoordinateResponse coordinate= CoordinateResponse.builder()
					.lat(station.get().getCoordinate().getLat())
					.lng(station.get().getCoordinate().getLng())
					.build();
			return coordinate;
		}
		return null;
		
		
	}
	public BicycleStationModel  getStationByID(int id) {
		var station= bicycleStationModelRepository.findById(id);
		if(station.isPresent()) {
			return station.get();
		}else {
			return null;
		}
	}
	public void numBicycleOfStation() {
		List<BicycleStationModel>  listStation= bicycleStationModelRepository.findAll();
		for(BicycleStationModel sation : listStation) {
			BicycleStationModel bicycleStation= BicycleStationModel.builder()
					.id(sation.getId())
					.coordinate(sation.getCoordinate())
					.numBicycle(sation.getBicycleModels().size())
					.build();
			bicycleStationModelRepository.save(bicycleStation);
		}
	
	}

}
