import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.SignatureException;

/* 
	Note that this demonstrates the process of both signing data with a private key and then verifing that data with the public key (an example of authentication)
	Normally, the key generation, signing, and verification would be done between the buyer and seller in order to verify the seller's identity (in separate file locations)
	However, for simplification, this file does key generation, signing, & verification all in one file (as you are working with fake people)
 */
 
public class DigitalSignature {
	
	// returns a boolean representing the signing and verification of our transaction using private/public key pairs
	public static boolean verifySignature(String tran) {
		try {
			
			//generating key pair
			KeyPairGenerator kpGen = KeyPairGenerator.getInstance("DSA","SUN");
			SecureRandom sRandom = SecureRandom.getInstance("SHA1PRNG", "SUN");
			kpGen.initialize(1024, sRandom);
			
			KeyPair pair = kpGen.generateKeyPair();
			//grabbing the private & public keys
			PrivateKey priv = pair.getPrivate();
			PublicKey pub = pair.getPublic();
			
			//sign the data witht he private key
			Signature dsa = Signature.getInstance("SHA1withDSA", "SUN");
			dsa.initSign(priv);
			
			//convert transaction String to Bytes
			byte[] bytes = tran.getBytes();
			dsa.update(bytes);
			
			//obtain the real signature
			byte[] realSig = dsa.sign();
			
			//verify the signature
			dsa.initVerify(pub);
			dsa.update(bytes);
			
			return dsa.verify(realSig);
		}
		catch(NoSuchAlgorithmException | NoSuchProviderException ex) {
			ex.printStackTrace();
		}
		catch(InvalidKeyException ex) {
			ex.printStackTrace();
		}
		catch(SignatureException ex) {
			ex.printStackTrace();
		}
		return false;
	}
	
	
}
