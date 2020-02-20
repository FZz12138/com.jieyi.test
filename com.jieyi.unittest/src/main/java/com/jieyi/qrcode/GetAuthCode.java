package com.jieyi.qrcode;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bouncycastle.crypto.AsymmetricCipherKeyPair;

import com.google.gson.Gson;
import com.jieyi.util.Consts;
import com.jieyi.util.HttpClientUtil;

import jieyi.tools.algorithmic.RSAUtil;
import jieyi.tools.algorithmic.sm.SM2;
import jieyi.tools.util.DateUtil;
import jieyi.tools.util.StringUtil;

public class GetAuthCode {

	public static void main(String[] args) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("joininstid", "20000002");
		map.put("joininstssn", StringUtil.getRandomStringAccordingSystemtimeForNumberFlag(20, 0));
		map.put("reqdate", DateUtil.getSystemDateTime("yyyyMMdd"));
		map.put("reqtime", DateUtil.getSystemDateTime("HHmmss"));
		
//		map.put("joininstid", "20000002");
//		map.put("joininstssn", "20190102198");
//		map.put("reqdate", DateUtil.getSystemDateTime("20190102"));
//		map.put("reqtime", DateUtil.getSystemDateTime("173058"));

		// 开始签名
		Gson gson = new Gson();
		String modulus = "A5A97E718FB1192D34A3684298075A5248DA0D903C51AAA3C99DA1531D6719FFDA2DFA5E382834E26E714C8466F162BACAA6EA224EE4A02DB36264FD92DF0E73A96CD24C10EC1BF935AF65EF4D71531B2650F7EE1CCEBB43F26BFD1B91F5FEE99A139F22D62155E49AA9E25A8DDE046063E79F5C9FC8C48D299E400FAAF8A7F9";
		//String exponent = "112B4331696A8E8E2091FB21D8BAF7E7AAFE998FCC2ECFE57E32F6A3ECAB1B7ECE47BDA4F734BEF4E497406E4437A91E1BA6AE68DCBF5190D37A01B976053D3BF54320F1776180B2DA350C4AD27E27185C2ED5453FBBD3406DCEAC9F5A898B0021567903797696F8405AB6241BDC03B5CF534E244D5C91C1CC436A3916BF79A1";
		RSAPublicKey publicKey = RSAUtil.loadPublicKey(modulus, "10001", 16);
		//RSAPrivateKey privateKey = RSAUtil.loadPrivateKey(modulus, exponent, 16);

//		// 2、公钥加密，后台使用私钥解密验签
		List<Map.Entry<String, Object>> mappingList = null;
		mappingList = new ArrayList<Map.Entry<String, Object>>(map.entrySet());
		Collections.sort(mappingList, new Comparator<Map.Entry<String, Object>>() {
			public int compare(Map.Entry<String, Object> mapping1, Map.Entry<String, Object> mapping2) {
				return mapping1.getKey().compareTo(mapping2.getKey());
			}
		});

		String strForSign = "";
		for (Map.Entry<String, Object> m : mappingList) {
			// System.out.println(m.getKey()+"|"+m.getValue());
			strForSign += m.getKey() + m.getValue();
		}
		System.out.println(strForSign);
		byte[] cipherText = RSAUtil.publicKeyEncrypt("RSA", "ECB", "PKCS1Padding", publicKey,
				strForSign.getBytes("GBK"));
		map.put("sign", StringUtil.bytesToHexString(cipherText).toUpperCase());// 签名结果

		// 2、公钥加密，后台使用私钥解密验签
//		List<Map.Entry<String, Object>> mappingList = null;
//		mappingList = new ArrayList<Map.Entry<String, Object>>(map.entrySet());
//		Collections.sort(mappingList, new Comparator<Map.Entry<String, Object>>() {
//			public int compare(Map.Entry<String, Object> mapping1, Map.Entry<String, Object> mapping2) {
//				return mapping1.getKey().compareTo(mapping2.getKey());
//			}
//		});
//		
//		String strForSign = "";
//		for (Map.Entry<String, Object> m : mappingList) {
//			// System.out.println(m.getKey()+"|"+m.getValue());
//			strForSign += m.getKey() + m.getValue();
//		}
//
//		// 开始签名
//		Gson gson = new Gson();
//		SM2 sm2 = new SM2();
//		//AsymmetricCipherKeyPair keyPair = sm2.generateKeyPair(StringUtil.hexStringToBytes("00A06EF28AA2F3AB21CA50E7DFE37C155F06F6645619E9D20DB35C9DBFA17124C3"),StringUtil.hexStringToBytes("02C193406C1D372BFD3A520006213FF8634CB6020FC2BC825A7EA21765A9F1A497"));
//		AsymmetricCipherKeyPair keyPair = sm2.generateKeyPair(StringUtil.hexStringToBytes("00D2FD01034C93995C875AA6B5603AF2F6D36D99F021F7614A9F0800F0426D34E2"),StringUtil.hexStringToBytes("03D3B4A3C7F0DDA25EE8BABFF66C2AEABDC5E34E82CFA1C98606610F540D351968"));
//		byte[] sign = sm2.sign(strForSign.getBytes("GBK"),keyPair);
//		
//		
//		map.put("sign", StringUtil.bytesToHexString(sign).toUpperCase());// 签名结果
		// 真正的业务数据
		Map<String, Object> dataMap = new HashMap<String, Object>();
		//dataMap.put("memopenid", "201810151019489087K3G012U82R55H8");// 根据memopenid来查询
		dataMap.put("memopenid", "124104540306802665200000");// 根据memopenid来查询
		// 把数据放进去
		//map.put("data", dataMap);// 根据memopenid来查询

		// 请求
		System.out.println("gson.toJson(map):" + gson.toJson(map));
		long start = System.currentTimeMillis();
//		String strRet = HttpClientUtil.httpPostJson(Consts.URL_HEADER45001 + "/qrcode/qrcodepay/getAuthCode/V1",
//				gson.toJson(map));
		String strRet = HttpClientUtil.httpPostJson("http://58.209.250.8:45001" + "/qrcode/qrcodepay/getAuthCode/V1",
				gson.toJson(map));
		
//		String strRet = HttpClientUtil.httpPostJson("http://58.209.250.8:45001"  + "/qrcode/qrcodepay/getAuthCode/V1",
//		String strRet = HttpClientUtil.httpPostJson(Consts.URL_HEADER45001 + "/qrcode/qrcodepay/getAuthCode/V1",
//				gson.toJson(map));
//		String strRet = HttpClientUtil.httpPostJson("http://58.209.250.8:45001"  + "/qrcode/qrcodepay/getAuthCode/V1",
//				gson.toJson(map));
		System.out.println("strRet:" + strRet);
		long end = System.currentTimeMillis();
		System.out.println("cost:" + (end-start)+"ms");
	}

}
