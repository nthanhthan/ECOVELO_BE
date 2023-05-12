package com.example.ecovelo.entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Entity
public class Coordinate {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column 
	private String lat;

	@Column 
	private String lng;
	
	@Column
	private String address;
	
	
	
	@OneToMany(mappedBy = "coordinate")
    private List<BicycleModel> bicycles;
	
	@OneToMany(mappedBy = "coordinate")
    private List<BicycleStationModel> stations;
	
	@OneToMany(mappedBy= "coordinateStartRent")
	private List<RentBicycleModel> startRents;
	
	@OneToMany(mappedBy= "coordinateEndRent")
	private List<RentBicycleModel>  endRents;
}
