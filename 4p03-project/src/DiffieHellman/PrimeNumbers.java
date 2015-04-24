package DiffieHellman;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.BitSet;
import java.util.Random;

public class PrimeNumbers{
	
	private byte[] seed1;
	
	private BigInteger p;
	private BigInteger q;
	private int l;
	private BigInteger hash1;
	private BigInteger hash2;
	private BigInteger seed1Int;
	private int g;
	private BigInteger c;
	
	private PrintWriter w;
	
	private BigInteger[] V;
	

	
	public PrimeNumbers(int el) throws FileNotFoundException, UnsupportedEncodingException{
		w = new PrintWriter("test.txt","UTF-8");
		l = el;
		g = 160;
	}
	
	//Using l=2, b=100
	public void nistGetPrimes() throws NoSuchAlgorithmException{
		int L = 512 + 64*(l);
		int b = (L-1) % 160;
		int n = (L - 1 - b)/160;
		
		System.out.println("L: "+L+" b: "+b +" n:"+ n);
		boolean qProbPrime = false;
		boolean pProbPrime = false;
		
		//Change to while, when Miller-Rabin completed
		while (!pProbPrime){
			while (!qProbPrime){
			//if (!probPrime){
				seed1 = randSeed();
				BitSet bs = new BitSet();
				//System.out.println("PLEASE: "+seed1.length*8);
				hash1 = new SHA1Hash(seed1).getHash();
				//System.out.println("AHHHHH: "+hash1.toByteArray().length*8);
				
				//s
				seed1Int = new BigInteger(seed1);
				
				//(s+1) mod 2^g
				BigInteger seed2Int = (seed1Int.add(BigInteger.ONE).mod(BigInteger.valueOf(2).pow(g)));
				byte[] seed2 = seed2Int.toByteArray();
				hash2 = new SHA1Hash(seed2).getHash();
				
				
				BigInteger U = hash1.xor(hash2);
				/*System.out.println("START: "+U.toString(2));
				System.out.println("START length: "+U.toByteArray().length*8);*/
				//Setting most significant bit to 1
				//U = U.flipBit(0);
				U = U.abs();
				if (U.signum()==0){
					U = new BigInteger(1, U.toByteArray());
				}
				
				
				//Setting least significant bit to 1
				if(U.mod(new BigInteger("2")).equals(BigInteger.ZERO)){
				//if (U.getLowestSetBit()==0){
					//U = U.flipBit(U.bitLength());
					U = U.add(BigInteger.ONE);
				}
				/*System.out.println("FINISH:" +U);
				System.out.println("FINISH:" +U.toString(2));
				System.out.println("FINISH length: "+U.toByteArray().length*8);
				System.out.println();*/
				
				//Now that you have modified U, set it as q
				q = U;
				//System.out.println("Testing:	"+q);
				//System.out.println("Binary:		"+q.toString(2));
				
				//Test q using Miller-Rabin. If true, probPrime = true
				PrimeValidation pv1 = new PrimeValidation();
				int t;
				if (q.isProbablePrime(10)){
				//if (pv1.probablyPrime(q, 18)){
					System.out.println("Probable prime: "+q);
					qProbPrime = true;
				}
				//else{System.out.println(q);};
				
	
			}
			int i = 0;
			int j = 2;
			
			//System.out.println(i);
			
			while (i < 4096){
				
				BigInteger X = BigInteger.ZERO;
				BigInteger W = BigInteger.ZERO;
				c = BigInteger.ZERO;
				p = BigInteger.ZERO;
				
				V = new BigInteger[n+1];
				
				for (int k=0;k<=n;k++){
					
					//((s + j + k) mod 2^g)
					byte[] val = ((seed1Int.add(BigInteger.valueOf(j)).add(BigInteger.valueOf(k))).mod(BigInteger.valueOf(2).pow(g))).toByteArray();
					//V_k = Hash((s + j + k) mod 2^g)
					V[k] = new SHA1Hash(val).getHash();
					
					//So long as k isn't n...
					//increment W by V_k * 2^(160*k)
					//Otherwise, increment W by (V_k mod 2^b) * 2^(160*k)
					//System.out.println(W.toByteArray().length*8);
					if (k != n){
						W = W.add((V[k].multiply(BigInteger.valueOf(2).pow(160*k))));
					}else{
						W = W.add(((V[k].mod(BigInteger.valueOf(2).pow(b))).multiply(BigInteger.valueOf(2).pow(160*k))));
					}
					//w.println(W.toByteArray().length*8);
				}
				//X = W + 2^(L-1)
				//System.out.println("W, after... "+W.toByteArray().length*8);
				X = W.add(BigInteger.valueOf(2).pow(L-1));
				//System.out.println(L);
				//System.out.println("X, after... "+X.toByteArray().length*8);
				//System.out.println("2^(L-1), after... "+BigInteger.valueOf(2).pow(L-1).toByteArray().length*8);


				
				//c = X mod 2q
				c = X.mod(q.multiply(BigInteger.valueOf(2)));
				p = X.subtract(c.subtract(BigInteger.ONE));
				
				if (p.compareTo(BigInteger.valueOf(2).pow(L-1)) >= 0){
					PrimeValidation pv2 = new PrimeValidation();
					int t = 5;
					if (p.isProbablePrime(10)){
					//if (pv2.probablyPrime(p, t)){
						boolean asd = p.compareTo(BigInteger.valueOf(2).pow(L-1))>0;
						boolean asd1 = p.compareTo(BigInteger.valueOf(2).pow(L))<0;
						w.println("p gr8r than 2^(L-1):" +asd);
						w.println("p less than 2^(L):" +asd1);
						w.println("p: "+p);
						
						w.println("p in binary: "+p.toString(2));
						byte[] asdf = p.toByteArray();
						int sadfasdf = asdf.length*8;
						w.println("p bit count: "+p.bitCount());
						w.println("p bit length: "+p.bitLength());
						
						w.println("q: "+q);
						w.println("q in binary: "+q.toString(2));
						byte[] asdf1 = q.toByteArray();
						int sadfasdf1 = asdf1.length*8;
						w.println("q bit count: "+q.bitCount());
						w.println("q bit length: "+q.bitLength());
						
						pProbPrime = true;
						w.close();
						break;
					}
				}
				i += 1;
				j += (n+1);
			}
		}
		
	}
	
	public byte[] randSeed(){
		
		Random rand = new SecureRandom();
		byte[] randSeed = new byte[20];
		rand.nextBytes(randSeed);
		
		return randSeed;
	}
}