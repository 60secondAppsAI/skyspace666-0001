package com.skyspace666.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;

import com.skyspace666.domain.Terminal;
import com.skyspace666.dto.TerminalDTO;
import com.skyspace666.dto.TerminalSearchDTO;
import com.skyspace666.dto.TerminalPageDTO;
import com.skyspace666.dto.TerminalConvertCriteriaDTO;
import com.skyspace666.service.GenericService;
import com.skyspace666.dto.common.RequestDTO;
import com.skyspace666.dto.common.ResultDTO;
import java.util.List;
import java.util.Optional;





public interface TerminalService extends GenericService<Terminal, Integer> {

	List<Terminal> findAll();

	ResultDTO addTerminal(TerminalDTO terminalDTO, RequestDTO requestDTO);

	ResultDTO updateTerminal(TerminalDTO terminalDTO, RequestDTO requestDTO);

    Page<Terminal> getAllTerminals(Pageable pageable);

    Page<Terminal> getAllTerminals(Specification<Terminal> spec, Pageable pageable);

	ResponseEntity<TerminalPageDTO> getTerminals(TerminalSearchDTO terminalSearchDTO);
	
	List<TerminalDTO> convertTerminalsToTerminalDTOs(List<Terminal> terminals, TerminalConvertCriteriaDTO convertCriteria);

	TerminalDTO getTerminalDTOById(Integer terminalId);







}





