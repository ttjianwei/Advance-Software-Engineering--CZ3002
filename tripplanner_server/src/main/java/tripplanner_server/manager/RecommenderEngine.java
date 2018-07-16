package tripplanner_server.manager;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

import org.apache.commons.lang3.tuple.Pair;

import net.sf.sprockets.google.Place;
import tripplanner_server.models.ActivitiesComparator;
import tripplanner_server.models.Itinerary;
import tripplanner_server.models.Location;
import tripplanner_server.models.PlaceActivity;
import tripplanner_server.models.Transport;
import tripplanner_server.models.TransportActivity;
import tripplanner_server.models.Tree;
import tripplanner_server.models.Trip;

public class RecommenderEngine {

	public static int NUM_TRIPS = 5;
	static final long ONE_MINUTE_IN_MILLIS = 60000;
	private static List<List<Object>> listGroupActivities;

	public static List<Itinerary> getStaticItinerary(Trip trip) throws ParseException {
		if (trip == null)
			return null;
		List<Itinerary> resultList = new ArrayList<Itinerary>();
		Map<Integer, List<Object>> map = new HashMap<Integer, List<Object>>();
		List<Object> listObjects = new ArrayList<Object>();
		DateFormat df = new SimpleDateFormat("EEE MMM dd kk:mm:ss z yyyy", Locale.ENGLISH);
		PlaceActivity placeActivity1 = new PlaceActivity("Id{id=ChIJiXjjB5IZ2jERZ37fZcd5d6Y, scope=GOOGLE}",
				df.parse("Thu Feb 23 08:23:17 SGT 2017"), df.parse("Thu Feb 23 11:23:17 SGT 2017"));
		TransportActivity transportActivity1 = new TransportActivity(
				new Transport(new Location(1.290270, 103.851959), new Location(1.340270, 103.671959), 1000,
						36 * ONE_MINUTE_IN_MILLIS, "TAXI"),
				df.parse("Thu Feb 23 11:23:17 SGT 2017"), df.parse("Thu Feb 23 12:00:00 SGT 2017"));
		PlaceActivity placeActivity2 = new PlaceActivity("Id{id=ChIJqbrxYIwZ2jERV9rTVcsl1Lk, scope=GOOGLE}",
				df.parse("Thu Feb 23 12:00:17 SGT 2017"), df.parse("Thu Feb 23 14:23:17 SGT 2017"));
		PlaceActivity placeActivity3 = new PlaceActivity("Id{id=ChIJdY7JN4oZ2jERVg6-qPEGJ3U, scope=GOOGLE}",
				df.parse("Thu Feb 23 14:23:10 SGT 2017"), df.parse("Thu Feb 23 18:23:17 SGT 2017"));
		listObjects.add(placeActivity1);
		listObjects.add(transportActivity1);
		listObjects.add(placeActivity2);
		listObjects.add(placeActivity3);
		map.put(1, listObjects);
		map.put(2, listObjects);
		map.put(3, listObjects);
		System.out.println(listObjects);
		System.out.println(map.toString());
		Itinerary itr = new Itinerary(trip.getId(), map);
		System.out.println(itr.getActivityMap().toString());
		resultList.add(itr);
		resultList.add(itr);
		return resultList;
	}

