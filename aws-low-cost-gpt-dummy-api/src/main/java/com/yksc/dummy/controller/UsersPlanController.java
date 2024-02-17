package com.yksc.dummy.controller;

import java.util.Collection;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yksc.dummy.data.Data;
import com.yksc.model.rest.AiModel;

@RestController
@RequestMapping("/UsersPlan")
public class UsersPlanController {

	
	@GetMapping
	public ResponseEntity<Object> getUserPlanByUserId( @RequestHeader("user-mail-address") String userMailAddress ) {
		//TODO all result.
		Collection<AiModel> aiModelList = Data.aiModelMap.values();
		if( aiModelList != null ) {
			return ResponseEntity.ok( aiModelList );
		} else {
			return ResponseEntity.status( HttpStatus.NOT_FOUND ).body( "not found getUserByAccountName" );
		}
	}

}
