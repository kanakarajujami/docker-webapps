package com.nt.bindings;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserAccount {
private Integer userId;	
private String userName;
private String emailId;
private  Long contactNo;
private String gender="Female";
private LocalDate dob;
private Long aadharNo;

}
