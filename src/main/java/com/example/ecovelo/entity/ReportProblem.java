package com.example.ecovelo.entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "report_problem")
public class ReportProblem {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id_report_problem;
	
	@Column 
	private Date createAt;
	@Column
	private String desciption;
	@Column
	private String upload_image;
	@Column
	private String id_bicycle;

	@ManyToOne
	@JoinColumn(name= "id_problem")
	private Problem problemModel;
	
	@OneToOne
	@JoinColumn(name= "id_user")
	private UserModel userModelReport;
}
