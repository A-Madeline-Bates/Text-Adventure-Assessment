package dataStorage;
import parseExceptions.TokenMissing;
import tokeniser.Tokeniser;

import java.util.ArrayList;
import java.util.List;

public class PlayerStore {
	private final List<PlayerState> playerList = new ArrayList<PlayerState>();
	private final String firstLocation;

	public PlayerStore(String firstLocation){
		this.firstLocation = firstLocation;
	}

	public PlayerState getCurrentPlayer(Tokeniser tokeniser) throws TokenMissing {
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

	private static String findValidName(Tokeniser tokeniser) throws parseExceptions.TokenMissing {
		StringBuilder fullName = new StringBuilder();
		while(true) {
			String playerName = tokeniser.getNextToken();
			if (playerName.endsWith(":")) {
				//If we have found a string that ends with ":", this is the end of the string and we can add it,
				//remove the colon and return
				fullName.append(playerName);
				fullName.deleteCharAt(fullName.length() - 1);
				return fullName.toString();
			}
			else{
				fullName.append(playerName).append(" ");
			}
		}
	}

	private PlayerState createPlayer(String playerName){
		PlayerState newPlayer = new PlayerState(firstLocation);
		newPlayer.setPlayerName(playerName);
		playerList.add(newPlayer);
		return newPlayer;
	}

	//Lists all the players in the same location as the current player
	public String findOtherPlayers(int ourLocation, String ourPlayer){
		StringBuilder allPlayers = new StringBuilder();
		for(PlayerState thisPlayer : playerList){
			//is the player location matches, but the player doesn't have the same name as us (i.e, is us)
			if((thisPlayer.getCurrentLocation() == ourLocation) && (!thisPlayer.getPlayerName().equals(ourPlayer))){
				allPlayers.append(thisPlayer.getPlayerName()).append("\n");
			}
		}
		return resolveIfEmpty(allPlayers.toString());
	}

	private static String resolveIfEmpty(String returnString){
		if(returnString.equals("")){
			return "\n";
		}
		else{
			return returnString;
		}
	}
}
