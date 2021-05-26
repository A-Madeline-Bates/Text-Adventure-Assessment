package cmdClasses;
import data.PlayerState;

public class CMDInventory extends CMDState implements CMDType{

	public CMDInventory(PlayerState playerState){
		this.playerState = playerState;
	}

	public String getExitMessage(){
		return "In your inventory, you have: " + playerState.getInventoryString() + "\n";
	}
}