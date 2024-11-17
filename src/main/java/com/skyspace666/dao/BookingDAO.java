package com.skyspace666.dao;

import java.util.List;

import com.skyspace666.dao.GenericDAO;
import com.skyspace666.domain.Booking;





public interface BookingDAO extends GenericDAO<Booking, Integer> {
  
	List<Booking> findAll();
	






}


