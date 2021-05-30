import com.alexmerz.graphviz.Parser;
import com.alexmerz.graphviz.objects.Graph;
import java.io.FileReader;
import java.util.ArrayList;

public class ParseEntities {
	private ArrayList<Graph> graphs;

	public ParseEntities(String entitiesFile) throws java.io.FileNotFoundException {
		try {
			Parser parser = new Parser();
			FileReader reader = new FileReader(entitiesFile);
			parser.parse(reader);
			this.graphs = parser.getGraphs();
		} catch (com.alexmerz.graphviz.ParseException pe) {
			System.out.println("Parse exception: " + pe);
		}
	}

	public ArrayList<Graph> getEntities(){
		return graphs;
	}
}
