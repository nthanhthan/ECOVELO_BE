package com.example.ecovelo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


import com.example.ecovelo.entity.RentBicycleModel;
import com.example.ecovelo.entity.UserModel;

public interface RentBicycleModelRepository extends JpaRepository<RentBicycleModel, Integer>{
	List<RentBicycleModel> findByUserModelRent(UserModel userModel);
}
