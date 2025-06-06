package com.skyspace666.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;

import com.skyspace666.domain.CrewAssignment;
import com.skyspace666.dto.CrewAssignmentDTO;
import com.skyspace666.dto.CrewAssignmentSearchDTO;
import com.skyspace666.dto.CrewAssignmentPageDTO;
import com.skyspace666.dto.CrewAssignmentConvertCriteriaDTO;
import com.skyspace666.service.GenericService;
import com.skyspace666.dto.common.RequestDTO;
import com.skyspace666.dto.common.ResultDTO;
import java.util.List;
import java.util.Optional;





public interface CrewAssignmentService extends GenericService<CrewAssignment, Integer> {

	List<CrewAssignment> findAll();

	ResultDTO addCrewAssignment(CrewAssignmentDTO crewAssignmentDTO, RequestDTO requestDTO);

	ResultDTO updateCrewAssignment(CrewAssignmentDTO crewAssignmentDTO, RequestDTO requestDTO);

    Page<CrewAssignment> getAllCrewAssignments(Pageable pageable);

    Page<CrewAssignment> getAllCrewAssignments(Specification<CrewAssignment> spec, Pageable pageable);

	ResponseEntity<CrewAssignmentPageDTO> getCrewAssignments(CrewAssignmentSearchDTO crewAssignmentSearchDTO);
	
	List<CrewAssignmentDTO> convertCrewAssignmentsToCrewAssignmentDTOs(List<CrewAssignment> crewAssignments, CrewAssignmentConvertCriteriaDTO convertCriteria);

	CrewAssignmentDTO getCrewAssignmentDTOById(Integer crewAssignmentId);







}





