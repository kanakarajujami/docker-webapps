package com.nt.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nt.bindings.UserAccount;
import com.nt.entity.UserMaster;

public interface IUserMasterRepository extends JpaRepository<UserMaster, Integer> {
      public List<UserMaster> findByEmailIdAndPassword(String emailId,String password);
      public UserMaster findByEmailIdAndUserName(String emailId,String userName);
}
