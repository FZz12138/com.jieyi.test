package com.jieyi.account;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.*;

import com.jieyi.util.RsaUtil;
import jieyi.tools.algorithmic.RSAUtil;
import jieyi.tools.util.DateUtil;
import jieyi.tools.util.StringUtil;

import com.google.gson.Gson;
import com.jieyi.util.Consts;
import com.jieyi.util.HttpClientUtil;

public class OpenAccount {
	public static void main(String[] args) throws Exception {		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("joininstid", "10000004");
		map.put("joininstssn", StringUtil.getRandomStringAccordingSystemtimeForNumberFlag(20, 0));
		map.put("reqdate", DateUtil.getSystemDateTime("yyyyMMdd"));
		map.put("reqtime", DateUtil.getSystemDateTime("HHmmss"));

        String encode = URLEncoder.encode(" ", "GBK");
        System.out.println(  encode);
        System.out.println(URLDecoder.decode("MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAIQOB9+uyw7Cvm4qLZM5CJEC9+7/Obqj5a7npuLNX+G0SDz6lR5DGOz0FpmHOQP0iyLhLXrDgRdCVTaFJMctofYyuqj8IsKGsIiWaFqYn7vFqDQRaE1hVNCfVQgy8t8qzu2iU5iDRco5uNpCNEHCko/oTItt1PpMWOJEqdkREXS5AgMBAAECgYB78td9IwnBfxVTPg+tIlliE1E4aSN8hh4uP40rUO3wYlFzuxiAYSZiMvGLhZsElWKLiKQxmJPwiviExhAxTCm6G/1g9g/me1qQp/pVn9443OP9g4hVHA9rk7LWSz5qbiiNTwJeaB4J6esbeRhXEeEwWWjl3tUenLIcItD/gwF5iQJBAMvmYvSzX8gX8UPZ4G64qi1VLeKu2fo3CO9NHuBZEX3rC9/cGmsGCOzTi7ABAcB6KyCroQxsqrm7MDHnzhbm9ycCQQClzBI14CXLyL3Mg4zj6ZzUvVpMw414Qph03fqGz1k/HCZP7loRxYs3N7OuirXukVa1/p9ScCMV3P2rhxADRqEfAkEAjkKu1iX0dOnnhjyUbwveiAR/IEJ4iTAuxq+bFFJwdtcwkL7Dm9o5DetG8cSyETXrsz6r/bwvnahxptAhpS7+TwJAWugf/AZg8vGoj+B5eeX3pfYE8x4uDYiGS14DJbO/PYlTwHPyUtg70xTpooRP6PUr1DI+bVcrBavcMxalQwaRBQJBAJh3H2fS8pCeSewhc8KkF2rQ4JHgwg5MYKNoTpakbBZQvcZu1Pi8Ud0pdEvEizqHu538JukvetVSHxPnJxW1iOY="));
        String l=Long.toString(System.currentTimeMillis()).substring(7,13);
        // 开始签名
		Gson gson = new Gson();
		//String modulus = "A48D3AA7253648A7B056188D8289F1DF50208451FAB4E713EBA6A13D98C8F8F55BF7C2C73380F1293D541FA7856CFE7E6B4065370087E93E42BED4072DB34CA621B01D901D86A6DE6006B3F58DC6C758D6923AE6795D7920B2C0A7727CEE037AE7F3C1301A7045ED2195ECCF031B75EC696EE1BC9F14DEC88656873B0FBC9437";
		//String exponent = "112B4331696A8E8E2091FB21D8BAF7E7AAFE998FCC2ECFE57E32F6A3ECAB1B7ECE47BDA4F734BEF4E497406E4437A91E1BA6AE68DCBF5190D37A01B976053D3BF54320F1776180B2DA350C4AD27E27185C2ED5453FBBD3406DCEAC9F5A898B0021567903797696F8405AB6241BDC03B5CF534E244D5C91C1CC436A3916BF79A1";
		String rsa_mud="0088b240fcfac66dcaaa785ee15dff4437585a5bca431b4a29cc6f4d5699fc4b0f9e499eecd7fba02f8cade5694008afd680af4856dfc115f992fcac97c6ac55ad24fea6f74e5d5a444fb1eb063f3fe34ca86d644737a793791b8677dcd80b982b0c59a02be99f805034bb5f906374a1997acb9502efa1ecfac04c39ffc137db0f";
        String rsa_prv="3b23f3ddff994df2fae1ed1ad0121aaf5f3868b20f6a8521a4631b30b69a62d735026111d337f84bda2f7a2b991aa908c2d0131a12e074e36f66863d7c251f438ec28ee4a7096070ee4f077da01bc14aadd9b8e59f1ffae8b942a7eb09ce00831ad11be8c3d2128ea4515d4f0022645ebae5755e0eac495025dd7e3024e7e761";
		//String rsa_mud="00B91704BC7A5D9C88E827F3F947916E08C57514E1CA0AFAC9164013D17C4B4C6F5487D7BB228A5271F34C54DD2C4F0667F3D15A440E6CC5FDCCB480BF9DE3E5F400E13DDF3248A83158EE25B4BB8FC8746C9F1E7DB813775A126A8CE79C668496E696340CCB42EA38D91A86072C6EFB3EA57FF1D8519610E949F2C6E017748D37";
       // String rsa_prv="009348FCECDAD6D9349CC106450CAD6E892E4FCFF128ACFA09C059F72B51243E4BAD5177DB234E3CF30E6F646CA7D0AEC153E4F95684C00C01BDBEB96141CC53742E0E1E5A40262748B1730B977094645847EE1A8E2E800E4D9028FE3E5FF3B321DCBB460C18BADDF81A672A0353AB22EBA1C04EACE97330502D8F29AF67D3B6A9";
        String rsa_pub="010001";
        RSAPublicKey publicKey = RSAUtil.loadPublicKey(rsa_mud,rsa_pub, 16);
		RSAPrivateKey privateKey = RSAUtil.loadPrivateKey(rsa_mud,rsa_prv, 16);

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
      /*  String sign = RsaUtil.sign(strForSign, privateKey, "UTF-8");
        map.put("sign",sign);*/
		// 真正的业务数据
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("memopenid", "20180919164043494JN77QJ012726530"); 
		dataMap.put("idno", "222222222222222222222"); 
		dataMap.put("idtype", "01"); 
		dataMap.put("custname", "测试"); 

		// 把数据放进去
		map.put("data", dataMap);// 根据memopenid来查询

		// 请求
		System.out.println("gson.toJson(map):" + gson.toJson(map));
		//Consts.URL_HEADER31001
		String strRet = HttpClientUtil.httpPostJson( "http://127.0.0.1:31001"+ "/member/info/openYktAcc/V1",
				gson.toJson(map));
		System.out.println("strRet:" + strRet);
	}
}
