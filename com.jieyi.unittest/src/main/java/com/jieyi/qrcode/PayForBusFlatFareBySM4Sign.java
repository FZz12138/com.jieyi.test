package com.jieyi.qrcode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
public class PayForBusFlatFareBySM4Sign {

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
		AsymmetricCipherKeyPair keyPair = sm2.generateKeyPair(StringUtil.hexStringToBytes("00A06EF28AA2F3AB21CA50E7DFE37C155F06F6645619E9D20DB35C9DBFA17124C3"),StringUtil.hexStringToBytes("02C193406C1D372BFD3A520006213FF8634CB6020FC2BC825A7EA21765A9F1A497"));
		byte[] sign = sm2.sign(strForSign.getBytes("GBK"),keyPair);
		
		
		map.put("sign", StringUtil.bytesToHexString(sign).toUpperCase());// 签名结果

		// 真正的业务数据
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("mchntid", "004131305200001");
		dataMap.put("termid", "210000010001");
		dataMap.put("termseq", "12345666");
		dataMap.put("driverid", "123456");
		dataMap.put("qrcode", "gQFKJAEBAAACEgVFMFISQAAAAQQEACEDEapRO5cFO9fiTXZ7cTQ6sX3Zhv/lS+Mnoo+o1b6f8aI3+GPeRrDPbqiaYaE0J6hjZ+FGZx8sOx8t7rjsgixqTAXzna51hOG3YcXSAO85BS6YFOB4f/ZAmNwiLXLyF7dVODAxOTAxMDIwMDAwMDAwMgMQSDFZmQAAFzAAAAAAAAAAAAAAEAACi+hEd9CRV5+Hy6Y73GIoS7tj28+3UleHOuCOLswwV2RcNatHAHgCABWIJqUYo7NXg3bPkYpQqGH9nFDz4ra/t/TXk1tPLcL4P+S+uuSRPhOY0O2TpWrXuUPlN7NtL/qSBIMneMbA8y95XDWp3xXemmcVCk8gLFhxn4nbjxwy78xlZ6EvXF56lSbPyINmzF3Qqkhtp0JewFwDLeIGqrrt3YKtWFn0+4Rst7+I09G2");
		//正式吗
		//dataMap.put("qrcode", "gQFKJAEBAAACEgVFMFISQAAAAQQEACEDiWnGmGNDzm1vg0BqXrJoxUWHXEK8NZ4b/FBF6tVFwOIsmdQpqCYYAWxTveg/Qpq0MsczKjosOqSE5a+wL6WctxYiU796tgRnVIWvaeeFZ6nLuNwzl5OHUwcnFycKn6fsODAxODEyMjkwMDAwMDAwNwMQSDFQAAAAdAYAAAAAAAAAAAAAEAAC1gm5/MYcblFsjwyOODEE+rTHRQMWj/AOSOK/61n+kltcJwDGAHgCABXODwn9THAnchBCSb0L+Sjyw55AQepP6AG+1JcFBIejcK2ezF/kKwb2eafB8O6aLi7TtyphNyUOBTte8bcvsZw5XCb/XhWoiHtOf9KWMp/nE7yRFMFvzH3xYXTatwDahxkBYVkXK6bslOeeJM3pApQyQn6xfAv1jIOQXu6wsMpBQ7EYoHKu");
		dataMap.put("ifflatfare", "N");
		dataMap.put("orgamt", "100");
		dataMap.put("buslineid", "000016");
		dataMap.put("busstationid", "32");
		dataMap.put("busdirection", "01");
		dataMap.put("busno", "G00001");
		dataMap.put("buslongitude", "22.123456");
		dataMap.put("buslatitude", "111.987654");
		dataMap.put("txndate", DateUtil.getSystemDateTime("yyyyMMdd"));
		dataMap.put("txntime", DateUtil.getSystemDateTime("HHmmss"));
		// 把数据放进去
		map.put("data", dataMap);// 根据memopenid来查询

		// 请求
		System.out.println("gson.toJson(map):" + gson.toJson(map));
		long start = System.currentTimeMillis();
//		String strRet = HttpClientUtil.httpPostJson(Consts.URL_HEADER45001 + "/qrcode/qrcodepay/payForBus/V1",
//				gson.toJson(map));
//		String strRet = HttpClientUtil.httpPostJson("http://127.0.0.1:41003"+ "/qrcode/qrcodepay/payForBus/V1",
//				gson.toJson(map));
		String strRet = HttpClientUtil.httpPostJson("http://58.209.250.8:45001"+"/qrcode/qrcodepay/payForBus/V1",
				gson.toJson(map));//                 http://58.209.250.8:45001   /qrcode/qrcodepay/payForBus/V1
		long end = System.currentTimeMillis();
		System.out.println("cost:" + (end-start)+"ms");
		System.out.println("strRet:" + strRet);
	}
//正式的5个码gQFKJAEBAAACEgVFMFISQAAAAQQEACEDiWnGmGNDzm1vg0BqXrJoxUWHXEK8NZ4b/FBF6tVFwOIsmdQpqCYYAWxTveg/Qpq0MsczKjosOqSE5a+wL6WctxYiU796tgRnVIWvaeeFZ6nLuNwzl5OHUwcnFycKn6fsODAxODEyMjkwMDAwMDAwNwMQSDFQAAAAdAYAAAAAAAAAAAAAEAAC1gm5/MYcblFsjwyOODEE+rTHRQMWj/AOSOK/61n+kltcJwDGAHgCABXODwn9THAnchBCSb0L+Sjyw55AQepP6AG+1JcFBIejcK2ezF/kKwb2eafB8O6aLi7TtyphNyUOBTte8bcvsZw5XCb/XhWoiHtOf9KWMp/nE7yRFMFvzH3xYXTatwDahxkBYVkXK6bslOeeJM3pApQyQn6xfAv1jIOQXu6wsMpBQ7EYoHKu","gQFKJAEBAAACEgVFMFISQAAAAQQEACEDiWnGmGNDzm1vg0BqXrJoxUWHXEK8NZ4b/FBF6tVFwOIsmdQpqCYYAWxTveg/Qpq0MsczKjosOqSE5a+wL6WctxYiU796tgRnVIWvaeeFZ6nLuNwzl5OHUwcnFycKn6fsODAxODEyMjkwMDAwMDAwNwMQSDFQAAAAdAYAAAAAAAAAAAAAEAAC1gm5/MYcblFsjwyOODEE+rTHRQMWj/AOSOK/61n+kltcJwDGAHgCABXODwn9THAnchBCSb0L+Sjyw55AQepP6AG+1JcFBIejcK2ezF/kKwb2eafB8O6aLi7TtyphNyUOBTte8bcvsZw5XCb/YBVzNcecfn8K9f4J+WVVHVMlJeS2MbnJWfThj/MEtdZEYcjD04twPJ6eQ/8oRs54AZe6K8dXlpaLsYeGsV7I07yI
}
