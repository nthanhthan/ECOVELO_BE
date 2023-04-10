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
	public ReviewModel(int id_review, String comment, String reason_fall_bicycle, int num_of_star, Date review_datetime,
			RentBicycleModel rentBicycleModel) {
		super();
		this.id_review = id_review;
		this.comment = comment;
		this.reason_fall_bicycle = reason_fall_bicycle;
		this.num_of_star = num_of_star;
		this.review_datetime = review_datetime;
		this.rentBicycleModel = rentBicycleModel;
	}
	public ReviewModel() {}
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id_review;
	
	@Column 
	private String comment;
	
	@Column 
	private String reason_fall_bicycle;
	
	@Column(nullable = false)
	private int num_of_star;
	
	@Column 
	private Date review_datetime;
	
	@OneToOne
	@JoinColumn(name= "user_rent_bicycle")
	private RentBicycleModel rentBicycleModel;
	
	public int getId_review() {
		return id_review;
	}
	public void setId_review(int id_review) {
		this.id_review = id_review;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getReason_fall_bicycle() {
		return reason_fall_bicycle;
	}
	public void setReason_fall_bicycle(String reason_fall_bicycle) {
		this.reason_fall_bicycle = reason_fall_bicycle;
	}
	public int getNum_of_star() {
		return num_of_star;
	}
	public void setNum_of_star(int num_of_star) {
		this.num_of_star = num_of_star;
	}
	public Date getReview_datetime() {
		return review_datetime;
	}
	public void setReview_datetime(Date review_datetime) {
		this.review_datetime = review_datetime;
	}
	public RentBicycleModel getRentBicycleModel() {
		return rentBicycleModel;
	}
	public void setRentBicycleModel(RentBicycleModel rentBicycleModel) {
		this.rentBicycleModel = rentBicycleModel;
	}
}
