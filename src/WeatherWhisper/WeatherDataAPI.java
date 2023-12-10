package WeatherWhisper;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import org.json.*;

// Stores all of the Weather Data for a single location
public class WeatherDataAPI {
	
	private String inputLocation;
	private JSONObject weatherData;
	private Boolean validAddress;
	private Object address;
	private ZonedDateTime currentTime;
	private ZonedDateTime sunriseTime;
	private ZonedDateTime sunsetTime;
	private Object currentTemp;
	private Object currentSkyConditions;
	private Object currentWindSpeed;
	private Object currentWindDirection;
	private Object currentUVindex;
	private ArrayList<Object> hourlyTemps;
	private ArrayList<Object> hourlyWindSpeeds;
	private ArrayList<Object> hourlyPrecipProbs;
	private ArrayList<Object> hourlyHumidities;
	private ArrayList<Object> hourlySkyConditions;
	private ArrayList<Object> dailyMaxTemps;
	private ArrayList<Object> dailyMinTemps;
	private ArrayList<Object> dailySkyConditions;

	// Constructor that uses pre-existing JSON data (i.e., API has already been called)
	public WeatherDataAPI (JSONObject weatherData){
		
		this.weatherData = weatherData;
		
		// Initialize ArrayList fields
		hourlyTemps = new ArrayList<Object>();
		hourlyWindSpeeds = new ArrayList<Object>();
		hourlyPrecipProbs = new ArrayList<Object>();
		hourlyHumidities = new ArrayList<Object>();
		hourlySkyConditions = new ArrayList<Object>();
		dailyMaxTemps = new ArrayList<Object>();
		dailyMinTemps = new ArrayList<Object>();
		dailySkyConditions = new ArrayList<Object>();
		
		// Extract all desired data from JSONObject, as long as the address was valid
		setValidity();
		if(isValid()) {
			setAddress();
			setTime();
			setSunriseTime();
			setSunsetTime();
			setCurrentTemp();
			setCurrentSkyConditions();
			setCurrentWindSpeed();
			setCurrentWindDirection();
			setCurrentUVindex();
			setHourlyTemps();
			setHourlyWindSpeeds();
			setHourlyPrecipProbs();
			setHourlyHumidities();
			setHourlySkyConditions();
			setDailyMaxTemps();
			setDailyMinTemps();
			setDailySkyConditions();
		}
		
		// inputLocation is used for updateWeatherData(); however, it's not provided as a parameter in this constructor.
		// So, default it to the resolvedAddress from the JSON (already stored in this.address after setAddress())
		inputLocation = (String) address;
	}
	
	// Constructor that takes String containing location name (i.e., API has not already been called -> constructor must call API)
	public WeatherDataAPI (String inputLocation) {
		
		this.inputLocation = inputLocation;
		
		// Initialize ArrayList fields
		hourlyTemps = new ArrayList<Object>();
		hourlyWindSpeeds = new ArrayList<Object>();
		hourlyPrecipProbs = new ArrayList<Object>();
		hourlyHumidities = new ArrayList<Object>();
		hourlySkyConditions = new ArrayList<Object>();
		dailyMaxTemps = new ArrayList<Object>();
		dailyMinTemps = new ArrayList<Object>();
		dailySkyConditions = new ArrayList<Object>();
		
		// Use API to fetch JSON data and extract desired weather measurements
		updateWeatherData();
	}
	
