package DiffieHellman;
 
import java.net.*;
import java.io.*;
 
public class CommServer {
	
	public CommServer(){
		/*if (args.length != 1) {
        System.err.println("Usage: java KKMultiServer <port number>");
        System.exit(1);
    	}*/

	    int portNum = 4000;
	    boolean isListen = true;
	     
	    try (ServerSocket serverSocket = new ServerSocket(portNum)) { 
	        while (isListen){
	            new CommThread(serverSocket.accept()).start();
	        }
	    }
	    catch (IOException e){
	        System.err.println("Could not listen on port " + portNum);
	        System.out.println("IOExcetion: Incorrect port - "+portNum);
	        //System.exit(-1);
	    }
	}
	
    public static void main(String[] args) throws IOException {
    	new CommServer();
	
    }
}