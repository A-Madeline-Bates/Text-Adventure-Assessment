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
			if(myLocationGraphs.get(i).getId().getId().equalsIgnoreCase("artefacts")){
				ArrayList<Node> artefactArray = myLocationGraphs.get(i).getNodes(true);
				for(int j=0; j<artefactArray.size(); j++) {
					if(!artefactArray.get(i).getId().getId().equals("node")) {
						if(myLocationGraphs.get(i).getNodes(false).get(j).getAttribute("description").equalsIgnoreCase(comparisonString)){
							return j;
						}
						//we will also accept a shorter artefact definition
						else if(myLocationGraphs.get(i).getNodes(false).get(j).getId().getId().equalsIgnoreCase(comparisonString)){
							return j;
						}
					}
				}
			}
		}
		return -1;
	}

	public int returnLocationPosition(String comparisonString){
		ArrayList<Graph> myLocationList = entities.get(0).getSubgraphs().get(0).getSubgraphs();
		for(int i=0; i<myLocationList.size(); i++){
			if(myLocationList.get(i).getNodes(false).get(0).getAttribute("description").equalsIgnoreCase(comparisonString)) {
				return i;
			}
			//we will also accept a shorter location definition
			else if(myLocationList.get(i).getNodes(false).get(0).getId().getId().equalsIgnoreCase(comparisonString)){
				return i;
			}
		}
		return -1;
	}

	public boolean isLocationAccessible(String currentLocation, String targetLocation){
		Graph testEdges = entities.get(0).getSubgraphs().get(1);
		for(int x=0;x<testEdges.getEdges().size(); x++){
			if(testEdges.getEdges().get(x).getSource().getNode().getId().getId().equalsIgnoreCase(currentLocation)){
				for(int y=0;y<testEdges.getEdges().size(); y++){
					if(testEdges.getEdges().get(y).getTarget().getNode().getId().getId().equalsIgnoreCase(targetLocation)) {
						return true;
					}
				}
			}
		}
		return false;
	}
}
