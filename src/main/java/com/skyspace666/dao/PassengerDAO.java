package com.skyspace666.dao;

import java.util.List;

import com.skyspace666.dao.GenericDAO;
import com.skyspace666.domain.Passenger;





public interface PassengerDAO extends GenericDAO<Passenger, Integer> {
  
	List<Passenger> findAll();
	






}


