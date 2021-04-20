import com.alexmerz.graphviz.Parser;
import com.alexmerz.graphviz.objects.Graph;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

public class ParseEntities {
	ArrayList<Graph> graphs;

	public ParseEntities(String entitiesFile){
		try {
			Parser parser = new Parser();
			FileReader reader = new FileReader(entitiesFile);
			parser.parse(reader);
			this.graphs = parser.getGraphs();
		} catch (FileNotFoundException fnfe) {
			System.out.println(fnfe);
		} catch (com.alexmerz.graphviz.ParseException pe) {
			System.out.println(pe);
		}
	}

	public ArrayList<Graph> getEntities(){
		return graphs;
	}
}
