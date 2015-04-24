package DiffieHellman;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

public class KeyExchange {

	public static void main(String[] args) throws NoSuchAlgorithmException, FileNotFoundException, UnsupportedEncodingException {

		PrimeNumbers primes = new PrimeNumbers(4);
		primes.nistGetPrimes();
		PrimeValidation validator = new PrimeValidation();
		Circle peeps = new Circle(2); 
		
		
	}

}
