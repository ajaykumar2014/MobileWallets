import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class Test {
	
	public static void main(String args[]) throws NoSuchAlgorithmException{
		SecureRandom secureRandomGenerator = SecureRandom.getInstance("SHA1PRNG");
		for(int i=0;i<10;i++){
			
			byte[] randomBytes = new byte[128];
			secureRandomGenerator.nextBytes(randomBytes);

			// Create two secure number generators with the same seed
			int seedByteCount = 5;
			byte[] seed = secureRandomGenerator.generateSeed(seedByteCount);

			/*SecureRandom secureRandom1 = SecureRandom.getInstance("SHA1PRNG");
			secureRandom1.setSeed(seed);
			SecureRandom secureRandom2 = SecureRandom.getInstance("SHA1PRNG");
			secureRandom2.setSeed(seed);*/
			System.out.println(new String(seed).toCharArray());
			
			
		}
	}

}
