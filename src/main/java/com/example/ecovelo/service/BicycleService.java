
package com.example.ecovelo.service;


import org.springframework.stereotype.Service;
import com.example.ecovelo.repository.BicycleModelRepository;
import lombok.RequiredArgsConstructor;



@Service
@RequiredArgsConstructor
public class BicycleService {
	private final BicycleModelRepository bicycleModelRepository;
	
	public boolean checkExistBicycleID(String id) {
	
		return  bicycleModelRepository.existsById(id);
		
		
	}


}
