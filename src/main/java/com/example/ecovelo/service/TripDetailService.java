package com.example.ecovelo.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.ecovelo.entity.RentBicycleModel;
import com.example.ecovelo.entity.TripDetail;
import com.example.ecovelo.repository.RentBicycleModelRepository;
import com.example.ecovelo.repository.TripDetailModelRepository;
import com.example.ecovelo.request.TripDetailReq;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TripDetailService {
	final private TripDetailModelRepository tripDetailRepo;
	final private RentBicycleModelRepository rentRepo; 
	
	public boolean createTripDetail(TripDetailReq tripDetail) {
		Optional<RentBicycleModel> rentBicycle=rentRepo.findById(tripDetail.getId_rent());
		if(rentBicycle.isPresent()) {
			var trip= TripDetail.builder()
					.lat(tripDetail.getLat())
					.lng(tripDetail.getLng())
					.rentBicycleModel(rentBicycle.get())
					.build();
			tripDetailRepo.save(trip);
			return true;
		}
		return false;
	}
}
