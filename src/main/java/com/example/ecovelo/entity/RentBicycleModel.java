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
public class RentBicycleModel {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(nullable = false)
	private Long beginTimeRent;
	
	@Column(nullable = false)
	private Long endTimeRent;
	
	@Column 
	private int numFallBicycle;
	
	@Column(nullable = false)
	private float totalCharge;
	
	@ManyToOne
	@JoinColumn(name= "startRent")
	private Coordinate  coordinateStartRent;
	
	@ManyToOne
	@JoinColumn(name= "endRent")
	private Coordinate  coordinateEndRent;
	
	@ManyToOne
	@JoinColumn(name= "id_bicycle")
	private BicycleModel bicycleModel;
	

	@OneToOne
	@JoinColumn(name= "id_user")
	private UserModel userModelRent;
	
	@OneToOne(mappedBy = "rentBicycleModel")
	private ReviewModel reviewModel;
	
	@OneToMany(mappedBy ="rentBicycleModel")
	private List<TripDetail>  coordinateItinerary;
}
