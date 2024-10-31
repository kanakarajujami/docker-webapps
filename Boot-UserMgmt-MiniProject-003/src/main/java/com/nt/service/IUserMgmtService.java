package com.nt.service;

import java.util.List;

import com.nt.bindings.ActivateUser;
import com.nt.bindings.ForgotPassword;
import com.nt.bindings.LoginCredintials;
import com.nt.bindings.UserAccount;

public interface IUserMgmtService {
public String enrollUser(UserAccount userData) throws Exception;
public String activateUserAccount(ActivateUser userActive);
public String login(LoginCredintials credintials);
public List<UserAccount> GetAllUserAccounts();
public  UserAccount showUserById(Integer userId);
public  UserAccount showUserByEmailAndName(String email,String name);
public  String updateUserDetails(UserAccount userData);
public String deleteUserById(Integer userId);
public String changeStatusById(Integer userId,String status);
public String recoveryPassword(ForgotPassword recoveryPassword) throws Exception;
}
