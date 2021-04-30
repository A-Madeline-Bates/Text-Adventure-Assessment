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
//		ArrayList<Graph> myLocationGraphs = subGraphs.get(0).getSubgraphs().get(0 /*location*/).getSubgraphs();
//		for(int i=0; i<myLocationGraphs.size(); i++){
//			if(myLocationGraphs.get(i).getId().getId().equalsIgnoreCase("artefacts")){
//				ArrayList<Node> artefactArray = myLocationGraphs.get(i).getNodes(true);
//				for(int j=0; j<artefactArray.size(); j++) {
//					if(!artefactArray.get(i).getId().getId().equals("node")) {
//						System.out.println(myLocationGraphs.get(i).getNodes(false).get(j).getAttribute("description"));
//					}
//				}
//			}
//		}
		return 2;
	}
}
