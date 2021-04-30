import CMDClasses.CMDType;
import Data.Actions;
import Data.Entities;
import Data.Inventory;
import ParseExceptions.ParseException;
import Tokeniser.Tokeniser;
import com.alexmerz.graphviz.objects.Graph;
import org.json.simple.JSONArray;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

class StagServer
{
    String entityFilename;
    String actionFilename;
    Actions actionClass;
    Entities entityClass;
    Inventory inventory;

    public static void main(String args[])
    {
        if(args.length != 2) System.out.println("Usage: java StagServer <entity-file> <action-file>");
        else new StagServer(args[0], args[1], 8888);
    }

    public StagServer(String entityFilename, String actionFilename, int portNumber)
    {
        this.entityFilename = entityFilename;
        this.actionFilename = actionFilename;
        try {
            ServerSocket ss = new ServerSocket(portNumber);
            System.out.println("Server Listening");
            while(true) acceptNextConnection(ss);
        } catch(IOException ioe) {
            System.err.println(ioe);
        }
        createDatabases();
    }

    private void acceptNextConnection(ServerSocket ss)
    {
        try {
            // Next line will block until a connection is received
            Socket socket = ss.accept();
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            processNextCommand(in, out);
            out.close();
            in.close();
            socket.close();
        } catch(IOException ioe) {
            System.err.println(ioe);
        }
    }

    private void processNextCommand(BufferedReader in, BufferedWriter out) throws IOException
    {
        String line = in.readLine();
        //parse instructions and create command class
        Tokeniser tokeniser = new Tokeniser(line);
        try {
            CommandFactory factory = new CommandFactory(entityClass, actionClass, inventory);
            CMDType command = factory.createCMD(tokeniser);
            System.out.println(command.getExitMessage());
        } catch(ParseException exception){
            System.out.println(exception);
        }
    }

    //walk through and check the input for action command (it doesn't have to be the first word)

    private void createDatabases(){
        this.inventory = new Inventory();
        createActionClass();
        createEntityClass();
        new Test();
    }

    private void createActionClass(){
        JSONArray actions = new ParseActions(actionFilename).getActions();
        this.actionClass = new Actions(actions);
    }

    private void createEntityClass(){
        ArrayList<Graph> entities = new ParseEntities(entityFilename).getEntities();
        this.entityClass = new Entities(entities);
    }
}

//find "start" location (This starting point is always the first location that is
// encountered when reading in the "entities" file.)

//need an instruction parser
//maybe create factory for
//"inventory" (or "inv" for short): lists all of the artefacts currently being carried by the player
//load up empty 'inventory' array to start
//"get": picks up a specified artefact from current location and puts it into player's inventory
//"drop": puts down an artefact from player's inventory and places it into the current location
//"goto": moves from one location to another (if there is a path between the two)
//"look": describes the entities in the current location and lists the paths to other locations
//there will also be ANOTHER class which will cross compare to see if something is a valid action (in the
//actions array) + whether the player has the correct furniture/artefacts and inventory to do it. This
//must check ALL instances of that action in the array before saying something is impossible. It will
//then shuffle items about or add paths based on the result


//each location has its own 'inventory' containing all artefacts + anything dropped by a player
//maybe load all artefacts/furniture to locations at the beginning so that the items aren't reset every time
//you visit a location. Or maybe edit entities graph itself? When item disappears it moves to 'unplaced'

//The skeleton StagServer class you have been given includes the code to deal with network communication.
//You will however be required to deal with reading and writing to the socket stream.

//For multiplayer, we might need to hold two inventories and two different variables to indicate where the
//players are?

//STRATEGY design pattern
