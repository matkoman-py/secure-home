	package com.example.myhome.domain;

import java.util.Date;

public class MessageDTO {
	private String id;
    private Date date;
    private String message;
    private DeviceType type;
    private String address;
	public MessageDTO(String id, Date date, String message, DeviceType type, String address) {
		super();
		this.id = id;
		this.date = date;
		this.message = message;
		this.type = type;
		this.address = address;
	}
	public MessageDTO() {
		super();
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public DeviceType getType() {
		return type;
	}
	public void setType(DeviceType type) {
		this.type = type;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
}
