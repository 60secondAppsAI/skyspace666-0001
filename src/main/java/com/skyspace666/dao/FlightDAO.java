package com.skyspace666.dao;

import java.util.List;

import com.skyspace666.dao.GenericDAO;
import com.skyspace666.domain.Flight;





public interface FlightDAO extends GenericDAO<Flight, Integer> {
  
	List<Flight> findAll();
	






}


