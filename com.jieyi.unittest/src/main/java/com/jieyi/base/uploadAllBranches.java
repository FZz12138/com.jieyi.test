package com.jieyi.base;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jieyi.tools.algorithmic.RSAUtil;
import jieyi.tools.util.DateUtil;
import jieyi.tools.util.StringUtil;

import com.google.gson.Gson;
import com.jieyi.util.Consts;
import com.jieyi.util.HttpClientUtil;

public class uploadAllBranches {
	public static void main(String[] args) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		//map.put("instid", "11111111");
		String a = StringUtil.getRandomStringAccordingSystemtimeForNumberFlag(20, 0);
		String b = DateUtil.getSystemDateTime("yyyyMMdd");
		String c = DateUtil.getSystemDateTime("HHmmss");
		System.out.println("joininstssn   "+a);
		System.out.println("reqdate   "+b);
		System.out.println("reqtime   "+c);
		map.put("joininstid", "00000000");
		map.put("joininstssn","20181116173605697273");
		map.put("reqdate","20181116");
		map.put("reqtime","173605");

		// 开始签名
		Gson gson = new Gson();
		String modulus = "A48D3AA7253648A7B056188D8289F1DF50208451FAB4E713EBA6A13D98C8F8F55BF7C2C73380F1293D541FA7856CFE7E6B4065370087E93E42BED4072DB34CA621B01D901D86A6DE6006B3F58DC6C758D6923AE6795D7920B2C0A7727CEE037AE7F3C1301A7045ED2195ECCF031B75EC696EE1BC9F14DEC88656873B0FBC9437";
		String exponent = "112B4331696A8E8E2091FB21D8BAF7E7AAFE998FCC2ECFE57E32F6A3ECAB1B7ECE47BDA4F734BEF4E497406E4437A91E1BA6AE68DCBF5190D37A01B976053D3BF54320F1776180B2DA350C4AD27E27185C2ED5453FBBD3406DCEAC9F5A898B0021567903797696F8405AB6241BDC03B5CF534E244D5C91C1CC436A3916BF79A1";
		RSAPublicKey publicKey = RSAUtil.loadPublicKey(modulus, "10001", 16);
		System.out.println("publicKey   "+publicKey.toString());
		RSAPrivateKey privateKey = RSAUtil.loadPrivateKey(modulus, exponent, 16);

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
		System.out.println(strForSign);
		byte[] cipherText = RSAUtil.publicKeyEncrypt("RSA", "ECB", "PKCS1Padding", publicKey,
				strForSign.getBytes("GBK"));
		map.put("sign", StringUtil.bytesToHexString(cipherText).toUpperCase());// 签名结果

		// 真正的业务数据
	/*	Map<String, Object> dataMap = new HashMap<String, Object>();
		//dataMap.put("mobilephone", "18651072789");// 根据memopenid来查询
		dataMap.put("mobilephone", "15601838927");// 根据memopenid来查询
		dataMap.put("verifycodeusage", "4");// 根据memopenid来查询
*/
		List<Map<String, Object>> branchlist = new ArrayList<Map<String, Object>>();
		Map<String, Object> datamap = new HashMap<String, Object>();
		datamap.put("mchntid", "001");
		datamap.put("branchid", "666");
		datamap.put("branchname", "公交666公司");
		branchlist.add(datamap);
		Map<String, Object> datamap1 = new HashMap<String, Object>();
		datamap1.put("mchntid", "002");
		datamap1.put("branchid", "333");
		datamap1.put("branchname", "公交333公司");
		branchlist.add(datamap1);
		// 把数据放进去
		map.put("data", branchlist);// 根据memopenid来查询

		// 请求
		System.out.println("gson.toJson(map):" + gson.toJson(map));
		long start = System.currentTimeMillis();
		String strRet = HttpClientUtil.httpPostJson(Consts.URL_HEADER31000 + "/base/branch/uploadAllBranches/V1",
				gson.toJson(map));
		long end = System.currentTimeMillis();
		System.out.println("cost:" + (end-start)+"ms");
		System.out.println("strRet:" + strRet);
	}
}
