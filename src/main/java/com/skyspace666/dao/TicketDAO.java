package com.skyspace666.dao;

import java.util.List;

import com.skyspace666.dao.GenericDAO;
import com.skyspace666.domain.Ticket;





public interface TicketDAO extends GenericDAO<Ticket, Integer> {
  
	List<Ticket> findAll();
	






}

