package Data;

import com.alexmerz.graphviz.objects.Graph;
import com.alexmerz.graphviz.objects.Id;
import com.alexmerz.graphviz.objects.Node;

import java.util.ArrayList;

public class Entities {
	ArrayList<Graph> entities;
	String locationString;
	int locationInt;
	String artefactId;
	String artefactDescription;

	public Entities(ArrayList<Graph> entities){
		this.entities = entities;
	}

	/********************************************************
	 **************   SEARCH FOR ARTEFACTS   ****************
	 ********************************************************/

	public int returnArtefactPosition(String comparisonString, int location){
		ArrayList<Graph> myLocationGraphs = entities.get(0).getSubgraphs().get(0).getSubgraphs().get(location).getSubgraphs();
		for(int i=0; i<myLocationGraphs.size(); i++){
			if(myLocationGraphs.get(i).getId().getId().equalsIgnoreCase("artefacts")){
				ArrayList<Node> artefactArray = myLocationGraphs.get(i).getNodes(true);
				for(int j=0; j<artefactArray.size(); j++) {
					if(!artefactArray.get(i).getId().getId().equals("node")) {
						if(myLocationGraphs.get(i).getNodes(false).get(j).getAttribute("description").equalsIgnoreCase(comparisonString)){
							this.artefactId = myLocationGraphs.get(i).getNodes(false).get(j).getId().getId();
							this.artefactDescription = myLocationGraphs.get(i).getNodes(false).get(j).getAttribute("description");
							return j;
						}
						//we will also accept a shorter artefact definition
						else if(myLocationGraphs.get(i).getNodes(false).get(j).getId().getId().equalsIgnoreCase(comparisonString)){
							this.artefactId = myLocationGraphs.get(i).getNodes(false).get(j).getId().getId();
							this.artefactDescription = myLocationGraphs.get(i).getNodes(false).get(j).getAttribute("description");
							return j;
						}
					}
				}
			}
		}
		return -1;
	}

	public String getArtefactId(){
		return artefactId;
	}

	public String getArtefactDescription(){
		return artefactDescription;
	}

	/********************************************************
	 ************   RETURN FOR LOCATION INFO   **************
	 ********************************************************/

	public String getLocationName(int location){
		return entities.get(0).getSubgraphs().get(0).getSubgraphs().get(location).getNodes(false).get(0).getAttribute("description");
	}

	public String getLocationAttributes(int location){
		String allArtefacts = "";
		ArrayList<Graph> myLocationGraphs = entities.get(0).getSubgraphs().get(0).getSubgraphs().get(location).getSubgraphs();
		for(int i=0; i<myLocationGraphs.size(); i++){
			if(myLocationGraphs.get(i).getId().getId().equalsIgnoreCase("artefacts")){
				ArrayList<Node> artefactArray = myLocationGraphs.get(i).getNodes(true);
				for(int j=0; j<artefactArray.size(); j++) {
					if(!artefactArray.get(i).getId().getId().equals("node")) {
						allArtefacts = allArtefacts + myLocationGraphs.get(i).getNodes(false).get(j).getAttribute("description") + "\n";
					}
				}
			}
		}
		return allArtefacts;
	}

	public String getPaths(int location){
		String locationName = entities.get(0).getSubgraphs().get(0).getSubgraphs().get(location).getNodes(false).get(0).getId().getId();
		String allAccessibleLocations = "";
		Graph testEdges = entities.get(0).getSubgraphs().get(1);
		for(int x=0;x<testEdges.getEdges().size(); x++){
			if(testEdges.getEdges().get(x).getSource().getNode().getId().getId().equalsIgnoreCase(locationName)){
				allAccessibleLocations = allAccessibleLocations + testEdges.getEdges().get(x).getTarget().getNode().getId().getId() + "\n";
			}
		}
		return allAccessibleLocations;
	}

	/********************************************************
	 ************   SEARCH FOR NEW LOCATIONS   **************
	 ********************************************************/

	public void findNewLocation(String comparisonString){
		ArrayList<Graph> myLocationList = entities.get(0).getSubgraphs().get(0).getSubgraphs();
		//If location is found, set our variables
		for(int i=0; i<myLocationList.size(); i++){
			if(myLocationList.get(i).getNodes(false).get(0).getAttribute("description").equalsIgnoreCase(comparisonString)) {
				//the location string is always set to be the ID version of the location name, because that is the
				//version used in the 'path'
				setNewLocationInfo(myLocationList.get(i).getNodes(false).get(0).getId().getId(), i);
				return;
			}
			//we will also accept a shorter location definition
			else if(myLocationList.get(i).getNodes(false).get(0).getId().getId().equalsIgnoreCase(comparisonString)){
				setNewLocationInfo(myLocationList.get(i).getNodes(false).get(0).getId().getId(), i);
				return;
			}
		}
		//If we don't find the location, clear location string and set coordinate to -1
		setNewLocationInfo("", -1);
	}

	private void setNewLocationInfo(String locationString, int locationInt){
		this.locationString = locationString;
		this.locationInt = locationInt;
	}

	public String getNewLocationString(){
		return locationString;
	}

	public int getNewLocationCoordinate(){
		return locationInt;
	}

	public boolean isNewLocationAccessible(String currentLocation, String targetLocation){
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

	/********************************************************
	 ***********   ADDING AND REMOVING OBJECTS   ************
	 ********************************************************/

	public void removeObjectFromLocation(int currentLocation, int artefactPosition){
		ArrayList<Graph> myLocationGraphs = entities.get(0).getSubgraphs().get(0).getSubgraphs().get(currentLocation).getSubgraphs();
		for(int i=0; i<myLocationGraphs.size(); i++){
			if(myLocationGraphs.get(i).getId().getId().equalsIgnoreCase("artefacts")){
				myLocationGraphs.get(i).getNodes(true).remove(artefactPosition);
				return;
			}
		}
	}

	public void addObjectToLocation(String droppedObject, String droppedObjectDescription, int currentLocation){
		ArrayList<Graph> myLocationGraphs = entities.get(0).getSubgraphs().get(0).getSubgraphs().get(currentLocation).getSubgraphs();
		for(int i=0; i<myLocationGraphs.size(); i++){
			if(myLocationGraphs.get(i).getId().getId().equalsIgnoreCase("artefacts")){
				//Get list of 'artefact' nodes attached to the location
				ArrayList<Node> artefactArray = myLocationGraphs.get(i).getNodes(true);
				addToArtefactArray(artefactArray, droppedObject, droppedObjectDescription);
				return;
			}
		}
	}

	private void addToArtefactArray(ArrayList<Node> artefactArray, String droppedObject, String droppedObjectDescription){
		Id newId = new Id();
		newId.setId(droppedObject);
		Node newNode = new Node();
		newNode.setId(newId);
		newNode.setAttribute("description", droppedObjectDescription);
		artefactArray.add(newNode);
	}
}
