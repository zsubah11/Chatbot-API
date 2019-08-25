//setting up the bot 
import org.jibble.pircbot.*;

public class MyBotMain {
	
	public static void main(String [] args) throws Exception
	{
		//create a bot object
		MyBot bot = new MyBot(); 
		
		bot.setVerbose(true); //log entries will be printed to standard output 
		
		bot.connect("irc.freenode.net"); //tells it where to connect to i.e the website
		bot.joinChannel("#testTT"); //name of the channel that we will be connecting to
		
		
		//default message when pircbot goes live 
		//welcome message
		bot.sendMessage("#testTT", "Hey! Enter any message and I'll respond"); 
	}
}