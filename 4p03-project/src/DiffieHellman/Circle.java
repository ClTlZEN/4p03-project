package DiffieHellman;

import java.math.BigInteger;

public class Circle {
 
	private int numCommunicators;
	private Communicator [] aliceAndFriends;
	private BigInteger [] sharedKeys;
	private boolean shared;
	
	public Circle(int n){
		numCommunicators = n;
		aliceAndFriends = new Communicator[numCommunicators];
		sharedKeys = new BigInteger[numCommunicators];
		shared = false;
		
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
		BigInteger temp;
		
		for (int i=0 ; i<numCommunicators ; i++){
			temp = sharedKeys[0];
			for (int j=0 ; j<numCommunicators ; j++){
				if (numCommunicators-i-1 == 1)
					shared=true;
				if (shared){
					System.out.println("calculating secret key**************");
					if (j+1 > numCommunicators-1) {
						aliceAndFriends[j].calculateSecretKey(temp);
						System.out.println("calculating for CAROL w: " + sharedKeys[0]);
					}
					else {
						aliceAndFriends[j].calculateSecretKey(sharedKeys[j+1]);
						System.out.println("calculating for ALICE/BOB w: " + sharedKeys[j+1]);
					}
				}
				else {
					System.out.println("**************calculating public key");
					if (j+1 < numCommunicators){
						sharedKeys[j] = aliceAndFriends[j].calculateSharedKey(sharedKeys[j+1]);
						System.out.println("calculating for ALICE/BOB w: " + sharedKeys[j+1]);
					}
						
					else{
						sharedKeys[j] = aliceAndFriends[j].calculateSharedKey(temp);
						System.out.println("calculating for CAROL w: " + sharedKeys[0]);
					}
						
				}	
				
				System.out.println("ok");
			}
			if (shared) return;
			System.out.println("Traded: " + sharedKeys[0].bitLength() + " ----- " + sharedKeys[1].bitLength() + "----- "+ sharedKeys[2].bitLength() + "----- "+ sharedKeys[3].bitLength() );
		}
	}
	
	BigInteger[] getpublicKeys(){
		return sharedKeys;
	}
	
}
