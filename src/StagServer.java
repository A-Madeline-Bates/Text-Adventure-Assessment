import java.io.*;
import java.net.*;
import java.util.*;

class StagServer
{
    String entityFilename;
    String actionFilename;

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
        //first call a class to parse entityFilename and actionFilename and return entity Graph and actions
        //all other classes will walk through these graphs and pull correct data. data will also be changed +
        //manipulated in these structs to present changes
        String line = in.readLine();
        out.write("You said... " + line + "\n");
    }
}
