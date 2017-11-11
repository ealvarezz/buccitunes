package com.buccitunes.model;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity(name="AdminUser")
public class AdminUser extends User {
	
	public AdminUser(String email, String name, String password, String username) {
		super(email, name, password, username);
		// TODO Auto-generated constructor stub
	}

	@Column(unique=true)
	private int employeeId;

	public int getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}
	
	
}
