package DiffieHellman;

import java.net.*;
import java.io.*;

public class CommThread extends Thread {
    private Socket socket;

    public CommThread(Socket socket) {
        //super("KKMultiServerThread");
        this.socket = socket;
    }
    
    public void run() {

        try (
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        	) 
        	{
            String inputLine, outputLine;
            CommProtocol kkp = new CommProtocol();
            outputLine = kkp.processInput(null);
            out.println(outputLine);

            while ((inputLine = in.readLine()) != null) {
                outputLine = kkp.processInput(inputLine);
                out.println(outputLine);
                if (outputLine.equals("Bye"))
                	break;
                /*if (inputLine == "DONE"){
                	break;
                }*/
            }
            socket.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}