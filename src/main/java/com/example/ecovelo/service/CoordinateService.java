package com.example.ecovelo.service;

import org.springframework.stereotype.Service;

import com.example.ecovelo.entity.Coordinate;
import com.example.ecovelo.repository.CoordinateModelRepository;
import lombok.RequiredArgsConstructor;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CoordinateService {
	private final CoordinateModelRepository coordinate;
	
	public void createListCoordinateStation(List<Coordinate> listCoordinates) {
		coordinate.saveAll(listCoordinates);
		
	}
	
}
