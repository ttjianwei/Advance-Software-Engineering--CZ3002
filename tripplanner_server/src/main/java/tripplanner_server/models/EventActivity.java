package tripplanner_server.models;

import java.util.Date;
/**
 * 
 * @author MADHAVAN001
 *
 */
public class EventActivity extends Activity {
	EventObject event;

	public EventActivity(EventObject event, Date fromDate, Date toDate) {
		super(fromDate, toDate);
		this.event = event;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((event == null) ? 0 : event.hashCode())+super.hashCode();
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (!(obj instanceof EventActivity))
			return false;
		EventActivity other = (EventActivity) obj;
		if (event == null) {
			if (other.event != null)
				return false;
		} else if (!event.equals(other.event))
			return false;
		return true & super.equals(obj);
	}

	/**
	 * @return the event
	 */
	public EventObject getEvent() {
		return event;
	}

	/**
	 * @param event
	 *            the event to set
	 */
	public void setEvent(EventObject event) {
		this.event = event;
	}

}
