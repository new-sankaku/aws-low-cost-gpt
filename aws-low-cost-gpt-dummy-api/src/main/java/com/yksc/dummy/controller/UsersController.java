package com.yksc.dummy.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yksc.dummy.data.Data;
import com.yksc.model.db.User;
import com.yksc.model.db.UserSetting;
import com.yksc.model.util.IdGeneraterUtil;

@RestController
@RequestMapping("/Users")
public class UsersController {

	/**
	 * not use.
	 * 
	 * @param userMailAddress
	 * @return
	 */
	@GetMapping
	public ResponseEntity<Object> getUserByUserId( @RequestHeader("user-mail-address") String userMailAddress ) {
		User user = Data.usersMap.get( userMailAddress );
		if( user != null ) {
			return ResponseEntity.ok( user );
		} else {
			return ResponseEntity.status( HttpStatus.NOT_FOUND ).body( "not found getUserByAccountName" );
		}
	}

	@GetMapping("/UserSettings")
	public ResponseEntity<Object> getChatRoomMessageById( @RequestHeader("user-mail-address") String userMailAddress ) {
		UserSetting userSetting = Data.getUserSetting( userMailAddress );
		
		if( userSetting != null ) {
			return ResponseEntity.ok( userSetting );
		} else {
			return ResponseEntity.status( HttpStatus.NOT_FOUND ).body( "not found getChatRoomMessageById" );
		}
	}

	@PutMapping("/UserSettings")
	public ResponseEntity<Object> getChatRoomMessageById( @RequestHeader("user-mail-address") String userMailAddress, @RequestBody UserSetting userSetting ) {
		UserSetting resultUserSetting = Data.updateUserSetting( userMailAddress, userSetting );

		if( resultUserSetting != null ) {
			return ResponseEntity.ok( resultUserSetting );
		} else {
			return ResponseEntity.status( HttpStatus.NOT_FOUND ).body( "not found getChatRoomMessageById" );
		}
	}

	
	/**
	 * not use.
	 * 
	 * @param user
	 * @return
	 */
	@PostMapping
	public ResponseEntity<Object> createUser( @RequestBody User user ) {
		Data.usersMap.put( IdGeneraterUtil.nextGuid(), user );
		return ResponseEntity.ok( user );
	}

	
	/**
	 * not use.
	 * 
	 * @param userMailAddress
	 * @param updateUser
	 * @return
	 */
	@PutMapping
	public ResponseEntity<Object> updateUser( @RequestHeader("user-mail-address") String userMailAddress, @RequestBody User updateUser ) {
		User user = Data.usersMap.get( userMailAddress );
		if( user != null ) {
			user.setFirstName( updateUser.getFirstName() );
			user.setLastName( updateUser.getLastName() );
			user.setEmail( updateUser.getEmail() );
			return ResponseEntity.ok( user );
		} else {
			return ResponseEntity.status( HttpStatus.NOT_FOUND ).body( "not found getUserByAccountName" );
		}
	}

	/**
	 * not use.
	 * 
	 * @param userMailAddress
	 */
	@DeleteMapping
	public void deleteUser( @RequestHeader("user-mail-address") String userMailAddress ) {
		// ユーザーを削除
		Data.usersMap.remove( userMailAddress );
	}
}
