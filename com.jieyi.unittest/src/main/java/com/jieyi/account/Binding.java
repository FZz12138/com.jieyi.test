package com.jieyi.account;

import com.google.gson.Gson;
import com.jieyi.util.Consts;
import com.jieyi.util.HttpClientUtil;
import com.jieyi.util.SignUtil;
import jieyi.tools.algorithmic.RSAUtil;
import jieyi.tools.util.DateUtil;
import jieyi.tools.util.StringUtil;
import org.apache.commons.lang3.StringUtils;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.*;

public class Binding {
	public static void main(String[] args) throws Exception {		
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("instid", "21550000");
		map.put("instseq", StringUtil.getRandomStringAccordingSystemtimeForNumberFlag(20, 0));
		map.put("timestamp", DateUtil.getSystemDateTime("yyyyMMddHHmmss"));
		map.put("mchntid", DateUtil.getSystemDateTime("000100320500000"));
        map.put("txncode", DateUtil.getSystemDateTime("1030"));


		// 开始签名
		Gson gson = new Gson();
		String rsa_mud="009ae0a6d86ef4b9d362b5c9990f200d1b946c8d253519a991f917c8f793688f28679efb2b07162b5a9c7ef314d5bb1ef158f0c6780bb13f479e1295145151f515391f367d39bd8ca12c66ef7f0e1118d0f71673e4e42c8843373e990012fa2e907316e2610b841ad2221695cd55ddec17a822d9220736feec1ebd3747887a6b67";
        String rsa_prv="00a61ddcb00a4873fc8a059fad21e2614443c9d806859f30eb710b09cdf69bbfe83b0d2be10c70dff94adf203926c4c857ae5b2bf6f2abda650e3233c5ef61db0b0b912c595afcd37be3f1d18fc7224b51b2ff08792ad489f44ce261d1c53310f52f823b068fffe7e569f9bfc1b87cb0df7f9ad7dc5494b624f099fefd89ae8831";
        String rsa_pub="010001";
        RSAPublicKey publicKey = RSAUtil.loadPublicKey(rsa_mud,rsa_pub, 16);
		RSAPrivateKey privateKey = RSAUtil.loadPrivateKey(rsa_mud,rsa_prv, 16);

		// 2、公钥加密，后台使用私钥解密验签


		String strForSign = SignUtil.buildSigndata(map);

		System.out.println(strForSign);
		byte[] cipherText = RSAUtil.privateKeyEncrypt("RSA", "ECB", "PKCS1Padding", privateKey,
				strForSign.getBytes("GBK"));
		map.put("sign", StringUtil.bytesToHexString(cipherText).toUpperCase());// 签名结果
        Map<String, String> dataMap = new HashMap<String,String>();
        dataMap.put("idtype", "01");
        dataMap.put("idname", "史垚祎");
        dataMap.put("idno", "32058619900618051X");
        dataMap.put("citizen_card_no", "11001967");

        dataMap.put("acctype", "80");
        dataMap.put("accno", "80191115000000147119");

        // 把数据放进去
        map.put("data",dataMap);


		// 请求
		System.out.println("gson.toJson(map):" + gson.toJson(map));
		//Consts.URL_HEADER31001
		String strRet = HttpClientUtil.httpPostJson( "http://127.0.0.1:31002"+ "/manager/1030/V1",
				gson.toJson(map));
		System.out.println("strRet:" + strRet);
	}



}
