import com.alexmerz.graphviz.*;
import com.alexmerz.graphviz.objects.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

public class GraphParserExample {

    public static void main(String[] args) {
        new GraphParserExample(args[0]);
        new JSONParserExperiment(args[1]);
    }

    public GraphParserExample(String file){
        graphParse(file);
    }

    private void graphParse(String file){
        try {
            Parser parser = new Parser();
            FileReader reader = new FileReader(file);
            parser.parse(reader);
            ArrayList<Graph> graphs = parser.getGraphs();
            ArrayList<Graph> subGraphs = graphs.get(0).getSubgraphs(); //down level

            System.out.print("* " + subGraphs.get(0).getId().getId() + " * " + subGraphs.get(1).getId().getId() + "\n");
            //subGraphs.get(0).getId().getId() = locations
            //subGraphs.get(1).getId().getId() = paths
            Graph location = subGraphs.get(0);
            System.out.print("* " + location.getSubgraphs().get(0).getNodes(false).get(0) + "\n");
            //location.getSubgraphs().get(0).getNodes(false).get(0) = start [description="An empty room"]
            System.out.print("* " + location.getSubgraphs().get(0).getNodes(false).get(0).getId().getId() + "\n");
            //location.getSubgraphs().get(0).getNodes(false).get(0).getId().getId() = start
            System.out.print("* " + location.getSubgraphs().get(0).getNodes(false).get(0).getAttribute("description") + "\n");
            //location.getSubgraphs().get(0).getNodes(false).get(0).getAttribute() = "An empty room"

            for(Graph g : subGraphs){
                System.out.printf("id = %s\n", g.getId().getId()); //"locations" then "paths"
                ArrayList<Graph> subGraphs1 = g.getSubgraphs(); //down level
                for (Graph g1 : subGraphs1){
                    ArrayList<Node> nodesLoc = g1.getNodes(false); //accessing node from subgraph level
                    Node nLoc = nodesLoc.get(0);
                    System.out.printf("\tid = %s, name = %s\n",g1.getId().getId(), nLoc.getId().getId()); //subgraph id:"cluster 001" - node id:"start" & subgraph id:"cluster 002" - node id:"forest"
                    ArrayList<Graph> subGraphs2 = g1.getSubgraphs(); //down level
                    for (Graph g2 : subGraphs2) {
                        System.out.printf("\t\tid = %s\n", g2.getId().getId()); //"artefacts" & "furniture" etc
                        ArrayList<Node> nodesEnt = g2.getNodes(false); //accessing node from subgraph level
                        for (Node nEnt : nodesEnt) {
                            System.out.printf("\t\t\tid = %s, description = %s\n", nEnt.getId().getId(), nEnt.getAttribute("description")); //id:"potion" - description:"Magic potion" & id:"door" - description:"Wooden door" etc
                        }
                    }
                }
                //cluster999 has no objects attached to its nodes but it has every node type and so it only displays "id = characters id = artefacts id = furniture"

                ArrayList<Edge> edges = g.getEdges(); //this looks at "subgraph paths"
                for (Edge e : edges){
                    System.out.printf("Path from %s to %s\n", e.getSource().getNode().getId().getId(), e.getTarget().getNode().getId().getId());//"Path from start to forest" & "Path from forest to start" etc
                }
            }

        } catch (FileNotFoundException fnfe) {
            System.out.println(fnfe);
        } catch (com.alexmerz.graphviz.ParseException pe) {
            System.out.println(pe);
        }
    }
}

//for basic-entities.dot
//  id = locations
//        id = cluster001, name = start
//            id = artefacts
//                id = potion, description = Magic potion
//            id = furniture
//                id = door, description = Wooden door
//        id = cluster002, name = forest
//            id = artefacts
//                id = key, description = Brass key
//        id = cluster003, name = cellar
//            id = characters
//                id = elf, description = Angry Elf
//        id = cluster999, name = unplaced
//            id = characters
//            id = artefacts
//            id = furniture
//  id = paths
//  Path from start to forest
//  Path from forest to start
//  Path from cellar to start
