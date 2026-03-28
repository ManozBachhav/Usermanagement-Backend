package com.Service;

import java.util.List;
import java.util.stream.Collectors;

import com.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Entity.User;
import com.dto.UserDto;
import com.repo.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserServiceIMPL implements userService {

	@Autowired
	private UserRepository repo;

	//  must autowire
	@Autowired
	private JwtUtil jwtUtil;

	//  CREATE USER
	@Override
	public User getuserInservice(User user) {
		log.info("Saving user: " + user);
		return repo.save(user);
	}

	//  LOGIN (JWT)
	public String login(String email, String password) {

		log.info("Login attempt for email: " + email);

		User user = repo.findByEmail(email);

		if (user == null) {
			log.warn("User not found with email: " + email);
			return null;
		}

		if (!user.getPassword().equals(password)) {
			log.warn("Invalid password for email: " + email);
			return null;
		}

		// 🔥 generate token
		String token = jwtUtil.generateToken(email);

		log.info("JWT generated successfully");

		return token;
	}

	//  GET USER BY ID
	@Override
	public UserDto getuserByidinservice(int uid) {

		log.info("Fetching user with id: " + uid);

		User user = repo.findById(uid).orElse(null);

		if (user == null) {
			log.warn("User not found with id: " + uid);
			return null;
		}

		return new UserDto(
				user.getUid(),
				user.getUname(),
				user.getUaddress(),
				user.getSalary()
		);
	}

	//  GET ALL USERS
	@Override
	public List<UserDto> getAllUsers() {

		return repo.findAll()
				.stream()
				.map(u -> new UserDto(
						u.getUid(),
						u.getUname(),
						u.getUaddress(),
						u.getSalary()
				))
				.collect(Collectors.toList());
	}

	//  UPDATE USER
	@Override
	public UserDto updateUser(int id, User user) {

		User existing = repo.findById(id).orElse(null);

		if (existing == null) {
			log.warn("User not found for update: " + id);
			return null;
		}

		existing.setUname(user.getUname());
		existing.setUaddress(user.getUaddress());
		existing.setSalary(user.getSalary());

		User updated = repo.save(existing);

		return new UserDto(
				updated.getUid(),
				updated.getUname(),
				updated.getUaddress(),
				updated.getSalary()
		);
	}

	//  DELETE USER
	@Override
	public boolean deleteUser(int id) {

		if (!repo.existsById(id)) {
			log.warn("User not found for delete: " + id);
			return false;
		}

		repo.deleteById(id);
		log.info("User deleted with id: " + id);
		return true;
	}
}