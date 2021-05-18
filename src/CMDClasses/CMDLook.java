package CMDClasses;
import Data.Entities;
import Data.PlayerState;
import com.alexmerz.graphviz.objects.Node;

public class CMDLook extends CMDState implements CMDType{
	Entities entityClass;

	public CMDLook(Entities entityClass, PlayerState playerState){
		this.entityClass = entityClass;
		this.playerState = playerState;
	}

	public String getExitMessage(){
		Node location = entityClass.getLocationNode(playerState.getCurrentLocation());
		String exitMessage =
				"You are in " + location.getAttribute("description");
		return exitMessage;
	}
}