	// Re-calls the Visual Crossing API w/ the same inputLocation String to update weather data ... can also be used for initial API call
	public void updateWeatherData() {
		try {
			// Call API with specified location to retrieve JSONObject
			weatherData = WeatherService.fetchWeatherData(inputLocation);
			
			// Extract all desired data from JSONObject
			setValidity();
			if(isValid()) {
				setAddress();
				setTime();
				setSunriseTime();
				setSunsetTime();
				setCurrentTemp();
				setCurrentSkyConditions();
				setCurrentWindSpeed();
				setCurrentWindDirection();
				setCurrentUVindex();
				setHourlyTemps();
				setHourlyWindSpeeds();
				setHourlyPrecipProbs();
				setHourlyHumidities();
				setHourlySkyConditions();
				setDailyMaxTemps();
				setDailyMinTemps();
				setDailySkyConditions();
			}
			
		} catch (IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	// Determine if JSONObject represents valid location (i.e., that the API request wasn't bad) based on "validAddress" field of JSONObject
	private void setValidity() {
		validAddress = (Boolean) weatherData.get("validAddress");
	}
	
	public Boolean isValid() {
		return validAddress;
	}
	
	// Set address of city according to resolved address determined by Visual Crossing API
	// (e.g., "Conway,,,,, Arkansas" resolves to "Conway, AR, United States" by API itself
	private void setAddress() {
		address = weatherData.get("resolvedAddress");
	}
	
	public Object getAddress() {
		return address;
	}
	
	// Determines the local time of the city whose weather is being stored by converting our system time to the city's local time via
	// timezones. We ignore the "datetime" and "datetimeEpoch" JSON variables because they only update in intervals of 15 minutes while 
	// also not always showing the most recent 15-minute marker (likely due to the free pricing plan of the Visual Crossing API), which
	// can be problematic if the city's time is 3:08pm and the JSON is storing 2:45pm, for example.
	private void setTime() {
		// Determine weather-location's time zone
		String locationTimeZone = (String) weatherData.get("timezone");
		ZoneId locationZone = ZoneId.of(locationTimeZone);
		
		// Determine system's time zone
		LocalDateTime systemTime = LocalDateTime.now();
		ZoneId systemZone = ZoneId.systemDefault();
		
		// Convert system's local time to weather-location's local time
		ZonedDateTime currentZonedDateTime = ZonedDateTime.of(systemTime, systemZone);
		ZonedDateTime locationZonedDateTime = currentZonedDateTime.withZoneSameInstant(locationZone);
		
		currentTime = locationZonedDateTime;
	}
	
	public ZonedDateTime getTime() {
		return currentTime;
	}
	
	public int getHour() {
		return currentTime.getHour();
	}
	
	private void setSunriseTime() {
		String locTimeZone = (String) weatherData.get("timezone");
		
		// Create/format string containing all time/date info for the sunrise
		String sunriseStr = (String) weatherData.getJSONArray("days").getJSONObject(0).get("datetime");
		sunriseStr += "T" + (String) weatherData.getJSONObject("currentConditions").getString("sunrise");
		sunriseStr += ZoneId.of(locTimeZone).getRules().getOffset(LocalDateTime.now());
		sunriseStr += "[" + locTimeZone + "]";
		
		// Set sunriseTime by parsing sunriseStr
		sunriseTime = ZonedDateTime.parse(sunriseStr);
	}
	
	public ZonedDateTime getSunriseTime() {
		return sunriseTime;
	}
	
	private void setSunsetTime() {
		String locTimeZone = (String) weatherData.get("timezone");
		
		String sunsetStr = (String) weatherData.getJSONArray("days").getJSONObject(0).get("datetime");
		sunsetStr += "T" + (String) weatherData.getJSONObject("currentConditions").getString("sunset");
		sunsetStr += ZoneId.of(locTimeZone).getRules().getOffset(LocalDateTime.now());
		sunsetStr += "[" + locTimeZone + "]";
		
		sunsetTime = ZonedDateTime.parse(sunsetStr);
	}
	
	public ZonedDateTime getSunsetTime() {
		return sunsetTime;
	}
	
	// Sets currentTemp based on "temp" field of the "currentConditions" JSONObject
	private void setCurrentTemp() {
		currentTemp = weatherData.getJSONObject("currentConditions").get("temp");
	}
	
	public Object getCurrentTemp() {
		return currentTemp;
	}
	
	// Sets currentSkyConditions based on "conditions" field of the "currentConditions" JSONObject
	private void setCurrentSkyConditions() {
		currentSkyConditions = weatherData.getJSONObject("currentConditions").get("conditions");
	}
	
	public Object getCurrentSkyConditions() {
		return currentSkyConditions;
	}
	
	// Sets currentWindSpeed based on "windspeed" field of the "currentConditions" JSONObject
	private void setCurrentWindSpeed() {
		currentWindSpeed = weatherData.getJSONObject("currentConditions").get("windspeed");
	}
	
	public Object getCurrentWindSpeed() {
		return currentWindSpeed;
	}
	
	// Sets currentWindDirection based on "winddir" field of the "currentConditions" JSONObject
	private void setCurrentWindDirection() {
		currentWindDirection = weatherData.getJSONObject("currentConditions").get("winddir");
	}
	
	public Object getCurrentWindDirection() {
		return currentWindDirection;
	}
	
	private void setCurrentUVindex() {
		currentUVindex = weatherData.getJSONObject("currentConditions").get("uvindex");
	}
	
	public Object getCurrentUVindex() {
		return currentUVindex;
	}
	
	// Starting at (currentHour + 1) of day 0 (today), for the next 24 JSONObjects that appear in the "hours" JSONArrays of the first two
	// JSONObjects in the "days" JSONArray (i.e, the next 24 hours across today and tomorrow), appends the value in the "temp" field to our 
	// ArrayList of hourlyTemps. Index 0 of hourlyTemps represents the next hour (i.e., the temp at 5pm if it is 4:12pm). 
	private void setHourlyTemps() {
		hourlyTemps.clear();
			
		// Starting at currentHour + 1, append today's remaining hourly temperatures (i.e., until !(hour < 24))
		int currentHour = getHour();
		for(int hour = currentHour + 1; hour < 24; hour++) {
			hourlyTemps.add(weatherData.getJSONArray("days").getJSONObject(0).getJSONArray("hours").getJSONObject(hour).get("temp"));
		}
		
		// Starting at hour 0 (i.e. midnight), append tomorrow's hourly temperatures until 24 total hourlyTemps have been stored
		for(int hour = 0; hour <= currentHour; hour++) {
			hourlyTemps.add(weatherData.getJSONArray("days").getJSONObject(1).getJSONArray("hours").getJSONObject(hour).get("temp"));
		}
		
	}
	
	public ArrayList<Object> getHourlyTemps() {
		return hourlyTemps;
	}
	
	// Starting at (currentHour + 1) of day 0 (today), for the next 24 JSONObjects that appear in the "hours" JSONArrays of the first two
	// JSONObjects in the "days" JSONArray (i.e, the next 24 hours across today and tomorrow), appends the value in the "windspeed" field to our 
	// ArrayList of hourlyWindSpeeds. Index 0 of hourlyWindSpeeds represents the next hour (i.e., the windSpeed at 5pm if it is 4:12pm). 
	private void setHourlyWindSpeeds() {
		hourlyWindSpeeds.clear();
		
		// Starting at currentHour + 1, append today's remaining hourly WindSpeeds (i.e., until !(hour < 24))
		int currentHour = getHour();
		for(int hour = currentHour + 1; hour < 24; hour++) {
			hourlyWindSpeeds.add(weatherData.getJSONArray("days").getJSONObject(0).getJSONArray("hours").getJSONObject(hour).get("windspeed"));
		}
				
		// Starting at hour 0 (i.e. midnight), append tomorrow's hourly WindSpeeds until 24 total hourlyWindSpeeds have been stored
		for(int hour = 0; hour <= currentHour; hour++) {
			hourlyWindSpeeds.add(weatherData.getJSONArray("days").getJSONObject(1).getJSONArray("hours").getJSONObject(hour).get("windspeed"));
		}
		
	}
	
	public ArrayList<Object> getHourlyWindSpeeds() {
		return hourlyWindSpeeds;
	}
	
	// Starting at (currentHour + 1) of day 0 (today), for the next 24 JSONObjects that appear in the "hours" JSONArrays of the first two
	// JSONObjects in the "days" JSONArray (i.e, the next 24 hours across today and tomorrow), appends the value in the "precipprob" field to our 
	// ArrayList of hourlyPrecipProbs. Index 0 of hourlyPrecipProbs represents the next hour (i.e., the precipProb at 5pm if it is 4:12pm). 
	private void setHourlyPrecipProbs() {
		hourlyPrecipProbs.clear();
		
		// Starting at currentHour + 1, append today's remaining hourly PrecipProbs (i.e., until !(hour < 24))
		int currentHour = getHour();
		for(int hour = currentHour + 1; hour < 24; hour++) {
			hourlyPrecipProbs.add(weatherData.getJSONArray("days").getJSONObject(0).getJSONArray("hours").getJSONObject(hour).get("precipprob"));
		}
						
		// Starting at hour 0 (i.e. midnight), append tomorrow's hourly PrecipProbs until 24 total hourlyPrecipProbs have been stored
		for(int hour = 0; hour <= currentHour; hour++) {
			hourlyPrecipProbs.add(weatherData.getJSONArray("days").getJSONObject(1).getJSONArray("hours").getJSONObject(hour).get("precipprob"));
		}
		
	}
	
	public ArrayList<Object> getHourlyPrecipProbs() {
		return hourlyPrecipProbs;
	}
	
	// [Same implementation as previous hourly setters]
	// Index 0 of hourlyHumidities represents the next hour (i.e., the humidity at 5pm if it is 4:12pm)
	private void setHourlyHumidities() {
		hourlyHumidities.clear();
		
		// Starting at currentHour + 1, append today's remaining hourly humidities (i.e., until !(hour < 24))
		int currentHour = getHour();
		for(int hour = currentHour + 1; hour < 24; hour++) {
			hourlyHumidities.add(weatherData.getJSONArray("days").getJSONObject(0).getJSONArray("hours").getJSONObject(hour).get("humidity"));
		}
						
		// Starting at hour 0 (i.e. midnight), append tomorrow's hourly humidities until 24 total hourlyHumidities have been stored
		for(int hour = 0; hour <= currentHour; hour++) {
			hourlyHumidities.add(weatherData.getJSONArray("days").getJSONObject(1).getJSONArray("hours").getJSONObject(hour).get("humidity"));
		}
	}
	
	public ArrayList<Object> getHourlyHumidities(){
		return hourlyHumidities;
	}
	
	// [Same implementation as previous hourly setters]
	// Index 0 of hourlySkyConditions represents the next hour (i.e., the sky conditions at 5pm if it is 4:12pm)
	private void setHourlySkyConditions() {
		hourlySkyConditions.clear();
		
		// Starting at currentHour + 1, append today's remaining hourly humidities (i.e., until !(hour < 24))
		int currentHour = getHour();
		for(int hour = currentHour + 1; hour < 24; hour++) {
			hourlySkyConditions.add(weatherData.getJSONArray("days").getJSONObject(0).getJSONArray("hours").getJSONObject(hour).get("conditions"));
		}
								
		// Starting at hour 0 (i.e. midnight), append tomorrow's hourly humidities until 24 total hourlyHumidities have been stored
		for(int hour = 0; hour <= currentHour; hour++) {
			hourlySkyConditions.add(weatherData.getJSONArray("days").getJSONObject(1).getJSONArray("hours").getJSONObject(hour).get("conditions"));
		}
	}
	
	public ArrayList<Object> getHourlySkyConditions(){
		return hourlySkyConditions;
	}
	
	// For each JSONObject in the "days" JSONArray (i.e., for each day), 
	// appends the value in the "tempmax" field to our ArrayList of dailyMaxTemps. Index 0 is today's maximum temp.
	private void setDailyMaxTemps() {
		dailyMaxTemps.clear();
		
		for(int day = 0; day < 15; day++) {
			dailyMaxTemps.add(weatherData.getJSONArray("days").getJSONObject(day).get("tempmax"));
		}
	}
	
	public ArrayList<Object> getDailyMaxTemps() {
		return dailyMaxTemps;
	}
	
	// For each JSONObject in the "days" JSONArray (i.e., for each day), 
	// appends the value in the "tempmin" field to our ArrayList of dailyMinTemps. Index 0 is today's minimum temp.
	private void setDailyMinTemps() {
		dailyMinTemps.clear();
		
		for(int day = 0; day < 15; day++) {
			dailyMinTemps.add(weatherData.getJSONArray("days").getJSONObject(day).get("tempmin"));
		}
	}
	
	public ArrayList<Object> getDailyMinTemps() {
		return dailyMinTemps;
	}
	
	// For each JSONObject in the "days" JSONArray (i.e., for each day), 
	// appends the value in the "conditions" field to our ArrayList of dailySkyConditions. Index 0 is today's sky conditions.
	private void setDailySkyConditions() {
		dailySkyConditions.clear();
		
		for(int day = 0; day < 15; day++) {
			dailySkyConditions.add(weatherData.getJSONArray("days").getJSONObject(day).get("conditions"));
		}
	}
	
	public ArrayList<Object> getDailySkyConditions(){
		return dailySkyConditions;
	}
	
}
