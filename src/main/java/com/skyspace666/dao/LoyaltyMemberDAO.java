package com.skyspace666.dao;

import java.util.List;

import com.skyspace666.dao.GenericDAO;
import com.skyspace666.domain.LoyaltyMember;





public interface LoyaltyMemberDAO extends GenericDAO<LoyaltyMember, Integer> {
  
	List<LoyaltyMember> findAll();
	






}


