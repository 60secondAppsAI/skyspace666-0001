package com.skyspace666.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;

import com.skyspace666.domain.LoyaltyMember;
import com.skyspace666.dto.LoyaltyMemberDTO;
import com.skyspace666.dto.LoyaltyMemberSearchDTO;
import com.skyspace666.dto.LoyaltyMemberPageDTO;
import com.skyspace666.dto.LoyaltyMemberConvertCriteriaDTO;
import com.skyspace666.service.GenericService;
import com.skyspace666.dto.common.RequestDTO;
import com.skyspace666.dto.common.ResultDTO;
import java.util.List;
import java.util.Optional;





public interface LoyaltyMemberService extends GenericService<LoyaltyMember, Integer> {

	List<LoyaltyMember> findAll();

	ResultDTO addLoyaltyMember(LoyaltyMemberDTO loyaltyMemberDTO, RequestDTO requestDTO);

	ResultDTO updateLoyaltyMember(LoyaltyMemberDTO loyaltyMemberDTO, RequestDTO requestDTO);

    Page<LoyaltyMember> getAllLoyaltyMembers(Pageable pageable);

    Page<LoyaltyMember> getAllLoyaltyMembers(Specification<LoyaltyMember> spec, Pageable pageable);

	ResponseEntity<LoyaltyMemberPageDTO> getLoyaltyMembers(LoyaltyMemberSearchDTO loyaltyMemberSearchDTO);
	
	List<LoyaltyMemberDTO> convertLoyaltyMembersToLoyaltyMemberDTOs(List<LoyaltyMember> loyaltyMembers, LoyaltyMemberConvertCriteriaDTO convertCriteria);

	LoyaltyMemberDTO getLoyaltyMemberDTOById(Integer loyaltyMemberId);







}