	public static List<Itinerary> generateTrips(Trip trip) {
		if (trip == null)
			return null;
		Location startLocation = trip.getStartPoint();
		Comparator<Place> comparator = new ActivitiesComparator();
		List<Itinerary> listItinerary = null;
		int numDays = RecommenderEngine.betweenDates(trip.getFromDate(), trip.getToDate());
		listItinerary = new ArrayList<Itinerary>();
		Map<String, Location> locationMap = new HashMap<String, Location>();
		Set<Place> uniqueSet = new HashSet<Place>();
		Map<Integer, List<Object>> itineraryMap = new HashMap<Integer, List<Object>>();
		for (int i = 0; i < numDays; i++) {
			Tree<Pair<Object, Integer>> tree = new Tree(Pair.of(startLocation, 0));
			Queue<Pair<Object, Integer>> treeQueue = new LinkedList<Pair<Object, Integer>>();
			treeQueue.add(Pair.of((Object) startLocation, 0));
			long time = trip.getFromDate().getTime() + 1440 * i * ONE_MINUTE_IN_MILLIS;

			Date currentDate = new Date(time);
			currentDate.setHours(7);
			// Tree<Pair<Object, Integer>> node = new
			// Tree(Pair.of(startLocation, 0));
			boolean tmp = true;
			
			while (treeQueue.size() > 0) {

				Pair<Object, Integer> obj = treeQueue.remove();
				if (obj.getLeft() instanceof PlaceActivity
						&& (((PlaceActivity) obj.getLeft()).getToDate().getHours() > 21
								|| ((PlaceActivity) obj.getLeft()).getToDate().getHours() < 8 || obj.getRight() >= 12))
					continue;

				if (obj.getLeft() instanceof Location) {
					PriorityQueue<Place> queue = new PriorityQueue<Place>(40, comparator);
					List<String> listInterests = new ArrayList<String>();
					listInterests.add("Breakfast");
					listInterests.add("bakery");
					queue.addAll(PlaceManager.getListAttractionPlaces(startLocation, 10000, listInterests));
					for (int j = 0; j < Math.min(1, queue.size()); j++) {

						Place tmpObject = queue.remove();
						while (uniqueSet.contains(tmpObject) && queue.size() > 0) {
							tmpObject = queue.remove();
						}
						if (uniqueSet.contains(tmpObject))
							continue;
						uniqueSet.add(tmpObject);
						TransportActivity activity = TransportManager.getTransportationDetails(startLocation,
								new Location(tmpObject.getLatitude(), tmpObject.getLongitude()), currentDate);
						Date endDate = new Date(currentDate.getTime());
						endDate.setHours(9);
						endDate.setMinutes(0);
						PlaceActivity placeActivity = new PlaceActivity(tmpObject.getPlaceId().toString(), currentDate,
								endDate);
						treeQueue.add(Pair.of((Object) (placeActivity), obj.getRight() + 2));

						tree.addLeaf(Pair.of((Object) obj.getLeft(), obj.getRight()),
								Pair.of((Object) activity, obj.getRight() + 1));
						tree.addLeaf(Pair.of((Object) activity, obj.getRight() + 1),
								Pair.of((Object) placeActivity, obj.getRight() + 2));
						locationMap.put(tmpObject.getPlaceId().toString(),
								new Location(tmpObject.getLatitude(), tmpObject.getLongitude()));
					}
				} else if (obj.getLeft() instanceof PlaceActivity) {
					PriorityQueue<Place> queue = new PriorityQueue<Place>(40, comparator);
					if (obj.getRight() != 4 && obj.getRight() != 10) {
						queue.addAll(PlaceManager.getListAttractionPlaces(
								locationMap.get(((PlaceActivity) obj.getLeft()).getPlaceId()), 10000,
								trip.getListInterests()));
						for (int j = 0; j < Math.min(1, queue.size()); j++) {
							Place tmpObject = queue.remove();

							while (uniqueSet.contains(tmpObject) && queue.size() > 0) {
								tmpObject = queue.remove();
							}

							if (uniqueSet.contains(tmpObject))
								continue;
							uniqueSet.add(tmpObject);

							Place placeObj = null;
							Iterator<Place> itr = uniqueSet.iterator();
							while (itr.hasNext()) {
								Place place = (Place) itr.next();
								if (((PlaceActivity) obj.getLeft()).getPlaceId().equals(place.getPlaceId().toString()))
									placeObj = place;
							}

							TransportActivity activity = TransportManager.getTransportationDetails(
									new Location(placeObj.getLatitude(), placeObj.getLongitude()),
									new Location(tmpObject.getLatitude(), tmpObject.getLongitude()), currentDate);

							Date endDate = null;
							if (obj.getRight() == 2) {
								endDate = new Date(currentDate.getTime());
								endDate.setHours(12);
								endDate.setMinutes(0);
							} else if (obj.getRight() == 6) {
								endDate = new Date(currentDate.getTime());
								endDate.setHours(16);
								endDate.setMinutes(0);
							} else if (obj.getRight() == 8) {
								endDate = new Date(currentDate.getTime());
								endDate.setHours(19);
								endDate.setMinutes(0);
							}

							PlaceActivity placeActivity = new PlaceActivity(tmpObject.getPlaceId().toString(),
									activity.getToDate(), endDate);
							treeQueue.add(Pair.of((Object) (placeActivity), obj.getRight() + 2));

							tree.addLeaf(Pair.of((Object) obj.getLeft(), obj.getRight()),
									Pair.of((Object) activity, obj.getRight() + 1));
							tree.addLeaf(Pair.of((Object) activity, obj.getRight() + 1),
									Pair.of((Object) placeActivity, obj.getRight() + 2));
							locationMap.put(tmpObject.getPlaceId().toString(),
									new Location(tmpObject.getLatitude(), tmpObject.getLongitude()));
						}
					} else {

						List<String> listInterests = new ArrayList<String>();
						listInterests.add("Lunch");
						listInterests.add("restaurant");
						queue.addAll(PlaceManager.getListAttractionPlaces(startLocation, 10000, listInterests));
						for (int j = 0; j < Math.min(1, queue.size()); j++) {
							Place tmpObject = queue.remove();

							while (uniqueSet.contains(tmpObject) && queue.size() > 0) {
								tmpObject = queue.remove();
							}

							if (uniqueSet.contains(tmpObject))
								continue;
							uniqueSet.add(tmpObject);

							Place placeObj = null;
							Iterator<Place> itr = uniqueSet.iterator();
							while (itr.hasNext()) {
								Place place = (Place) itr.next();
								if (((PlaceActivity) obj.getLeft()).getPlaceId().equals(place.getPlaceId().toString()))
									placeObj = place;
							}

							TransportActivity activity = TransportManager.getTransportationDetails(
									new Location(placeObj.getLatitude(), placeObj.getLongitude()),
									new Location(tmpObject.getLatitude(), tmpObject.getLongitude()), currentDate);

							Date endDate = null;
							if (obj.getRight() == 4) {
								endDate = new Date(currentDate.getTime());
								endDate.setHours(13);
								endDate.setMinutes(0);
							} else if (obj.getRight() == 10) {
								endDate = new Date(currentDate.getTime());
								endDate.setHours(20);
								endDate.setMinutes(0);
							}

							PlaceActivity placeActivity = new PlaceActivity(tmpObject.getPlaceId().toString(),
									currentDate, endDate);
							treeQueue.add(Pair.of((Object) (placeActivity), obj.getRight() + 2));

							tree.addLeaf(Pair.of((Object) obj.getLeft(), obj.getRight()),
									Pair.of((Object) activity, obj.getRight() + 1));
							tree.addLeaf(Pair.of((Object) activity, obj.getRight() + 1),
									Pair.of((Object) placeActivity, obj.getRight() + 2));
							locationMap.put(tmpObject.getPlaceId().toString(),
									new Location(tmpObject.getLatitude(), tmpObject.getLongitude()));
						}
					}
				}
			}
			listGroupActivities = new ArrayList<List<Object>>();
			getItineraryFromTree(new ArrayList<Object>(), tree);
			if(listGroupActivities.size()>0)
				itineraryMap.put(i, listGroupActivities.get(0));
		}
		Itinerary itro = new Itinerary(trip.getId(), itineraryMap);
		listItinerary.add(itro);
		return listItinerary;
	}

