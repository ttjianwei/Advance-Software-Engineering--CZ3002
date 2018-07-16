/**
 * Model for Trip request
 */
package tripplanner_server.models;

import java.util.Date;
import java.util.List;

/**
 * @author MADHAVAN001
 *
 */
public class Trip {
	int id;
	String userName;
	double budget;
	List<String> listInterests;
	Date fromDate;
	Date toDate;
	String city;
	Location startPoint;

	/**
	 * 
	 * @param id
	 * @param budget
	 * @param listInterests
	 * @param fromDate
	 * @param toDate
	 * @param city
	 */
	public Trip(int id, String userName, double budget, List<String> listInterests, Date fromDate, Date toDate, String city, Location location) {
		this.id = id;
		this.userName = userName;
		this.budget = budget;
		this.listInterests = listInterests;
		this.fromDate = fromDate;
		this.toDate = toDate;
		this.city = city;
		this.startPoint = location;
	}

	/**
	 * 
	 * @param budget
	 * @param listInterests
	 * @param fromDate
	 * @param toDate
	 * @param city
	 */
	public Trip(String userName,double budget, List<String> listInterests, Date fromDate, Date toDate, String city, Location location) {
		this.userName = userName;
		this.budget = budget;
		this.listInterests = listInterests;
		this.fromDate = fromDate;
		this.toDate = toDate;
		this.city = city;
		this.startPoint = location;
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
		long temp;
		temp = Double.doubleToLongBits(budget);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((city == null) ? 0 : city.hashCode());
		result = prime * result + ((fromDate == null) ? 0 : fromDate.hashCode());
		result = prime * result + ((listInterests == null) ? 0 : listInterests.hashCode());
		result = prime * result + ((startPoint == null) ? 0 : startPoint.hashCode());
		result = prime * result + ((toDate == null) ? 0 : toDate.hashCode());
		result = prime * result + ((userName == null) ? 0 : userName.hashCode());
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
		if (!(obj instanceof Trip))
			return false;
		Trip other = (Trip) obj;
		if (Double.doubleToLongBits(budget) != Double.doubleToLongBits(other.budget))
			return false;
		if (city == null) {
			if (other.city != null)
				return false;
		} else if (!city.equals(other.city))
			return false;
		if (fromDate == null) {
			if (other.fromDate != null)
				return false;
		} else if (!fromDate.equals(other.fromDate))
			return false;
		if (listInterests == null) {
			if (other.listInterests != null)
				return false;
		} else if (!listInterests.equals(other.listInterests))
			return false;
		if (startPoint == null) {
			if (other.startPoint != null)
				return false;
		} else if (!startPoint.equals(other.startPoint))
			return false;
		if (toDate == null) {
			if (other.toDate != null)
				return false;
		} else if (!toDate.equals(other.toDate))
			return false;
		if (userName == null) {
			if (other.userName != null)
				return false;
		} else if (!userName.equals(other.userName))
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
	 * @return the startPoint
	 */
	public Location getStartPoint() {
		return startPoint;
	}

	/**
	 * @param startPoint
	 *            the startPoint to set
	 */
	public void setStartPoint(Location startPoint) {
		this.startPoint = startPoint;
	}

	/**
	 * @return the budget
	 */
	public double getBudget() {
		return budget;
	}

	/**
	 * @param budget
	 *            the budget to set
	 */
	public void setBudget(double budget) {
		this.budget = budget;
	}

	/**
	 * @return the listInterests
	 */
	public List<String> getListInterests() {
		return listInterests;
	}

	/**
	 * @param listInterests
	 *            the listInterests to set
	 */
	public void setListInterests(List<String> listInterests) {
		this.listInterests = listInterests;
	}

	/**
	 * @return the fromDate
	 */
	public Date getFromDate() {
		return fromDate;
	}

	/**
	 * @param fromDate
	 *            the fromDate to set
	 */
	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	/**
	 * @return the toDate
	 */
	public Date getToDate() {
		return toDate;
	}

	/**
	 * @param toDate
	 *            the toDate to set
	 */
	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @param city
	 *            the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName
	 *            the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

}
