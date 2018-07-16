/**
 * 
 */
package tripplanner_server.models;

import java.util.Date;

/**
 * @author MADHAVAN001
 *
 */
public class EventObject {
	int id;
	String title;
	String venueName;
	String venueCity;
	String description;
	double price;
	Date startTime;
	Date stopTime;
	String getURL;
	Location location;

	/**
	 * 
	 * @param title
	 * @param venueName
	 * @param venueCity
	 * @param description
	 * @param price
	 * @param startTime
	 * @param stopTime
	 * @param getURL
	 * @param location
	 */
	public EventObject(String title, String venueName, String venueCity, String description, double price,
			Date startTime, Date stopTime, String getURL, Location location) {
		this.title = title;
		this.venueName = venueName;
		this.venueCity = venueCity;
		this.price = price;
		this.startTime = startTime;
		this.stopTime = stopTime;
		this.getURL = getURL;
		this.location = location;
	}

	/**
	 * 
	 * @param id
	 * @param title
	 * @param venueName
	 * @param venueCity
	 * @param description
	 * @param price
	 * @param startTime
	 * @param stopTime
	 * @param getURL
	 * @param location
	 */
	public EventObject(int id, String title, String venueName, String venueCity, String description, double price,
			Date startTime, Date stopTime, String getURL, Location location) {
		this.id = id;
		this.title = title;
		this.venueName = venueName;
		this.venueCity = venueCity;
		this.price = price;
		this.startTime = startTime;
		this.stopTime = stopTime;
		this.getURL = getURL;
		this.location = location;
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
		result = prime * result + ((startTime == null) ? 0 : startTime.hashCode());
		result = prime * result + ((stopTime == null) ? 0 : stopTime.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		result = prime * result + ((venueCity == null) ? 0 : venueCity.hashCode());
		result = prime * result + ((venueName == null) ? 0 : venueName.hashCode());
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
		if (getClass() != obj.getClass())
			return false;
		EventObject other = (EventObject) obj;
		if (startTime == null) {
			if (other.startTime != null)
				return false;
		} else if (!startTime.equals(other.startTime))
			return false;
		if (stopTime == null) {
			if (other.stopTime != null)
				return false;
		} else if (!stopTime.equals(other.stopTime))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		if (venueCity == null) {
			if (other.venueCity != null)
				return false;
		} else if (!venueCity.equals(other.venueCity))
			return false;
		if (venueName == null) {
			if (other.venueName != null)
				return false;
		} else if (!venueName.equals(other.venueName))
			return false;
		return true;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title
	 *            the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the venueName
	 */
	public String getVenueName() {
		return venueName;
	}

	/**
	 * @param venueName
	 *            the venueName to set
	 */
	public void setVenueName(String venueName) {
		this.venueName = venueName;
	}

	/**
	 * @return the venueCity
	 */
	public String getVenueCity() {
		return venueCity;
	}

	/**
	 * @param venueCity
	 *            the venueCity to set
	 */
	public void setVenueCity(String venueCity) {
		this.venueCity = venueCity;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the price
	 */
	public double getPrice() {
		return price;
	}

	/**
	 * @param price
	 *            the price to set
	 */
	public void setPrice(double price) {
		this.price = price;
	}

	/**
	 * @return the startTime
	 */
	public Date getStartTime() {
		return startTime;
	}

	/**
	 * @param startTime
	 *            the startTime to set
	 */
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	/**
	 * @return the stopTime
	 */
	public Date getStopTime() {
		return stopTime;
	}

	/**
	 * @param stopTime
	 *            the stopTime to set
	 */
	public void setStopTime(Date stopTime) {
		this.stopTime = stopTime;
	}

	/**
	 * @return the getURL
	 */
	public String getGetURL() {
		return getURL;
	}

	/**
	 * @param getURL
	 *            the getURL to set
	 */
	public void setGetURL(String getURL) {
		this.getURL = getURL;
	}

	/**
	 * @return the location
	 */
	public Location getLocation() {
		return location;
	}

	/**
	 * @param location
	 *            the location to set
	 */
	public void setLocation(Location location) {
		this.location = location;
	}

}