	public static void getItineraryFromTree(List<Object> object, Tree<Pair<Object, Integer>> tree) {
		if ((tree.getHead().getLeft() instanceof TransportActivity)
				|| (tree.getHead().getLeft() instanceof PlaceActivity))
			object.add(tree.getHead().getLeft());
		if (tree.getSubTrees().size() == 0) {
			listGroupActivities.add(new ArrayList<Object>(object));
		}
		for (Tree<Pair<Object, Integer>> child : tree.getSubTrees()) {
			getItineraryFromTree(object, child);
		}
		if ((tree.getHead().getLeft() instanceof TransportActivity)
				|| (tree.getHead().getLeft() instanceof PlaceActivity))
			object.remove(object.size() - 1);
	}

	public static String printTree(int increment, Tree<Pair<Object, Integer>> tree) {

		String s = "";
		String inc = "";
		for (int i = 0; i < increment; ++i) {
			inc = inc + " ";
		}
		if (tree.getHead().getLeft() instanceof Location)
			s = inc + ((Location) tree.getHead().getLeft()).getLatitude();
		else if (tree.getHead().getLeft() instanceof PlaceActivity)
			s = inc + ((PlaceActivity) tree.getHead().getLeft()).getPlaceId().toString() + " StartTime: "
					+ ((PlaceActivity) tree.getHead().getLeft()).getFromDate() + " EndTime: "
					+ ((PlaceActivity) tree.getHead().getLeft()).getToDate();
		for (Tree<Pair<Object, Integer>> child : tree.getSubTrees()) {
			s += "\n" + printTree(increment + 2, child);
		}
		return s;
	}

	public static int betweenDates(Date firstDate, Date secondDate) {
		return (int) ChronoUnit.DAYS.between(firstDate.toInstant(), secondDate.toInstant());
	}
}
