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
@Table(name = "problem")
public class Problem {
	public Problem(int id_problem, String name_problem, List<ReportProblem> listReportProblems) {
		super();
		this.id_problem = id_problem;
		this.name_problem = name_problem;
		this.listReportProblems = listReportProblems;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id_problem;
	
	@Column 
	private String name_problem;

	public int getId_user() {
		return id_problem;
	}

	public void setId_user(int id_problem) {
		this.id_problem = id_problem;
	}

	public String getName_() {
		return name_problem;
	}

	public void setName_(String name_problem) {
		this.name_problem = name_problem;
	}
	
	@OneToMany(mappedBy = "problemModel")
	private List<ReportProblem> listReportProblems;

	public int getId_problem() {
		return id_problem;
	}

	public void setId_problem(int id_problem) {
		this.id_problem = id_problem;
	}

	public String getName_problem() {
		return name_problem;
	}

	public void setName_problem(String name_problem) {
		this.name_problem = name_problem;
	}

	public List<ReportProblem> getListReportProblems() {
		return listReportProblems;
	}

	public void setListReportProblems(List<ReportProblem> listReportProblems) {
		this.listReportProblems = listReportProblems;
	}
}
