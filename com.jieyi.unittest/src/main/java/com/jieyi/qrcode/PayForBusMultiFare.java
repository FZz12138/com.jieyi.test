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

/**
 * 乘车多
 * @author feiwe
 *
 */
public class PayForBusMultiFare {

	public static void main(String[] args) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("joininstid", "12345677");
		map.put("joininstssn", StringUtil.getRandomStringAccordingSystemtimeForNumberFlag(20, 0));
		map.put("reqdate", DateUtil.getSystemDateTime("yyyyMMdd"));
		map.put("reqtime", DateUtil.getSystemDateTime("HHmmss"));
		

//		// 开始签名
//		Gson gson = new Gson();
//		String modulus = "A48D3AA7253648A7B056188D8289F1DF50208451FAB4E713EBA6A13D98C8F8F55BF7C2C73380F1293D541FA7856CFE7E6B4065370087E93E42BED4072DB34CA621B01D901D86A6DE6006B3F58DC6C758D6923AE6795D7920B2C0A7727CEE037AE7F3C1301A7045ED2195ECCF031B75EC696EE1BC9F14DEC88656873B0FBC9437";
//		String exponent = "112B4331696A8E8E2091FB21D8BAF7E7AAFE998FCC2ECFE57E32F6A3ECAB1B7ECE47BDA4F734BEF4E497406E4437A91E1BA6AE68DCBF5190D37A01B976053D3BF54320F1776180B2DA350C4AD27E27185C2ED5453FBBD3406DCEAC9F5A898B0021567903797696F8405AB6241BDC03B5CF534E244D5C91C1CC436A3916BF79A1";
//		RSAPublicKey publicKey = RSAUtil.loadPublicKey(modulus, "10001", 16);
//		RSAPrivateKey privateKey = RSAUtil.loadPrivateKey(modulus, exponent, 16);

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
				AsymmetricCipherKeyPair keyPair = sm2.generateKeyPair(StringUtil.hexStringToBytes("00D2FD01034C93995C875AA6B5603AF2F6D36D99F021F7614A9F0800F0426D34E2"),StringUtil.hexStringToBytes("03D3B4A3C7F0DDA25EE8BABFF66C2AEABDC5E34E82CFA1C98606610F540D351968"));
				byte[] sign = sm2.sign(strForSign.getBytes("GBK"),keyPair);
				
				
				map.put("sign", StringUtil.bytesToHexString(sign).toUpperCase());// 签名结果

		// 真正的业务数据
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("mchntid", "004131305200001");
		dataMap.put("termid", "210000010001");
		dataMap.put("termseq", "201812031654");
		dataMap.put("driverid", "123456");
		dataMap.put("qrcode", "gQFKJAEBAAAAEgVFMFIRGRgRIQQEAEADvMvgsjMjvalVkJESriZQSaJGKc+TQK8QOf0mwTnIRzynnSnbLD81jKe1DC4LUN00ekaZdVcL/MSHqoGui0qwv7TUfmIcT9Zf6G+KWohHXZxofbz9JxqwFCl6Pdh/ZMKYODAxODExMTkwMDAwMDAwMgMQSDFQAFIiImIAAAAAAAAAAAAAEAADllLiqhCINPO1m2z1cS7/z3XveBNclu3txV3C4ag0QrRcBWfuALQCABXH+9LJdS3jsIAJL2KArq+7LHt5fQtq3JZ7QFFmd1XSpqbUv1cC7KE5cq16WeRTWFo7wM3ZqNVDOU3Q/2VqwhkvXAVmhhVecyrONyug8tJKtt2Yz+oepddXBwskyiJPehcKnv/TWQlssUMoxWvNmShfEKh5aElYSSMVgtPTL2cEtpvwfvWV");
		dataMap.put("ifflatfare", "N");
		dataMap.put("buslineid", "999");
		dataMap.put("busstationid", "79");
		dataMap.put("busdirection", "01");
		dataMap.put("busno", "E00001");
		dataMap.put("buslongitude", "22.123456");
		dataMap.put("buslatitude", "111.987654");
		dataMap.put("txndate", DateUtil.getSystemDateTime("yyyyMMdd"));
		dataMap.put("txntime", DateUtil.getSystemDateTime("HHmmss"));
		// 把数据放进去
		map.put("data", dataMap);// 根据memopenid来查询

		// 请求
		System.out.println("gson.toJson(map):" + gson.toJson(map));
		long start = System.currentTimeMillis();
		String strRet = HttpClientUtil.httpPostJson(Consts.URL_HEADER45001 + "/qrcode/qrcodepay/payForBus//V1",
				gson.toJson(map));
//		String strRet = HttpClientUtil.httpPostJson("http://127.0.0.1:41003" + "/qrcode/qrcodepay/payForBus//V1",
//				gson.toJson(map));
		long end = System.currentTimeMillis();
		System.out.println("cost:" + (end-start)+"ms");
		System.out.println("strRet:" + strRet);
	}

}
