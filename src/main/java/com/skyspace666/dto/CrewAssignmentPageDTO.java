package com.skyspace666.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CrewAssignmentPageDTO {

	private Integer page = 0;
	private Long totalElements = 0L;

	private List<CrewAssignmentDTO> crewAssignments;
}





