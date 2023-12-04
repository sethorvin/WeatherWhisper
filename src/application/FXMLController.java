package application;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.text.Text;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import WeatherWhisper.WeatherDataAPI;
public class FXMLController {
		
		private LocalDate date = LocalDate.now();
		
		//fields for dates
		@FXML private Text D1;
		@FXML private Text D2;
		@FXML private Text D3;
		@FXML private Text D4;
		@FXML private Text D5;
		
		//fields for high/lows
		@FXML private Text D1F;
		@FXML private Text D2F;
		@FXML private Text D3F;
		@FXML private Text D4F;
		@FXML private Text D5F;
		
		//fields for top display info
		@FXML private Label address;
		@FXML private Label currentTemp;
		@FXML private Label weatherDesc;
		@FXML private Label currentBounds;
		@FXML private Text currentWind;
		
		//fields for time for hourly display
		@FXML private Text T11;
		@FXML private Text T21;
		@FXML private Text T31;
		@FXML private Text T41;
		@FXML private Text T51;
		@FXML private Text T61;
		@FXML private Text T71;
		
		@FXML private Text T12;
		@FXML private Text T22;
		@FXML private Text T32;
		@FXML private Text T42;
		@FXML private Text T52;
		@FXML private Text T62;
		@FXML private Text T72;
		
		@FXML private Text T13;
		@FXML private Text T23;
		@FXML private Text T33;
		@FXML private Text T43;
		@FXML private Text T53;
		@FXML private Text T63;
		@FXML private Text T73;
		
		@FXML private Text T14;
		@FXML private Text T24;
		@FXML private Text T34;
		@FXML private Text T44;
		@FXML private Text T54;
		@FXML private Text T64;
		@FXML private Text T74;	
		//fields for hourly temperature farenheit
		@FXML private Text T1F;
		@FXML private Text T2F;
		@FXML private Text T3F;
		@FXML private Text T4F;
		@FXML private Text T5F;
		@FXML private Text T6F;
		@FXML private Text T7F;
		
		//fields for hourly temperature Celsius
		@FXML private Text T1C;
		@FXML private Text T2C;
		@FXML private Text T3C;
		@FXML private Text T4C;
		@FXML private Text T5C;
		@FXML private Text T6C;
		@FXML private Text T7C;
		
		//wind speeds hourly 
		@FXML private Text T1W;
		@FXML private Text T2W;
		@FXML private Text T3W;
		@FXML private Text T4W;
		@FXML private Text T5W;
		@FXML private Text T6W;
		@FXML private Text T7W;
		
		//precipitation hourly
		@FXML private Text T1P;
		@FXML private Text T2P;
		@FXML private Text T3P;
		@FXML private Text T4P;
		@FXML private Text T5P;
		@FXML private Text T6P;
		@FXML private Text T7P;
	
