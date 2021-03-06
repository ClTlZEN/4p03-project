package DiffieHellman;
 
import java.io.*;
import java.net.*;
 
public class CommClient {
	
	public CommClient(){
		/*if (args.length != 2) {
        System.err.println(
            "Usage: java EchoClient <host name> <port number>");
        System.exit(1);
    }*/

    String host = "localhost";
    int portNum = 4000;
    

    try (
        Socket kkSocket = new Socket(host, portNum);
        PrintWriter out = new PrintWriter(kkSocket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(
            new InputStreamReader(kkSocket.getInputStream()));
    ) {
        BufferedReader stdIn =
            new BufferedReader(new InputStreamReader(System.in));
        String fromServer;
        String fromUser;

        while ((fromServer = in.readLine()) != null) {
            System.out.println("Server: " + fromServer);
            if (fromServer.equals("Bye."))
                break;
             
            fromUser = stdIn.readLine();
            if (fromUser != null) {
                System.out.println("Client: " + fromUser);
                out.println(fromUser);
            }
        }
    } catch (UnknownHostException e) {
        System.err.println("Don't know about host " + host);
        System.exit(1);
    } catch (IOException e) {
        System.err.println("Couldn't get I/O for the connection to " +
            host);
        System.exit(1);
    }
	}
	
    public static void main(String[] args) throws IOException {
         new CommClient();
    	
    }
}
