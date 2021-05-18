package CMDClasses;
import Data.PlayerState;
import java.util.ArrayList;

public class CMDInventory extends CMDState implements CMDType{

	public CMDInventory(PlayerState playerState){
		this.playerState = playerState;
		System.out.println("List: " + playerState.getInventory());
	}

	public String getExitMessage(){
		return "in CMDInventory";
	}
}
