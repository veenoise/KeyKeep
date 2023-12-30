import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;


public class PasswordBasedEncryptionDecryption {

	private int T_LEN = 128;

	private int KEY_SIZE = 256;

	private int intSaltLen = 16;

	private int IV_LEN = 12;

	public byte[] generateSalt() {
		SecureRandom sRand = new SecureRandom();
		byte[] byteArrSalt = new byte[this.intSaltLen];
		sRand.nextBytes(byteArrSalt);
		return byteArrSalt;
	}

	public SecretKey generateKeySha256(String strMasterPassword, byte[] byteArrSalt) {
		try {
			KeySpec kSpec = new PBEKeySpec(strMasterPassword.toCharArray(), byteArrSalt, 600000, KEY_SIZE);
			SecretKeyFactory skFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
			SecretKey sKey = new SecretKeySpec(skFactory.generateSecret(kSpec).getEncoded(), "AES");
			return sKey;
		} catch (Exception e) {
			System.err.println(e + ": There was an error in the generateKeySha256 method.");
		}

		return null;
	}

	public SecretKey generateKeySha512(String strMasterPassword, byte[] byteArrSalt) {
		try {
			KeySpec kSpec = new PBEKeySpec(strMasterPassword.toCharArray(), byteArrSalt, 600000, 512);
			SecretKeyFactory skFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
			SecretKey sKey = new SecretKeySpec(skFactory.generateSecret(kSpec).getEncoded(), "AES");
			return sKey;
		} catch (Exception e) {
			System.err.println(e + ": There was an error in the generateKeySha512 method.");
		}

		return null;
	}

	public byte[] generateRandomIV() {
		byte[] nonce = new byte[this.IV_LEN];
		new SecureRandom().nextBytes(nonce);
		return nonce;
	}

	public String encrypt(String strMessage, byte[] byteArrIv, SecretKey sKey) {
		try {
			byte[] byteArrMessage = strMessage.getBytes("UTF-8");
			Cipher encryptCipher = Cipher.getInstance("AES/GCM/NoPadding");
			encryptCipher.init(Cipher.ENCRYPT_MODE, sKey, new GCMParameterSpec(T_LEN, byteArrIv));
			byte[] byteArrEncrypted = encryptCipher.doFinal(byteArrMessage);
			return encode(byteArrEncrypted);
		} catch (Exception e) {
			System.err.println(e + ": There was an error in the encrypt method.");
		}

		return null;
	}

	public String decrypt(String strMessage, byte[] byteArrIv, SecretKey sKey) {
		try {
			byte[] byteArrMessage = decode(strMessage);
			Cipher decryptCipher = Cipher.getInstance("AES/GCM/NoPadding");
			decryptCipher.init(Cipher.DECRYPT_MODE, sKey, new GCMParameterSpec(T_LEN, byteArrIv));
			byte[] byteArrDecrypted = decryptCipher.doFinal(byteArrMessage);
			return new String(byteArrDecrypted);
		} catch (Exception e) {
			System.err.println(e + ": There was an error in the decrypt method.");
		}

		return null;
	}

	protected String encode(byte[] byteArrData) {
		return Base64.getEncoder().encodeToString(byteArrData);
	}

	protected byte[] decode(String strData) {
		try {
			return Base64.getDecoder().decode(strData.getBytes());
		} catch (Exception e) {
			System.err.println(e + ": There was an error in the decode method.");
		}

		return null;
	}
}