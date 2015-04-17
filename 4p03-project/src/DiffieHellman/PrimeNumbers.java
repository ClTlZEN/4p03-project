package DiffieHellman;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Random;

public class PrimeNumbers{
	
	private BigInteger p;
	private BigInteger q;
	private int l;
	
	public PrimeNumbers(int el){
		l = el;
	}
	public void nistGetPrimes(int l){
		int L = 512 + 65*(l);
		
		boolean probPrime = false;
		
		while (!probPrime){
			
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