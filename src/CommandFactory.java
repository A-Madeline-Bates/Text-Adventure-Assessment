import CMDClasses.*;
import ParseExceptions.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class CommandFactory {

	//This will act as a factory for instances of CMDType
	public CMDType createCMD(Tokeniser tokeniser, JSONArray actions) throws ParseException{
		String nextToken = tokeniser.nextToken();
		if(nextToken == null){
			throw new CommandMissing();
		}
		else{
			return commandSwitch(nextToken, actions);
		}
	}

	private CMDType commandSwitch(String nextToken, JSONArray actions) throws ParseException{
		switch (nextToken.toUpperCase()) {
			case "INVENTORY":
			case "INV":
				return new CMDInventory();
			case "GET":
				return new CMDGet();
			case "DROP":
				return new CMDDrop();
			case "GOTO":
				return new CMDGoto();
			case "LOOK":
				return new CMDLook();
			default:
				return findCMDAction(nextToken, actions);
				//these CMDClasses only parse and create an instruction set?
		}
	}

	private CMDType findCMDAction(String nextToken, JSONArray actions) throws ParseException{
		for(Object action : actions) {
			JSONObject jsonAction = (JSONObject) action;
			JSONArray actionTriggers = (JSONArray) jsonAction.get("triggers");
			for(int i=0; i<actionTriggers.size(); i++){
				String trigger = (String) actionTriggers.get(i);
				if(nextToken.equals(trigger)){
					return new CMDAction(trigger);
				}
			}
		}
		throw new InvalidFirstCommand(nextToken);
	}
}
