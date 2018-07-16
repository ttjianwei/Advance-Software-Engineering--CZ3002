/**
 * 
 */
package tripplanner_server.manager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.tuple.Pair;

import com.evdb.javaapi.APIConfiguration;
import com.evdb.javaapi.EVDBAPIException;
import com.evdb.javaapi.EVDBRuntimeException;
import com.evdb.javaapi.data.Event;
import com.evdb.javaapi.data.SearchResult;
import com.evdb.javaapi.data.request.EventSearchRequest;
import com.evdb.javaapi.operations.EventOperations;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import tripplanner_server.models.EventObject;
import tripplanner_server.models.Location;

/**
 * @author MADHAVAN001
 *
 */
public class EventsManager {
	public static Map<Pair<String, String>, List<EventObject>> eventsMap;
	private String API_KEY = "7m2B8sjhmH2vHpt5";
	DatabaseManager manager;
	static {
		eventsMap = new HashMap<Pair<String, String>, List<EventObject>>();
	}

	public EventsManager() {
		manager = new DatabaseManager();
	}

	/**
	 * 
	 * @param event
	 * @return
	 */
	public int addEvents(EventObject event) {
		if (event == null)
			return -1;
		return manager.addEvent(event);
	}

	/**
	 * 
	 * @param keyword
	 * @param dateRange
	 * @return
	 * @throws EVDBRuntimeException
	 * @throws EVDBAPIException
	 */
	public List<EventObject> getEvents(String keyword, String dateRange) throws EVDBRuntimeException, EVDBAPIException {

		if (keyword == null || keyword.length() == 0 || dateRange == null || dateRange.length() == 0)
			return null;

		if (eventsMap.containsKey(Pair.of(keyword, dateRange)))
			return eventsMap.get(Pair.of(keyword, dateRange));
		
		List<EventObject> eventsList = new ArrayList<EventObject>();

		String title;
		String venueName;
		String venueCity;
		String description;
		double price;
		Date startTime;
		Date stopTime;
		String getURL;
		double longitude;
		double latitude;

		APIConfiguration.setApiKey(API_KEY);
		APIConfiguration.setEvdbUser("AppTest");
		APIConfiguration.setEvdbPassword("123456");

		EventOperations eo = new EventOperations();
		EventSearchRequest esr = new EventSearchRequest();
		esr.setLocation("Singapore");
		esr.setKeywords(keyword);
		esr.setDateRange(dateRange); // format e.g 2017020000-20170420000

		esr.setPageSize(50); // can change depending
		esr.setPageNumber(1);

		SearchResult sr = null;

		sr = eo.search(esr);

		ArrayList<Event> arrayList = (ArrayList<Event>) sr.getEvents();

		for (int i = 0; i < arrayList.size(); i++) {
			title = arrayList.get(i).getTitle();
			venueName = arrayList.get(i).getVenueName();
			venueCity = arrayList.get(i).getVenueCity();
			description = arrayList.get(i).getDescription();
			try {
				price = Double.parseDouble(arrayList.get(i).getPrice());
			} catch (Exception e) {
				price = 0.0;
			}
			startTime = arrayList.get(i).getStartTime();
			stopTime = arrayList.get(i).getStopTime();
			getURL = arrayList.get(i).getURL();
			latitude = arrayList.get(i).getVenueLatitude();
			longitude = arrayList.get(i).getVenueLongitude();
			EventObject e = new EventObject(title, venueName, venueCity, description, price, startTime, stopTime,
					getURL, new Location(latitude, longitude));
			eventsList.add(e);
		}
		eventsMap.put(Pair.of(keyword, dateRange), eventsList);
		return eventsList;
	}

	/**
	 * 
	 * @param keyword
	 * @param dateRange
	 * @return
	 * @throws JsonGenerationException
	 * @throws JsonMappingException
	 * @throws IOException
	 * @throws EVDBRuntimeException
	 * @throws EVDBAPIException
	 */
	public String[] handleEventRequestJSON(String keyword, String dateRange)
			throws JsonGenerationException, JsonMappingException, IOException, EVDBRuntimeException, EVDBAPIException {
		String dateTime = "";
		if (dateRange == null) {
			dateTime = null;
		} else
			dateTime = dateRange;

		List<EventObject> eventList = getEvents(keyword, dateTime);

		ObjectMapper mapper = new ObjectMapper();

		String[] eventToJSONString = new String[eventList.size()];
		for (int i = 0; i < eventList.size(); i++) {

			eventToJSONString[i] = mapper.writeValueAsString(eventList.get(i));

		}
		return eventToJSONString;

	}
}
