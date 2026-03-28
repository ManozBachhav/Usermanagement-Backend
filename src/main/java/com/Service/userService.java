package com.Service;

import java.util.List;

import com.Entity.User;
import com.dto.UserDto;

public interface userService {

	//  CREATE (must return User)
	User getuserInservice(User user);

	// READ by ID
	UserDto getuserByidinservice(int uid);

	//  READ all
	List<UserDto> getAllUsers();

	//  UPDATE
	UserDto updateUser(int id, User user);

	String login(String email, String password);

	//  DELETE
	boolean deleteUser(int id);
}