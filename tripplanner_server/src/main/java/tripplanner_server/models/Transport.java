package tripplanner_server.models;

public class Transport {
	Location startPoint;
	Location endPoint;
	double duration;
	double distance;
	String modeOfTransport;
	int id;

	/**
	 * 
	 * @param startPoint
	 * @param endPoint
	 * @param distance
	 * @param duration
	 * @param modeOfTransport
	 */
	public Transport(Location startPoint, Location endPoint, double distance, double duration, String modeOfTransport) {
		this.distance = distance;
		this.startPoint = startPoint;
		this.endPoint = endPoint;
		this.duration = duration;
		this.modeOfTransport = modeOfTransport;
	}

	/**
	 * 
	 * @param id
	 * @param startPoint
	 * @param endPoint
	 * @param distance
	 * @param duration
	 * @param modeOfTransport
	 */
	public Transport(int id, Location startPoint, Location endPoint, double distance, double duration,
			String modeOfTransport) {
		this.id = id;
		this.distance = distance;
		this.startPoint = startPoint;
		this.endPoint = endPoint;
		this.duration = duration;
		this.modeOfTransport = modeOfTransport;
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
		temp = Double.doubleToLongBits(distance);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(duration);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((endPoint == null) ? 0 : endPoint.hashCode());
		result = prime * result + id;
		result = prime * result + ((modeOfTransport == null) ? 0 : modeOfTransport.hashCode());
		result = prime * result + ((startPoint == null) ? 0 : startPoint.hashCode());
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
		if (!(obj instanceof Transport))
			return false;
		Transport other = (Transport) obj;
		if (Double.doubleToLongBits(distance) != Double.doubleToLongBits(other.distance))
			return false;
		if (Double.doubleToLongBits(duration) != Double.doubleToLongBits(other.duration))
			return false;
		if (endPoint == null) {
			if (other.endPoint != null)
				return false;
		} else if (!endPoint.equals(other.endPoint))
			return false;
		if (id != other.id)
			return false;
		if (modeOfTransport == null) {
			if (other.modeOfTransport != null)
				return false;
		} else if (!modeOfTransport.equals(other.modeOfTransport))
			return false;
		if (startPoint == null) {
			if (other.startPoint != null)
				return false;
		} else if (!startPoint.equals(other.startPoint))
			return false;
		return true;
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
	 * @return the endPoint
	 */
	public Location getEndPoint() {
		return endPoint;
	}

	/**
	 * @param endPoint
	 *            the endPoint to set
	 */
	public void setEndPoint(Location endPoint) {
		this.endPoint = endPoint;
	}

	/**
	 * @return the duration
	 */
	public double getDuration() {
		return duration;
	}

	/**
	 * @param duration
	 *            the duration to set
	 */
	public void setDuration(double duration) {
		this.duration = duration;
	}

	/**
	 * @return the distance
	 */
	public double getDistance() {
		return distance;
	}

	/**
	 * @param distance
	 *            the distance to set
	 */
	public void setDistance(double distance) {
		this.distance = distance;
	}

	/**
	 * @return the modeOfTransport
	 */
	public String getModeOfTransport() {
		return modeOfTransport;
	}

	/**
	 * @param modeOfTransport
	 *            the modeOfTransport to set
	 */
	public void setModeOfTransport(String modeOfTransport) {
		this.modeOfTransport = modeOfTransport;
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

}
