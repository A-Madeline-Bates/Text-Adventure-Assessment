import data.PlayerState;
import parseExceptions.InvalidNameFormat;
import parseExceptions.ParseException;
import parseExceptions.TokenMissing;
import tokeniser.Tokeniser;

import java.util.ArrayList;
import java.util.List;

public class PlayerStore {
	final List<PlayerState> playerList = new ArrayList<PlayerState>();
	String firstLocation;

	public PlayerStore(String firstLocation){
		this.firstLocation = firstLocation;
	}

	public PlayerState getCurrentPlayer(Tokeniser tokeniser) throws ParseException {
		//find the players name- if it not in the correct format, an error will be thrown
		String playerName = findValidName(tokeniser);
		//search through the player list we have stored
		for(PlayerState thisPlayer : playerList){
			if(thisPlayer.getPlayerName().equalsIgnoreCase(playerName)){
				return thisPlayer;
			}
		}
		//If there is no player found with this name in the game already, create one.
		return createPlayer(playerName);
	}

	private static String findValidName(Tokeniser tokeniser) throws ParseException {
		String playerName = tokeniser.getNextToken();
		if(playerName.endsWith(":")){
			//If the user has written something like "Simon:" need to remove the colon from their string before it's
			//usable as a name
			StringBuilder shortenedName = new StringBuilder(playerName);
			shortenedName.deleteCharAt(shortenedName.length()-1);
			return shortenedName.toString();
		}
		if(checkForColon(tokeniser)){
			return playerName;
		}
		else{
			throw new InvalidNameFormat(playerName, tokeniser.peekNextToken());
		}
	}

	private static boolean checkForColon(Tokeniser tokeniser) throws TokenMissing {
		String colonSearch = tokeniser.peekNextToken();
		if(colonSearch.equals(":")){
			//If token 2 is a colon, token 1 is a valid name and we can use it. Advance the token reader by one so that
			//if doesn't confuse our code when we are looking for commands.
			tokeniser.advanceToken();
			return true;
		}
		return false;
	}

	private PlayerState createPlayer(String playerName){
		PlayerState newPlayer = new PlayerState(firstLocation);
		newPlayer.setPlayerName(playerName);
		playerList.add(newPlayer);
		return newPlayer;
	}
}
