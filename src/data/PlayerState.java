package data;

import java.util.ArrayList;
import java.util.List;

public class PlayerState {
	final List<List<String>> inventoryList = new ArrayList<List<String>>();
	private int health;
	//following the logic that initial location is always 0- this might be worth changing
	private int currentLocation;
	final String currentLocationName;

	public PlayerState(){
		this.currentLocation = 0;
		this.currentLocationName = "start";
		//we are initially setting health as 3
		this.health = 3;
		initialiseInvList();
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
			//If input matches inventory object's name or description, provide coordinate
			if (inventoryList.get(i).get(0).equalsIgnoreCase(object)) {
				return i;
			}
			else if (inventoryList.get(i).get(1).equalsIgnoreCase(object)) {
				return i;
			}
		}
		return -1;
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
