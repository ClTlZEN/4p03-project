package DiffieHellman;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.security.NoSuchAlgorithmException;

public class Communicator {

	private int cID;
	//private BigInteger privateKey;
	private int personalKey;
	private BigInteger publicKey;
	private BigInteger secretKey;
	private BigInteger p;
	private int g;
	public SecureRandom rand;
//	public int numCommunicators;	//temporary
	 
	private BigInteger [] keys;		//will hold public computed keys g^() sent from other communicators
	
	public Communicator(int id){
		//temporary - will use client/server info
		//numCommunicators = n;
		
		cID = id;
		
		//keys = new BigInteger[n-1];
		
		//for now will give p and g arbitrary values
		try {
			rand = SecureRandom.getInstance("SHA1PRNG");
			p = new BigInteger(160, rand);
		} catch (NoSuchAlgorithmException e){}
		
		g = 2;
		
		pickPrivateKey();
		encodePrivateKey();
		
		System.out.println("--------------Communicator # " + cID);

	}
	
	/*the personal private key should be large and randomly generated for security*/
	public void pickPrivateKey(){
		
/*		For a = 160 bit number. Computation of BigInteger^BigInteger is too complex for my machine
 * 		try {
			rand = SecureRandom.getInstance("SHA1PRNG");
			//make sure the private key is between 2 .. p
			do {
				privateKey = new BigInteger(160, rand);
				System.out.println("help");
			} while (privateKey.compareTo(p.subtract(BigInteger.valueOf(1))) >= 0 // must be smaller than n-2 (0=equal, 1=GT)
					|| privateKey.compareTo(BigInteger.valueOf(1)) < 0);
			System.out.println("Alice's private key: " + privateKey);
		} catch (NoSuchAlgorithmException e){
			System.err.println(e.getMessage());
		}*/
		
		//many sources claim that it is sufficient for a to be between 0 and 100
		personalKey = rand.nextInt(30)+3;
		System.out.println("Private integer key " +personalKey);
	}
	
	public void encodePrivateKey(){
		publicKey = BigInteger.ZERO;
		publicKey = BigInteger.valueOf(g).pow(personalKey);
		//publicKey = pow(BigInteger.valueOf(g), privateKey);
		System.out.println("Encoded public key: " + publicKey);
	}
	
/*	public BigInteger sendKey(){
		return publicKey;
	}*/
	
	//temporary
/*	public void getKey(BigInteger key){
		for (int i=0 ; i<keys.length ; i++){
			if (keys[i]==null){
				keys[i] = key;
				break;
			}
		}
	}
	
	public void printKeys(){
		for (int i=0 ; i<keys.length; i++){
			System.out.println(keys[i] + " ");
		}
	}*/
	
	BigInteger calculateSharedKey(BigInteger key){
		BigInteger result = key.pow(personalKey);
		return result;
	}
	
	public void calculateSecretKey(BigInteger key){
		secretKey = key.pow(personalKey);
	}
	
	
	/* Defining a helper function which allows a BigInteger to be raised
	 * to another BigInteger, and not an int.
	 * Uses repeated squaring.
	 * Based of: http://stackoverflow.com/questions/4582277/biginteger-powbiginteger*/
	BigInteger pow(BigInteger base, BigInteger exp){
		int i=0;
		BigInteger result = BigInteger.ONE;
		while (exp.signum() > 0){
			if (exp.testBit(0)){
				result = result.multiply(base);
			}
			//System.out.println("modifying base & exp then looping again " + i++);
			base = base.multiply(base);
			exp = exp.shiftRight(1);
		}
		return result;
	}
	
	BigInteger getKey(){
		return publicKey;
	}
	
	Integer getCID(){
		return this.cID;
	}
	
	/*discarding at end of session for perfect forward security*/
/*	public void discardKeys(){
		privateKey=null;
		publicKey=null;
		sharedSecretKey=null;
	}*/
	
	
}
