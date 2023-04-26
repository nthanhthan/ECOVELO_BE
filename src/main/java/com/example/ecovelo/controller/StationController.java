package com.example.ecovelo.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.ecovelo.service.StationService;
import lombok.RequiredArgsConstructor;
import com.example.ecovelo.response.StationResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/station")
public class StationController {
	private final StationService stationService;
	 @GetMapping("/getStation")
	  public ResponseEntity<List<StationResponse> >getListStation(
	  ) {
	    return ResponseEntity.ok(stationService.getListStation());
	  }
}
