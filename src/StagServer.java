import cmdClasses.CMDType;
import data.Actions;
import data.Entities;
import data.PlayerState;
import data.PlayerStore;
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
    private PlayerStore playerStore;
    private String exitMessage = "";

    public static void main(String args[])
    {
        if(args.length != 2) {
            System.out.println("Usage: java StagServer <entity-file> <action-file>");
        }
        else {
            new StagServer(args[0], args[1], 8888);
        }
    }

    public StagServer(String entityFilename, String actionFilename, int portNumber)
    {
        this.entityFilename = entityFilename;
        this.actionFilename = actionFilename;
        try {
            //create our databases from the input files
            createDatabases();
            ServerSocket ss = new ServerSocket(portNumber);
            System.out.println("Server Listening");
            while(true) acceptNextConnection(ss);
        } catch (FileNotFoundException fnfe) {
            System.out.println("File not found exception: " + fnfe);
        } catch(IOException ioe) {
            System.err.println("IO Exception: " + ioe);
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
            System.err.println("IO Exception: " + ioe);
        } catch(NullPointerException npe) {
            System.out.println("Connection Lost");
        }
    }

    private void processNextCommand(BufferedReader in, BufferedWriter out) throws IOException
    {
        String line = in.readLine();
        Tokeniser tokeniser = new Tokeniser(line);
        this.exitMessage = exitMessage + "\n~~~~~~~~~~~~~~~~~~~~~~~\n";
        processCommand(tokeniser);
        this.exitMessage = exitMessage + "~~~~~~~~~~~~~~~~~~~~~~~";
        out.write(exitMessage);
        //clear exitMessage
        this.exitMessage = "";
        //This is used for EOF
        out.write(((char)4));
        out.flush();
    }

    private void processCommand(Tokeniser tokeniser){
        try {
            PlayerState playerState = playerStore.getCurrentPlayer(tokeniser);
            CommandFactory factory = new CommandFactory(entityClass, actionClass, playerState, playerStore);
            CMDType command = factory.createCMD(tokeniser);
            this.exitMessage = exitMessage + command.getExitMessage();
            //CHECK IF DEAD
        } catch(ParseException exception){
            this.exitMessage = exitMessage + "Error: " + exception;
        }
    }

    private void createDatabases() throws IOException{
        createActionClass();
        createEntityClass();
        this.playerStore = new PlayerStore(entityClass.findFirstLocation());
    }

    private void createActionClass() throws IOException{
        JSONArray actions = new ParseActions(actionFilename).getActions();
        this.actionClass = new Actions(actions);
    }

    private void createEntityClass() throws IOException{
        ArrayList<Graph> entities = new ParseEntities(entityFilename).getEntities();
        this.entityClass = new Entities(entities);
    }
}

//Walk through and check the input for action command (it doesn't have to be the first word) !!
//Be sure to make your command interpreter as flexible and robust as possible (to deal with "varied" input from
// the user !)
// it should be able to cope with any order
// One subject needs to be present in a command in order to make it valid
// Actions can be in two words?

//It is worth noting that action names are NOT unique - for example there may be multiple "open" actions that act on
// different entities. So be careful when storing and accessing actions.

//entities names cannot be more than one word (it would make the dot file very hard to parse), action trigger can be
// more than one word (because the double quotes allow them to be parsed)

//ignore punctuation in commands?

// As an extension to the basic game, you might like to add a "health level" feature. Each player should start with
// a health level of 3. Consumption of "Poisons & Potions" or interaction with beneficial or dangerous characters will
// increase or decrease a player's health.
// When a player's health runs out (i.e. reaches zero) they should lose all of the items in their inventory (which are
// dropped in the location where they ran out of health) and then they should return to the start location. In order
// to implement these features in your game engine, you should also add a new health command keyword that reports
// back the player's current health level (so the player can keep track of it).
// A prompt should be provided after a player die, something like "you have died and lose all of your items, return to the start"

// You should include the descriptions in look (otherwise they would never get seen !) as well as the names

//Consume path?

// Check the style guide + previous feedback
// Reduce complexity
// Shorten method names / make sure all methods start with verb
// Shorten lines!

//TEST WITH THE SERVER PROVIDED
