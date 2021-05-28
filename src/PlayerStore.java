import data.PlayerState;
import parseExceptions.ParseException;

import java.util.ArrayList;
import java.util.List;

public class PlayerStore {
	final List<PlayerState> playerList = new ArrayList<PlayerState>();
	String firstLocation;

	public PlayerStore(String firstLocation){
		this.firstLocation = firstLocation;
	}

	public PlayerState getCurrentPlayer(String playerName) throws ParseException {
		//search through the player list we have stored
		for(PlayerState thisPlayer : playerList){
			if(thisPlayer.getPlayerName().equalsIgnoreCase(playerName)){
				return thisPlayer;
			}
		}
		//If there is no player found with this name in the game already, create one.
		return createPlayer(playerName);
	}

	private PlayerState createPlayer(String playerName){
		PlayerState newPlayer = new PlayerState(firstLocation);
		newPlayer.setPlayerName(playerName);
		playerList.add(newPlayer);
		return newPlayer;
	}
}
