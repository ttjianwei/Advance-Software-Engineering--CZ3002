package tripplanner_server.tripplanner_server.controller;

import java.util.List;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import tripplanner_server.manager.ItineraryManager;
import tripplanner_server.manager.UserAccountManager;
import tripplanner_server.models.Itinerary;
import tripplanner_server.models.Trip;
import tripplanner_server.models.UserAccount;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class HomeController {
	 @RequestMapping(value ="/trip")
	   
		   public void getEvent(HttpServletResponse response,HttpServletRequest request) throws IOException{
		  //get receive JSON data from request
		   BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
		   String json ="";
		   if(br!=null){
			   json = br.readLine();
		   }	  
		   
		   //set response type to JSON
		   response.setContentType("application/json");
		   
		   ObjectMapper mapper = new ObjectMapper();
		   Trip trip = mapper.readValue(json,Trip.class);
		   List<Itinerary> itinerary;
		   ItineraryManager im = new ItineraryManager();
		   itinerary = im.planItinerary(trip);
		    
			 try {

				    mapper.writeValue(response.getOutputStream(),itinerary);
						
					 }
			 catch(IOException e){
				 e.printStackTrace();
			 }
	 		
	   }
	
		 @RequestMapping(value ="/login") 
		   public void authenticateUser(HttpServletResponse response,HttpServletRequest request) throws IOException{
		  //get receive JSON data from request
		   BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
		   String json ="";
		   if(br!=null){
			   json = br.readLine();
		   }  
		   UserAccount ua;
		   //set response type to JSON
		   response.setContentType("application/json");
		   //user's credential, log in using email and password
		   String email = request.getParameter("email");
		   String password = request.getParameter("password");
		   
		   
			 try {
				UserAccountManager uam = new UserAccountManager();
				 /*call method from UserAccountManager to validate email and password
			   
				ua = tm.handleTransportRequestJSON();//should return a JSONString
					
				 response.getWriter().println(ua); //pass to client
				 
			} catch (JsonGenerationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JsonMappingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	 
			 }
			 */
			 }
				 catch(Exception e){
					 e.printStackTrace();
				 }
			 }
		 
}
			
	 



