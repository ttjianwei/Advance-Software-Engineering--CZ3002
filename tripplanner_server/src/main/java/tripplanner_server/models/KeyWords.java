/**
 * 
 */
package tripplanner_server.models;

import java.util.HashMap;
import java.util.Map;

/**
 * @author MADHAVAN001
 *
 */
public class KeyWords {
	public static String ACCOUNTING = "accounting";
	public static String AIRPORT = "airport";
	public static String AMUSEMENT_PARK = "amusement_park";
	public static String AQUARIUM = "aquarium";
	public static String ART_GALLERY = "art_gallery";
	public static String ATM = "atm";
	public static String BAKERY = "bakery";
	public static String BANK = "bank";
	public static String BAR = "bar";
	public static String BEAUTY_SALON = "beauty_salon";
	public static String BICYCLE_STORE = "bicycle_store";
	public static String BOOK_STORE = "book_store";
	public static String BOWLING_ALLEY = "bowling_alley";
	public static String BUS_STATION = "bus_station";
	public static String CAFE = "cafe";
	public static String CAMPGROUND = "campground";
	public static String CAR_DEALER = "car_dealer";
	public static String CAR_RENTAL = "car_rental";
	public static String CAR_REPAIR = "car_repair";
	public static String CAR_WASH = "car_wash";
	public static String CASINO = "casino";
	public static String CEMETERY = "cemetery";
	public static String CHURCH = "church";
	public static String CITY_HALL = "city_hall";
	public static String CLOTHING_STORE = "clothing_store";
	public static String CONVINIENCE_STORE = "convenience_store";
	public static String COURTHOUSE = "courthouse";
	public static String DENTIST = "dentist";
	public static String DEPARTMENT_STORE = "department_store";
	public static String DOCTOR = "doctor";
	public static String ELECTRICIAN = "electrician";
	public static String ELECTRONICS_STORE = "electronics_store";
	public static String EMBASSY = "embassy";
	public static String ESTABLISHMENT = "establishment";
	public static String FINANCE = "finance";
	public static String FIRE_STATION = "fire_station";
	public static String FLORIST = "florist";
	public static String FOOD = "food";
	public static String FUNERAL_HOME = "funeral_home";
	public static String FURNITURE_STORE = "furniture_store";
	public static String GAS_STATION = "gas_station";
	public static String GROCERY = "grocery_or_supermarket";
	public static String GYM = "gym";
	public static String HAIR_CARE = "hair_care";
	public static String HARDWARE_STORE = "hardware_store";
	public static String HEALTH = "health";
	public static String HINDU_TEMPLE = "hindu_temple";
	public static String HOME_GOODS = "home_goods_store";
	public static String HOSPITAL = "hospital";
	public static String INSURANCE_AGENCY = "insurance_agency";
	public static String JEWELERY_STORE = "jewelry_store";
	public static String LAUNDRY = "laundry";
	public static String LAWYER = "lawyer";
	public static String LIBRARY = "library";
	public static String LIQUOR = "liquor_store";
	public static String GOVERNMENT_OFFICE = "local_government_office";
	public static String LOCKSMITH = "locksmith";
	public static String LODGING = "lodging";
	public static String MEAL_DELIVERY = "meal_delivery";
	public static String MEAL_TAKEAWAY = "meal_takeaway";
	public static String MOSQUE = "mosque";
	public static String MOVIE_RENTAL = "movie_rental";
	public static String MOVIE_THEATER = "movie_theater";
	public static String MOVING_COMPANY = "moving_company";
	public static String MUSEUM = "museum";
	public static String NIGHT_CLUB = "night_club";
	public static String PAINTER = "painter";
	public static String PARK = "park";
	public static String PARKING = "parking";
	public static String PET_STORE = "pet_store";
	public static String PHARMACY = "pharmacy";
	public static String PHYSIOTHERAPIST = "physiotherapist";
	public static String PLACE_OF_WORSHIP = "place_of_worship";
	public static String PLUMBER = "plumber";
	public static String POLICE = "police";
	public static String POST_OFFICE = "post_office";
	public static String REAL_ESTATE_AGENCY = "real_estate_agency";
	public static String RESTAURANT = "restaurant";
	public static String ROOFING_CONTRACTOR = "roofing_contractor";
	public static String RV_PARK = "rv_park";
	public static String SCHOOL = "school";
	public static String SHOE_STORE = "shoe_store";
	public static String SHOPPING_MALL = "shopping_mall";
	public static String SPA = "spa";
	public static String STADIUM = "stadium";
	public static String STORAGE = "storage";
	public static String STORE = "store";
	public static String SUBWAY_STATION = "subway_station";
	public static String TAXI_STAND = "taxi_stand";
	public static String TRAIN_STATION = "train_station";
	public static String TRANSIT_STATION = "transit_station";
	public static String TRAVEL_AGENCY = "travel_agency";
	public static String UNIVERSITY = "university";
	public static String VETERINARY_CARE = "veterinary_care";
	public static String ZOO = "zoo";
	
	public static Map<String, Integer> timeMap;
	
	static{
		timeMap = new HashMap<String,Integer>();
		
		timeMap.put(AQUARIUM, 180);
		timeMap.put(AMUSEMENT_PARK, 360);
		timeMap.put(ART_GALLERY, 180);
		timeMap.put(BAKERY, 60);
		timeMap.put(BOWLING_ALLEY, 180);
		timeMap.put(CAFE, 60);
		timeMap.put(CAMPGROUND, 240);
	}
	
	

}
