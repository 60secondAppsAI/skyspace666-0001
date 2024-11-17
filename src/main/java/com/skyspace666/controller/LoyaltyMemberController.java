package com.skyspace666.controller;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.List;
import java.util.ArrayList;


import com.skyspace666.util.Util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.sql.Timestamp;
import java.util.Date;

import com.skyspace666.domain.LoyaltyMember;
import com.skyspace666.dto.LoyaltyMemberDTO;
import com.skyspace666.dto.LoyaltyMemberSearchDTO;
import com.skyspace666.dto.LoyaltyMemberPageDTO;
import com.skyspace666.service.LoyaltyMemberService;
import com.skyspace666.dto.common.RequestDTO;
import com.skyspace666.dto.common.ResultDTO;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;




@CrossOrigin(origins = "*")
@RequestMapping("/loyaltyMember")
@RestController
public class LoyaltyMemberController {

	private final static Logger logger = LoggerFactory.getLogger(LoyaltyMemberController.class);

	@Autowired
	LoyaltyMemberService loyaltyMemberService;



	@RequestMapping(value="/", method = RequestMethod.GET)
	public List<LoyaltyMember> getAll() {

		List<LoyaltyMember> loyaltyMembers = loyaltyMemberService.findAll();
		
		return loyaltyMembers;	
	}

	@GetMapping(value = "/{loyaltyMemberId}")
	@ResponseBody
	public LoyaltyMemberDTO getLoyaltyMember(@PathVariable Integer loyaltyMemberId) {
		
		return (loyaltyMemberService.getLoyaltyMemberDTOById(loyaltyMemberId));
	}

 	@RequestMapping(value = "/addLoyaltyMember", method = RequestMethod.POST)
	public ResponseEntity<?> addLoyaltyMember(@RequestBody LoyaltyMemberDTO loyaltyMemberDTO, HttpServletRequest request) {

		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = loyaltyMemberService.addLoyaltyMember(loyaltyMemberDTO, requestDTO);
		
		return result.asResponseEntity();
	}

	@GetMapping("/loyaltyMembers")
	public ResponseEntity<LoyaltyMemberPageDTO> getLoyaltyMembers(LoyaltyMemberSearchDTO loyaltyMemberSearchDTO) {
 
		return loyaltyMemberService.getLoyaltyMembers(loyaltyMemberSearchDTO);
	}	

	@RequestMapping(value = "/updateLoyaltyMember", method = RequestMethod.POST)
	public ResponseEntity<?> updateLoyaltyMember(@RequestBody LoyaltyMemberDTO loyaltyMemberDTO, HttpServletRequest request) {
		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = loyaltyMemberService.updateLoyaltyMember(loyaltyMemberDTO, requestDTO);
		
//		if (result.isSuccessful()) {
//		}

		return result.asResponseEntity();
	}



}
