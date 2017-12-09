package com.buccitunes.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;




@Entity(name="SUPPORT_TICKET")
public class SupportTicket {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	@Column(columnDefinition = "VARCHAR(5000)")
	private String description;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "user_id")
	@JsonIgnoreProperties(value = "supportTickets")
	private User ticketHolder;
	
	public SupportTicket(){}
	
	public SupportTicket(String description, User ticketHolder) {
		this.description = description;
		this.ticketHolder = ticketHolder;
	}


	public int getId() {
		return id;
	}


	public String getDescription() {
		return description;
	}


	public User getTicketHolder() {
		return ticketHolder;
	}


	public void setId(int id) {
		this.id = id;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public void setTicketHolder(User ticketHolder) {
		this.ticketHolder = ticketHolder;
	}
	
	
	
	

}
