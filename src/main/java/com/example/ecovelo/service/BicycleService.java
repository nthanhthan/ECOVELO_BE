
package com.example.ecovelo.service;


import java.util.Optional;
import org.springframework.stereotype.Service;

import com.example.ecovelo.entity.BicycleModel;
import com.example.ecovelo.repository.BicycleModelRepository;
import lombok.RequiredArgsConstructor;



@Service
@RequiredArgsConstructor
public class BicycleService {
	private final BicycleModelRepository bicycleModelRepository;
	public boolean checkExistBicycleID(String id) {
		boolean existID=false;
		Optional<BicycleModel> bicycle= bicycleModelRepository.findById(id);
		if(bicycle.get().isStatus() && !bicycle.get().isUsing()){
			existID=true;
		}
		return  existID;
	}
//	public Optional<BicycleModel> getBicycleIById(String id) {
//		Optional<BicycleModel> bicycle= bicycleModelRepository.findById(id);
//		bicycle.ifPresent(b->{
//			if(b.isStatus() && !b.isUsing()) {
//				
//			}
//		});
//
//		
//		return  bicycle.map(b ->new BicycleModel(b.getId(), b.getLat(), b.getLng(), b.getBicycleStationModel()));
//		
//		
//	}


}
