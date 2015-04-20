package DiffieHellman;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.BitSet;
import java.util.Random;

public class PrimeNumbers{
	
	private byte[] seed1;
	
	private BigInteger p;
	private BigInteger q;
	private double l;
	private BigInteger hash1;
	private BigInteger hash2;
	private BigInteger seed1Int;
	private int g;
	private BigInteger c;
	
	private BigInteger[] V;
	
	public PrimeNumbers(double el){
		l = el;
		g = 160;
	}
	
	//Using l=2, b=100
	public boolean nistGetPrimes() throws NoSuchAlgorithmException{
		double L = 512 + 65*(l);
		double b = 100;
		double n = (L - 1 - b)/160;
		boolean probPrime = false;
		
		//Change to while, when Miller-Rabin completed
		System.out.println("hry");
		//while (!probPrime){
		if (!probPrime){
			seed1 = randSeed();
			hash1 = new SHA1Hash(seed1).getHash();
			
			//s
			seed1Int = new BigInteger(seed1);
			System.out.println(seed1Int);
			
			//(s+1) mod 2^g
			BigInteger seed2Int = (seed1Int.add(BigInteger.ONE).mod(BigInteger.valueOf((long) Math.pow(2,g))));
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
			System.out.println("sdfsds");
			//Test q using Miller-Rabin. If true, probPrime = true
			PrimeValidation pv1 = new PrimeValidation();
			int t;
			if (pv1.probablyPrime(q, 18)){
				probPrime = true;
			}
			else{System.out.println(q);};
			

		}
		int i = 0;
		int j = 2;
		BigInteger X = null;
		BigInteger W = null;
		System.out.println(i);
		while (i < 4096){
			
			for (int k=0;i<=n;k++){
				
				//((s + j + k) mod 2^g)
				byte[] val = ((seed1Int.add(BigInteger.valueOf(j)).add(BigInteger.valueOf(k))).mod(BigInteger.valueOf((long) Math.pow(2, g)))).toByteArray();
				//V_k = Hash((s + j + k) mod 2^g)
				V[k] = new SHA1Hash(val).getHash();
				
				//So long as k isn't n...
				//increment W by V_k * 2^(160*k)
				//Otherwise, increment W by (V_k mod 2^b) * 2^(160*k)
				if (k != n){
					W = W.add((V[k].multiply(BigInteger.valueOf((long) (Math.pow(2, 160*k))))));
				}else{
					W = W.add(((V[k].mod(BigInteger.valueOf((long)Math.pow(2, b)))).multiply(BigInteger.valueOf((long) (Math.pow(2, 160*k))))));
				}
			}
			//X = W + 2^(L-1)
			X = W.add(BigInteger.valueOf((long) Math.pow(2, L-1)));
			
			//c = X mod 2q
			c = X.mod(q.multiply(BigInteger.valueOf(2)));
			p = X.subtract(c.subtract(BigInteger.ONE));
			
			if (p.compareTo(BigInteger.valueOf((long)Math.pow(2,L-1))) >= 0){
				PrimeValidation pv2 = new PrimeValidation();
				int t = 5;
				if (pv2.probablyPrime(p, t)){
					System.out.println("p: "+p);
					System.out.println("q: "+q);
					return true;
				}
			}
			i += 1;
			j += (n+1);
		}
		return false;
		
	}
	
	public byte[] randSeed(){
		
		Random rand = new SecureRandom();
		byte[] randSeed = new byte[20];
		rand.nextBytes(randSeed);
		
		return randSeed;
	}
}