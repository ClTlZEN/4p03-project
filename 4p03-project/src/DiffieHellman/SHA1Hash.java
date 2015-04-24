package DiffieHellman;

import java.math.BigInteger;
import java.nio.ByteBuffer;
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
	public SHA1Hash(int ml){
		byte[] msg = new byte[64];
		
		int h0 = 0x67452301;
		int h1 = 0xEFCDAB89;
		int h2 = 0x98BADCFE;
		int h3 = 0x10325476;
		int h4 = 0xC3D2E1F0;
		
		Long bl = new Long(ml);
		
		//Making sure first bit is 1 (10000000)
		msg[0] = (byte) 0x80;
		
		//Adding 0's until it is of length 448
		for (int i =1;i<=55;i++){
			msg[i] = 0x00;
		}
		
		//Turn the 64-bit long value of the message length into a byte array
		byte[] blArr = ByteBuffer.allocate(8).putLong(bl.longValue()).array();
		int start = 56; 
		for (int i = 0; i < blArr.length; i ++) {
		   msg[start + i] = blArr[i];
		}
		
		//Test print
		/*System.out.println(new BigInteger(msg).toString(2));
		for (byte b : msg) {
		    System.out.println(Integer.toBinaryString(b & 255 | 256).substring(1));
		}*/
		
		//Break the 512 bit chunk into 16 32-bit chunks
		int[] words = new int[80];
		int count = 0;
		int chunk = 0;
		for (int j=0;j<msg.length;j+=4){
			//Convert the next 4 8-bit bytes into a 32-bit integer, and add it to the array
			byte[] bytes = new byte[]{msg[j],msg[j+1],msg[j+2],msg[j+3]};
			chunk = ByteBuffer.wrap(bytes).getInt();
			words[count] = chunk;
			count += 1;
		}
		
		//Extend 16 32-bit words into 80 32-bit words
		for (int i=16;i<80;i++){
			words[i] = Integer.rotateLeft((words[i-3] ^ words[i-8] ^ words[i-14] ^ words[i-16]), 1); 
		}
		int a = h0;
		int b = h1;
		int c = h2;
		int d = h3;
		int e = h4;
		int f, k;
		f = k = 0;
		
		for (int i=0;i<80;i++){
			if ((0 <= i) && (i <= 19)){
				f = (b & c) | ((~b)&d);
				k = 0x5A827999;
			}else if((20 <= i) && (i <= 39)){
				f = b ^ c ^ d;
			    k = 0x6ED9EBA1;
			}else if((40 <= i) && (i <= 59)){
				f = (b & c) | (b & d) | (c & d); 
			    k = 0x8F1BBCDC;
			}else if((60 <= i) && (i <= 79)){
				f = b ^ c ^ d;
				k = 0xCA62C1D6;
			}
			int temp = rotate16(a, 5) + f + e + k + words[i];
			e = d;
			d = c;
			c = rotate16(b,30);
			b = a;
			a = temp;
		}
		h0 += a;
		h1 += b;
		h2 += c;
		h3 += d;
		h4 += e;
		
		BigInteger finalHash = BigInteger.valueOf((h0 << 128) | (h1 << 96) | (h2 << 64) | (h3 << 32) | h4);
		hash = finalHash;
		
	}
	public static int rotate16(int data, int distance) {
	     distance &= 15;
	     data &= 0xFFFF;
	     return (data >> distance) | (data << (16 - distance));
	}
	
	public BigInteger getHash(){
		return hash;
	}
}