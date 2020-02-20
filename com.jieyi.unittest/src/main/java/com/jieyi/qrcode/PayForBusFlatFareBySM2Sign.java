package com.jieyi.qrcode;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jieyi.tools.algorithmic.RSAUtil;
import jieyi.tools.algorithmic.sm.SM2;
import jieyi.tools.util.DateUtil;
import jieyi.tools.util.StringUtil;

import org.bouncycastle.crypto.AsymmetricCipherKeyPair;

import com.google.gson.Gson;
import com.jieyi.util.Consts;
import com.jieyi.util.HttpClientUtil;

/**
 * 乘车单票制
 * @author feiwe
 *
 */
public class PayForBusFlatFareBySM2Sign {

	public static void main(String[] args) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("joininstid", "20000001");
		map.put("joininstssn", StringUtil.getRandomStringAccordingSystemtimeForNumberFlag(20, 0));
		map.put("reqdate", DateUtil.getSystemDateTime("yyyyMMdd"));
		map.put("reqtime", DateUtil.getSystemDateTime("HHmmss"));

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
		dataMap.put("mchntid", "21000001");
		dataMap.put("termid", "210000010001");
		dataMap.put("termseq", "1");
		dataMap.put("driverid", "123456");
		dataMap.put("qrcode", "gQFKJAEBAAAAEgVFMFIRGRgRIQQEAEADvMvgsjMjvalVkJESriZQSaJGKc+TQK8QOf0mwTnIRzynnSnbLD81jKe1DC4LUN00ekaZdVcL/MSHqoGui0qwv7TUfmIcT9Zf6G+KWohHXZxofbz9JxqwFCl6Pdh/ZMKYODAxODExMTkwMDAwMDAwMgMQSDFQAFIiImIAAAAAAAAAAAAAEAADllLiqhCINPO1m2z1cS7/z3XveBNclu3txV3C4ag0QrRcASEMAJYCABVeNfwac1FdDfwY9dozE5MT9e1jzHXsh8XEFRwW8PpkQxPKD9LRNMel4xgkDTl8osmXIWs/LvCxyGsG9tjxNenAXAEfpBXFZALiDMhW/+Y54n36roa6NCP6s2WO+D1FJ9pj0BVrt+p11jgXN+HO5IGySh0bBxSKNUnQptkQFGOwgtpZxrK+");
		dataMap.put("ifflatfare", "Y");
		dataMap.put("orgamt", "200");
		dataMap.put("buslineid", "100");
		dataMap.put("busstationid", "1");
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
		String strRet = HttpClientUtil.httpPostJson("http://127.0.0.1:35001" + "/qrcode/qrcodepay/payForBus//V1",
				gson.toJson(map));
		long end = System.currentTimeMillis();
		System.out.println("cost:" + (end-start)+"ms");
		System.out.println("strRet:" + strRet);
	}

}
