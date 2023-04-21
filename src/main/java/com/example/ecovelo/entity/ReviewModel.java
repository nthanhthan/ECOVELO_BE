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
@Table(name = "review")
public class ReviewModel {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column 
	private String comment;
	
	@Column 
	private String reasonFallBicycle;
	
	@Column(nullable = false)
	private int numOfStar;
	
	@Column 
	private Date reviewDatetime;
	
	@OneToOne
	@JoinColumn(name= "user_rent_bicycle")
	private RentBicycleModel rentBicycleModel;
}
