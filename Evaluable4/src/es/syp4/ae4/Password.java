package es.syp4.ae4;

import java.io.Serializable;
import java.security.MessageDigest;
import java.util.Arrays;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;

public class Password implements Serializable{
	String textPass, encryptedPass;
	private static final long serialVersionUID = 1L;
	String key ="myKey";

	public Password(String text, String encrypted) {
		this.textPass=text;
		this.encryptedPass=encrypted;
		
	}


	public String getTextPass() {
		return textPass;
	}

	public String getKey() {
		return key;
	}

	public void setTextPass(String textPass) {
		this.textPass = textPass;
	}


	public String getEncryptedPass() {
		return encryptedPass;
	}


	public void setEncryptedPass(String encryptedPass) {
		this.encryptedPass = encryptedPass;
	}
	
	public void AsciiEnc(String textPass) {
		String encrypted="";
		int asciiValue, asciiNew;
		char convertToText;
		char[] chars = textPass.toCharArray();
		for (int i=0; i<chars.length;i++) {
			asciiValue= (int) chars[i];
			asciiNew = asciiValue+1;
			if (asciiNew <=31 || asciiNew == 127) {
				encrypted+="*";
			} else {
			convertToText = (char) asciiNew;
			encrypted+=convertToText;		
			}
		}
		
		setEncryptedPass(encrypted);
	}
	
	public void MD5Enc(String key, String textPass) {
		String encrypted="";
		try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] llavePassword = md5.digest(key.getBytes("utf-8"));
            byte[] BytesKey = Arrays.copyOf(llavePassword, 24);
            SecretKey keys = new SecretKeySpec(BytesKey, "DESede");
            Cipher cifrado = Cipher.getInstance("DESede");
            cifrado.init(Cipher.ENCRYPT_MODE, keys);
            byte[] plainTextBytes = textPass.getBytes("utf-8");
            byte[] buf = cifrado.doFinal(plainTextBytes);
            byte[] base64Bytes = Base64.encodeBase64(buf);
            encrypted = new String(base64Bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
		
		setEncryptedPass(encrypted);
	}

}
