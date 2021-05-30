package data;
import com.alexmerz.graphviz.objects.*;
import java.util.ArrayList;
import java.util.Locale;

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

	public int entitySearch(String comparisonString, int location, String entityType){
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
	private boolean matchEndsOfPath(ArrayList<Edge> testEdges, int position, String location, String type){
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

	public int locationQuantity(){
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

	public void removeObject(int currentLocation, int artefactPosition, String objectType){
		ArrayList<Graph> myLocationGraphs = entities.get(0).getSubgraphs().get(0).getSubgraphs().get(currentLocation).getSubgraphs();
		for (Graph myLocationGraph : myLocationGraphs) {
			if (myLocationGraph.getId().getId().equalsIgnoreCase(objectType)) {
				myLocationGraph.getNodes(true).remove(artefactPosition);
				return;
			}
		}
	}

	public void addObject(String objectID, String objectDesc, int currentLocation, String objectType){
		ArrayList<Graph> myLocationGraphs = entities.get(0).getSubgraphs().get(0).getSubgraphs().get(currentLocation).getSubgraphs();
		for (int i=0; i<myLocationGraphs.size(); i++) {
			if (myLocationGraphs.get(i).getId().getId().equalsIgnoreCase(objectType)) {
				//Get list of nodes attached to the location which are of our objectType
				ArrayList<Node> objectArray = myLocationGraphs.get(i).getNodes(true);
				addObjectNode(objectArray, objectID, objectDesc, "description");
				return;
			}
		}
		//If there is not a pre-existing list to add out entity to, we will need to create one (i.e, if we are trying
		// to add furniture to a location that doesn't already contain furniture, we need to create a furniture list)
		addObjectType(myLocationGraphs, objectID, objectDesc, objectType);
	}

	private static void addObjectNode(ArrayList<Node> objectArray, String objectID, String objectDesc, String nodeType){
		Id newId = new Id();
		newId.setId(objectID);
		Node newNode = new Node();
		newNode.setId(newId);
		newNode.setAttribute(nodeType, objectDesc);
		objectArray.add(newNode);
	}

	private void addObjectType(ArrayList<Graph> myLocationGraphs, String objectID, String objectDesc, String objectType){
		myLocationGraphs = addNewGraph(myLocationGraphs, objectType);
		//Get location of our new subgraph- it will be last on the list of graphs attached to the location
		int newGraphLocation = myLocationGraphs.size() - 1;
		//Get our new graph
		ArrayList<Node> objectArray = myLocationGraphs.get(newGraphLocation).getNodes(true);
		//adding shape node to the array
		addObjectNode(objectArray, "node", getNodeShape(objectType), "shape");
		//adding our object to the array
		addObjectNode(objectArray, objectID, objectDesc, "description");
		return;
	}

	private ArrayList<Graph> addNewGraph(ArrayList<Graph> myLocationGraphs, String objectType){
		Graph newGraph = new Graph();
		Id newId = new Id();
		newId.setId(objectType);
		newGraph.setId(newId);
		myLocationGraphs.add(newGraph);
		return myLocationGraphs;
	}

	private String getNodeShape(String objectType){
		if(objectType.equalsIgnoreCase("furniture")){
			return "hexagon";
		}
		else if(objectType.equalsIgnoreCase("artefacts")){
			return "diamond";
		}
		//must be characters
		else{
			return "ellipse";
		}
	}

	/********************************************************
	 ***************   RETURN DISPLAY TEXT    ****************
	 ********************************************************/

	public String findLocationId(int location){
		return entities.get(0).getSubgraphs().get(0).getSubgraphs().get(location).getNodes(false).get(0).getId().getId();
	}

	public String findLocationDescription(int location){
		return entities.get(0).getSubgraphs().get(0).getSubgraphs().get(location).getNodes(false).get(0).getAttribute("description");
	}

	public String findFirstLocation(){
		return entities.get(0).getSubgraphs().get(0).getSubgraphs().get(0).getNodes(false).get(0).getId().getId();
	}

	public String getEntityString(int location, String entityType){
		StringBuilder allArtefacts = new StringBuilder();
		ArrayList<Graph> myLocationGraphs = entities.get(0).getSubgraphs().get(0).getSubgraphs().get(location).getSubgraphs();
		for (Graph myLocationGraph : myLocationGraphs) {
			if (myLocationGraph.getId().getId().equalsIgnoreCase(entityType)) {
				ArrayList<Node> artefactArray = myLocationGraph.getNodes(true);
				for (int j = 0; j < artefactArray.size(); j++) {
					//Add item ID
					allArtefacts.append(artefactArray.get(j).getId().getId().toUpperCase()).append(" : ");
					//Add item description
					allArtefacts.append(myLocationGraph.getNodes(false).get(j).getAttribute("description")).append("\n");
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
