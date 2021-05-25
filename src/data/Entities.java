package data;

import com.alexmerz.graphviz.objects.*;

import java.util.ArrayList;

public class Entities {
	final ArrayList<Graph> entities;
	private String locationString;
	private int locationInt;
	private String entityId;
	private String entityDescription;

	public Entities(ArrayList<Graph> entities){
		this.entities = entities;
	}

	/********************************************************
	 **************   SEARCH FOR ENTITIES   ****************
	 ********************************************************/

	public int setEntityInfo(String comparisonString, int location, String entityType){
		ArrayList<Graph> myLocationGraphs = entities.get(0).getSubgraphs().get(0).getSubgraphs().get(location).getSubgraphs();
		for (Graph myLocationGraph : myLocationGraphs) {
			if (myLocationGraph.getId().getId().equalsIgnoreCase(entityType)) {
				ArrayList<Node> entityArray = myLocationGraph.getNodes(true);
				for (int j = 0; j < entityArray.size(); j++) {
					if (!entityArray.get(j).getId().getId().equals("node")) {
						if (myLocationGraph.getNodes(false).get(j).getAttribute("description").equalsIgnoreCase(comparisonString)) {
							this.entityId = myLocationGraph.getNodes(false).get(j).getId().getId();
							this.entityDescription = myLocationGraph.getNodes(false).get(j).getAttribute("description");
							return j;
						}
						//we will also accept a shorter entity definition
						else if (myLocationGraph.getNodes(false).get(j).getId().getId().equalsIgnoreCase(comparisonString)) {
							this.entityId = myLocationGraph.getNodes(false).get(j).getId().getId();
							this.entityDescription = myLocationGraph.getNodes(false).get(j).getAttribute("description");
							return j;
						}
					}
				}
			}
		}
		return -1;
	}

	public String getEntityId(){
		return entityId;
	}

	public String getEntityDescription(){
		return entityDescription;
	}

	public String getEntityString(int location, String entityType){
		StringBuilder allArtefacts = new StringBuilder();
		ArrayList<Graph> myLocationGraphs = entities.get(0).getSubgraphs().get(0).getSubgraphs().get(location).getSubgraphs();
		for (Graph myLocationGraph : myLocationGraphs) {
			if (myLocationGraph.getId().getId().equalsIgnoreCase(entityType)) {
				ArrayList<Node> artefactArray = myLocationGraph.getNodes(true);
				for (int j = 0; j < artefactArray.size(); j++) {
					if (!artefactArray.get(j).getId().getId().equals("node")) {
						allArtefacts.append(myLocationGraph.getNodes(false).get(j).getAttribute("description")).append("\n");
					}
				}
			}
		}
		return resolveIfEmpty(allArtefacts.toString());
	}

	/********************************************************
	 ************   RETURN FOR LOCATION INFO   **************
	 ********************************************************/

	public String getLocationName(int location){
		return entities.get(0).getSubgraphs().get(0).getSubgraphs().get(location).getNodes(false).get(0).getAttribute("description");
	}

	public String getPaths(int location){
		String locationName = entities.get(0).getSubgraphs().get(0).getSubgraphs().get(location).getNodes(false).get(0).getId().getId();
		StringBuilder allAccessibleLocations = new StringBuilder();
		Graph testEdges = entities.get(0).getSubgraphs().get(1);
		for(int x=0;x<testEdges.getEdges().size(); x++){
			if(testEdges.getEdges().get(x).getSource().getNode().getId().getId().equalsIgnoreCase(locationName)){
				allAccessibleLocations.append(testEdges.getEdges().get(x).getTarget().getNode().getId().getId()).append("\n");
			}
		}
		return resolveIfEmpty(allAccessibleLocations.toString());
	}

	private static String resolveIfEmpty(String returnString){
		if(returnString.equals("")){
			return "\n";
		}
		else{
			return returnString;
		}
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
	 **************   ADDING LOCATION PATH   ***************
	 ********************************************************/

	public void createPath(String startLocation, String newLocation){
		ArrayList<Edge> paths = entities.get(0).getSubgraphs().get(1).getEdges();
		Edge newEdge = new Edge();
		newEdge.setSource(createPortNode(startLocation));
		newEdge.setTarget(createPortNode(newLocation));
		paths.add(newEdge);
	}

	private static PortNode createPortNode(String location){
		Id newId = new Id();
		newId.setId(location);
		Node newNode = new Node();
		newNode.setId(newId);
		PortNode newPortNode = new PortNode();
		newPortNode.setNode(newNode);
		return newPortNode;
	}

	/********************************************************
	 ***********   ADDING AND REMOVING OBJECTS   ************
	 ********************************************************/

	public void removeObjectFromLocation(int currentLocation, int artefactPosition, String objectType){
		ArrayList<Graph> myLocationGraphs = entities.get(0).getSubgraphs().get(0).getSubgraphs().get(currentLocation).getSubgraphs();
		for (Graph myLocationGraph : myLocationGraphs) {
			if (myLocationGraph.getId().getId().equalsIgnoreCase(objectType)) {
				myLocationGraph.getNodes(true).remove(artefactPosition);
				return;
			}
		}
	}

	public void addArtefactToLocation(String droppedObject, String droppedObjectDescription, int currentLocation){
		ArrayList<Graph> myLocationGraphs = entities.get(0).getSubgraphs().get(0).getSubgraphs().get(currentLocation).getSubgraphs();
		for (Graph myLocationGraph : myLocationGraphs) {
			if (myLocationGraph.getId().getId().equalsIgnoreCase("artefacts")) {
				//Get list of 'artefact' nodes attached to the location
				ArrayList<Node> artefactArray = myLocationGraph.getNodes(true);
				addToArtefactArray(artefactArray, droppedObject, droppedObjectDescription);
				return;
			}
		}
	}

	private static void addToArtefactArray(ArrayList<Node> artefactArray, String droppedObject, String droppedObjectDescription){
		Id newId = new Id();
		newId.setId(droppedObject);
		Node newNode = new Node();
		newNode.setId(newId);
		newNode.setAttribute("description", droppedObjectDescription);
		artefactArray.add(newNode);
	}
}
