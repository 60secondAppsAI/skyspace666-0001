package com.skyspace666.dao;

import java.util.List;

import com.skyspace666.dao.GenericDAO;
import com.skyspace666.domain.Insurance;





public interface InsuranceDAO extends GenericDAO<Insurance, Integer> {
  
	List<Insurance> findAll();
	






}


