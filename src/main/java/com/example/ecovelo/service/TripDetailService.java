package com.example.ecovelo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.ecovelo.entity.RentBicycleModel;
import com.example.ecovelo.entity.TripDetail;
import com.example.ecovelo.entity.UserModel;
import com.example.ecovelo.repository.RentBicycleModelRepository;
import com.example.ecovelo.repository.TripDetailModelRepository;
import com.example.ecovelo.request.TripDetailReq;
import com.example.ecovelo.response.CoordinateResponse;
import com.example.ecovelo.response.DetailJourneyResponse;
import com.example.ecovelo.response.DetailTripResponse;
import java.util.ArrayList;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TripDetailService {
	final private TripDetailModelRepository tripDetailRepo;
	final private RentBicycleModelRepository rentRepo;
	final private AuthService authService;

	public boolean createTripDetail(TripDetailReq tripDetail) {
		Optional<RentBicycleModel> rentBicycle = rentRepo.findById(tripDetail.getId_rent());
		if (rentBicycle.isPresent()) {
			var trip = TripDetail.builder().lat(tripDetail.getLat()).lng(tripDetail.getLng())
					.rentBicycleModel(rentBicycle.get()).build();
			tripDetailRepo.save(trip);
			return true;
		}
		return false;
	}

	public List<DetailJourneyResponse> getListJourney(String token) {
		UserModel user = authService.getUserByToken(token);
		List<RentBicycleModel> listRent = rentRepo.findByUserModelRent(user);
		List<DetailJourneyResponse> listDetail = new ArrayList<>();

		if (!listRent.isEmpty()) {
			for (RentBicycleModel item : listRent) {
				if (item.getCoordinateEndRent() != null) {
					CoordinateResponse coordinateBegin = CoordinateResponse.builder()
							.lat(item.getCoordinateStartRent().getLat()).lng(item.getCoordinateStartRent().getLng())
							.build();
					CoordinateResponse coordinateEnd = CoordinateResponse.builder()
							.lat(item.getCoordinateEndRent().getLat()).lng(item.getCoordinateEndRent().getLng())
							.build();
					List<CoordinateResponse> listCoordinate = new ArrayList<>();

					List<TripDetail> listTripbyUser = item.getCoordinateItinerary();
					for (TripDetail trip : listTripbyUser) {
						CoordinateResponse coordinate = CoordinateResponse.builder().lat(trip.getLat())
								.lng(trip.getLng()).build();
						listCoordinate.add(coordinate);
					}
					DetailJourneyResponse detailJourney = DetailJourneyResponse.builder().id(item.getId())
							.coordinateBeginStaion(coordinateBegin).coordinateEndStaion(coordinateEnd)
							.beginTimeRent(item.getBeginTimeRent()).endTimeRent(item.getEndTimeRent())
							.money(item.getTotalCharge()).detailTrip(listCoordinate).build();
					listDetail.add(detailJourney);
				}
			}
			return listDetail;
		}
		return null;
	}

	public List<DetailTripResponse> getAllTrip() {
		List<DetailTripResponse> listDetail = new ArrayList<>();
		;
		List<TripDetail> listTrip = tripDetailRepo.findAll();
		for (TripDetail element : listTrip) {
			DetailTripResponse trip = DetailTripResponse.builder().id(element.getId()).lat(element.getLat())
					.lng(element.getLng()).rentBicycleModel(element.getRentBicycleModel().getId()).build();
			listDetail.add(trip);
		}
		return listDetail;

	}

}
