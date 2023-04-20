package com.example.ecovelo.entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "bicycle")
public class BicycleModel {
	public BicycleModel(String id_bicycle, String locate, boolean status, boolean is_using,
			BicycleStationModel bicycleStation, List<RentBicycleModel> rentBicycleModels) {
		super();
		this.id_bicycle = id_bicycle;
		this.locate = locate;
		this.status = status;
		this.is_using = is_using;
		this.bicycleStation = bicycleStation;
		this.rentBicycleModels = rentBicycleModels;
	}
	public BicycleModel() {
		
	}
	@Id
	private String  id_bicycle;
	
	@Column(nullable = false)
	private String locate;
	
	@Column 
	private boolean status;
	
	@Column 
	private boolean is_using;
	
	@OneToOne
	@JoinColumn(name= "id_bicycle_station")
	private BicycleStationModel bicycleStation;
	
	@OneToMany(mappedBy = "bicycleModel")
	private List<RentBicycleModel> rentBicycleModels;
	
	public String getId_bicycle() {
		return id_bicycle;
	}
	public void setId_bicycle(String id_bicycle) {
		this.id_bicycle = id_bicycle;
	}
	public String getLocate() {
		return locate;
	}
	public void setLocate(String locate) {
		this.locate = locate;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public boolean isIs_using() {
		return is_using;
	}
	public void setIs_using(boolean is_using) {
		this.is_using = is_using;
	}
	public BicycleStationModel getBicycleStation() {
		return bicycleStation;
	}
	public void setBicycleStation(BicycleStationModel bicycleStation) {
		this.bicycleStation = bicycleStation;
	}
	public List<RentBicycleModel> getRentBicycleModels() {
		return rentBicycleModels;
	}
	public void setRentBicycleModels(List<RentBicycleModel> rentBicycleModels) {
		this.rentBicycleModels = rentBicycleModels;
	}
}
