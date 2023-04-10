package com.example.ecovelo.entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "bicycle_station")
public class BicycleStationModel {
	public BicycleStationModel(int id_bicycle_station, String coordinates, String address_station, int num_bicycle,
			List<BicycleModel> bicycleModels) {
		super();
		this.id_bicycle_station = id_bicycle_station;
		this.coordinates = coordinates;
		this.address_station = address_station;
		this.num_bicycle = num_bicycle;
		this.bicycleModels = bicycleModels;
	}
	public BicycleStationModel() {}
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id_bicycle_station;
	
	@Column(nullable = false)
	private String coordinates;
	
	@Column(nullable = false)
	private String address_station;
	
	@Column 
	private int num_bicycle;
	
	@OneToMany(mappedBy = "bicycleStation")
	private List<BicycleModel> bicycleModels;
	
	public int getId_bicycle_station() {
		return id_bicycle_station;
	}
	public void setId_bicycle_station(int id_bicycle_station) {
		this.id_bicycle_station = id_bicycle_station;
	}
	public String getCoordinates() {
		return coordinates;
	}
	public void setCoordinates(String coordinates) {
		this.coordinates = coordinates;
	}
	public String getAddress_station() {
		return address_station;
	}
	public void setAddress_station(String address_station) {
		this.address_station = address_station;
	}
	public int getNum_bicycle() {
		return num_bicycle;
	}
	public void setNum_bicycle(int num_bicycle) {
		this.num_bicycle = num_bicycle;
	}
	public List<BicycleModel> getBicycleModels() {
		return bicycleModels;
	}
	public void setBicycleModels(List<BicycleModel> bicycleModels) {
		this.bicycleModels = bicycleModels;
	}
}
