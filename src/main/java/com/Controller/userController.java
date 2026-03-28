package com.Controller;

import java.io.File;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.Entity.User;
import com.Service.userService;
import com.dto.ApiResponse;
import com.dto.UserDto;
import com.dto.LoginRequest;
import com.exception.ResourceNotFoundException;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/user/action")
public class userController {

    @Autowired
    private userService us;

    @Autowired
    private JavaMailSender jms;

    //  CREATE USER
    @PostMapping("/registeruser")
    public ResponseEntity<ApiResponse<UserDto>> registerUser(@Validated @RequestBody User user) {

        log.info("Registering user: " + user);

        User savedUser = us.getuserInservice(user);

        UserDto dto = new UserDto(
                savedUser.getUid(),
                savedUser.getUname(),
                savedUser.getUaddress(),
                savedUser.getSalary()
        );

        return new ResponseEntity<>(
                new ApiResponse<>("User registered successfully", 201, dto),
                HttpStatus.CREATED
        );
    }

    // LOGIN (JWT)
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {

        log.info("Login request for email: " + request.getEmail());

        String token = us.login(request.getEmail(), request.getPassword());

        if (token == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid credentials");
        }

        return ResponseEntity.ok(token); //  returns JWT token
    }

    //  GET USER BY ID
    @GetMapping("/getuserId/{id}")
    public ResponseEntity<ApiResponse<UserDto>> getuserById(@PathVariable int id) {

        UserDto user = us.getuserByidinservice(id);

        if (user == null) {
            throw new ResourceNotFoundException("User not found with id: " + id);
        }

        return ResponseEntity.ok(
                new ApiResponse<>("User found", 200, user)
        );
    }

    //  GET ALL USERS
    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<UserDto>>> getAllUsers() {

        return ResponseEntity.ok(
                new ApiResponse<>("All users fetched successfully", 200, us.getAllUsers())
        );
    }

    //  UPDATE USER
    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<UserDto>> updateUser(
            @PathVariable int id,
            @RequestBody User user) {

        UserDto updated = us.updateUser(id, user);

        if (updated == null) {
            throw new ResourceNotFoundException("User not found with id: " + id);
        }

        return ResponseEntity.ok(
                new ApiResponse<>("User updated successfully", 200, updated)
        );
    }

    //  DELETE USER
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<String>> deleteUser(@PathVariable int id) {

        boolean deleted = us.deleteUser(id);

        if (!deleted) {
            throw new ResourceNotFoundException("User not found with id: " + id);
        }

        return ResponseEntity.ok(
                new ApiResponse<>("User deleted successfully", 200, "Deleted")
        );
    }

    //  SEND EMAIL
    @PostMapping("/sendmail")
    public ResponseEntity<ApiResponse<Void>> sendEmail() throws MessagingException {

        MimeMessage mm = jms.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mm, true);

        helper.setFrom("manojbachhav281@gmail.com");
        helper.setTo("manojbachhav281@gmail.com");
        helper.setSubject("Mime Message sending :- ");
        helper.setText("Sending Attachments");
        helper.addAttachment("offer letter", new File("C:\\offerletter.pdf"));

        jms.send(mm);

        return ResponseEntity.ok(
                new ApiResponse<>("Mail sent successfully", 200, null)
        );
    }
}