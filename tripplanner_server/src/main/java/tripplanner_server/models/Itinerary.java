package tripplanner_server.models;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author MADHAVAN001
 *
 */
public class Itinerary {
	int tripRequestId;
	Map<Integer, List<Object>> activityMap;

	public Itinerary(int tripRequestId, Map<Integer, List<Object>> activityMap) {
		this.activityMap = new HashMap<Integer, List<Object>>(activityMap);
	}

	/**
	 * @return the tripRequestId
	 */
	public int getTripRequestId() {
		return tripRequestId;
	}

	/**
	 * @param tripRequestId
	 *            the tripRequestId to set
	 */
	public void setTripRequestId(int tripRequestId) {
		this.tripRequestId = tripRequestId;
	}

	/**
	 * @return the activityMap
	 */
	public Map<Integer, List<Object>> getActivityMap() {
		return activityMap;
	}

	/**
	 * @param activityMap
	 *            the activityMap to set
	 */
	public void setActivityMap(Map<Integer, List<Object>> activityMap) {
		this.activityMap = activityMap;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((activityMap == null) ? 0 : activityMap.hashCode());
		result = prime * result + tripRequestId;
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Itinerary))
			return false;
		Itinerary other = (Itinerary) obj;
		if (activityMap == null) {
			if (other.activityMap != null)
				return false;
		} else if (!activityMap.equals(other.activityMap))
			return false;
		if (tripRequestId != other.tripRequestId)
			return false;
		return true;
	}

}
