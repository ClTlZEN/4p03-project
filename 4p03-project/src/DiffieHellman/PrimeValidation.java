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
		System.out.println("mine: " + probablyPrime(a, 50));
		System.out.println("BigInteger's: " + a.isProbablePrime(3));
		System.out.println("----------------------------");
		System.out.println("a2: "+a2);
		System.out.println("mine for b: " + probablyPrime(a2, 50));
		System.out.println("BigInteger's: " + a2.isProbablePrime(3));
		System.out.println("----------------------------");
		System.out.println("a3: "+a3);
		System.out.println("mine for c: " + probablyPrime(a3, 50));
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
			return true;
		}
		if (p.mod(BigInteger.valueOf(2)) == BigInteger.ZERO || p.intValue() < 2){
			return false;
		}

		 
		
		
		Random rnd = new Random();
		
		
		for (int i=0 ; i<t ; i++){
			BigInteger b;
			//pick a random integer between 2 and n-2 inclusive
			do {
				b  = new BigInteger(p.bitLength(), rnd);
				System.out.println("b: "+b);
				System.out.println("p-2: "+ p.subtract(BigInteger.valueOf(2)));

			} while (b.compareTo(p.subtract(BigInteger.valueOf(2))) >= 0 // must be smaller than n-2 (0=equal, 1=GT)
					|| b.compareTo(BigInteger.valueOf(2)) <= 0);	//must be larger than 2 (0=equal -1=LT)
			
			//System.out.println("Found a b between 2 and p-2: " + (b.compareTo(p.subtract(BigInteger.valueOf(2))) <= 0 // must be smaller than n-2 (0=equal, 1=GT)
			//		|| b.compareTo(BigInteger.valueOf(2)) >= 0));
			
			
			//find a congruence of p-1, in the form 2^a*d
			BigInteger a = BigInteger.ZERO;
			BigInteger d = p.subtract(BigInteger.ONE).divide(BigInteger.valueOf(2));
			while (d.mod(BigInteger.valueOf(2)) == BigInteger.ZERO){
				d = d.divide(BigInteger.valueOf(2));
				a.add(BigInteger.ONE);
			}
			
			
			BigInteger c = b.modPow(d, p);
			for (int j=0 ; j < a.intValue() ; j ++){
				BigInteger newC = c.multiply(c).mod(p);
				if (newC.equals(BigInteger.ONE) && !c.equals(BigInteger.ONE) && !c.equals(p.subtract(BigInteger.ONE))){
					return false; //definitely a composite
				}
				if (!c.equals(BigInteger.ONE)) return false;
				return true;
			}
/*			
			//skip this part if b==1 or b==p-1
			if (b.compareTo(BigInteger.ONE) != 0 || b.compareTo(p.subtract(BigInteger.ONE)) != 0){
				for (BigInteger j=a.subtract(BigInteger.ONE) ; j.compareTo(BigInteger.ZERO) <= 0 ; j.subtract(BigInteger.ONE)){
					b = b.modPow(BigInteger.valueOf(2), p);
					if (b.compareTo(BigInteger.ONE) == 0){
						System.out.println("composite, returns");
						return false; //composite
					}
					if (b.compareTo(p.subtract(BigInteger.ONE)) == 0){
						System.out.println("Break and loop again");
						break;	//break the current loop & continue to next loop iteration up to t
					}
				}
				if (b.compareTo(p.subtract(BigInteger.ONE)) != 0)
					System.out.println("composite, returns");
					return false;	//composite if b is never equal to p-1
			}*/
		}
		System.out.println("prime, end of method");
		return true;
	}
	
}
