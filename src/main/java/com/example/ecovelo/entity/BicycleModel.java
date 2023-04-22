package com.example.ecovelo.entity;

import java.util.List;

import com.example.ecovelo.enums.Role;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
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
	private String locate;
	
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
