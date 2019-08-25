import org.jibble.pircbot.*;
import com.google.gson.*;
import java.util.*; 
import java.net.*; 
import java.io.*;

public class MyBot extends PircBot {
	
	public MyBot()
	{
		this.setName("ZSbot"); //the nickname for the bot  
	}
	
	//this method will be called whenever a message is sent to the channel
	@Override 
	public void onMessage(String channel, String sender, String login, String hostname, String message)  
	{
		//using regex to ensure user can type in multiple variations of greetings
		//and still get a reply 
		if(message.matches("[hH]i|[hH]ey|[hH]ello"))
		{
			sendMessage(channel, "Hi " + sender 
					+ " would you like to know the weather "
					+ "or get the definition of a word"
					+ " or do something random?"); 
		}
		
		else if(message.matches(".*\\d.*")) //if the message contains numbers
		{
			//replace all the characters except numbers such as a-z, A-Z 
			//or white spaces, ? and ' or , or . or !
			//with blank spaces to extract just the number
			//using regex here 
			String m = message.replaceAll("[A-Z]|[? ' , . !]|[a-z]|\\s+", ""); 
			
			weatherAPI w = new weatherAPI(); //create an object for the weather API
			
			//front part of the URL
			//this API uses zipcode to find the weather 
			String myAPIurl = "http://api.openweathermap.org/data/2.5/weather?zip=";
	
			//store their result in the string 
			String userInput = m; 
		
			//rear part of the URL
			//the api key for me 
			String myApiToken = "&APPID=f2c69d9fd953c530b637aea5e5638c19"; 
		
			//append the entire URL together
			//add us to the URL to find the weather for a US city with the given zipcode 
			String weatherURL = myAPIurl + userInput + ",us" + myApiToken;
		
			double temp = 0, tempMin = 0, tempMax = 0;
		
			//using try and catch to handle the unhandled Exception error
			try 
			{
				//returns the JSON as a string 
				String result = w.URLtoString(weatherURL); 
			
				//function returns the average temperature 
				//calls the function from the weather API class using dot notation
				temp = w.parseJsonFunction(result, "Temp"); 
			
				//function returns the minimum temperature
				tempMin = w.parseJsonFunction(result, "Min"); 
			
				//function returns the maximum temperature
				tempMax = w.parseJsonFunction(result, "Max"); 
			} 
		
			catch (Exception e)
			{
				System.out.println("Exception occured"); 
			} 
		
		
			//sends a message to the channel telling what the weather in the zipcode is 
			//and what the max and min temperatures are
			sendMessage(channel, "The weather in " + userInput + " is " + temp 
				+ " degrees Fahrenheit with a high of " + tempMax 
				+ " and a low of " + tempMin + ".");
		}  
		
		//this is not my second API 
		//this is an extra API just for fun 
		//if the user wants a Kanye West quote 
		else if(message.matches(".*[kK]anye [Ww]est.*|.*[Kk]anye.*|.*[Ww]est.*"))
		{
			//the url for the json 
			String myAPIurl = "https://api.kanye.rest/";
			kanyeAPI k = new kanyeAPI(); //create an object of the kanyeAPI class
			String quote = ""; 
			//converts the json to a string
			//handles Unhandled Exception error
			try 
			{
				String result = k.URLtoString(myAPIurl);
				quote = k.parseJsonFunction(result);  //this function will return 
			}
			
			catch (Exception e)
			{
				System.out.println("Exception occurred"); 
			}
		
			//privides users with a random Kanye West quote 
			sendMessage(channel, "Kanye once said " + "\"" + quote + "\""); 
		}
		
		//if user wants to do something random, then bot asks what user would like to hear
		else if (message.matches(".*[Rr]andom.*"))
		{
			sendMessage(channel, "Would like a joke, some motivation or hear from the great Kanye West?"); 
		}
		
		else if (message.matches(".*[Jj]oke.*"))
		{
			sendMessage(channel, "I like having conversations with kids. Grownups never ask me what my third favorite reptile is.");
		}
			
		
		//if user asks the bot how it is, it answers with this
		else if (message.matches(".*[hH]ow are you?.*"))
		{
			sendMessage(channel, "I am GREAT. Thank you for asking!"); 
		}
			
		//just for fun
		//if user asks for a motivational quote 
		else if(message.matches(".*[Mm]otivation.*"))
		{
			sendMessage(channel, "If you're still looking for that one person who will change your life take a look in the mirror.");
		}
		
		//bot replies to this frequently asked question
		else if(message.matches(".*[Hh]uman or [Rr]obot.*|.*[Rr]obot or [Hh]uman.*")) 
		{
			sendMessage(channel, "Shhh.. That's a confidential issue."); 
		}
						
		
		//this is my official second API
		//this is the Merriam-Webster dictionary API
		//if the user enters a word
		//it returns the definition for that word 
		else if (message.matches(".*[Dd]efinition.*|.*[Dd]efine.*")) //if the users asks for a definition
		{
			String word = ""; 
			//first part of the URL
			String myAPIurl = "https://dictionaryapi.com/api/v3/references/thesaurus/json/";
			
			
			//if the user asks for example: define brave
			
			if (message.matches(".*[Dd]efine.*"))
			{
				word = message.replaceAll(".*[Dd]efine", ""); 
				
			}
			//check if the message contains "of" and find its last occurrence
			//For example in the form: Definition of brave
			//find out when the word "of" appears for the last time
			//any word after that is what the definition is asking for
			else if(message.matches(".*of.*"))
			{
				//index of 'of' gives us the index of o. add 3 to it to get to the word
				int i = message.lastIndexOf("of") + 3; 
		
			
				//gets the word/words after the occurrence of "of" 
				//put that in a substring to use it for the URL
				word = message.substring(i); 
			}
			
			//check if the message contains "for" and find its last occurrence
			//For example in the form: Definition for brave
			//find out when the word "for" appears for the last time
			//any word after that is what the definition is asking for
			else if(message.matches(".*for.*"))
			{
				//index of 'for' gives us the index of f. add 4 to it to get to the word
				int i = message.lastIndexOf("for") + 4; 
				
				
				//gets the word/words after the occurrence of "of" 
				//put that in a substring to use it for the URL
				word = message.substring(i); 
				
			}
			
			//if user has any punctuation at the end of the sentence
			//it shall be replaced with no space so that the word' definition can be found effectively
			word = word.replaceAll("[, ? ! .]", ""); 
			
		
			//rear part of the URL
			//the API key
			String myApiToken = "?key=b69601da-33e8-46d8-bbd0-fc63925a279d"; 
			
		
			//append the entire URL together
			String wordURL = myAPIurl + word + myApiToken; 
			
			//using try and catch to handle the unhandled Exception error
			//returns the JSON as a string 
			secondAPI s = new secondAPI(); 
			
			String result = "";
			String def = ""; 
			
			try 
			{
				result = s.URLtoString(wordURL); 
				def = s.parseJsonFunction(result); 
			}
			
			catch (Exception e)
			{
				System.out.println("Exception occurred"); 
			}
			
			//this sentence adds %20 to fill in the blanks in the URL
			word = word.replaceAll("%20", " "); 
			
			//provides user with the definition
			sendMessage(channel, "Definition of " + word + " is " + def); 
		}
		
		
		//if users say thank you or thanks, then bot replies with welcome
		else if(message.matches(".*[Tt]hanks.*|.*[Tt]hankyou.*|.*[Tt]hank you.*"))
		{
			sendMessage(channel, "You are most welcome " + sender); 
		}
		
		
		//if the user says bye, disconnect the bot from the channel
		//it means the conversation has ended 
		//and we do not want that bot to be present at the same time as another bot
		else if(message.matches(".*[Bb]ye.*|.*[Gg]oodbye.*"))
		{
			//disconnects the bot from the channel so next time user uses the channel, two bots are not active at the same time
			disconnect(); 
			
		}		
	
		//will send this message if user asks for something other than the actions we defined
		else
		{
			sendMessage(channel, "Sorry I did not understand what you asked for."); 
		}
	}
}	