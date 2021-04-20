import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.io.FileReader;
import org.json.simple.parser.*;
import org.json.simple.parser.ParseException;


public class JSONParserExperiment {
	public JSONParserExperiment(String file2){
		jsonParse(file2);
	}

	public void jsonParse(String file2){
		try {
			JSONParser parser = new JSONParser();
			FileReader reader = new FileReader(file2);
			Object actionObject = parser.parse(reader);
			System.out.println("\n\n\n JSON PARSER");

		} catch (FileNotFoundException fnfe) {
			System.out.println(fnfe);
		} catch (ParseException parse){
			System.out.println(parse);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
