package com.example.ecovelo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.ecovelo.entity.Coordinate;
import com.example.ecovelo.service.CoordinateService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/coordinate")
public class CoordinateController {
	@Autowired
	private final CoordinateService coordinateService;
	@PostMapping("/create-co-station")
	 public void saveCoordinateStation(@RequestBody List<Coordinate> coordinates) {
		 
		coordinateService.createListCoordinateStation(coordinates);
	 }
}
