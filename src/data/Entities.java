package data;
import com.alexmerz.graphviz.objects.*;
import java.util.ArrayList;

public class Entities {
	private final ArrayList<Graph> entities;
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

	public int entitySearch(String comparisonString, int location, String entityType){
		ArrayList<Graph> myLocationGraphs = getLocationGraphs(location);
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
			int positionResult = matchEntityStrings(myLocationGraph, j, comparisonString);
			if(positionResult != -3){
				return positionResult;
			}
		}
		//-2 used to specify nothing found yet
		return -2;
	}

	private int matchEntityStrings(Graph myLocationGraph, int j, String comparisonString){
		Node locationNode = myLocationGraph.getNodes(false).get(j);
		if (locationNode.getAttribute("description").equalsIgnoreCase(comparisonString)) {
			setEntityVariables(myLocationGraph, j);
			return j;
		}
		//we will also accept a shorter entity definition
		else if (locationNode.getId().getId().equalsIgnoreCase(comparisonString)) {
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
	public void searchLocations(String comparisonString){
		ArrayList<Graph> myLocationList = entities.get(0).getSubgraphs().get(0).getSubgraphs();
		//If location is found, set our variables
		for(int i=0; i<myLocationList.size(); i++){
			Node locationNode = myLocationList.get(i).getNodes(false).get(0);
			if(locationNode.getAttribute("description").equalsIgnoreCase(comparisonString)) {
				setLocationResult(locationNode.getId().getId(), i);
				return;
			}
			//we will also accept a shorter location definition
			else if(locationNode.getId().getId().equalsIgnoreCase(comparisonString)){
				setLocationResult(locationNode.getId().getId(), i);
				return;
			}
		}
		//If we don't find the location, clear location string and set coordinate to -1 to indicate
		// that it doesn't exist
		setLocationResult("", -1);
	}

	private void setLocationResult(String locationId, int locationInt){
		//the location string is always set to be the ID version of the location name, because that is the
		//version used in the 'path'
		this.locationId = locationId;
		this.locationInt = locationInt;
	}

	public boolean isLocationAccessible(String currentLocation, String targetLocation){
		ArrayList<Edge> testEdges = entities.get(0).getSubgraphs().get(1).getEdges();
		for(int x=0; x<testEdges.size(); x++){
			if(matchEndsOfPath(testEdges, x, currentLocation, "source") &&
				matchEndsOfPath(testEdges, x, targetLocation, "target")) {
				return true;
			}
		}
		return false;
	}

	//matches a path with a string location name- returns true if there is a match
	private static boolean matchEndsOfPath(ArrayList<Edge> testEdges, int position, String location, String type){
		PortNode edgeEnd;
		if(type.equalsIgnoreCase("source")){
			edgeEnd = testEdges.get(position).getSource();
		}
		//Must be target
		else{
			edgeEnd = testEdges.get(position).getTarget();
		}
		return edgeEnd.getNode().getId().getId().equalsIgnoreCase(location);
	}

	public int findLocQuantity(){
		return  entities.get(0).getSubgraphs().get(0).getSubgraphs().size();
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

	public void removePath(String startLocation, String endLocation){
		ArrayList<Edge> testEdges = entities.get(0).getSubgraphs().get(1).getEdges();
		for(int x=0; x<testEdges.size(); x++){
			if(matchEndsOfPath(testEdges, x, startLocation, "source") &&
					matchEndsOfPath(testEdges, x, endLocation, "target")) {
				testEdges.remove(x);
				return;
			}
		}
	}

	/********************************************************
	 ***********   ADDING AND REMOVING ARTEFACTS   ***********
	 ********************************************************/

	public void removeObject(int currentLocation, int artefactPosition, String objType){
		ArrayList<Graph> myLocationGraphs = getLocationGraphs(currentLocation);
		for (Graph myLocationGraph : myLocationGraphs) {
			if (myLocationGraph.getId().getId().equalsIgnoreCase(objType)) {
				myLocationGraph.getNodes(true).remove(artefactPosition);
				return;
			}
		}
	}

	public void addObject(String objID, String objDesc, int currentLocation, String objType){
		Graph locationGraph= entities.get(0).getSubgraphs().get(0).getSubgraphs().get(currentLocation);
		ArrayList<Graph> locationSubgraphs = locationGraph.getSubgraphs();
		for (Graph locationSubgraph : locationSubgraphs) {
			if (locationSubgraph.getId().getId().equalsIgnoreCase(objType)) {
				//Get list of nodes attached to the location which are of our objType
				ArrayList<Node> objArray = locationSubgraph.getNodes(true);
				addObjectNode(objArray, objID, objDesc);
				return;
			}
		}
		//If there is not a pre-existing list to add out entity to, we will need to create one (i.e, if we are trying
		// to add furniture to a location that doesn't already contain furniture, we need to create a furniture list)
		addObjectType(locationGraph, objID, objDesc, objType);
	}

	private static void addObjectNode(ArrayList<Node> objArray, String objID, String objDesc){
		Id newId = new Id();
		newId.setId(objID);
		Node newNode = new Node();
		newNode.setId(newId);
		newNode.setAttribute("description", objDesc);
		objArray.add(newNode);
	}

	private static void addObjectType(Graph locationGraph, String objID, String objDesc, String objType){
		addNewGraph(locationGraph, objType);
		ArrayList<Graph> myLocationGraphs = locationGraph.getSubgraphs();
		//Get location of our new subgraph- it will be last on the list of graphs attached to the location
		int newGraphLocation = myLocationGraphs.size() - 1;
		//Get our new graph
		ArrayList<Node> objArray = myLocationGraphs.get(newGraphLocation).getNodes(true);
		addObjectNode(objArray, objID, objDesc);
	}

	private static void addNewGraph(Graph locationGraph, String objType){
		Graph newSubgraph = new Graph();
		newSubgraph.setType(Graph.DIRECTED);
		Id newId = new Id();
		newId.setId(objType);
		newSubgraph.setId(newId);
		locationGraph.addSubgraph(newSubgraph);
	}

	private ArrayList<Graph> getLocationGraphs(int currentLocation){
		return entities.get(0).getSubgraphs().get(0).getSubgraphs().get(currentLocation).getSubgraphs();
	}

	/********************************************************
	 ***************   RETURN DISPLAY TEXT    ****************
	 ********************************************************/

	public String findLocationId(int location){
		Graph locationGraph = entities.get(0).getSubgraphs().get(0).getSubgraphs().get(location);
		return locationGraph.getNodes(false).get(0).getId().getId();
	}

	public String findLocationDesc(int location){
		Graph locationGraph = entities.get(0).getSubgraphs().get(0).getSubgraphs().get(location);
		return locationGraph.getNodes(false).get(0).getAttribute("description");
	}

	public String getEntityString(int location, String entityType){
		StringBuilder allArtefacts = new StringBuilder();
		ArrayList<Graph> locGraphs = entities.get(0).getSubgraphs().get(0).getSubgraphs().get(location).getSubgraphs();
		for (Graph myLocationGraph : locGraphs) {
			if (myLocationGraph.getId().getId().equalsIgnoreCase(entityType)) {
				ArrayList<Node> artefactArray = myLocationGraph.getNodes(true);
				for (int j = 0; j < artefactArray.size(); j++) {
					//Add item ID
					String entityID = artefactArray.get(j).getId().getId().toUpperCase();
					allArtefacts.append(entityID).append(" : ");
					//Add item description
					String entityDesc = myLocationGraph.getNodes(false).get(j).getAttribute("description");
					allArtefacts.append(entityDesc).append("\n");
				}
			}
		}
		return resolveIfEmpty(allArtefacts.toString());
	}

	public String getPathsList(int location){
		String locationName = findLocationId(location);
		StringBuilder allLocations = new StringBuilder();
		Graph testEdges = entities.get(0).getSubgraphs().get(1);
		for(int x=0;x<testEdges.getEdges().size(); x++){
			if(testEdges.getEdges().get(x).getSource().getNode().getId().getId().equalsIgnoreCase(locationName)){
				allLocations.append(testEdges.getEdges().get(x).getTarget().getNode().getId().getId()).append("\n");
			}
		}
		return resolveIfEmpty(allLocations.toString());
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
