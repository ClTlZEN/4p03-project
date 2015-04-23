package DiffieHellman;

import java.math.BigInteger;
import java.util.Random;

public class PrimeValidation {

	boolean prime;	
	
	public PrimeValidation(){
		prime = false;		
		System.out.println("hi");
		System.out.println("Testing for primes 1, 2, 3");
		System.out.println("mine: " + probablyPrime(BigInteger.ONE, 1));
		System.out.println("BigInteger's: " + BigInteger.ONE.isProbablePrime(1));
		System.out.println("----------------------------");
		System.out.println("mine for 2: " + probablyPrime(BigInteger.valueOf(2), 1));
		System.out.println("BigInteger's: " + BigInteger.valueOf(2).isProbablePrime(1));
		System.out.println("----------------------------");
		System.out.println("mine for 3: " + probablyPrime(BigInteger.valueOf(3), 1));
		System.out.println("BigInteger's: " + BigInteger.valueOf(3).isProbablePrime(1));
		System.out.println("----------------------------");
		System.out.println("mine for 5: " + probablyPrime(BigInteger.valueOf(5), 1));
		System.out.println("BigInteger's: " + BigInteger.valueOf(5).isProbablePrime(1));
		
	}
	
	/**p is the prime that we're testing for validity. t is the margin of accuracy.
	 * First checks for the trivial cases. If not trivially prime, then uses the
	 * square roots of unity lemma & Fermat's little theorem to find a congruence
	 * of 1 (mod p) in the form 2^a*d.
	 * Returns false if congruent, true if probably prime.**/
	public boolean probablyPrime(BigInteger p, int t){
		//checks for trivial cases
		if (p.intValue()==2 || p.intValue()==3){
			return true;
		}
		if (p.mod(BigInteger.valueOf(2)) == BigInteger.ZERO || p.intValue() < 2){
			return false;
		}

		 
		//find a congruence of p-1, in the form 2^a*d
		BigInteger a = BigInteger.ZERO;
		BigInteger d = p.subtract(BigInteger.ONE);
		while (d.mod(BigInteger.valueOf(2)) == BigInteger.ZERO){
			d = d.divide(BigInteger.valueOf(2));
			a.add(BigInteger.ONE);
		}
		
		Random rnd = new Random();
		
		for (int i=0 ; i<t ; i++){
			BigInteger b;
			do {
				b  = new BigInteger(4, rnd);
				System.out.println("b: "+b);
				System.out.println("p-2: "+ p.subtract(BigInteger.valueOf(2)));

			} while (b.compareTo(p.subtract(BigInteger.valueOf(2))) >= 0 // must be smaller than n-2 (0=equal, 1=GT)
					|| b.compareTo(BigInteger.valueOf(2)) <= 0);	//must be larger than 2 (0=equal -1=LT)
			
			//skip this part if b==1 or b==p-1
			if (b.compareTo(BigInteger.ONE) != 0 || b.compareTo(p.subtract(BigInteger.ONE)) != 0){
				for (BigInteger j=a.subtract(BigInteger.ONE) ; j.compareTo(BigInteger.ZERO) <= 0 ; j.subtract(BigInteger.ONE)){
					b = b.modPow(BigInteger.valueOf(2), p);
					if (b.compareTo(BigInteger.ONE) == 0){
						return false; //composite
					}
					if (b.compareTo(p.subtract(BigInteger.ONE)) == 0){
						break;	//break the current loop & continue to next loop iteration up to t
					}
				}
				if (b.compareTo(p.subtract(BigInteger.ONE)) != 0)
					return false;	//composite if b is never equal to p-1
			}
		}
		return true;
	}
	
}
