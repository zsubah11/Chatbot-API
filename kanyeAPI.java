import com.google.gson.*;
import java.util.*; 
import java.net.*; 
import java.io.*;

//this API includes the URL that returns random Kanye West quotes
public class kanyeAPI {
	
	//Function that will parse your JSON 
	public String parseJsonFunction(String json) 
	{
		JsonObject object = new JsonParser().parse(json).getAsJsonObject();
		//print out object and see what is inside of it 
		
		String quote = object.get("quote").getAsString(); //get the quote 
		
		return quote;
	}  
	
	
	//method to convert the buffer reader info into a string 
	public String URLtoString(String mURL) throws Exception 
	{
		URL url = new URL(mURL);
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