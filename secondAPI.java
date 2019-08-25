import com.google.gson.*;
import java.util.*; 
import java.net.*; 
import java.io.*;
import java.lang.*; 

//official second API
//merriam webster dictionary
//returns definitions of words you ask for 
public class secondAPI {
	
	//parses the JSON and returns the value we want 
	public String parseJsonFunction(String json) 
	{
		//gets the entire array from first to end
		JsonArray array = new JsonParser().parse(json).getAsJsonArray();
	
		String s = "";
		
		//iterates through the array which only has one index 
		//and gets the definition for the word 
	
		//gets the 1st JSON object if multiple of them exist
		//in the API, sometimes multiple objects exist
		//and sometimes only one object exists depending on the word we need
		//therefore, to be safe, we shall use the first object since there will always be one object
		JsonObject object = array.get(0).getAsJsonObject(); 
		
	
		//extracts the JSOn array of shortdef from the list 
		JsonArray shortdef = object.getAsJsonArray("shortdef"); 
			
		//define a string of arrays
		//using a function from JSON
		//GSON can parse things into JSON using this method
		//the first parameter shortdef is the JSON source 
		//the second parameter is the java class to parse the JSON into an instance of
		String[] arr = new Gson().fromJson(shortdef, String[].class);
		
		List<String> listName = new ArrayList<>(); //define an array list 
		listName = Arrays.asList(arr); //returns a list view of the specified array
		
		s = listName.get(0); //returns the first definition in the array list 
		
		return s; //returns the first definition in the list 

	}

	
	
	//method to convert the buffer reader info into a string 
	public String URLtoString(String wURL) throws Exception 
	{
		URL url = new URL(wURL); //create a url object from a string 
		
		//an URL connection that supports HTTP and has HTTP specific features 
		//open connection refers to the connection to the remote object referred to by the URL
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