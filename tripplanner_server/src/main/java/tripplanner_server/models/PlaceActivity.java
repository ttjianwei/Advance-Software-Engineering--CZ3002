package tripplanner_server.models;

import java.util.Date;

/**
 * 
 * @author MADHAVAN001
 *
 */
public class PlaceActivity extends Activity {
	String placeId;

	public PlaceActivity(String placeId, Date fromDate, Date toDate) {
		super(fromDate, toDate);
		this.placeId = placeId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((placeId == null) ? 0 : placeId.hashCode())+super.hashCode();
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
			return true & super.equals(obj);
		if (!super.equals(obj))
			return false;
		if (!(obj instanceof PlaceActivity))
			return false;
		PlaceActivity other = (PlaceActivity) obj;
		if (placeId == null) {
			if (other.placeId != null)
				return false;
		} else if (!placeId.equals(other.placeId))
			return false;
		return true & super.equals(obj);
	}

	/**
	 * @return the placeId
	 */
	public String getPlaceId() {
		return placeId;
	}

	/**
	 * @param placeId
	 *            the placeId to set
	 */
	public void setPlaceId(String placeId) {
		this.placeId = placeId;
	}

}
