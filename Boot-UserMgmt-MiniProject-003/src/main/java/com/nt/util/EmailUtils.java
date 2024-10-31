package com.nt.util;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.internet.MimeMessage;



@Service	
public class EmailUtils {
	 @Autowired
 private JavaMailSender sender;
  public boolean sendEmailMessage(String toEmail,String Subject,String body) throws Exception {
	  boolean mailSentStatus=false;
	  try {
	  MimeMessage message=sender.createMimeMessage();

	   MimeMessageHelper helper=new MimeMessageHelper(message,true);
	    helper.setTo(toEmail);
	    helper.setSentDate(new Date());
	    helper.setSubject(Subject);
	    helper.setText(body,true);
	    sender.send(message);
	    mailSentStatus=true;
	  }
	  catch(Exception e){
		  e.printStackTrace();
		  throw e;
	  }
	  return mailSentStatus;
  }//end of method
}

