/**
 * 
 */
package tripplanner_server.models;

import java.util.Comparator;

import net.sf.sprockets.google.Place;

/**
 * @author MADHAVAN001
 *
 */
public class ActivitiesComparator implements Comparator<Place> {

	@Override
	public int compare(Place left, Place right) {
		if (left.getRating() * left.getRatingCount() < right.getRating() * right.getRatingCount())
			return -1;
		else if (left.getRating() * left.getRatingCount() > right.getRating() * right.getRatingCount())
			return 1;
		else
			return 0;
	}
}
