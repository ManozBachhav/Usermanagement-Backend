package com.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

	private int uid;
	private String uname;
	private String uaddress;
	private int salary;
}

