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
import org.bouncycastle.util.encoders.Base64;

import com.google.gson.Gson;
import com.jieyi.util.Consts;
import com.jieyi.util.HttpClientUtil;

import jieyi.tools.algorithmic.RSAUtil;
import jieyi.tools.algorithmic.sm.SM2;
import jieyi.tools.util.DateUtil;
import jieyi.tools.util.StringUtil;

public class getQrcode {

	public static void main(String[] args) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("joininstid", "20000001");
		map.put("joininstssn", StringUtil.getRandomStringAccordingSystemtimeForNumberFlag(20, 0));
		map.put("reqdate", DateUtil.getSystemDateTime("yyyyMMdd"));
		map.put("reqtime", DateUtil.getSystemDateTime("HHmmss"));

	
		
//		// 开始签名
//		Gson gson = new Gson();
//		String modulus = "A48D3AA7253648A7B056188D8289F1DF50208451FAB4E713EBA6A13D98C8F8F55BF7C2C73380F1293D541FA7856CFE7E6B4065370087E93E42BED4072DB34CA621B01D901D86A6DE6006B3F58DC6C758D6923AE6795D7920B2C0A7727CEE037AE7F3C1301A7045ED2195ECCF031B75EC696EE1BC9F14DEC88656873B0FBC9437";
//		String exponent = "112B4331696A8E8E2091FB21D8BAF7E7AAFE998FCC2ECFE57E32F6A3ECAB1B7ECE47BDA4F734BEF4E497406E4437A91E1BA6AE68DCBF5190D37A01B976053D3BF54320F1776180B2DA350C4AD27E27185C2ED5453FBBD3406DCEAC9F5A898B0021567903797696F8405AB6241BDC03B5CF534E244D5C91C1CC436A3916BF79A1";
//		RSAPublicKey publicKey = RSAUtil.loadPublicKey(modulus, "10001", 16);
//		RSAPrivateKey privateKey = RSAUtil.loadPrivateKey(modulus, exponent, 16);
//
//		// 2、公钥加密，后台使用私钥解密验签
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
//		System.out.println(strForSign);
//		byte[] cipherText = RSAUtil.publicKeyEncrypt("RSA", "ECB", "PKCS1Padding", publicKey,
//				strForSign.getBytes("GBK"));
//		map.put("sign", StringUtil.bytesToHexString(cipherText).toUpperCase());// 签名结果

		// 2、公钥加密，后台使用私钥解密验签
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

		// 开始签名
		Gson gson = new Gson();
		SM2 sm2 = new SM2();
		AsymmetricCipherKeyPair keyPair = sm2.generateKeyPair(StringUtil.hexStringToBytes("00A06EF28AA2F3AB21CA50E7DFE37C155F06F6645619E9D20DB35C9DBFA17124C3"),StringUtil.hexStringToBytes("02C193406C1D372BFD3A520006213FF8634CB6020FC2BC825A7EA21765A9F1A497"));
		byte[] sign = sm2.sign(strForSign.getBytes("GBK"),keyPair);
		
		
		map.put("sign", StringUtil.bytesToHexString(sign).toUpperCase());// 签名结果
		// 真正的业务数据
		Map<String, Object> dataMap = new HashMap<String, Object>();
		//dataMap.put("queryflag", "0");// 根据memopenid来查询
		//dataMap.put("memopenid", "20181015130754822A10B40757P77CYX");// 根据memopenid来查询
		//dataMap.put("memopenid", "242342423223");// 根据memopenid来查询 zhenghsi
		//dataMap.put("cardno", "3104831500000007406");zhengshi
		
		//dataMap.put("memopenid", "20190109120151250156");// 
		//dataMap.put("cardno", "3104831599900100836");
		dataMap.put("authcode", "20190408161257463465");
		dataMap.put("memopenid", "124104540306802665200000");// 根据memopenid来查询
		//dataMap.put("authcode", "20181226111012045085");// 授权码
		dataMap.put("cardno", "3104831599900020008");
		dataMap.put("deviceid", "1234");// 手机唯一序号
		dataMap.put("clientip", "127.0.0.1");// 客户端IP
		dataMap.put("qrcodenum", "5");// 展
		
		// 把数据放进去
		map.put("data", dataMap);// 根据memopenid来查询

		//
		System.out.println("gson.toJson(map):" + gson.toJson(map));
		long start = System.currentTimeMillis();
//		String strRet = HttpClientUtil.httpPostJson(Consts.URL_HEADER45001 + "/qrcode/qrcodepay/getQrcode/V1",
//				gson.toJson(map));
				String strRet = HttpClientUtil.httpPostJson("http://58.209.250.8:45001" + "/qrcode/qrcodepay/getQrcode/V1",
				gson.toJson(map));//58.209.250.8
				//SSSSSString strRet = HttpClientUtil.httpPostJson("http://58.211.8.187:45001" + "/qrcode/qrcodepay/getQrcode/V1",
//		String strRet = HttpClientUtil.httpPostJson(Consts.URL_HEADER45001 + "/qrcode/qrcodepay/getQrcode/V1",
//				gson.toJson(map));
		System.out.println("strRet:" + strRet);
		long end = System.currentTimeMillis();
		System.out.println("cost:" + (end-start)+"ms");
		
		
		//转出来byte的扩展，用来生成文件
		Map mapRet=  gson.fromJson(strRet, Map.class);
		Map data= (Map) mapRet.get("data");
		String token = (String) data.get("token");
		System.out.println(StringUtil.bytesToHexString(org.apache.commons.codec.binary.Base64.decodeBase64(token)));
		
	}

}
