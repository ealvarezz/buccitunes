package com.buccitunes.model;

import javax.persistence.Column;
import javax.persistence.Entity;

import com.buccitunes.constants.UserRole;

@Entity(name="AdminUser")
public class AdminUser extends User {
	
	public AdminUser() {
		super();
		super.setRole(UserRole.ADMIN);
	}
	
	public AdminUser(String email, String name, String password, String username) {
		super(email, name, password, username);
		super.setRole(UserRole.ADMIN);
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
