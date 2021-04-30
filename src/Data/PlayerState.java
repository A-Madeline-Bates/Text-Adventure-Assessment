package Data;

import java.util.ArrayList;

public class PlayerState {
	ArrayList<String> inventoryList = new ArrayList<String>();
	//following the logic that initial location is always 0- this might be worth changing
	int currentLocation = 0;

	public void addToInventory(String object){
		inventoryList.add(object);
	}

	public boolean isInInventory(String object){
		for (String searchObject : inventoryList) {
			if (searchObject.equalsIgnoreCase(object)) {
				return true;
			}
		}
		return false;
	}

	public boolean consumedFromInventory(String object){
		for (int i=0; i<inventoryList.size(); i++) {
			if (inventoryList.get(i).equalsIgnoreCase(object)) {
				inventoryList.remove(i);
				return true;
			}
		}
		return false;
	}

	public ArrayList<String> getInventory(){
		return inventoryList;
	}

	public int getCurrentLocation(){
		return currentLocation;
	}

	public void setCurrentLocation(int currentLocation){
		this.currentLocation = currentLocation;
	}
}
