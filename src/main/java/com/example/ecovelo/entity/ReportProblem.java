package com.example.ecovelo.entity;
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
	private int id;
	
	@Column 
	private Long createAt;
	@Column
	private String desciption;
	@Column
	private String uploadImage;
	@Column
	private String idBicycle;

	@ManyToOne
	@JoinColumn(name= "id_problem")
	private Problem problemModel;
	
	@OneToOne
	@JoinColumn(name= "id_user")
	private UserModel userModelReport;
}
