package Data;

import com.alexmerz.graphviz.objects.Graph;
import com.alexmerz.graphviz.objects.Node;

import java.util.ArrayList;

public class Entities {
	ArrayList<Graph> entities;

	public Entities(ArrayList<Graph> entities){
		this.entities = entities;
	}

	public Node getLocationNode(int currentLocation){
		return entities.get(0).getSubgraphs().get(currentLocation).getSubgraphs().get(0).getNodes(false).get(0);
	}
}
