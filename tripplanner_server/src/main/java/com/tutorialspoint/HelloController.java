package com.tutorialspoint;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.evdb.javaapi.EVDBAPIException;
import com.evdb.javaapi.EVDBRuntimeException;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import tripplanner_server.manager.EventsManager;

@Controller

public class HelloController{
 
   @RequestMapping(value ="/event")
   //public void getEvent(HttpServletResponse response,HttpServletRequest request) throws JsonGenerationException, JsonMappingException, IOException, EVDBRuntimeException, EVDBAPIException {
	   public void getEvent(HttpServletResponse response,HttpServletRequest request) throws IOException{
	   
	  //get receive JSON data from request
	   BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
	   String json ="";
	   if(br!=null){
		   json = br.readLine();
	   }
	   
	   System.out.println(json);
	  
	   String[] eventRequest = new String[600000];
	   //set response type to JSON
	   response.setContentType("application/json");
	   
	   
	   EventsManager em = new EventsManager();
		// String keyword = request.getHeader("keyword");
		 //String dateRange = request.getHeader("DataRange");
		 
		 String keyword = request.getParameter("keyword");
		 String dateRange = request.getParameter("DateRange");
		 
		 System.out.println(keyword ="this is running");
		 System.out.println(dateRange);
		 
		 try {
			eventRequest = em.handleEventRequestJSON(keyword, dateRange);
			
			 for(int i=0;i<eventRequest.length;i++){
					System.out.println(eventRequest[i]);
					response.getWriter().println(eventRequest[i]);
					
				 }

			 

			
				 
		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (EVDBRuntimeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (EVDBAPIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	

			
   }
 
   
}