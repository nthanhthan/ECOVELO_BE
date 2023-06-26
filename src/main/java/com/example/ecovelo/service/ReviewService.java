package com.example.ecovelo.service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.ecovelo.entity.RentBicycleModel;
import com.example.ecovelo.entity.ReviewModel;
import com.example.ecovelo.repository.RentBicycleModelRepository;
import com.example.ecovelo.repository.ReviewRepository;
import com.example.ecovelo.request.ReviewRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReviewService {
	private final ReviewRepository reviewRepo;
	private final RentBicycleModelRepository rentRepo;
	
	public boolean reviewTrip(ReviewRequest reviewReq) {
		Optional<RentBicycleModel> rent= rentRepo.findById(reviewReq.getIdRent());
		if(rent.isPresent() && rent!=null) {
		ReviewModel reviewModel=ReviewModel.builder()
				.comment(reviewReq.getComment())
				.numOfStar(reviewReq.getNumStar())
				.reasonFallBicycle(reviewReq.getReasonFall())
				.reviewDatetime(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli())
				.rentBicycleModel(rent.get())
				.build();
		reviewRepo.save(reviewModel);
		return true;
		}
		return false;
	}
}
