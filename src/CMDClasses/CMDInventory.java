package CMDClasses;
import Data.PlayerState;

public class CMDInventory extends CMDState implements CMDType{
	public CMDInventory(PlayerState playerState){
		System.out.println("in CMDInventory");
	}

	public String getExitMessage(){
		return "in CMDAction";
	}
}
