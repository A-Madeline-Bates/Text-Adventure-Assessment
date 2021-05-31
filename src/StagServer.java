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
    private final String entityFilename;
    private final String actionFilename;
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
            CheckForDeath deathCheck = new CheckForDeath(entityClass, playerState);
            this.exitMessage = exitMessage + deathCheck.getExitMessage();
        } catch(ParseException exception){
            this.exitMessage = exitMessage + "Error: " + exception;
        }
    }

    private void createDatabases() throws IOException{
        createActionClass();
        createEntityClass();
        //Set to name of first location
        this.playerStore = new PlayerStore(entityClass.findLocationId(0));
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

// Check the style guide + previous feedback
// Reduce complexity
// Shorten lines!
//Refactor to make some classes smaller?
//TEST
//TEST WITH THE SERVER PROVIDED
