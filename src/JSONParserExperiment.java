import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;

import org.json.simple.*;
import org.json.simple.parser.*;

public class JSONParserExperiment {
	public JSONParserExperiment(String file2){
		jsonParse(file2);
	}

	public void jsonParse(String file2){
		try {
			JSONParser parser = new JSONParser();
			FileReader reader = new FileReader(file2);
			JSONObject actionObject = (JSONObject) parser.parse(reader);
			System.out.println("\n\n JSON PARSER");
			JSONArray actions = (JSONArray) actionObject.get("actions");

			System.out.println(actions); //gives everything past "actions:"
			JSONObject firstAction = (JSONObject) actions.get(0);
			System.out.println(firstAction); //gives all information attached to action one
			System.out.println(firstAction.get("triggers")); //gives "["open","unlock"]"
			JSONObject secondAction = (JSONObject) actions.get(1);
			System.out.println(secondAction); //gives all information attached to action two
			System.out.println(secondAction.get("triggers")); //gives "["chop","cut","cutdown"]"

			for(Object action : actions){
				JSONObject jsonAction = (JSONObject) action;
				System.out.println(jsonAction.get("triggers"));

//				if(jsonAction.get("triggers").equals("[drink]")){
//					System.out.println("*");
//					System.out.println(jsonAction.get("produced"));
//				}
			}
		} catch (FileNotFoundException fnfe) {
			System.out.println(fnfe);
		} catch (ParseException parse){
			System.out.println(parse);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