	//read data from the provided weather api pull
@FXML public void initialize(WeatherDataAPI weather) 
{
	DateTimeFormatter format = DateTimeFormatter.ofPattern("MM/dd");
	DecimalFormat dFormat = new DecimalFormat("#.#");
	int currentHour = LocalTime.now().getHour();
	//get the dates for display
	D1.setText(format.format(date.plusDays(1)));
	D2.setText(format.format(date.plusDays(2)));
	D3.setText(format.format(date.plusDays(3)));
	D4.setText(format.format(date.plusDays(4)));
	D5.setText(format.format(date.plusDays(5)));
	
	//get header info
	
	address.setText(weather.getAddress().toString());
	currentTemp.setText(weather.getCurrentTemp().toString() + "°F");
	weatherDesc.setText(weather.getCurrentSkyConditions().toString());
	currentBounds.setText(weather.getDailyMaxTemps().get(0).toString()+ "°F/" +weather.getDailyMinTemps().get(0).toString()+ "°F");
	
	//get weather
	D1F.setText(weather.getDailyMaxTemps().get(1) + "°F/" + weather.getDailyMinTemps().get(1) + "°F");
	D2F.setText(weather.getDailyMaxTemps().get(2) + "°F/" + weather.getDailyMinTemps().get(2) + "°F");
	D3F.setText(weather.getDailyMaxTemps().get(3) + "°F/" + weather.getDailyMinTemps().get(3) + "°F");
	D4F.setText(weather.getDailyMaxTemps().get(4) + "°F/" + weather.getDailyMinTemps().get(4) + "°F");
	D5F.setText(weather.getDailyMaxTemps().get(5) + "°F/" + weather.getDailyMinTemps().get(5) + "°F");
	
	currentWind.setText("Wind Direction: " + weather.getCurrentWindDirection()+" Speed: "+weather.getCurrentWindSpeed()+ " MPH");

	//fields for time for hourly display
	T11.setText(timeToString(currentHour+1));
	T21.setText(timeToString(currentHour+2));
	T31.setText(timeToString(currentHour+3));
	T41.setText(timeToString(currentHour+4));
	T51.setText(timeToString(currentHour+5));
	T61.setText(timeToString(currentHour+6));
	T71.setText(timeToString(currentHour+7));
	
	T12.setText(timeToString(currentHour+1));
	T22.setText(timeToString(currentHour+2));
	T32.setText(timeToString(currentHour+3));
	T42.setText(timeToString(currentHour+4));
	T52.setText(timeToString(currentHour+5));
	T62.setText(timeToString(currentHour+6));
	T72.setText(timeToString(currentHour+7));
	
	T13.setText(timeToString(currentHour+1));
	T23.setText(timeToString(currentHour+2));
	T33.setText(timeToString(currentHour+3));
	T43.setText(timeToString(currentHour+4));
	T53.setText(timeToString(currentHour+5));
	T63.setText(timeToString(currentHour+6));
	T73.setText(timeToString(currentHour+7));
	
	T14.setText(timeToString(currentHour+1));
	T24.setText(timeToString(currentHour+2));
	T34.setText(timeToString(currentHour+3));
	T44.setText(timeToString(currentHour+4));
	T54.setText(timeToString(currentHour+5));
	T64.setText(timeToString(currentHour+6));
	T74.setText(timeToString(currentHour+7));
	
	//fields for hourly temperature farenheit
	T1F.setText(weather.getHourlyTemps().get(currentHour+1) + "°F");
	T2F.setText(weather.getHourlyTemps().get(currentHour+2)+"°F");
	T3F.setText(weather.getHourlyTemps().get(currentHour+3)+"°F");
	T4F.setText(weather.getHourlyTemps().get(currentHour+4)+"°F");
	T5F.setText(weather.getHourlyTemps().get(currentHour+5)+"°F");
	T6F.setText(weather.getHourlyTemps().get(currentHour+6)+"°F");
	T7F.setText(weather.getHourlyTemps().get(currentHour+7)+"°F");
			
	//fields for hourly temperature Celsius
	T1C.setText(dFormat.format((Double.parseDouble((weather.getHourlyTemps().get(currentHour+1)).toString())-32)*5/9) + "°C");
	T2C.setText(dFormat.format((Double.parseDouble((weather.getHourlyTemps().get(currentHour+2)).toString())-32)*5/9) + "°C");
	T3C.setText(dFormat.format((Double.parseDouble((weather.getHourlyTemps().get(currentHour+3)).toString())-32)*5/9) + "°C");
	T4C.setText(dFormat.format((Double.parseDouble((weather.getHourlyTemps().get(currentHour+4)).toString())-32)*5/9) + "°C");
	T5C.setText(dFormat.format((Double.parseDouble((weather.getHourlyTemps().get(currentHour+5)).toString())-32)*5/9) + "°C");
	T6C.setText(dFormat.format((Double.parseDouble((weather.getHourlyTemps().get(currentHour+6)).toString())-32)*5/9) + "°C");
	T7C.setText(dFormat.format((Double.parseDouble((weather.getHourlyTemps().get(currentHour+7)).toString())-32)*5/9) + "°C");
	
	//wind speeds hourly 
	T1W.setText((weather.getHourlyWindSpeeds().get(currentHour+1)) + "MPH");
	T2W.setText((weather.getHourlyWindSpeeds().get(currentHour+2)) + "MPH");
	T3W.setText((weather.getHourlyWindSpeeds().get(currentHour+3)) + "MPH");
	T4W.setText((weather.getHourlyWindSpeeds().get(currentHour+4)) + "MPH");
	T5W.setText((weather.getHourlyWindSpeeds().get(currentHour+5)) + "MPH");
	T6W.setText((weather.getHourlyWindSpeeds().get(currentHour+6)) + "MPH");
	T7W.setText((weather.getHourlyWindSpeeds().get(currentHour+7)) + "MPH");
	
	//precipitation hourly
	T1P.setText((weather.getHourlyPrecipProbs().get(currentHour+1))+ "%");
	T2P.setText((weather.getHourlyPrecipProbs().get(currentHour+2))+ "%");
	T3P.setText((weather.getHourlyPrecipProbs().get(currentHour+3))+ "%");
	T4P.setText((weather.getHourlyPrecipProbs().get(currentHour+4))+ "%");
	T5P.setText((weather.getHourlyPrecipProbs().get(currentHour+5))+ "%");
	T6P.setText((weather.getHourlyPrecipProbs().get(currentHour+6))+ "%");
	T7P.setText((weather.getHourlyPrecipProbs().get(currentHour+7))+ "%");
}
	
	//Handler for button reload
	
	
	//return string based on AM or PM of time
	private String timeToString(int time)
	{
		if (time == 0)
				return "12 AM";
		if (time == 12)
			return "12 PM";
		if (time > 12)
		{
			return time - 12 + "PM";
		}
		else return time + "AM";
	}
}