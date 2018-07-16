package tripplanner_server.models;

import java.util.Date;

/**
 * Class defining the best transport mode between two points
 * 
 * @author MADHAVAN001
 *
 */
public class TransportActivity extends Activity {

	Transport transport;

	public TransportActivity(Transport transport, Date fromDate, Date toDate) {
		super(fromDate, toDate);
		this.transport = transport;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((transport == null) ? 0 : transport.hashCode())+super.hashCode();
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
		if (!(obj instanceof TransportActivity))
			return false;
		TransportActivity other = (TransportActivity) obj;
		if (transport == null) {
			if (other.transport != null)
				return false;
		} else if (!transport.equals(other.transport))
			return false;
		return true & super.equals(obj);
	}

	/**
	 * @return the transport
	 */
	public Transport getTransport() {
		return transport;
	}

	/**
	 * @param transport
	 *            the transport to set
	 */
	public void setTransport(Transport transport) {
		this.transport = transport;
	}

}
