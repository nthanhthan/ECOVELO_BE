package com.example.ecovelo.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ecovelo.request.BicycleReq;
import com.example.ecovelo.request.ReportProblemRequest;
import com.example.ecovelo.request.ReviewRequest;
import com.example.ecovelo.request.StopRent;
import com.example.ecovelo.response.StopRentResponse;
import com.example.ecovelo.service.BicycleService;
import com.example.ecovelo.service.ReviewService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/bicycle")
public class BicycleController {
	private final BicycleService bicycleService;
	private final ReviewService reviewService;

	@PostMapping("/checkQR")
	public ResponseEntity<Boolean> checkQR(@RequestBody String id) {
		return ResponseEntity.ok(bicycleService.checkExistBicycleID(id));
	}

	@PostMapping("/rent-bicycle")
	public ResponseEntity<Integer> rentBicycle(@RequestHeader(name = "Authorization") String token,
			@RequestBody String bicycleID) {
		final String jwt = token.substring(7);
		return ResponseEntity.ok(bicycleService.rentBicycle(jwt, bicycleID.trim()));
	}

	@PostMapping("/stop-rent")
	public ResponseEntity<StopRentResponse> stopRentbicycle(@RequestBody StopRent stopRent) {
		return ResponseEntity.ok(bicycleService.endJourney(stopRent));
	}
	@PostMapping("/report-problem")
	public ResponseEntity<Boolean> reportProblem(@RequestBody ReportProblemRequest report) {
		return ResponseEntity.ok(bicycleService.reportProblem(report));
	}
	
	@PostMapping("/check-report")
	public ResponseEntity<Boolean> checkReport(@RequestBody String id) {
		return ResponseEntity.ok(bicycleService.checkBicycleReport(id));
	}
	@PostMapping("/feedback")
	public ResponseEntity<Boolean> feedbackTrip(@RequestBody ReviewRequest review) {
		return ResponseEntity.ok(reviewService.reviewTrip(review));
	}
	@GetMapping("/fallBicycle/{idRent}")
	public ResponseEntity<?> fallBicycle(@PathVariable("idRent") int  rentID) {
	     bicycleService.fallBicycle(rentID);
		return ResponseEntity.ok().build();
	}
	@PostMapping("/createBicycle")
	public void createBicyle(@RequestBody List<BicycleReq> biclyeList) {
		bicycleService.createBicycle(biclyeList);
	}
}
