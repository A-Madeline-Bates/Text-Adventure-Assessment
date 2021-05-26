package data;
import com.alexmerz.graphviz.objects.*;
import java.util.ArrayList;

public class Entities {
	final ArrayList<Graph> entities;
	private String locationId;
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
				//-2 used to specify nothing found yet
				int positionResult = checkEntitiesOfType(myLocationGraph, comparisonString);
				if(positionResult != -2){
					return positionResult;
				}
			}
		}
		//-1 used to specify nothing found as the final result
		return -1;
	}

	private int checkEntitiesOfType(Graph myLocationGraph, String comparisonString){
		ArrayList<Node> entityArray = myLocationGraph.getNodes(true);
		for (int j = 0; j < entityArray.size(); j++) {
			//-3 used to specify nothing found yet
			int positionResult = checkEntityIdentifiers(myLocationGraph, j, comparisonString);
			if(positionResult != -3){
				return positionResult;
			}
		}
		//-2 used to specify nothing found yet
		return -2;
	}

	private int checkEntityIdentifiers(Graph myLocationGraph, int j, String comparisonString){
		if (myLocationGraph.getNodes(false).get(j).getAttribute("description").equalsIgnoreCase(comparisonString)) {
			setEntityVariables(myLocationGraph, j);
			return j;
		}
		//we will also accept a shorter entity definition
		else if (myLocationGraph.getNodes(false).get(j).getId().getId().equalsIgnoreCase(comparisonString)) {
			setEntityVariables(myLocationGraph, j);
			return j;
		}
		else {
			//-3 used to specify nothing found yet
			return -3;
		}
	}

	private void setEntityVariables(Graph myLocationGraph, int j){
		this.entityId = myLocationGraph.getNodes(false).get(j).getId().getId();
		this.entityDescription = myLocationGraph.getNodes(false).get(j).getAttribute("description");
	}

	/********************************************************
	 ****************   GET ENTITIES RESULTS  ****************
	 ********************************************************/

	public String getEntityId(){
		return entityId;
	}

	public String getEntityDescription(){
		return entityDescription;
	}

	/********************************************************
	 **************   SEARCH FOR LOCATIONS   *****************
	 ********************************************************/

	//Finds whether a location is in the dot file
	public void locationSearch(String comparisonString){
		ArrayList<Graph> myLocationList = entities.get(0).getSubgraphs().get(0).getSubgraphs();
		//If location is found, set our variables
		for(int i=0; i<myLocationList.size(); i++){
			if(myLocationList.get(i).getNodes(false).get(0).getAttribute("description").equalsIgnoreCase(comparisonString)) {
				setLocationResult(myLocationList.get(i).getNodes(false).get(0).getId().getId(), i);
				return;
			}
			//we will also accept a shorter location definition
			else if(myLocationList.get(i).getNodes(false).get(0).getId().getId().equalsIgnoreCase(comparisonString)){
				setLocationResult(myLocationList.get(i).getNodes(false).get(0).getId().getId(), i);
				return;
			}
		}
		//If we don't find the location, clear location string and set coordinate to -1 to indicate that it doesn't exist
		setLocationResult("", -1);
	}

	private void setLocationResult(String locationId, int locationInt){
		//the location string is always set to be the ID version of the location name, because that is the
		//version used in the 'path'
		this.locationId = locationId;
		this.locationInt = locationInt;
	}

	public boolean isLocationAccessible(String currentLocation, String targetLocation){
		Graph testEdges = entities.get(0).getSubgraphs().get(1);
		for(int x=0;x<testEdges.getEdges().size(); x++){
			if(matchEndOfPath(testEdges, x, currentLocation)){
				//In this instance, false means 'we haven't found a match yet'
				//If we find a match searchOurTargets() will return true, and we can return true
				if(searchOurTargets(testEdges, targetLocation)){
					return true;
				}
			}
		}
		return false;
	}

	private boolean searchOurTargets(Graph testEdges, String targetLocation){
		for(int y=0;y<testEdges.getEdges().size(); y++){
			if(matchEndOfPath(testEdges, y, targetLocation)) {
				return true;
			}
		}
		//In this instance, false means 'we haven't found a match yet'
		return false;
	}

	//matches a path with a string location name- returns true is there is a match
	private boolean matchEndOfPath(Graph testEdges, int position, String location){
		return testEdges.getEdges().get(position).getSource().getNode().getId().getId().equalsIgnoreCase(location);
	}

	/********************************************************
	 ****************   GET LOCATION RESULTS  ****************
	 ********************************************************/

	public String getLocationResultId(){
		return locationId;
	}

	public int getLocationResultInt(){
		return locationInt;
	}

	/********************************************************
	 *******************   ADDING PATH   ********************
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
	 ***********   ADDING AND REMOVING ARTEFACTS   ***********
	 ********************************************************/

	public void removeArtefact(int currentLocation, int artefactPosition, String objectType){
		ArrayList<Graph> myLocationGraphs = entities.get(0).getSubgraphs().get(0).getSubgraphs().get(currentLocation).getSubgraphs();
		for (Graph myLocationGraph : myLocationGraphs) {
			if (myLocationGraph.getId().getId().equalsIgnoreCase(objectType)) {
				myLocationGraph.getNodes(true).remove(artefactPosition);
				return;
			}
		}
	}

	public void addArtefact(String droppedObject, String droppedObjectDescription, int currentLocation){
		ArrayList<Graph> myLocationGraphs = entities.get(0).getSubgraphs().get(0).getSubgraphs().get(currentLocation).getSubgraphs();
		for (Graph myLocationGraph : myLocationGraphs) {
			if (myLocationGraph.getId().getId().equalsIgnoreCase("artefacts")) {
				//Get list of 'artefact' nodes attached to the location
				ArrayList<Node> artefactArray = myLocationGraph.getNodes(true);
				addArtefactNode(artefactArray, droppedObject, droppedObjectDescription);
				return;
			}
		}
	}

	private static void addArtefactNode(ArrayList<Node> artefactArray, String droppedObject, String droppedObjectDescription){
		Id newId = new Id();
		newId.setId(droppedObject);
		Node newNode = new Node();
		newNode.setId(newId);
		newNode.setAttribute("description", droppedObjectDescription);
		artefactArray.add(newNode);
	}

	/********************************************************
	 ***************   RETURN DISPLAY TEXT    ****************
	 ********************************************************/

	public String findLocationDescription(int location){
		return entities.get(0).getSubgraphs().get(0).getSubgraphs().get(location).getNodes(false).get(0).getAttribute("description");
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

	public String getPathsList(int location){
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
}
