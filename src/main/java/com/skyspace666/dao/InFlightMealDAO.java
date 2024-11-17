package com.skyspace666.dao;

import java.util.List;

import com.skyspace666.dao.GenericDAO;
import com.skyspace666.domain.InFlightMeal;





public interface InFlightMealDAO extends GenericDAO<InFlightMeal, Integer> {
  
	List<InFlightMeal> findAll();
	






}


