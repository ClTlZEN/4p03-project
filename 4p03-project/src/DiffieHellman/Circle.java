package DiffieHellman;

import java.math.BigInteger;

public class Circle {
 
	private int numCommunicators;
	private Communicator [] aliceAndFriends;
	private BigInteger [] sharedKeys;
	
	public Circle(int n){
		numCommunicators = n;
		aliceAndFriends = new Communicator[numCommunicators];
		sharedKeys = new BigInteger[numCommunicators];
		
		addCommunicators();
		System.out.println("communicators: " + aliceAndFriends[0].getCID() + " " + aliceAndFriends[1].getCID() + " " + aliceAndFriends[2].getCID() + " " + aliceAndFriends[3].getCID());
		shareKeys();
		System.out.println("communicator keys: " + sharedKeys[0] + " " + sharedKeys[1] + " " + sharedKeys[2] + " " + sharedKeys[3]);
		tradeKeys();
		
		//if n < 3 use circle exchange method - communicators only need one array of shared public keys
		
		//otherwise use the divide & conquer method - think about how to hold shared key information
		
	}
	
	/**Initializes and adds all communicators to the communicator list
	 * Initialization of a Communicator generates and encodes each individual's personal secret keys*/
	public void addCommunicators(){
		for (int i=0 ; i<aliceAndFriends.length ; i++){
			aliceAndFriends[i] = new Communicator(i);
		}
	}
	
	public void shareKeys(){
		for (int i=0 ; i<numCommunicators ; i++){
			sharedKeys[i] = aliceAndFriends[i].getKey();
			//System.out.println(sharedKeys[i]);
		}
	}
	
	public void tradeKeys(){
		for (int i=0 ; i<numCommunicators-1 ; i++){
			for (int j=0 ; j<numCommunicators ; j++){
				if (j+1 < numCommunicators)
					sharedKeys[j] = aliceAndFriends[j].calculateSharedKey(sharedKeys[j+1]);
				else
					sharedKeys[j] = aliceAndFriends[j].calculateSharedKey(sharedKeys[1]);
				System.out.println("ok");
			}
			System.out.println("Traded: " + sharedKeys[0] + " ----- " + sharedKeys[1] + "----- "+ sharedKeys[2] + "----- "+ sharedKeys[3] );
		}
	}
	
	/*Is called to add a specific communicator involved in the message transmission*/
/*	public void addCommunicator(Communicator c){
		if (c.getCID() > numCommunicators) 
			System.out.println("Sorry, you already have all your communicators");
		else aliceAndFriends[c.getCID()] = c;
	}*/
	
	BigInteger[] getpublicKeys(){
		return sharedKeys;
	}
	
}
