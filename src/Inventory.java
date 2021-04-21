import java.util.ArrayList;

public class Inventory {
	ArrayList<String> inventoryList = new ArrayList<String>();

	public void addToInventory(String object){
		inventoryList.add(object);
	}

	public boolean isInInventory(String object){
		for (String searchObject : inventoryList) {
			if (searchObject.equals(object)) {
				return true;
			}
		}
		return false;
	}

	public boolean consumedFromInventory(String object){
		for (int i=0; i<inventoryList.size(); i++) {
			if (inventoryList.get(i).equals(object)) {
				inventoryList.remove(i);
				return true;
			}
		}
		return false;
	}

	public ArrayList<String> getInventory(){
		return inventoryList;
	}
}
