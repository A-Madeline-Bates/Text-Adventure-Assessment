package cmdClasses;
import data.PlayerState;

public class CMDHealth extends CMDState implements CMDType{

	public CMDHealth(PlayerState playerState){
		this.playerState = playerState;
	}

	public String getExitMessage(){
		return "Your health level is: " + playerState.getHealthLevel() + "\n";
	}
}
