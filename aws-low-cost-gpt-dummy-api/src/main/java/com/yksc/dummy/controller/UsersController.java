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
import com.yksc.dummy.model.User;
import com.yksc.dummy.util.IdGeneraterUtil;

@RestController
@RequestMapping("/Users")
public class UsersController {

	@GetMapping
	public ResponseEntity<Object> getUserByUserId( @RequestHeader("user-mail-address") String userMailAddress ) {
		User user = Data.usersMap.get( userMailAddress );
		if( user != null ) {
			return ResponseEntity.ok( user );
		} else {
			return ResponseEntity.status( HttpStatus.NOT_FOUND ).body( "not found getUserByAccountName" );
		}
	}

	@PostMapping
	public ResponseEntity<Object> createUser( @RequestBody User user ) {
		Data.usersMap.put( IdGeneraterUtil.nextGuid(), user );
		return ResponseEntity.ok( user );
	}

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

	@DeleteMapping
	public void deleteUser( @RequestHeader("user-mail-address") String userMailAddress ) {
		// ユーザーを削除
		Data.usersMap.remove( userMailAddress );
	}
}
