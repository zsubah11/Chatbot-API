import com.google.gson.*;
import java.util.*; 
import java.net.*; 
import java.io.*;

//class for the weather API
//returns the weather and the high and low for that zipcode 

public class weatherAPI {

	public double parseJsonFunction(String json, String m) 
	{
		JsonObject object = new JsonParser().parse(json).getAsJsonObject();
		
		//print out object and see what is inside of it 
		JsonObject main = object.getAsJsonObject("main"); 
		
		//since we need the temperature, the max and the mins
		//instead of creating the function three times
		//we can use a string to determine which one we want and return that 
		//converted the temperatures from Kelvin to Fahrenheit using the formula 1.8(K - 273) + 32
		if (m == "Temp")
		{
			//converts the temp from Kelvins to Fahrenheit
			double t = 1.8 * ((main.get("temp").getAsDouble()) - 273.15) + 32;
			String s = String.valueOf(t); //turns the double to a string
			s = String.format("%.2f", t); //formats it to two decimal places
			return Double.parseDouble(s); //then returns the value by converting it to a double
		}
			
		else if (m == "Max")
		{
			//converts the temp from Kelvins to Fahrenheit
			double t = 1.8 * ((main.get("temp_max").getAsDouble()) - 273.15) + 32;
			String s = String.valueOf(t); //turns the double to a string
			s = String.format("%.2f", t); //formats it to two decimal places
			return Double.parseDouble(s); //then returns the value by converting it to a double
			
			
		}
			
		else if(m == "Min")
		{
			//converts the temp from Kelvins to Fahrenheit
			double t = 1.8 * ((main.get("temp_min").getAsDouble()) - 273.15) + 32;
			String s = String.valueOf(t); //turns the double to a string
			s = String.format("%.2f", t); //formats it to two decimal places
			return Double.parseDouble(s); //then returns the value by converting it to a double
		}
			
		else
		{
			return 0.0; 
		}
			
	} 
		
	//method to convert the buffer reader info into a string 
	public String URLtoString(String wURL) throws Exception 
	{
		URL url = new URL(wURL);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
				
		StringBuilder sd = new StringBuilder(); 
		BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		String r;
				 
		// Convert rd variable from BufferReader to String and store in variable called result
		//reads the line until we reach the end of the line 
		while ((r = rd.readLine()) != null) 
		{
			sd.append(r); //append the series of strings to the string builder  
		}
		rd.close(); //closes the buffer reader 
			
		return sd.toString(); //convert to a string and return 
		}
} 
