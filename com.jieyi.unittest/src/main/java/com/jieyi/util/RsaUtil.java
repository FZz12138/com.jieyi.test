package com.jieyi.util;

import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

import org.apache.commons.codec.binary.Hex;

public class RsaUtil {
	private static String RSA_KEY_TYPE = "RSA";

	/**
	 * 产生公私密钥对
	 * 
	 * @param initLen
	 *            密钥长度，范围：512～2048
	 * @return
	 * @throws Exception
	 */
	public static KeyPair generateKey(int initLen) throws Exception {
		KeyPairGenerator keyGen = KeyPairGenerator.getInstance(RSA_KEY_TYPE);
		keyGen.initialize(initLen);
		KeyPair keyPair = keyGen.generateKeyPair();
		return keyPair;
	}

	/**
	 * 装载公钥
	 * 
	 * @param modulus
	 * @param exponent
	 * @param radix
	 *            10 或则 16进制
	 * @return
	 * @throws Exception
	 */
	public static RSAPublicKey loadPublicKey(String modulus, String exponent, int radix) throws Exception {
		BigInteger mod = new BigInteger(modulus, radix);
		BigInteger exp = new BigInteger(exponent, radix);
		RSAPublicKeySpec pubKeySpec = new RSAPublicKeySpec(mod, exp);
		KeyFactory keyFac = KeyFactory.getInstance(RSA_KEY_TYPE);
		return (RSAPublicKey) keyFac.generatePublic(pubKeySpec);
	}

	/**
	 * 装载私钥
	 * 
	 * @param modulus
	 * @param exponent
	 * @param radix
	 *            10 或则 16进制
	 * @return
	 * @throws Exception
	 */
	public static RSAPrivateKey loadPrivateKey(String modulus, String exponent, int radix) throws Exception {
		BigInteger mod = new BigInteger(modulus, radix);
		BigInteger exp = new BigInteger(exponent, radix);
		RSAPrivateKeySpec priKeySpec = new RSAPrivateKeySpec(mod, exp);
		KeyFactory keyFac = KeyFactory.getInstance(RSA_KEY_TYPE);
		return (RSAPrivateKey) keyFac.generatePrivate(priKeySpec);
	}

	/**
	 * 公钥加密
	 */
	public static byte[] publicKeyEncrypt(String algor, String mode, String padding, RSAPublicKey rsaPublicKey,
			byte[] plainText) throws Exception {
		if (algor == null || algor.equals("")) {
			algor = "RSA";
		}
		if (mode == null || mode.equals("")) {
			mode = "ECB";
		}
		if (padding == null || padding.equals("")) {
			padding = "PKCS1Padding";
		}
		String cipherAlgor = algor + "/" + mode + "/" + padding;
		Cipher cipher = Cipher.getInstance(cipherAlgor);
		cipher.init(Cipher.ENCRYPT_MODE, rsaPublicKey);
		byte[] cipherText = cipher.doFinal(plainText);
		return cipherText;
	}

	/**
	 * 私钥解密
	 */
	public static byte[] privateKeyDecrypt(String algor, String mode, String padding, RSAPrivateKey rsaPrivateKey,
			byte[] cipherText) throws Exception {
		if (algor == null || algor.equals("")) {
			algor = "RSA";
		}
		if (mode == null || mode.equals("")) {
			mode = "ECB";
		}
		if (padding == null || padding.equals("")) {
			padding = "PKCS1Padding";
		}
		String cipherAlgor = algor + "/" + mode + "/" + padding;
		Cipher cipher = Cipher.getInstance(cipherAlgor);
		cipher.init(Cipher.DECRYPT_MODE, rsaPrivateKey);
		byte[] plainText = cipher.doFinal(cipherText);
		return plainText;
	}

