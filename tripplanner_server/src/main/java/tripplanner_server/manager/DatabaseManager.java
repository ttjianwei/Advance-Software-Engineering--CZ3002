/**
 * 
 */
package tripplanner_server.manager;

import java.sql.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang3.tuple.Pair;

import tripplanner_server.models.EventActivity;
import tripplanner_server.models.EventObject;
import tripplanner_server.models.Itinerary;
import tripplanner_server.models.Location;
import tripplanner_server.models.PlaceActivity;
import tripplanner_server.models.Transport;
import tripplanner_server.models.TransportActivity;
import tripplanner_server.models.Trip;
import tripplanner_server.models.UserAccount;

/**
 * @author MADHAVAN001
 *
 */
public class DatabaseManager {
	Connection connection = null;
	String schemaName = "TripPlanner";

	public DatabaseManager() {
		try {
			Class.forName("org.postgresql.Driver");
			connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "admin");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param userName
	 * @return
	 */
	public List<Pair<Trip, Itinerary>> getListItinerary(String userName) {
		if (userName == null || userName.length() == 0)
			return null;
		String sql = "SELECT id, username, listinterests, fromdate, todate, startlatitude, startlongitude, city, budget	FROM \""
				+ schemaName + "\".triprequests WHERE username = \'" + userName + "\';";

		List<Pair<Trip, Itinerary>> itineraryList = new ArrayList<Pair<Trip, Itinerary>>();
		Statement statement = null;
		try {
			statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);
			while (resultSet.next()) {
				Trip trip = this.getTripById(resultSet.getInt("id"));
				Itinerary itinerary = this.getIteneraryByTripId(trip.getId());
				itineraryList.add(Pair.of(trip, itinerary));
			}
			statement.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return itineraryList;
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	public Itinerary getIteneraryByTripId(int id) {
		if (id <= 0)
			return null;
		String sql = "SELECT id, triprequest, day, orderid, starttime, endtime, activitytype, activityid FROM \""
				+ schemaName + "\".itinerary WHERE triprequest=" + id + " ORDER BY orderid;";
		Statement statement = null;
		Map<Integer, List<Object>> map = new HashMap<Integer, List<Object>>();
		try {
			statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);
			DateFormat df = new SimpleDateFormat("EEE MMM dd kk:mm:ss z yyyy", Locale.ENGLISH);
			while (resultSet.next()) {
				int type = resultSet.getInt("activitytype");
				if (type == 1) {
					EventObject event = this.getEventbyId(Integer.parseInt(resultSet.getString("activityid")));
					EventActivity activity = new EventActivity(event, df.parse(resultSet.getString("starttime")),
							df.parse(resultSet.getString("endtime")));
					if (!map.containsKey(resultSet.getInt("day")))
						map.put(resultSet.getInt("day"), new ArrayList<Object>());
					map.get(resultSet.getInt("day")).add(activity);
				}
				if (type == 2) {
					Transport transport = this.getTransportById(Integer.parseInt(resultSet.getString("activityid")));
					TransportActivity activity = new TransportActivity(transport,
							df.parse(resultSet.getString("starttime")), df.parse(resultSet.getString("endtime")));
					if (!map.containsKey(resultSet.getInt("day")))
						map.put(resultSet.getInt("day"), new ArrayList<Object>());
					map.get(resultSet.getInt("day")).add(activity);
				}
				if (type == 3) {
					PlaceActivity activity = new PlaceActivity(resultSet.getString("activityid"),
							df.parse(resultSet.getString("starttime")), df.parse(resultSet.getString("endtime")));
					if (!map.containsKey(resultSet.getInt("day")))
						map.put(resultSet.getInt("day"), new ArrayList<Object>());
					map.get(resultSet.getInt("day")).add(activity);
				}
			}
			statement.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new Itinerary(id, map);
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	public Transport getTransportById(int id) {
		if (id <= 0)
			return null;
		String sql = "SELECT id, distance, duration, startlatitude, startlongitude, endlatitude, endlongitude, modeoftransport FROM \""
				+ schemaName + "\".transport WHERE id=" + id + ";";

		Transport transport = null;
		Statement statement = null;
		try {
			statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);
			while (resultSet.next()) {
				transport = new Transport(id,
						new Location(resultSet.getDouble("startlatitude"), resultSet.getDouble("startlongitude")),
						new Location(resultSet.getDouble("endlatitude"), resultSet.getDouble("endlongitude")),
						resultSet.getDouble("distance"), resultSet.getDouble("duration"),
						resultSet.getString("modeoftransport"));
			}
			statement.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return transport;
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	public Trip getTripById(int id) {
		if (id <= 0)
			return null;
		String sql = "SELECT id, username, listinterests, fromdate, todate, startlatitude, startlongitude, city, budget	FROM \""
				+ schemaName + "\".triprequests WHERE id=" + id + ";";
		Statement statement = null;
		Trip trip = null;
		try {
			statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);
			DateFormat df = new SimpleDateFormat("EEE MMM dd kk:mm:ss z yyyy", Locale.ENGLISH);
			while (resultSet.next()) {
				Array listInterests = resultSet.getArray("listinterests");
				String[] interestsArray = (String[]) listInterests.getArray();
				trip = new Trip(resultSet.getInt("id"), resultSet.getString("username"), resultSet.getDouble("budget"),
						new ArrayList<String>(Arrays.asList(interestsArray)), df.parse(resultSet.getString("fromdate")),
						df.parse(resultSet.getString("todate")), resultSet.getString("city"),
						new Location(resultSet.getDouble("startlatitude"), resultSet.getDouble("startlatitude")));
				id = resultSet.getInt("id");
			}
			statement.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return trip;
	}

	/**
	 * 
	 * @param itinerary
	 * @return
	 */
	public boolean deleteItinerary(int tripRequest) {
		if (tripRequest <= 0)
			return false;
		String sql = "DELETE FROM \"" + schemaName + "\".itinerary WHERE triprequest = " + tripRequest;
		return this.executeUpdate(sql);
	}

	/**
	 * 
	 * @param itinerary
	 * @param responseId
	 * @return
	 */
	public boolean addItinerary(Itinerary itinerary, int responseId) {
		if (itinerary == null)
			return false;

		boolean result = true;
		Iterator<Integer> itr = itinerary.getActivityMap().keySet().iterator();
		while (itr.hasNext()) {
			Integer key = new Integer(itr.next());
			if (itinerary.getActivityMap().get(key) instanceof EventActivity) {
				EventActivity activity = (EventActivity) itinerary.getActivityMap().get(key);
				String sql = "INSERT INTO \"" + schemaName
						+ "\".itinerary(triprequest, day, orderid, starttime, endtime, activitytype, activityid) VALUES ("
						+ itinerary.getTripRequestId() + ", " + key + ", " + key.toString() + ", \'"
						+ activity.getFromDate() + "\', \'" + activity.getToDate() + "\', 1, "
						+ activity.getEvent().getId() + ");";
				result = result & this.executeUpdate(sql);
			} else if (itinerary.getActivityMap().get(key) instanceof TransportActivity) {
				TransportActivity activity = (TransportActivity) itinerary.getActivityMap().get(key);
				String sql = "INSERT INTO \"" + schemaName
						+ "\".itinerary(triprequest, day, orderid, starttime, endtime, activitytype, activityid) VALUES ("
						+ itinerary.getTripRequestId() + ", " + key + ", " + key.toString() + ", \'"
						+ activity.getFromDate() + "\', \'" + activity.getToDate() + "\', 2, "
						+ activity.getTransport().getId() + ");";
				result = result & this.executeUpdate(sql);
			} else if (itinerary.getActivityMap().get(key) instanceof PlaceActivity) {
				PlaceActivity activity = (PlaceActivity) itinerary.getActivityMap().get(key);
				String sql = "INSERT INTO \"" + schemaName
						+ "\".itinerary(triprequest, day, orderid, starttime, endtime, activitytype, activityid) VALUES ("
						+ itinerary.getTripRequestId() + ", " + key + ", " + key.toString() + ", \'"
						+ activity.getFromDate() + "\', \'" + activity.getToDate() + "\', 3, " + activity.getPlaceId()
						+ ");";
				result = result & this.executeUpdate(sql);
			}
		}
		return result;
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	public boolean removeTransport(int id) {
		if (id <= 0)
			return false;
		String sql = "DELETE FROM \"" + schemaName + "\".transport WHERE id=" + id;
		return this.executeUpdate(sql);
	}

	/**
	 * 
	 * @param transport
	 * @return
	 */
	public int addTransport(Transport transport) {
		if (transport == null)
			return -1;
		String sql = "INSERT INTO \"" + schemaName
				+ "\".transport(distance, duration, startlatitude, startlongitude, endlatitude, endlongitude, modeoftransport) VALUES("
				+ transport.getDistance() + ", " + transport.getDuration() + ", "
				+ transport.getStartPoint().getLatitude() + ", " + transport.getStartPoint().getLongitude() + ", "
				+ transport.getEndPoint().getLatitude() + ", " + transport.getEndPoint().getLongitude() + ", \'"
				+ transport.getModeOfTransport() + "\');";
		this.executeUpdate(sql);

		sql = "SELECT * FROM \"" + schemaName + "\".transport ORDER BY id DESC;";

		int id = -1;
		Statement statement = null;
		try {
			statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);
			if (resultSet.next()) {
				id = resultSet.getInt("id");
			}
			statement.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return id;
	}

	/**
	 * 
	 * @param eventId
	 * @return
	 */
	public EventObject getEventbyId(int eventId) {
		if (eventId <= 0)
			return null;

		String sql = "SELECT * FROM \"" + schemaName + "\".events WHERE id = " + eventId + ";";

		Statement statement = null;
		EventObject event = null;
		try {
			statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);
			if (resultSet.next()) {
				DateFormat df = new SimpleDateFormat("EEE MMM dd kk:mm:ss z yyyy", Locale.ENGLISH);
				event = new EventObject(eventId, resultSet.getString("title"), resultSet.getString("venueName"),
						resultSet.getString("venueCity"), resultSet.getString("description"),
						resultSet.getDouble("price"), df.parse(resultSet.getString("startdate")),
						df.parse(resultSet.getString("enddate")), resultSet.getString("url"),
						new Location(resultSet.getDouble("latitude"), resultSet.getDouble("longitude")));
			}
			statement.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return event;
	}

	/**
	 * 
	 * @param event
	 * @return
	 */
	public int addEvent(EventObject event) {
		if (event == null)
			return -1;
		String sql = "INSERT INTO \"" + schemaName
				+ "\".events(title, venuename, description, venuecity, latitude, longitude, url, startdate, enddate)	VALUES (\'"
				+ event.getTitle() + "\', \'" + event.getVenueName() + "\', \'" + event.getDescription() + "\', \'"
				+ event.getVenueCity() + "\', " + event.getLocation().getLongitude() + ", "
				+ event.getLocation().getLongitude() + ", \'" + event.getGetURL() + "\', \'" + event.getStartTime()
				+ "\', \'" + event.getStopTime() + "\');";
		this.executeUpdate(sql);

		sql = "SELECT MAX(id) AS id FROM \"" + schemaName + "\".events";

		int id = -1;
		Statement statement = null;
		try {
			statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);
			if (resultSet.next()) {
				id = resultSet.getInt("id");
			}
			statement.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return id;

	}

	/**
	 * 
	 * @param userName
	 * @param tripId
	 * @return
	 */
	public boolean deleteTrip(String userName, int tripId) {
		if (userName == null || tripId <= 0)
			return false;
		String sql = "DELETE FROM \"" + schemaName + "\".triprequests WHERE username = \'" + userName + "\' AND id="
				+ tripId + ";";
		return this.executeUpdate(sql);
	}

	/**
	 * 
	 * @param trip
	 * @return
	 */
	public int addTrip(Trip trip) {
		if (trip == null)
			return -1;

		String sql = "INSERT INTO \"" + schemaName
				+ "\".triprequests(username, listinterests, fromdate, todate, startlatitude, startlongitude, city, budget) VALUES (\'"
				+ trip.getUserName() + "\', ARRAY[";

		for (int i = 0; i < trip.getListInterests().size(); i++) {
			if (i != trip.getListInterests().size() - 1)
				sql += "\'" + trip.getListInterests().get(i) + "\',";
			else
				sql += "\'" + trip.getListInterests().get(i) + "\'";
		}
		sql += "], \'" + trip.getFromDate().toString() + "\', \'" + trip.getToDate().toString() + "\', "
				+ trip.getStartPoint().getLatitude() + ", " + trip.getStartPoint().getLongitude() + ", \'"
				+ trip.getCity() + "\', " + trip.getBudget() + ");";
		System.out.println(sql);
		this.executeUpdate(sql);

		sql = "SELECT id FROM \"" + schemaName + "\".triprequests WHERE username = \'" + trip.getUserName()
				+ "\' ORDER BY id DESC";

		int id = -1;
		Statement statement = null;
		try {
			statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);
			if (resultSet.next()) {
				id = resultSet.getInt("id");
			}
			statement.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return id;
	}

	/**
	 * 
	 * @param placeId
	 * @param interest
	 * @param recommendedList
	 * @return
	 */
	public boolean addRecommenderItem(String placeId, String interest, List<String> recommendedList) {
		if (placeId == null || interest == null || recommendedList == null)
			return false;
		if (checkRecommendationExists(placeId, interest))
			return false;
		String sql = "INSERT INTO \"" + schemaName + "\".recommender(placeid, interest, listinterests) VALUES (\'"
				+ placeId + "\', \'" + interest + "\',[";
		for (int i = 0; i < recommendedList.size(); i++) {
			if (i != recommendedList.size() - 1)
				sql += "\'" + recommendedList.get(i) + "\',";
			else
				sql += "\'" + recommendedList.get(i) + "\'";
		}
		sql += "]);";
		return this.executeUpdate(sql);
	}

	/**
	 * 
	 * @param placeId
	 * @param interest
	 * @return
	 */
	public boolean checkRecommendationExists(String placeId, String interest) {
		if (placeId == null || interest == null)
			return false;

		String sql = "SELECT * FROM \"" + schemaName + "\".recommender WHERE placeid = \'" + placeId
				+ "\' AND interest = \'" + interest + "\';";
		Statement statement;
		boolean result = false;
		try {
			statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);
			if (resultSet.next()) {
				String placeIdCheck = resultSet.getString("placeid");
				String interestCheck = resultSet.getString("interest");
				result = (placeIdCheck.equals(placeId) & interestCheck.equals(interest)) ? true : false;
			}
			statement.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;

	}

	/**
	 * 
	 * @param email
	 * @return
	 */
	public boolean checkAccount(String email) {
		if (email == null || email.length() == 0)
			return false;
		String sql = "SELECT * FROM \"" + "TripPlanner" + "\".useraccount WHERE email = \'" + email + "\';";
		Statement statement;
		boolean result = false;
		try {
			statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);
			if (resultSet.next()) {
				String emailCheck = resultSet.getString("email");
				result = emailCheck.equals(email) ? true : false;
			}
			statement.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 
	 * @param email
	 * @return
	 */
	public int getAccountId(String email) {
		if (email == null || email.length() == 0)
			return -1;
		String sql = "SELECT * FROM \"" + "TripPlanner" + "\".useraccount WHERE email = \'" + email + "\';";
		Statement statement;
		int id = -1;
		try {
			statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);
			if (resultSet.next()) {
				id = resultSet.getInt("id");
			}
			statement.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return id;
	}

	/**
	 * 
	 * @param account
	 * @return
	 */
	public boolean addUserAccount(UserAccount account) {
		if (account == null)
			return false;
		String sql = "INSERT INTO \"" + schemaName + "\".useraccount(username, password, email, date)	VALUES (\'"
				+ account.getUserName() + "\', \'" + account.getPassword() + "\', \'" + account.getEmail() + "\', \'"
				+ account.getDate() + "\');";
		return this.executeUpdate(sql);
	}

	/**
	 * 
	 * @param email
	 * @return
	 */
	public boolean deleteAccount(String email) {
		if (email == null || email.length() == 0)
			return false;

		String sql = "DELETE FROM \"" + schemaName + "\".useraccount	WHERE email = \'" + email + "\';";
		return this.executeUpdate(sql);

	}

	/**
	 * 
	 * @param email
	 * @param password
	 * @return
	 */
	public boolean checkValidity(String email, String password) {
		if (email == null || password == null || email.length() == 0 || password.length() == 0)
			return false;
		String sql = "SELECT * FROM \"" + schemaName + "\".useraccount WHERE email=\'" + email + "\' AND password = \'"
				+ password + "\';";

		Statement statement;
		boolean exists = false;
		try {
			statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);
			if (resultSet.next()) {
				exists = resultSet.getString("email").equals(email);
			}
			statement.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return exists;

	}

	/**
	 * 
	 * @param query
	 * @return
	 */
	public boolean executeUpdate(String query) {
		Statement statement;
		try {
			statement = getConnection().createStatement();
			statement.executeUpdate(query);
			statement.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * @return the connection
	 */
	public Connection getConnection() {
		return connection;
	}

	/**
	 * @param connection
	 *            the connection to set
	 */
	public void setConnection(Connection connection) {
		this.connection = connection;
	}
}
