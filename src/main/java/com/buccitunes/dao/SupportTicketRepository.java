package com.buccitunes.dao;


import org.springframework.data.repository.CrudRepository;
import com.buccitunes.model.SupportTicket;

public interface SupportTicketRepository extends CrudRepository<SupportTicket, Integer> {

}
