import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ParseActions {
	JSONArray actions;

	public ParseActions(String actionFile){
		try{
			JSONParser parser = new JSONParser();
			FileReader reader = new FileReader(actionFile);
			JSONObject actionObject = (JSONObject) parser.parse(reader);
			this.actions = (JSONArray) actionObject.get("actions");
		} catch (FileNotFoundException fnfe) {
			System.out.println(fnfe);
		} catch (ParseException parse){
			System.out.println(parse);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public JSONArray getActions(){
		return actions;
	}
}
