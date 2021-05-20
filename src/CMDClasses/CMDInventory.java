package CMDClasses;
import Data.PlayerState;

public class CMDInventory extends CMDState implements CMDType{

	public CMDInventory(PlayerState playerState){
		this.playerState = playerState;
	}

	public String getExitMessage(){
		return "In your inventory, you have: " + playerState.getInventory() + "\n";
	}
}
