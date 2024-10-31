package com.nt.service;

import java.io.BufferedReader;



import java.io.FileReader;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PatchMapping;

import com.nt.bindings.ActivateUser;
import com.nt.bindings.ForgotPassword;
import com.nt.bindings.LoginCredintials;
import com.nt.bindings.UserAccount;
import com.nt.entity.UserMaster;
import com.nt.repository.IUserMasterRepository;
import com.nt.util.EmailUtils;
@Service("userService")
public class UserMngmtServiceImpl implements IUserMgmtService {
	@Autowired
  private IUserMasterRepository userMasterRepo;
	@Autowired
	private Environment environment;
	@Autowired
	public EmailUtils emailUtils;
	@Override
	public String enrollUser(UserAccount userData) throws Exception {
		// TODO Auto-generated method stub
		//create master object
		UserMaster master=new UserMaster();
		//set user status inactive
		master.setActivwSW("Inactive");
		//set random string generation using six alphanumeric 
	       String tempPwd=randomGeneratedStr(6);
	       master.setPassword(tempPwd);
		  //copy UserAccount obj data to Master obj(entity object)
		BeanUtils.copyProperties(userData, master);
		UserMaster entity=userMasterRepo.save(master);
		  //set subject
	      String subject="User Registration Success";
	      String mailBody=readEmailMessageBody(environment.getProperty("mailbody.registration.location"), entity.getUserName(), tempPwd);
	      emailUtils.sendEmailMessage(entity.getEmailId(), subject, mailBody);
	      
		return entity!=null? "User Registered with id value:"+master.getUserId()+"mail sent to"+entity.getEmailId():"user registration failed";
	}

	@Override
	public String activateUserAccount(ActivateUser userActive) {
		                                                
          /* UserMaster master=new UserMaster();
            //copy ActivateUser obj data to UserMaster obj
           BeanUtils.copyProperties(userActive, master);
            Example<UserMaster> example=Example.of(master);
            List<UserMaster> listEntities=userMasterRepo.findAll(example);
            if(listEntities.size()==0) {
            	 return "user not found to activate";
            }else {
            	UserMaster entity=listEntities.get(0);
            	entity.setActivwSW("Active");
            	entity.setPassword(userActive.getConfirmPassword());
            	userMasterRepo.save(entity);
		     return "user status activated succesfully";
            }*/
            //another way to develop code
		List<UserMaster> listEntities= userMasterRepo.findByEmailIdAndPassword(userActive.getEmaild(),userActive.getTempPassword() );
         if(listEntities.size()==0) {
        	 return "User not found to activate";
         }   else {
        	 UserMaster entity=listEntities.get(0);
        	 entity.setActivwSW("Active");
        	 entity.setPassword(userActive.getNewPassword());
        	 userMasterRepo.save(entity);
        	 return "User activated successfully";
         } 
	}//end of methods

	@Override
	public String login(LoginCredintials credintials) {
       //convert loginCredintials object to entity class object
		   UserMaster master=new UserMaster();
		   BeanUtils.copyProperties(credintials, master);
		   Example<UserMaster> example=Example.of(master);
		  List<UserMaster> listEntities= userMasterRepo.findAll(example);
		 if(listEntities.size()==0) {
			 return "Login credintials invalid";
		 }else {
			 UserMaster entity=listEntities.get(0);
			 if(entity.getActivwSW().equalsIgnoreCase("Active")) {
				  return "user login succesfully";
			 }
			      return "User not activated";
		 }
	
	}//end of method

	@Override
	public List<UserAccount> GetAllUserAccounts() {
            /* List<UserMaster> listEntities= 
             List<UserAccount> listUsers=new ArrayList<UserAccount>();
	         listEntities.forEach(entity->{
	        	 UserAccount user=new UserAccount();
	        	 BeanUtils.copyProperties(entity, user);
	        	 listUsers.add(user);
	                  });
	                  
	                  
		return listUsers;*/
		List<UserAccount> listUsers=userMasterRepo.findAll().stream().map(entity->{
			UserAccount user=new UserAccount();
			BeanUtils.copyProperties(entity, user);
			return user;
		}).toList();
		  return listUsers;
	}//end of method

