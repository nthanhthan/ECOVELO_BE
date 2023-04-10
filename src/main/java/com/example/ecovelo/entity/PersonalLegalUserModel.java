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
@Table(name = "personal_legal_user")
public class PersonalLegalUserModel {
	public PersonalLegalUserModel(int id_personal_legal_user, String identify_front, String identify_backside,
			PersonalLegalModel personalLegalModel, UserModel userModelLegal) {
		super();
		this.id_personal_legal_user = id_personal_legal_user;
		this.identify_front = identify_front;
		this.identify_backside = identify_backside;
		this.personalLegalModel = personalLegalModel;
		this.userModelLegal = userModelLegal;
	}
	public PersonalLegalUserModel() {}
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id_personal_legal_user;
	
	@Column(nullable = false)
	private String identify_front;
	
	@Column(nullable = false)
	private String identify_backside;
	
	@ManyToOne
	@JoinColumn(name= "id_personal_legal")
	private PersonalLegalModel personalLegalModel;
	
	@OneToOne
	@JoinColumn(name= "id_user")
	private UserModel userModelLegal;
	
	public int getId_personal_legal_user() {
		return id_personal_legal_user;
	}
	public void setId_personal_legal_user(int id_personal_legal_user) {
		this.id_personal_legal_user = id_personal_legal_user;
	}
	public String getIdentify_front() {
		return identify_front;
	}
	public void setIdentify_front(String identify_front) {
		this.identify_front = identify_front;
	}
	public String getIdentify_backside() {
		return identify_backside;
	}
	public void setIdentify_backside(String identify_backside) {
		this.identify_backside = identify_backside;
	}
	public PersonalLegalModel getPersonalLegalModel() {
		return personalLegalModel;
	}
	public void setPersonalLegalModel(PersonalLegalModel personalLegalModel) {
		this.personalLegalModel = personalLegalModel;
	}
	public UserModel getUserModelLegal() {
		return userModelLegal;
	}
	public void setUserModelLegal(UserModel userModelLegal) {
		this.userModelLegal = userModelLegal;
	}
}
