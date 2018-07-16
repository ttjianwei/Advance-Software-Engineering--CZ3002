package tripplanner_server.manager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.sf.sprockets.google.Place;
import net.sf.sprockets.google.Places;
import net.sf.sprockets.google.Places.Params;
import net.sf.sprockets.google.Places.Response;
import tripplanner_server.models.Location;

/**
 * Manager class to create, edit and modify the place attributes
 * 
 * @author MADHAVAN001
 *
 */
public class PlaceManager {

	/**
	 * 
	 * @param centre
	 * @param radius
	 * @param listKeyword
	 * @return
	 */
	public static List<Place> getListAttractionPlaces(Location centre, int radius, List<String> listKeyword) {
		List<Place> attractions = new ArrayList<Place>();

		for (String keyword : listKeyword) {
			try {
				Response<List<Place>> response = Places.nearbySearch(
						Params.create().latitude(centre.getLatitude()).longitude(centre.getLongitude()).radius(radius)
								.type(keyword).openNow(true).maxResults(5000),
						Places.FIELD_NAME | Places.FIELD_VICINITY | Places.FIELD_DESCRIPTION | Places.FIELD_TYPES);

				List<Place> placesResult = response.getResult();
				attractions.addAll(placesResult);
			} catch (IOException e) {
				System.out.println("Error");
				e.printStackTrace();
			}
		}
		return attractions;
	}
	
	/**
	 * 
	 * @param query
	 * @return
	 */
	public static List<Place> getPlacebyText(String query) {
		List<Place> listPlaces = new ArrayList<Place>();
		try {
			listPlaces = Places.textSearch(Params.create().query(query)).getResult();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return listPlaces;
	}
}