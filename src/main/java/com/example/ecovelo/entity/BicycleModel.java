package com.example.ecovelo.entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
	
	@Column 
	private boolean isStatus;
	
	@Column 
	private boolean isUsing;
	
	 @ManyToOne
	 @JoinColumn(name = "coordinate_id")
	 private Coordinate coordinate;
	
	@OneToOne
	@JoinColumn(name= "id_bicycle_station")
	private BicycleStationModel bicycleStationModel;
	
	@OneToMany(mappedBy = "bicycleModel")
	private List<RentBicycleModel> rentBicycleModels;
}
