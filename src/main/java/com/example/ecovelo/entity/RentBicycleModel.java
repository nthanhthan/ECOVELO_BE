package com.example.ecovelo.entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "rent_bicycle")
public class RentBicycleModel {
	public RentBicycleModel(int id_rent_bicycle, Date begin_time_rent, Date end_time_rent, int num_fall_bicycle,
			float total_charge, String start_location, String end_location, TripItineraryModel tripItineraryModel,
			BicycleModel bicycleModel, UserModel userModelRent, ReviewModel reviewModel) {
		super();
		this.id_rent_bicycle = id_rent_bicycle;
		this.begin_time_rent = begin_time_rent;
		this.end_time_rent = end_time_rent;
		this.num_fall_bicycle = num_fall_bicycle;
		this.total_charge = total_charge;
		this.start_location = start_location;
		this.end_location = end_location;
		this.tripItineraryModel = tripItineraryModel;
		this.bicycleModel = bicycleModel;
		this.userModelRent = userModelRent;
		this.reviewModel = reviewModel;
	}
	public RentBicycleModel() {}
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id_rent_bicycle;
	
	@Column(nullable = false)
	private Date begin_time_rent;
	
	@Column(nullable = false)
	private Date end_time_rent;
	
	@Column 
	private int num_fall_bicycle;
	
	@Column(nullable = false)
	private float total_charge;
	
	@Column(nullable = false)
	private String start_location;
	
	@Column(nullable = false)
	private String end_location;
	
	@OneToOne(mappedBy = "rentbicycleModel")
	private TripItineraryModel tripItineraryModel;
	
	@OneToOne
	@JoinColumn(name= "id_bicycle")
	private BicycleModel bicycleModel;
	

	@OneToOne
	@JoinColumn(name= "id_user")
	private UserModel userModelRent;
	
	@OneToOne(mappedBy = "rentBicycleModel")
	private ReviewModel reviewModel;
	
	public int getId_rent_bicycle() {
		return id_rent_bicycle;
	}
	public void setId_rent_bicycle(int id_rent_bicycle) {
		this.id_rent_bicycle = id_rent_bicycle;
	}
	public Date getBegin_time_rent() {
		return begin_time_rent;
	}
	public void setBegin_time_rent(Date begin_time_rent) {
		this.begin_time_rent = begin_time_rent;
	}
	public Date getEnd_time_rent() {
		return end_time_rent;
	}
	public void setEnd_time_rent(Date end_time_rent) {
		this.end_time_rent = end_time_rent;
	}
	public int getNum_fall_bicycle() {
		return num_fall_bicycle;
	}
	public void setNum_fall_bicycle(int num_fall_bicycle) {
		this.num_fall_bicycle = num_fall_bicycle;
	}
	public float getTotal_charge() {
		return total_charge;
	}
	public void setTotal_charge(float total_charge) {
		this.total_charge = total_charge;
	}
	public String getStart_location() {
		return start_location;
	}
	public void setStart_location(String start_location) {
		this.start_location = start_location;
	}
	public String getEnd_location() {
		return end_location;
	}
	public void setEnd_location(String end_location) {
		this.end_location = end_location;
	}
	public TripItineraryModel getTripItineraryModel() {
		return tripItineraryModel;
	}
	public void setTripItineraryModel(TripItineraryModel tripItineraryModel) {
		this.tripItineraryModel = tripItineraryModel;
	}
	public BicycleModel getBicycleModel() {
		return bicycleModel;
	}
	public void setBicycleModel(BicycleModel bicycleModel) {
		this.bicycleModel = bicycleModel;
	}
	public UserModel getUserModelRent() {
		return userModelRent;
	}
	public void setUserModelRent(UserModel userModelRent) {
		this.userModelRent = userModelRent;
	}
	public ReviewModel getReviewModel() {
		return reviewModel;
	}
	public void setReviewModel(ReviewModel reviewModel) {
		this.reviewModel = reviewModel;
	}
}
