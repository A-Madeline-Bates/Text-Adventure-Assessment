package CMDClasses;

import Data.Actions;
import Data.Entities;
import com.alexmerz.graphviz.objects.Node;

public class CMDLook extends CMDState implements CMDType{
	Entities entityClass;
	Actions actionClass;

	public CMDLook(Entities entityClass, Actions actionClass){
		this.entityClass = entityClass;
		this.actionClass = actionClass;
	}

	public String getExitMessage(){
		Node location = entityClass.getLocationNode(currentLocation);
		String exitMessage =
				"You are in " + location.getAttribute("description")
			;
		return exitMessage;
	}
}
