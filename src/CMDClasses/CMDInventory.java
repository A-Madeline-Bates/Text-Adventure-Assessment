package CMDClasses;
import Data.PlayerState;
import java.util.ArrayList;

public class CMDInventory extends CMDState implements CMDType{

	public CMDInventory(PlayerState playerState){
		this.playerState = playerState;
	}

	public String getExitMessage(){

		return "In your inventory, you have: " + playerState.getInventory();
	}
}
