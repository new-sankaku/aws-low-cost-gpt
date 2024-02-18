package com.yksc.dummy.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yksc.dummy.data.Data;
import com.yksc.model.db.UserSetting;

@RestController
@RequestMapping("/UserSettings")
public class UserSettingsController {

	@GetMapping
	public ResponseEntity<Object> getUserSettings( @RequestHeader("user-mail-address") String userMailAddress ) {
		System.out.println("getUserSettings call");
		try {
			//Pretending to work
			Thread.sleep( 5000L );
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		try {
			//Pretending to work
			Thread.sleep( 3000L );
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		UserSetting userSetting = Data.getUserSetting( userMailAddress );
		
		if( userSetting != null ) {
			return ResponseEntity.ok( userSetting );
		} else {
			return ResponseEntity.status( HttpStatus.NOT_FOUND ).body( "not found getChatRoomMessageById" );
		}
	}

	@PostMapping
	public ResponseEntity<Object> postUserSettings( @RequestHeader("user-mail-address") String userMailAddress, @RequestBody UserSetting userSetting ) {
		System.out.println("updateUserSettings call");
		try {
			//Pretending to work
			Thread.sleep( 5000L );
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		try {
			//Pretending to work
			Thread.sleep( 3000L );
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		UserSetting resultUserSetting = Data.updateUserSetting( userMailAddress, userSetting );
		
		if( resultUserSetting != null ) {
			return ResponseEntity.ok( resultUserSetting );
		} else {
			return ResponseEntity.status( HttpStatus.NOT_FOUND ).body( "not found getChatRoomMessageById" );
		}
	}
	
}
