package com.skyspace666.service.impl;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;



import com.skyspace666.dao.GenericDAO;
import com.skyspace666.service.GenericService;
import com.skyspace666.service.impl.GenericServiceImpl;
import com.skyspace666.dao.LoyaltyMemberDAO;
import com.skyspace666.domain.LoyaltyMember;
import com.skyspace666.dto.LoyaltyMemberDTO;
import com.skyspace666.dto.LoyaltyMemberSearchDTO;
import com.skyspace666.dto.LoyaltyMemberPageDTO;
import com.skyspace666.dto.LoyaltyMemberConvertCriteriaDTO;
import com.skyspace666.dto.common.RequestDTO;
import com.skyspace666.dto.common.ResultDTO;
import com.skyspace666.service.LoyaltyMemberService;
import com.skyspace666.util.ControllerUtils;





@Service
public class LoyaltyMemberServiceImpl extends GenericServiceImpl<LoyaltyMember, Integer> implements LoyaltyMemberService {

    private final static Logger logger = LoggerFactory.getLogger(LoyaltyMemberServiceImpl.class);

	@Autowired
	LoyaltyMemberDAO loyaltyMemberDao;

	


	@Override
	public GenericDAO<LoyaltyMember, Integer> getDAO() {
		return (GenericDAO<LoyaltyMember, Integer>) loyaltyMemberDao;
	}
	
	public List<LoyaltyMember> findAll () {
		List<LoyaltyMember> loyaltyMembers = loyaltyMemberDao.findAll();
		
		return loyaltyMembers;	
		
	}

	public ResultDTO addLoyaltyMember(LoyaltyMemberDTO loyaltyMemberDTO, RequestDTO requestDTO) {

		LoyaltyMember loyaltyMember = new LoyaltyMember();

		loyaltyMember.setLoyaltyMemberId(loyaltyMemberDTO.getLoyaltyMemberId());


		loyaltyMember.setPoints(loyaltyMemberDTO.getPoints());


		LocalDate localDate = LocalDate.now();
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());

		loyaltyMember = loyaltyMemberDao.save(loyaltyMember);
		
		ResultDTO result = new ResultDTO();
		return result;
	}
	
	public Page<LoyaltyMember> getAllLoyaltyMembers(Pageable pageable) {
		return loyaltyMemberDao.findAll(pageable);
	}

	public Page<LoyaltyMember> getAllLoyaltyMembers(Specification<LoyaltyMember> spec, Pageable pageable) {
		return loyaltyMemberDao.findAll(spec, pageable);
	}

	public ResponseEntity<LoyaltyMemberPageDTO> getLoyaltyMembers(LoyaltyMemberSearchDTO loyaltyMemberSearchDTO) {
	
			Integer loyaltyMemberId = loyaltyMemberSearchDTO.getLoyaltyMemberId(); 
 			Integer points = loyaltyMemberSearchDTO.getPoints(); 
 			String sortBy = loyaltyMemberSearchDTO.getSortBy();
			String sortOrder = loyaltyMemberSearchDTO.getSortOrder();
			String searchQuery = loyaltyMemberSearchDTO.getSearchQuery();
			Integer page = loyaltyMemberSearchDTO.getPage();
			Integer size = loyaltyMemberSearchDTO.getSize();

	        Specification<LoyaltyMember> spec = Specification.where(null);

			spec = ControllerUtils.andIfNecessary(spec, loyaltyMemberId, "loyaltyMemberId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, points, "points"); 
			

		if (searchQuery != null && !searchQuery.isEmpty()) {
			spec = spec.and((root, query, cb) -> cb.or(

		));}
		
		Sort sort = Sort.unsorted();
		if (sortBy != null && !sortBy.isEmpty() && sortOrder != null && !sortOrder.isEmpty()) {
			if (sortOrder.equalsIgnoreCase("asc")) {
				sort = Sort.by(sortBy).ascending();
			} else if (sortOrder.equalsIgnoreCase("desc")) {
				sort = Sort.by(sortBy).descending();
			}
		}
		Pageable pageable = PageRequest.of(page, size, sort);

		Page<LoyaltyMember> loyaltyMembers = this.getAllLoyaltyMembers(spec, pageable);
		
		//System.out.println(String.valueOf(loyaltyMembers.getTotalElements()) + " total ${classNamelPlural}, viewing page X of " + String.valueOf(loyaltyMembers.getTotalPages()));
		
		List<LoyaltyMember> loyaltyMembersList = loyaltyMembers.getContent();
		
		LoyaltyMemberConvertCriteriaDTO convertCriteria = new LoyaltyMemberConvertCriteriaDTO();
		List<LoyaltyMemberDTO> loyaltyMemberDTOs = this.convertLoyaltyMembersToLoyaltyMemberDTOs(loyaltyMembersList,convertCriteria);
		
		LoyaltyMemberPageDTO loyaltyMemberPageDTO = new LoyaltyMemberPageDTO();
		loyaltyMemberPageDTO.setLoyaltyMembers(loyaltyMemberDTOs);
		loyaltyMemberPageDTO.setTotalElements(loyaltyMembers.getTotalElements());
		return ResponseEntity.ok(loyaltyMemberPageDTO);
	}

	public List<LoyaltyMemberDTO> convertLoyaltyMembersToLoyaltyMemberDTOs(List<LoyaltyMember> loyaltyMembers, LoyaltyMemberConvertCriteriaDTO convertCriteria) {
		
		List<LoyaltyMemberDTO> loyaltyMemberDTOs = new ArrayList<LoyaltyMemberDTO>();
		
		for (LoyaltyMember loyaltyMember : loyaltyMembers) {
			loyaltyMemberDTOs.add(convertLoyaltyMemberToLoyaltyMemberDTO(loyaltyMember,convertCriteria));
		}
		
		return loyaltyMemberDTOs;

	}
	
	public LoyaltyMemberDTO convertLoyaltyMemberToLoyaltyMemberDTO(LoyaltyMember loyaltyMember, LoyaltyMemberConvertCriteriaDTO convertCriteria) {
		
		LoyaltyMemberDTO loyaltyMemberDTO = new LoyaltyMemberDTO();
		
		loyaltyMemberDTO.setLoyaltyMemberId(loyaltyMember.getLoyaltyMemberId());

	
		loyaltyMemberDTO.setPoints(loyaltyMember.getPoints());

	

		
		return loyaltyMemberDTO;
	}

	public ResultDTO updateLoyaltyMember(LoyaltyMemberDTO loyaltyMemberDTO, RequestDTO requestDTO) {
		
		LoyaltyMember loyaltyMember = loyaltyMemberDao.getById(loyaltyMemberDTO.getLoyaltyMemberId());

		loyaltyMember.setLoyaltyMemberId(ControllerUtils.setValue(loyaltyMember.getLoyaltyMemberId(), loyaltyMemberDTO.getLoyaltyMemberId()));

		loyaltyMember.setPoints(ControllerUtils.setValue(loyaltyMember.getPoints(), loyaltyMemberDTO.getPoints()));



        loyaltyMember = loyaltyMemberDao.save(loyaltyMember);
		
		ResultDTO result = new ResultDTO();
		return result;
	}

	public LoyaltyMemberDTO getLoyaltyMemberDTOById(Integer loyaltyMemberId) {
	
		LoyaltyMember loyaltyMember = loyaltyMemberDao.getById(loyaltyMemberId);
			
		
		LoyaltyMemberConvertCriteriaDTO convertCriteria = new LoyaltyMemberConvertCriteriaDTO();
		return(this.convertLoyaltyMemberToLoyaltyMemberDTO(loyaltyMember,convertCriteria));
	}







}
