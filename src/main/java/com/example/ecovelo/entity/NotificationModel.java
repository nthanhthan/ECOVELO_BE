package com.example.ecovelo.entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "notification")
public class NotificationModel {
	public NotificationModel(int id_notification, String title, String body, Date datetime_push) {
		super();
		this.id_notification = id_notification;
		this.title = title;
		this.body = body;
		this.datetime_push = datetime_push;
	}
	public NotificationModel() {}
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id_notification;
	
	@Column 
	private String title;
	
	@Column 
	private String body;
	
	@Column 
	private Date datetime_push;
	
	public int getId_notification() {
		return id_notification;
	}
	public void setId_notification(int id_notification) {
		this.id_notification = id_notification;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public Date getDatetime_push() {
		return datetime_push;
	}
	public void setDatetime_push(Date datetime_push) {
		this.datetime_push = datetime_push;
	}
}
