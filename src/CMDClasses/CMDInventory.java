package CMDClasses;
import Data.Inventory;

public class CMDInventory extends CMDState implements CMDType{
	public CMDInventory(Inventory inventory){
		System.out.println("in CMDInventory");
	}

	public String getExitMessage(){
		return "in CMDAction";
	}
}
