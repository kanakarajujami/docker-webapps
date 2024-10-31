package com.nt.ms;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nt.bindings.ActivateUser;
import com.nt.bindings.ForgotPassword;
import com.nt.bindings.LoginCredintials;
import com.nt.bindings.UserAccount;
import com.nt.service.IUserMgmtService;

@RestController
@RequestMapping("/user-api")
public class UserMgmtOperationsController {
	@Autowired
  private  IUserMgmtService userService;
  @PostMapping("/save")
  public ResponseEntity<String>	 saveUser(@RequestBody UserAccount account){
	  try {
	        String resultMessage=userService.enrollUser(account);
	         return  new ResponseEntity<String>(resultMessage,HttpStatus.OK);
	  }
	   catch(Exception e){
		  e.printStackTrace();
		  return new ResponseEntity<String>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
	   }
  }//end of method
  @PostMapping("/activate")
  public ResponseEntity<String> activateUser(@RequestBody ActivateUser user){
	  try {
		   String resultMessage=userService.activateUserAccount(user);
		   return new ResponseEntity<String>(resultMessage,HttpStatus.OK);
	  }
	  catch(Exception e){
		  e.printStackTrace();
		  return new ResponseEntity<String>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
	   }
  }//end of method
  @PostMapping("/login")
 public ResponseEntity<String> userLogin(@RequestBody LoginCredintials login){
	 try {
		 String resultMessage=userService.login(login);
		 return new ResponseEntity<String>(resultMessage,HttpStatus.OK);
	 }
	 catch(Exception e){
		  e.printStackTrace();
		  return new ResponseEntity<String>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
	   }
 } //end of method
  @GetMapping("/allUsers")
  public ResponseEntity<?> findAllUsers(){
	  try {
		  List<UserAccount> listUsers=userService.GetAllUserAccounts();
		  return new ResponseEntity<List<UserAccount>>(listUsers,HttpStatus.OK);
	  }
	   catch(Exception e) {
		    e.printStackTrace();
		    return new ResponseEntity<String>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
	   }
  }//end of method
  @GetMapping("/find/{userId}")
  public ResponseEntity<?> showUserAccountById(@PathVariable Integer userId){
	  try {
		  UserAccount account=userService.showUserById(userId);
		  return new ResponseEntity<UserAccount>(account,HttpStatus.OK);
	  }
	  catch(Exception e) {
		    e.printStackTrace();
		    return new ResponseEntity<String>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
	   }
	  }//end of method
    @GetMapping("/find/{emailId}/{name}")
  public ResponseEntity<?> showUserByMailAndName(@PathVariable String emailId,@PathVariable String name){
	  try {
		  UserAccount account=userService.showUserByEmailAndName(emailId, name);
		  return new ResponseEntity<UserAccount>(account,HttpStatus.OK);
	  }
	  catch(Exception e) {
		    e.printStackTrace();
		    return new ResponseEntity<String>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
	  }
  }//end of method
    @PutMapping("/update")
    public ResponseEntity<String> updateUser(@RequestBody UserAccount account){
    	try {
          String resultMessage=userService.updateUserDetails(account);
          return new ResponseEntity<String>(resultMessage,HttpStatus.OK);
    }
    catch(Exception e) {
	    e.printStackTrace();
	    return new ResponseEntity<String>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
   }
    }//end method
    @PatchMapping("/changeStatus/{userId}/{status}")
    public ResponseEntity<String> changeUserStatusById(@PathVariable Integer userId,@PathVariable String status){
    	try {
    		String resultMessage=userService.changeStatusById(userId, status);
    		return new ResponseEntity<String>(resultMessage,HttpStatus.OK);
    	}
    	 catch(Exception e) {
    		    e.printStackTrace();
    		    return new ResponseEntity<String>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
    	   }
    }//end of method
    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<String> deleteUserById(@PathVariable Integer userId){
    	try {
    		String resultMessage=userService.deleteUserById(userId);
    		return new ResponseEntity<String>(resultMessage,HttpStatus.OK);
    	}
    	catch(Exception e) {
		    e.printStackTrace();
		    return new ResponseEntity<String>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
	   }
    }//end of method
    @PostMapping("/recoverypwd")
    public ResponseEntity<String> recoveryPassword(@RequestBody ForgotPassword password){
    	try {
    		String resultMsg=userService.recoveryPassword(password);
    		return new ResponseEntity<String>(resultMsg,HttpStatus.OK);
    	}
    	catch(Exception e){
    		e.printStackTrace();
    		return new ResponseEntity<String>(e.getMessage(),HttpStatus.OK);
    	}
    }//end of method
  }
