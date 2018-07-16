/**
 * 
 */
package tripplanner_server.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.tuple.Pair;

import tripplanner_server.models.EventActivity;
import tripplanner_server.models.Itinerary;
import tripplanner_server.models.TransportActivity;
import tripplanner_server.models.Trip;

/**
 * @author MADHAVAN001
 *
 */
public class ItineraryManager {
	static Map<String, List<Pair<Trip, Itinerary>>> itineraryMap;
	DatabaseManager manager;
	static {
		itineraryMap = new HashMap<String, List<Pair<Trip, Itinerary>>>();
	}

	public ItineraryManager() {
		manager = new DatabaseManager();
	}

	/**
	 * 
	 * @param userName
	 * @return
	 */
	public List<Pair<Trip, Itinerary>> getItinerary(String userName) {
		if (userName == null || userName.length() == 0)
			return null;
		if (itineraryMap.containsKey(userName))
			return itineraryMap.get(userName);
		return null;
	}

	/**
	 * 
	 * @param userName
	 * @param tripId
	 * @return
	 */
	public boolean deleteTrip(String userName, int tripId) {
		if (userName == null || userName.length() == 0 || tripId <= 0)
			return false;
		List<Pair<Trip, Itinerary>> listPair = itineraryMap.get(userName);
		for (Pair<Trip, Itinerary> pair : listPair) {
			if (pair.getLeft().getId() == tripId)
				itineraryMap.get(userName).remove(pair);
		}
		manager.deleteItinerary(tripId);
		manager.deleteTrip(userName, tripId);
		return true;
	}

	/**
	 * 
	 * @param userName
	 * @param trip
	 * @param itinerary
	 * @return
	 */
	public boolean addItinerary(String userName, Trip trip, Itinerary itinerary) {
		if (trip == null || itinerary == null)
			return false;
		int id = -1;
		if (trip.getId() == 0) {
			id = manager.addTrip(trip);
			trip.setId(id);
		}
		itinerary.setTripRequestId(trip.getId());
			Iterator<Integer> itr = itinerary.getActivityMap().keySet().iterator();
			while(itr.hasNext())
			{
				Integer key = itr.next();
				for(Object obj: itinerary.getActivityMap().get(key))
				{
					if(obj instanceof EventActivity)
					{
						int eventId = -1;
						eventId = manager.addEvent(((EventActivity) obj).getEvent());
						if(eventId != -1)
							((EventActivity) obj).getEvent().setId(eventId);
					}
					if(obj instanceof TransportActivity)
					{
						int transportId = -1;
						transportId = manager.addTransport(((TransportActivity)obj).getTransport());
						if(transportId != -1)
							((TransportActivity) obj).getTransport().setId(transportId);
					}
				}
			}
		
		if (!itineraryMap.containsKey(userName))
			itineraryMap.put(userName, new ArrayList<Pair<Trip, Itinerary>>());
		itineraryMap.get(userName).add(Pair.of(trip, itinerary));
		return manager.addItinerary(itinerary, 0);
	}

	/**
	 * 
	 * @param trip
	 * @return
	 */
	public List<Itinerary> planItinerary(Trip trip) {
		if (trip == null)
			return null;
		return RecommenderEngine.generateTrips(trip);
	}

}
