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

	public int returnArtefactPosition(String comparisonString, int location){
		ArrayList<Graph> myLocationGraphs = entities.get(0).getSubgraphs().get(0).getSubgraphs().get(location).getSubgraphs();
		for(int i=0; i<myLocationGraphs.size(); i++){
			System.out.println("hello- started looking in location");
			if(myLocationGraphs.get(i).getId().getId().equalsIgnoreCase("artefacts")){
				System.out.println("hello- found artefacts");
				ArrayList<Node> artefactArray = myLocationGraphs.get(i).getNodes(true);
				for(int j=0; j<artefactArray.size(); j++) {
					if(!artefactArray.get(i).getId().getId().equals("node")) {
						System.out.println("hello- found artefacts not node");
						if(myLocationGraphs.get(i).getNodes(false).get(j).getAttribute("description").equalsIgnoreCase(comparisonString)){
							System.out.println("hello- found specific artefact");
							return j;
						}
					}
				}
			}
		}
		return -1;
	}
}
