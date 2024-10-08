package com.example.ecovelo.entity;

import java.util.List;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
public class BicycleStationModel {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	 @ManyToOne
	 @JoinColumn(name = "coordinate_id")
	 private Coordinate coordinate;
	
	@Column 
	private int numBicycle;
	
	@OneToMany(mappedBy = "bicycleStationModel")
	private List<BicycleModel> bicycleModels;

}
