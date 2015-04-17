package DiffieHellman;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHA1Hash{
	
	public byte[] SHA1Hash(byte[] seed) throws NoSuchAlgorithmException{
		
		MessageDigest m;
		m = MessageDigest.getInstance("SHA-1");
		m.reset();
		return m.digest(seed);
		
	}
}