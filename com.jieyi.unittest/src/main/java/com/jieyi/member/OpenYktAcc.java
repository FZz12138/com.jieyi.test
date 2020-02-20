package com.jieyi.member;

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

public class OpenYktAcc {

	public static void main(String[] args) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("joininstid", "10000004");
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
		AsymmetricCipherKeyPair keyPair = sm2.generateKeyPair(StringUtil.hexStringToBytes("00A06EF28AA2F3AB21CA50E7DFE37C155F06F6645619E9D20DB35C9DBFA17124C3"),StringUtil.hexStringToBytes("02C193406C1D372BFD3A520006213FF8634CB6020FC2BC825A7EA21765A9F1A497"));
		byte[] sign = sm2.sign(strForSign.getBytes("GBK"),keyPair);
		
		
		map.put("sign", StringUtil.bytesToHexString(sign).toUpperCase());// 签名结果
		
		

		// 真正的业务数据
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("memopenid", "121213");//
		dataMap.put("idtype", "01");//
		dataMap.put("idno", "12121211");//
		dataMap.put("custname", "大胖11");//
		dataMap.put("mobilephone", "15919899871");//
		dataMap.put("cardtype", "1064");//

		//万达开户参数
//		dataMap.put("idtype", "01");
//		dataMap.put("idno", "1212121111");
//		dataMap.put("userId", "1112233");
//		dataMap.put("mobile", "11143432352");
//		dataMap.put("appId", "1212121111");
//		dataMap.put("username", "测试万达");
//		dataMap.put("sign", "");
		
		
		// 把数据放进去
		map.put("data", dataMap);// 根据memopenid来查询

		// 请求
		long start = System.currentTimeMillis();
		System.out.println("gson.toJson(map):" + gson.toJson(map));
//		String strRet = HttpClientUtil.httpPostJson(Consts.URL_HEADER3910004 + "/member/info/openYktAcc/V1",
//				gson.toJson(map));
		/*String strRet = HttpClientUtil.httpPostJson("http://192.168.1.40:45001" + "/member/info/openYktAcc/V1",
				gson.toJson(map));*/
		String strRet = HttpClientUtil.httpPostJson("http://localhost:31001" + "/member/info/openYktAcc/V1",
				gson.toJson(map));
//		String strRet = HttpClientUtil.httpPostJson("http://58.211.8.187:45001" + "/member/info/openYktAccByWanda",
//				gson.toJson(map));
		long end = System.currentTimeMillis();
		System.out.println("strRet:" + strRet);
		System.out.println("cost:" + (end-start)+"ms");
	}
}
