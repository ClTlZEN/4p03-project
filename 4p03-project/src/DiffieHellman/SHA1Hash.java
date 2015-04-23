package DiffieHellman;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHA1Hash{
	
	private BigInteger hash;
	
	public SHA1Hash(byte[] seed) throws NoSuchAlgorithmException{
		
		MessageDigest m;
		m = MessageDigest.getInstance("SHA-1");
		m.reset();
		hash = new BigInteger(m.digest(seed));
		
	}
	public BigInteger getHash(){
		return hash;
	}
}