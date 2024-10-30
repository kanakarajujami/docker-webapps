package com.nt.ms;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/wish-api")
public class WishMessageController {
	@GetMapping("/wish")
   public ResponseEntity<String> getWishMessage(){
	     LocalDateTime timeAndDate=LocalDateTime.now();
	     Integer hour=timeAndDate.getHour();
	     String message=null;
	     if(hour<12) {
	    	  message="Good morning";
	     }else if(hour<20){
	    	  message="Good Evening";
	     }else {
	         message="Good Night";
	     }
	     return new ResponseEntity<String>("wish Message::"+message,HttpStatus.OK);
   }
}
