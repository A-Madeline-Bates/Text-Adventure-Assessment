package data;
import java.util.ArrayList;
import java.util.List;

public class PlayerState {
	private String playerName;
	private final List<List<String>> inventoryList = new ArrayList<List<String>>();
	private int health;
	private int currentLocation;
	private String currentLocationName;

	public PlayerState(String firstLocation){
		this.currentLocation = 0;
		this.currentLocationName = firstLocation;
		//we are initially setting health as 3
		this.health = 3;
		initialiseInvList();
	}

	/********************************************************
	 ***********************   NAME   ***********************
	 ********************************************************/

	public void setPlayerName(String playerName){
		this.playerName = playerName;
	}

	public String getPlayerName(){
		return playerName;
	}

	/********************************************************
	 ********************   INVENTORY   *********************
	 ********************************************************/

	private void initialiseInvList(){
		ArrayList<String> inventoryEntry = new ArrayList<String>();
		inventoryEntry.add(null);
		inventoryEntry.add(null);
		this.inventoryList.add(inventoryEntry);
	}

	public void addToInventory(String object, String description){
		ArrayList<String> inventoryEntry = new ArrayList<String>();
		inventoryEntry.add(object);
		inventoryEntry.add(description);
		inventoryList.add(inventoryEntry);
	}

	public int findInInventory(String object){
		//Starting at 1, to skip first inventory entry, null
		for (int i=1; i<inventoryList.size(); i++) {
			//-2 used to specify nothing found yet
			int searchPosition = checkIdentifier(i, object);
			if(searchPosition != -2){
				return searchPosition;
			}
		}
		//-1 used to specify nothing found as the final result
		return -1;
	}

	private int checkIdentifier(int i, String object){
		//If input matches inventory object's name or description, provide coordinate
		if (inventoryList.get(i).get(0).equalsIgnoreCase(object)) {
			return i;
		}
		else if (inventoryList.get(i).get(1).equalsIgnoreCase(object)) {
			return i;
		}
		else{
			//-2 used to specify nothing found yet
			return -2;
		}
	}

	public String getInventoryObject(int inventoryPosition){
		return inventoryList.get(inventoryPosition).get(0);
	}

	public String getInventoryDescription(int inventoryPosition){
		return inventoryList.get(inventoryPosition).get(1);
	}

	public void consumedFromInventory(int objectPosition){
		inventoryList.remove(objectPosition);
	}

	public String getInventoryString(){
		StringBuilder invList = new StringBuilder();
		//Starting at 1, to skip first inventory entry, null
		for(int i=1; i<inventoryList.size(); i++) {
			invList.append(inventoryList.get(i).get(1)).append("\n");
		}
		return invList.toString();
	}

	public int getInventorySize(){
		return inventoryList.size();
	}

	/********************************************************
	 ********************   LOCATION   *********************
	 ********************************************************/

	public int getCurrentLocation(){
		return currentLocation;
	}

	public void setCurrentLocation(int currentLocation){
		this.currentLocation = currentLocation;
	}

	public String getCurrentLocationName(){
		return currentLocationName;
	}

	public void setCurrentLocationName(String currentLocationName){
		this.currentLocationName = currentLocationName;
	}

	/********************************************************
	 **********************   HEALTH   **********************
	 ********************************************************/

	public void addToHealth(){
		this.health = health + 1;
	}

	public void removeFromHealth(){
		this.health = health - 1;
	}

	public int getHealthLevel(){
		return health;
	}
}
