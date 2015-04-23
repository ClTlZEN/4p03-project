package DiffieHellman;

import java.math.BigInteger;
import java.util.Random;

public class PrimeValidation {

	boolean prime;	
	
	public PrimeValidation(){
		prime = false;		
		
		Random rnd = new Random();
		
		BigInteger a = new BigInteger(160, 2, rnd);
		BigInteger a2 = new BigInteger(160, 2, rnd);
		BigInteger a3 = new BigInteger(160, 2, rnd);
		
		System.out.println("hi");
		System.out.println("Testing for primes a, b, c");
		System.out.println("a: "+a);
		System.out.println("mine: " + probablyPrime(a, 200));
		System.out.println("BigInteger's: " + a.isProbablePrime(3));
		System.out.println("----------------------------");
		System.out.println("a2: "+a2);
		System.out.println("mine for b: " + probablyPrime(a2, 200));
		System.out.println("BigInteger's: " + a2.isProbablePrime(3));
		System.out.println("----------------------------");
		System.out.println("a3: "+a3);
		System.out.println("mine for c: " + probablyPrime(a3, 200));
		System.out.println("BigInteger's: " + a3.isProbablePrime(3));
		/*System.out.println("----------------------------");
		System.out.println("mine for 5: " + probablyPrime(BigInteger.valueOf(5), 1));
		System.out.println("BigInteger's: " + BigInteger.valueOf(5).isProbablePrime(1));*/
		
	}
	
	/**p is the prime that we're testing for validity. t is the margin of accuracy.
	 * First checks for the trivial cases. If not trivially prime, then uses the
	 * square roots of unity lemma & Fermat's little theorem to find a congruence
	 * of 1 (mod p) in the form 2^a*d.
	 * Returns false if congruent, true if probably prime.**/
	public boolean probablyPrime(BigInteger p, int t){
		
		//System.out.println("BigInteger's: " + p.isProbablePrime(2));
		
		//checks for trivial cases
		if (p.intValue()==2 || p.intValue()==3){
			System.out.println("Quitting too early...prime?");
			return true;
		}
		if (p.mod(BigInteger.valueOf(2)) == BigInteger.ZERO || p.intValue() ==1 || p.intValue() ==0){
			System.out.println("Quitting too early...composite?");
			return false;
		}

		 
		
		
		Random rnd = new Random();
		
		
		for (int i=0 ; i<t ; i++){
			BigInteger b;
			//pick a random integer between 2 and n-2 inclusive
			do {
				b  = new BigInteger(p.bitLength(), rnd);
				//System.out.println("b: "+b);
				//System.out.println("p-2: "+ p.subtract(BigInteger.valueOf(2)));

			} while (b.compareTo(p.subtract(BigInteger.valueOf(1))) >= 0 // must be smaller than n-2 (0=equal, 1=GT)
					|| b.compareTo(BigInteger.valueOf(1)) <= 0);	//must be larger than 2 (0=equal -1=LT)

			//find a congruence of p-1, in the form 2^a*d
			BigInteger a = BigInteger.ONE;
			BigInteger d = p.subtract(BigInteger.ONE).divide(BigInteger.valueOf(2));
			while (d.mod(BigInteger.valueOf(2)) == BigInteger.ZERO){
				d = d.divide(BigInteger.valueOf(2));
				a.add(BigInteger.ONE);
			}
			
			BigInteger c = b.modPow(d, p);
			for (int j=0 ; j < a.intValue() ; j ++){
				BigInteger newC = c.multiply(c).mod(p);
				if (newC.equals(BigInteger.ONE) && !c.equals(BigInteger.ONE) && !c.equals(p.subtract(BigInteger.ONE))){
					System.out.println("Defs a composite");
					return false; //definitely a composite
				}
				c = newC;
			}
			//System.out.println("Probs a prime");
			//return c.equals(BigInteger.ONE);
		}
		System.out.println("Probs a prime");
		return true;
	}
	
}
