package DiffieHellman;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.security.NoSuchAlgorithmException;

public class Communicator {

	private int cID;
	private int personalKey;
	private BigInteger publicKey;
	private BigInteger secretKey;
	private BigInteger p;
	private int g;
	public SecureRandom rand;
	 
	private BigInteger [] keys;		//will hold public computed keys g^() sent from other communicators
	
	public Communicator(int id){
		
		cID = id;
		
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
		//many sources claim that it is sufficient for a to be between 0 and 100
		personalKey = rand.nextInt(50) + 3;
		System.out.println("Private integer key " +personalKey);
	}
	
	public void encodePrivateKey(){
		publicKey = BigInteger.ZERO;
		publicKey = BigInteger.valueOf(g).pow(personalKey);
		System.out.println("Encoded public key: " + publicKey);
	}
	
	BigInteger calculateSharedKey(BigInteger key){
		BigInteger result = key.pow(personalKey);
		System.out.println("the PUBLIC shared key calculated with " + key + "^" + personalKey + " is " + result);
		return result;
	}
	
	public void calculateSecretKey(BigInteger baseKey){
		secretKey = baseKey.pow(personalKey);
		System.out.println("Communicator " + cID + "'s SECRET key: " + secretKey);
;	}
	
	
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
