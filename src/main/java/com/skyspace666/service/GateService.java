package com.skyspace666.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;

import com.skyspace666.domain.Gate;
import com.skyspace666.dto.GateDTO;
import com.skyspace666.dto.GateSearchDTO;
import com.skyspace666.dto.GatePageDTO;
import com.skyspace666.dto.GateConvertCriteriaDTO;
import com.skyspace666.service.GenericService;
import com.skyspace666.dto.common.RequestDTO;
import com.skyspace666.dto.common.ResultDTO;
import java.util.List;
import java.util.Optional;





public interface GateService extends GenericService<Gate, Integer> {

	List<Gate> findAll();

	ResultDTO addGate(GateDTO gateDTO, RequestDTO requestDTO);

	ResultDTO updateGate(GateDTO gateDTO, RequestDTO requestDTO);

    Page<Gate> getAllGates(Pageable pageable);

    Page<Gate> getAllGates(Specification<Gate> spec, Pageable pageable);

	ResponseEntity<GatePageDTO> getGates(GateSearchDTO gateSearchDTO);
	
	List<GateDTO> convertGatesToGateDTOs(List<Gate> gates, GateConvertCriteriaDTO convertCriteria);

	GateDTO getGateDTOById(Integer gateId);







}




