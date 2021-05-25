import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.FileReader;
import java.io.IOException;

public class ParseActions {
	private JSONArray actions;

	public ParseActions(String actionFile) throws IOException {
		try{
			JSONParser parser = new JSONParser();
			FileReader reader = new FileReader(actionFile);
			JSONObject actionObject = (JSONObject) parser.parse(reader);
			this.actions = (JSONArray) actionObject.get("actions");
		} catch (ParseException parse){
			System.out.println("Parse exception: " + parse);
		}
	}

	public JSONArray getActions(){
		return actions;
	}
}
