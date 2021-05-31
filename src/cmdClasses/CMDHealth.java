package cmdClasses;
import dataStorage.PlayerState;

public class CMDHealth extends CMDType{

	public CMDHealth(PlayerState playerState){
		this.playerState = playerState;
	}

	public String getExitMessage(){
		return "Your health level is: " + playerState.getHealthLevel() + "\n";
	}
}
