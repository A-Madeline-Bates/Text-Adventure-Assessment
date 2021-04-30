package CMDClasses;
import Data.Entities;
import com.alexmerz.graphviz.objects.Node;

public class CMDLook extends CMDState implements CMDType{
	Entities entityClass;

	public CMDLook(Entities entityClass){
		this.entityClass = entityClass;
	}

	public String getExitMessage(){
		Node location = entityClass.getLocationNode(currentLocation);
		String exitMessage =
				"You are in " + location.getAttribute("description")
			;
		return exitMessage;
	}
}