	/**
	 * 私钥加密
	 */
	public static byte[] privateKeyEncrypt(String algor, String mode, String padding, RSAPrivateKey rsaPrivateKey,
			byte[] cipherText) throws Exception {
		if (algor == null || algor.equals("")) {
			algor = "RSA";
		}
		if (mode == null || mode.equals("")) {
			mode = "ECB";
		}
		if (padding == null || padding.equals("")) {
			padding = "PKCS1Padding";// PKCS1Padding
		}
		String cipherAlgor = algor + "/" + mode + "/" + padding;
		Cipher cipher = Cipher.getInstance(cipherAlgor);
		cipher.init(Cipher.ENCRYPT_MODE, rsaPrivateKey);
		byte[] plainText = cipher.doFinal(cipherText);
		return plainText;
	}

	public static String sign(String data, PrivateKey privateKey, String charset) throws Exception {
		byte[] keyBytes = privateKey.getEncoded();
		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		PrivateKey key = keyFactory.generatePrivate(keySpec);
		Signature signature = Signature.getInstance("SHA1withRSA");
		signature.initSign(key);
		signature.update(data.getBytes(charset));
		return Hex.encodeHexString(signature.sign());
	}

	public static boolean verify(String srcData, PublicKey publicKey, String sign, String charset) throws Exception {
		byte[] keyBytes = publicKey.getEncoded();
		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		PublicKey key = keyFactory.generatePublic(keySpec);
		Signature signature = Signature.getInstance("SHA1withRSA");
		signature.initVerify(key);
		signature.update(srcData.getBytes(charset));
		return signature.verify(Hex.decodeHex(sign.toCharArray()));
	}

	public static void main(String[] args) throws Exception {
		KeyPair keys = generateKey(1024);
		RSAPublicKey pub = (RSAPublicKey) keys.getPublic();
		RSAPrivateKey prv = (RSAPrivateKey) keys.getPrivate();
		System.out.println(Hex.encodeHexString(pub.getModulus().toByteArray()));
		System.out.println(Hex.encodeHexString(pub.getPublicExponent().toByteArray()));
		System.out.println(Hex.encodeHexString(prv.getModulus().toByteArray()));
		System.out.println(Hex.encodeHexString(prv.getPrivateExponent().toByteArray()));
		
		String rsaMud = "00e8eebc392535f86e5ab5754087bb19a8ea0255ddb293003a7f9ef1b4c3f88e82a3c733d0039c8dd3fdacba587017bf68507b22f6113564c6c7041c3363b631754c803fa2b919a74daeb1627f77400e8609105f4cf8227b9b5f5f9e468a010553bb7b9a56ca66f01ac8d96efab2746835933c2b95d9fd88aad03cc9f6e382f1f5";
		String rsaPrv = "2fccf6315ff28065dd1589d751435313b7d9fcd6ff136da3939701910ffa4cfc80319bef5f1e5c7899813dc872505d4641e5d319de142ff71ed638174faa0ee1d82f18b210676f3337f6c585679afc1f93da784280907512648de3735c46de2512724d2e9908eb6e4d831f6745522ceaa4c1e2149a25475a53d57b4f143b2b61";
		String signData = "batchno=000000&cardinfo={\"cardsaledate\":\"20190417\",\"issueinst\":\"02273610\",\"carddeposit\":\"2000\",\"cardvaldate\":\"20180930\",\"cardbal\":\"00097919\",\"cardtype\":\"0201\",\"salemode\":\"00\",\"cardcnt\":\"004\",\"cardno\":\"3104990200020000084\",\"cardproduct\":\"003\"}&data={\"random\":\"E66E744A\",\"mac1\":\"44173728\"}&instid=23000001&instseq=000330&mchntid=000000010000002&netid=100000010000002&oprid=0000000000000000&termid=00001310&termseq=000006726&timestamp=20190702143330&transtype=02&txnamt=100&txncode=1162";
		RSAPrivateKey privateKey = RsaUtil.loadPrivateKey(rsaMud, rsaPrv, 16);
		String sign = RsaUtil.sign(signData, privateKey, "UTF-8");
		System.out.println(sign);
	

	}

}
