import cmdClasses.CMDType;
import data.Actions;
import data.Entities;
import data.PlayerState;
import parseExceptions.ParseException;
import tokeniser.Tokeniser;
import com.alexmerz.graphviz.objects.Graph;
import org.json.simple.JSONArray;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

class StagServer
{
    final String entityFilename;
    final String actionFilename;
    private Actions actionClass;
    private Entities entityClass;
    private PlayerState playerState;
    private String exitMessage = "";

    public static void main(@SuppressWarnings("CStyleArrayDeclaration") String args[])
    {
        if(args.length != 2) System.out.println("Usage: java StagServer <entity-file> <action-file>");
        else //noinspection MagicNumber
            new StagServer(args[0], args[1], 8888);
    }

    public StagServer(String entityFilename, String actionFilename, int portNumber)
    {
        this.entityFilename = entityFilename;
        this.actionFilename = actionFilename;
        createDatabases();
        try {
            ServerSocket ss = new ServerSocket(portNumber);
            System.out.println("Server Listening");
            //noinspection InfiniteLoopStatement
            while(true) acceptNextConnection(ss);
        } catch(IOException ioe) {
            System.err.println(ioe);
        }
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
        } catch(NullPointerException npe) {
            System.out.println("Connection Lost");
        }
    }

    private void processNextCommand(BufferedReader in, BufferedWriter out) throws IOException
    {
        String line = in.readLine();
        Tokeniser tokeniser = new Tokeniser(line);
        processCommand(tokeniser);
        out.write(exitMessage);
        //clear exitMessage
        this.exitMessage = "";
        //This is used for EOF
        out.write( ((char)4));
        out.flush();
    }

    private void processCommand(Tokeniser tokeniser){
        try {
            CommandFactory factory = new CommandFactory(entityClass, actionClass, playerState);
            CMDType command = factory.createCMD(tokeniser);
            this.exitMessage = command.getExitMessage();
        } catch(ParseException exception){
            this.exitMessage = "Error:" + exception;
        }
    }

    private void createDatabases(){
        this.playerState = new PlayerState();
        createActionClass();
        createEntityClass();
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

//walk through and check the input for action command (it doesn't have to be the first word) !!
//Maybe add some file check/IO exception stuff?
//Get Parse errors passed back to main

//find "start" location (This starting point is always the first location that is
// encountered when reading in the "entities" file.)

//need an instruction parser
//maybe create factory for
//"playerState" (or "inv" for short): lists all of the artefacts currently being carried by the player
//load up empty 'playerState' array to start
//"get": picks up a specified artefact from current location and puts it into player's playerState
//"drop": puts down an artefact from player's playerState and places it into the current location
//"goto": moves from one location to another (if there is a path between the two)
//"look": describes the entities in the current location and lists the paths to other locations
//there will also be ANOTHER class which will cross compare to see if something is a valid action (in the
//actions array) + whether the player has the correct furniture/artefacts and playerState to do it. This
//must check ALL instances of that action in the array before saying something is impossible. It will
//then shuffle items about or add paths based on the result

//each location has its own 'playerState' containing all artefacts + anything dropped by a player
//maybe load all artefacts/furniture to locations at the beginning so that the items aren't reset every time
//you visit a location. Or maybe edit entities graph itself? When item disappears it moves to 'unplaced'

//The skeleton StagServer class you have been given includes the code to deal with network communication.
//You will however be required to deal with reading and writing to the socket stream.

//For multiplayer, we might need to hold two inventories and two different variables to indicate where the
//players are?

//STRATEGY design pattern
