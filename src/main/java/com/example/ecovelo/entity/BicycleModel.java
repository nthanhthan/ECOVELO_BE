package com.example.ecovelo.entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Entity
public class BicycleModel {
	@Id
	private String  id;
	
	@Column(nullable = false)
	private double lat;
	
	@Column(nullable = false)
	private double lng;
	
	@Column 
	private boolean isStatus;
	
	@Column 
	private boolean isUsing;
	
	@OneToOne
	@JoinColumn(name= "id_bicycle_station")
	private BicycleStationModel bicycleStationModel;
	
	@OneToMany(mappedBy = "bicycleModel")
	private List<RentBicycleModel> rentBicycleModels;
}
