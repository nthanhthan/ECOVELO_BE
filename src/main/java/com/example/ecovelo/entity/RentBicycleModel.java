package com.example.ecovelo.entity;

import java.util.Date;
import java.util.List;

import com.example.ecovelo.enums.Role;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
public class RentBicycleModel {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(nullable = false)
	private Date beginTimeRent;
	
	@Column(nullable = false)
	private Date endTimeRent;
	
	@Column 
	private int numFallBicycle;
	
	@Column(nullable = false)
	private float totalCharge;
	
	@Column(nullable = false)
	private String startLocation;
	
	@Column(nullable = false)
	private String endLocation;
	
	@OneToOne(mappedBy = "rentBicycleModel")
	private TripItineraryModel tripItineraryModel;
	
	@ManyToOne
	@JoinColumn(name= "id_bicycle")
	private BicycleModel bicycleModel;
	

	@OneToOne
	@JoinColumn(name= "id_user")
	private UserModel userModelRent;
	
	@OneToOne(mappedBy = "rentBicycleModel")
	private ReviewModel reviewModel;
}
