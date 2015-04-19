package DiffieHellman;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.BitSet;
import java.util.Random;

public class PrimeNumbers{
	
	private BigInteger p;
	private BigInteger q;
	private double l;
	private BigInteger hash1;
	private BigInteger hash2;
	
	public PrimeNumbers(double el){
		l = el;
	}
	
	//Using l=2, b=100
	public void nistGetPrimes() throws NoSuchAlgorithmException{
		double L = 512 + 65*(l);
		double b = 100;
		double n = (L - 1 - b)/160;
		boolean probPrime = false;
		
		//Should be while
		if (!probPrime){
			byte[] seed1 = randSeed();
			hash1 = new SHA1Hash(seed1).getHash();
			
			//s
			BigInteger seed1Int = new BigInteger(seed1);
			
			//(s+1) mod 2^160
			BigInteger seed2Int = (seed1Int.add(BigInteger.ONE).mod(BigInteger.valueOf((long) Math.pow(2,160))));
			byte[] seed2 = seed2Int.toByteArray();
			hash2 = new SHA1Hash(seed2).getHash();
			
			//Setting most significant bit to 1, AKA, making sure it's negative
			BigInteger U = hash1.xor(hash2);
			if (U.compareTo(BigInteger.ZERO)>0){
				U = U.negate();
			}
			//Setting least significant bit to 1, AKA, making sure it's odd
			if(U.mod(new BigInteger("2")).equals(BigInteger.ZERO)){
				U = U.add(BigInteger.ONE);
			}
			
			//Now that you have modified U, set it as q
			BigInteger q = U;
			
			
		}
		
	}
	public boolean fermatsTestPrime(){
		return false;
		
	}
	
	public byte[] randSeed(){
		
		Random rand = new SecureRandom();
		byte[] randSeed = new byte[20];
		rand.nextBytes(randSeed);
		
		return randSeed;
	}
}