package Data;

import java.util.ArrayList;
import java.util.List;

public class PlayerState {
	private List<List<String>> inventoryList = new ArrayList<List<String>>();

	//following the logic that initial location is always 0- this might be worth changing
	private int currentLocation;
	private String currentLocationName;

	//These are both based on big assumptions and need fixing!!
	public PlayerState(){
		this.currentLocation = 0;
		this.currentLocationName = "start";
		ArrayList<String> inventoryEntry = new ArrayList<String>();
		inventoryEntry.add(null);
		inventoryEntry.add(null);
		inventoryList.add(inventoryEntry);
	}

	public void addToInventory(String object, String description){
		ArrayList<String> inventoryEntry = new ArrayList<String>();
		inventoryEntry.add(object);
		inventoryEntry.add(description);
		inventoryList.add(inventoryEntry);
	}

	public int findInInventory(String object){
		//Starting at 1, to skip first inventory entry, null
		for (int i=1; i<inventoryList.get(0).size(); i++) {
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

	public String getInventory(){
		String invList = "";
		//Starting at 1, to skip first inventory entry, null
		for(int i=1; i<inventoryList.size(); i++) {
			invList = invList + inventoryList.get(i).get(1) + "\n";
		}
		return invList;
	}

	public int getCurrentLocation(){
		return currentLocation;
	}

	public void setCurrentLocation(int currentLocation){
		this.currentLocation = currentLocation;
	}

	public String getCurrentLocationName(){
		return currentLocationName;
	}
}
