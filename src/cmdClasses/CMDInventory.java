package cmdClasses;
import dataStorage.PlayerState;

public class CMDInventory extends CMDType{
	public CMDInventory(PlayerState playerState){
		this.playerState = playerState;
	}

	public String getExitMessage(){
		return "In your inventory, you have: " + playerState.getInvString() + "\n";
	}
}
