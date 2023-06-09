package com.example.ecovelo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ecovelo.entity.UserModel;
import com.example.ecovelo.request.ReportProblemRequest;
import com.example.ecovelo.request.StopRent;
import com.example.ecovelo.service.BicycleService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/bicycle")
public class BicycleController {
	private final BicycleService bicycleService;

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
	public ResponseEntity<UserModel> stopRentbicycle(@RequestBody StopRent stopRent) {
		return ResponseEntity.ok(bicycleService.endJourney(stopRent.getBicycleID().trim(), stopRent.getRentID()));
	}
	@PostMapping("/report-problem")
	public ResponseEntity<Boolean> reportProblem(@RequestBody ReportProblemRequest report) {
		return ResponseEntity.ok(bicycleService.reportProblem(report));
	}
	
	@PostMapping("/check-report")
	public ResponseEntity<Boolean> checkReport(@RequestBody String id) {
		return ResponseEntity.ok(bicycleService.checkBicycleReport(id));
	}
}
