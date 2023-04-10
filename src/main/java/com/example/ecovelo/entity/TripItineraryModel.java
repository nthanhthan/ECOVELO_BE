package com.example.ecovelo.entity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "trip_itinerary")
public class TripItineraryModel {
	public TripItineraryModel(int id_trip_itinerary, String detail_trip, RentBicycleModel rentbicycleModel) {
		super();
		this.id_trip_itinerary = id_trip_itinerary;
		this.detail_trip = detail_trip;
		this.rentbicycleModel = rentbicycleModel;
	}
	public TripItineraryModel() {}
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id_trip_itinerary;
	
	@Column(nullable = false)
	private String detail_trip;
	
	@OneToOne
	@JoinColumn(name= "id_rent_bicycle")
	private RentBicycleModel rentbicycleModel;
	
	public int getId_trip_itinerary() {
		return id_trip_itinerary;
	}
	public void setId_trip_itinerary(int id_trip_itinerary) {
		this.id_trip_itinerary = id_trip_itinerary;
	}
	public String getDetail_trip() {
		return detail_trip;
	}
	public void setDetail_trip(String detail_trip) {
		this.detail_trip = detail_trip;
	}
	public RentBicycleModel getRentbicycleModel() {
		return rentbicycleModel;
	}
	public void setRentbicycleModel(RentBicycleModel rentbicycleModel) {
		this.rentbicycleModel = rentbicycleModel;
	}
}
