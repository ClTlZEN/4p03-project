package DiffieHellman;

import java.security.NoSuchAlgorithmException;

public class KeyExchange {

	public static void main(String[] args) throws NoSuchAlgorithmException {
		// TODO Auto-generated method stub
		PrimeNumbers primes = new PrimeNumbers(2);
		primes.nistGetPrimes();
	}

}
