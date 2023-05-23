package com.example.ecovelo.controller;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ecovelo.request.TripDetailReq;
import com.example.ecovelo.service.TripDetailService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/trip")
public class TripDetailController {
	final private  TripDetailService  tripService;
	
	@PostMapping("/addTripDetail")
	public ResponseEntity<Void>  createCoodinate(@RequestBody TripDetailReq request){
		   boolean isSuccess = tripService.createTripDetail(request);
		    if (isSuccess) {
		        return ResponseEntity.noContent().build();
		    } else {
		        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		    }
		
		
	}

}