	@Override
	public UserAccount showUserById(Integer userId) {
       Optional<UserMaster> opt=userMasterRepo.findById(userId);
       UserAccount user=new UserAccount();
        if(opt.isPresent()) {
    	  //  UserMaster master=opt.get();
    	    BeanUtils.copyProperties(opt.get(),user); 
       }
		return user;
	}

	@Override
	public String updateUserDetails(UserAccount userData) {
         Optional<UserMaster> opt=userMasterRepo.findById(userData.getUserId());
		  if(opt.isPresent()) {
			  UserMaster entity=opt.get();
			  BeanUtils.copyProperties(userData,entity); 
			  userMasterRepo.save(entity);
			  return "User data updated";
		  }
		return "User not found to update";
	}

	@Override
	public String deleteUserById(Integer userId) {
            Optional<UserMaster> opt=userMasterRepo.findById(userId);
            if(opt.isPresent()) {
            	 userMasterRepo.delete(opt.get());
            	 return "User deleted";
            }else
		
	        	return "User not found to delete";
	}//end of method
	@Override
	public UserAccount showUserByEmailAndName(String email, String name) {
	       UserMaster master=userMasterRepo.findByEmailIdAndUserName(email, name);
	       UserAccount user=null;
	       if(master!=null) {
	    	   user=new UserAccount();
	    	   BeanUtils.copyProperties(master,user);
	    	   	  }
		      return user;
	}
	@Override
	public String recoveryPassword(ForgotPassword user) throws Exception {
		UserMaster master=userMasterRepo.findByEmailIdAndUserName(user.getEmailId(),user.getUserName());
		  if(master!=null) {
			  String password=master.getPassword();
			  String subject="user recovery password";
			     String mailBody=readEmailMessageBody(environment.getProperty("mailbody.recoverypwd.location"),user.getUserName() , password);
			     emailUtils.sendEmailMessage(user.getEmailId(), subject, mailBody);
			 	  return password;
			 	  }
	    	return "User and Email not found";                            
		
	}//end of method
	@Override
	@PatchMapping("changeStatus/{userId}/{status}")
	public String changeStatusById(Integer userId, String status) {
		    Optional<UserMaster> opt=userMasterRepo.findById(userId);
		    if(opt.isPresent()) {
		    	UserMaster entity=opt.get();
		    	entity.setActivwSW(status);
		    	userMasterRepo.save(entity);
		    	return "User status changed";
		    }else
		    	return "User not found to change status";
	}//end pf method
	
	//generate random string alphanumeric code
	 private String randomGeneratedStr(int l)

	 {
	 // a list of characters to choose from in form of a string
	 String AlphaNumericStr = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvxyz0123456789";
	 // creating a StringBuffer size of AlphaNumericStr
	 StringBuilder s = new StringBuilder(l);
	 int i;
	 for ( i=0; i<l; i++) {
	  //generating a random number using math.random()
	   int ch = (int) (AlphaNumericStr.length() * Math.random());
	   //adding Random character one by one at the end of s
	   s.append(AlphaNumericStr.charAt(ch));
	  }
	    return s.toString();
	 }//end of method

  //to read mail message body from mail
	 private String readEmailMessageBody(String fileName,String fullName,String password) throws Exception {
		 String mailBody=null;
		 String url="http://localhost:4041/active";
		 try(BufferedReader br=new BufferedReader(new FileReader(fileName));) {
			  
			    StringBuffer buffer=new StringBuffer();
			    String line=null;
	       do {
			        line=br.readLine();
			        if(line!=null)
			        buffer.append(line);
			    }while(line!=null);
			mailBody=buffer.toString();
			mailBody= mailBody.replace("{FULL-NAME}",fullName);
			 mailBody=mailBody.replace("{PWD}", password);
			 mailBody=mailBody.replace("{URL}", url);
			 String msg="hii";
			
			                  
			  
		 }//try
		 catch(Exception e) {
			 e.printStackTrace();
			 throw e;
		 }
		      return mailBody;
		 }//end of method


	 }
