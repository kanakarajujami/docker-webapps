package com.nt.service;

import java.io.BufferedReader;
import java.io.FileReader;

public class FileClass {
	public void process() throws Exception {
 String filePath="h:htmppl";
	 try(BufferedReader br=new BufferedReader(new FileReader(filePath));) {
		  
		  
		                  
		  
	 }//try
	 catch(Exception e) {
		 e.printStackTrace();
		 throw e;
	 }
	}
}
