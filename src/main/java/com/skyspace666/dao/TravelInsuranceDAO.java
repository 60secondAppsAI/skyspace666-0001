package com.skyspace666.dao;

import java.util.List;

import com.skyspace666.dao.GenericDAO;
import com.skyspace666.domain.TravelInsurance;





public interface TravelInsuranceDAO extends GenericDAO<TravelInsurance, Integer> {
  
	List<TravelInsurance> findAll();
	






}


