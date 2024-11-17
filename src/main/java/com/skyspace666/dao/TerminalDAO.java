package com.skyspace666.dao;

import java.util.List;

import com.skyspace666.dao.GenericDAO;
import com.skyspace666.domain.Terminal;





public interface TerminalDAO extends GenericDAO<Terminal, Integer> {
  
	List<Terminal> findAll();
	






}


