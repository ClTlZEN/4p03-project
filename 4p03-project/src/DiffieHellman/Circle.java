package DiffieHellman;

import java.math.BigInteger;
import java.util.Arrays;

public class Circle {
 
	private int numCommunicators;
	private Communicator [] aliceAndFriends;
	private BigInteger [] sharedKeys;
	private boolean shared;
	private int divideCounter;
	private boolean traded;
	
	public Circle(int n){
		numCommunicators = n;
		aliceAndFriends = new Communicator[numCommunicators];
		sharedKeys = new BigInteger[numCommunicators];
		shared = false;
		divideCounter = 0;
		traded =false;
		
		addCommunicators();
		System.out.println("communicators: " + aliceAndFriends[0].getCID() + " " + aliceAndFriends[1].getCID() /*+ " " + aliceAndFriends[2].getCID() + " " + aliceAndFriends[3].getCID()*/);
		shareKeys();
		System.out.println("communicator keys once raised: " + sharedKeys[0] + " " + sharedKeys[1]/* + " " + sharedKeys[2] + " " + sharedKeys[3]*/);
		tradeKeys();
		
		System.out.println("*******NOW TRYING WITH DIVIDE AND CONQUER********");
		shareKeys();
		tradeKeysEfficiently(aliceAndFriends);
		
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
					System.out.println("SECRET----------------");
					if (j+1 > numCommunicators-1) {
						System.out.println("***Carol*** w/: " + sharedKeys[0]);
						aliceAndFriends[j].calculateSecretKey(temp);
					}
					else {
						System.out.println("***ALICE/BOB*** w/: " + sharedKeys[j+1]);
						aliceAndFriends[j].calculateSecretKey(sharedKeys[j+1]);
					}
				}
				else {
					System.out.println("PUBLIC--------------");
					if (j+1 < numCommunicators){
						System.out.println("***ALICE/BOB*** w/: " + sharedKeys[j+1]);
						sharedKeys[j] = aliceAndFriends[j].calculateSharedKey(sharedKeys[j+1]);
					}
						
					else{
						System.out.println("***Carol*** w: " + sharedKeys[0]);
						sharedKeys[j] = aliceAndFriends[j].calculateSharedKey(temp);
					}
						
				}	
				System.out.println("ok");
			}
			if (shared) return;
			System.out.println("Traded: " + sharedKeys[0].bitLength() + " ----- " + sharedKeys[1].bitLength()/* + "----- "+ sharedKeys[2].bitLength() + "----- "+ sharedKeys[3].bitLength() */);
		}
	}
	
	
	/**Base key should be the g^__ value that the group has computed, and exchanged 
	 * with the other group. With the first divide, the base key should be key[0] and key[length/2]*/
	public void tradeKeysEfficiently(Communicator [] group){
		BigInteger leftBase, rightBase;
		//makes sure there is at least two communicators
		if (group.length >= 2 && !traded){
			
			//split into two sub-groups
			System.out.println("1. Splitting into two sub-groups: ");
			Communicator [] left  = Arrays.copyOfRange(group, 0, group.length/2);
			System.out.println("--> LEFT GROUP");
			for (int i=0 ; i<left.length ; i++){
				System.out.println("Communicator " + left[i].getCID() + " with key: " + left[i].getKey());
			}
			
			Communicator[] right = Arrays.copyOfRange(group, (group.length/2), group.length);
			System.out.println("--> RIGHT GROUP");
			for (int i=0 ; i<right.length ; i++){
				System.out.println("Communicator " + right[i].getCID() + " with key: " + right[i].getKey());
			}
			
			System.out.println(" ");
			
			leftBase = sharedKeys[left[0].getCID()];
			rightBase = sharedKeys[right[0].getCID()];
			
			
			for (int l=0 ; l<left.length ; l++){
				if (l>0) {
					leftBase = left[l].calculateSharedKey(leftBase);
					System.out.println("2. Calculating the new base: " + leftBase);
				}
				sharedKeys[left[l].getCID()] = leftBase;	//updates public keys
			}
			System.out.println("3. Now dividing left side again");
			tradeKeysEfficiently(left);
			
			for (int r=0 ; r<right.length ; r++){
				if (r>0) {
					rightBase = right[r].calculateSharedKey(rightBase);
					System.out.println("2. Calculating the new base: " + rightBase);
				}
				sharedKeys[right[r].getCID()] = rightBase;	//updates public keys
			}
			System.out.println("3. Now dividing right side again");
			tradeKeysEfficiently(right);

		}
		else {
			divideCounter++;
			System.out.println("divide counter: " + divideCounter);
			if (divideCounter >= numCommunicators-1){
				System.out.println("4. At individuals...Calculating secret shared keys");
				tradeOnce();
				traded = true;
			}		
		}
			
	}
	
	public void tradeOnce(){
		BigInteger temp = sharedKeys[0];
		System.out.println();
		System.out.println("temp: " + temp);
		for (int i=0 ; i<numCommunicators ; i++){
			if (i+1 < numCommunicators){
				System.out.println("traded " + sharedKeys[i] + " with " + sharedKeys[i+1] + " for " + aliceAndFriends[i].getCID());
				aliceAndFriends[i].calculateSecretKey(sharedKeys[i+1]);
			}
				
			else{
				System.out.println("traded: "  + sharedKeys[i] + " with " + temp + " for " + aliceAndFriends[i].getCID());
				System.out.println("temp: " + temp);
				aliceAndFriends[i].calculateSecretKey(temp);
			}
		}
		traded = true;
	}
	
	BigInteger[] getpublicKeys(){
		return sharedKeys;
	}
	
}
