package com.skyspace666.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;

import com.skyspace666.domain.Plane;
import com.skyspace666.dto.PlaneDTO;
import com.skyspace666.dto.PlaneSearchDTO;
import com.skyspace666.dto.PlanePageDTO;
import com.skyspace666.dto.PlaneConvertCriteriaDTO;
import com.skyspace666.service.GenericService;
import com.skyspace666.dto.common.RequestDTO;
import com.skyspace666.dto.common.ResultDTO;
import java.util.List;
import java.util.Optional;





public interface PlaneService extends GenericService<Plane, Integer> {

	List<Plane> findAll();

	ResultDTO addPlane(PlaneDTO planeDTO, RequestDTO requestDTO);

	ResultDTO updatePlane(PlaneDTO planeDTO, RequestDTO requestDTO);

    Page<Plane> getAllPlanes(Pageable pageable);

    Page<Plane> getAllPlanes(Specification<Plane> spec, Pageable pageable);

	ResponseEntity<PlanePageDTO> getPlanes(PlaneSearchDTO planeSearchDTO);
	
	List<PlaneDTO> convertPlanesToPlaneDTOs(List<Plane> planes, PlaneConvertCriteriaDTO convertCriteria);

	PlaneDTO getPlaneDTOById(Integer planeId);







}





