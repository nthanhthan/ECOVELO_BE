package com.example.ecovelo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ecovelo.entity.RentBicycleModel;
import com.example.ecovelo.entity.TripDetail;

public interface TripDetailModelRepository  extends JpaRepository<TripDetail, Integer>{
	List<TripDetail> findByRentBicycleModel(RentBicycleModel rentBicycleModel);
}
