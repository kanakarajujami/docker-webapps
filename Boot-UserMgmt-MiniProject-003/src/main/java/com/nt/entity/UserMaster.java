package com.nt.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="USER_MASTER")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserMaster {
@Id	
@SequenceGenerator(name="gen1",sequenceName="user_seq",initialValue=10000,allocationSize=1)
@GeneratedValue(generator="gen1",strategy=GenerationType.SEQUENCE)
private Integer userId;
@Column(length=30)
private String userName;
@Column(length=20)
private String password;
@Column
private String emailId;
private Long contactNo;
@Column(length=10)
private String gender;
private LocalDate dob;
private String activwSW;
@CreationTimestamp
@Column(insertable=true,updatable=false)
private LocalDateTime creationTime;
@UpdateTimestamp
@Column(insertable=false,updatable=true)
private LocalDateTime updationTime;
@Column(length=30)
private String createdBy;
@Column(length=30)
private String updateddBy;
}
