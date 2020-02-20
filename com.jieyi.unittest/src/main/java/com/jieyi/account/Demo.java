package com.jieyi.account;

import com.google.gson.Gson;
import com.jieyi.util.HttpClientUtil;
import com.jieyi.util.MyDateUtil;
import jieyi.tools.algorithmic.RSAUtil;
import jieyi.tools.util.DateUtil;
import jieyi.tools.util.StringUtil;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.*;

public class Demo {
	public static void main(String[] args) throws Exception {		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("joininstid", "10000004");
        map.put("joininstssn", "IB26558879235026");
        map.put("reqdate", DateUtil.getSystemDateTime("yyyyMMdd"));
        map.put("reqtime", DateUtil.getSystemDateTime("HHmmss"));
        String now=DateUtil.getSystemDateTime("yyyyMMddHHmmss");
        String daly= MyDateUtil.getDate(now,7);
        System.out.println(DateUtil.getSystemDateTime("yyyyMMddHHmmssSSS"));
		// 开始签名
		Gson gson = new Gson();
		String rsa_mud="0088b240fcfac66dcaaa785ee15dff4437585a5bca431b4a29cc6f4d5699fc4b0f9e499eecd7fba02f8cade5694008afd680af4856dfc115f992fcac97c6ac55ad24fea6f74e5d5a444fb1eb063f3fe34ca86d644737a793791b8677dcd80b982b0c59a02be99f805034bb5f906374a1997acb9502efa1ecfac04c39ffc137db0f";
        String rsa_prv="3b23f3ddff994df2fae1ed1ad0121aaf5f3868b20f6a8521a4631b30b69a62d735026111d337f84bda2f7a2b991aa908c2d0131a12e074e36f66863d7c251f438ec28ee4a7096070ee4f077da01bc14aadd9b8e59f1ffae8b942a7eb09ce00831ad11be8c3d2128ea4515d4f0022645ebae5755e0eac495025dd7e3024e7e761";
        //String rsa_mud="00a5bf8ecfaa55d1d912717d4e97044b55bdbfb14f47a630018f70841d82fb5a885ca00dd6594a900528f6777851dda66b3305098e7bd3165b06a8cdd209ac9083720e1c83674efbec289b45597e0d8567417aa0ebfa1b9b5e91efd762a3ba253eb4de2ed4e3cd1fd278ca7914110328ee2fbaf77e3b34f741adabd1904f1eeba3";
        //String rsa_prv="780a4bfbca796d09b179d02ee2e0914effa59d5cb59f7e9522b572798d2b4d5e04f7460e3f8ce8d153e16c950f755fd9b391aa622efba9ed32dd7e72e7debaa9ca343ba82262af514793aaf61ab7a944504431d51942955b45b22e5b45338b3bd454533c4cde1bbc5fa7eceba3b9bb03875b86080f7d519e526679e4c39c0581";
        String rsa_pub="010001";
        RSAPublicKey publicKey = RSAUtil.loadPublicKey(rsa_mud,rsa_pub, 16);
		RSAPrivateKey privateKey = RSAUtil.loadPrivateKey(rsa_mud,rsa_prv, 16);
		System.out.println(publicKey);

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
        //map.put("sign","");
      /*  String sign = RsaUtil.sign(strForSign, privateKey, "UTF-8");
        map.put("sign",sign);*/
		// 真正的业务数据
		Map<String, Object> dataMap = new HashMap<String, Object>();

		//开户
      /*  map.put("mchntid","007523305200000");
        dataMap.put("chnltype", "11");
        dataMap.put("pinflag", "N");
        dataMap.put("pindata", "3030303030303030");
        dataMap.put("pinchkamt", "303030303030");
        dataMap.put("remamt", "303030303030");
        dataMap.put("acctype", "81");
        dataMap.put("idtype", "01");
        dataMap.put("idno", "310101194901013577");
        dataMap.put("custname", "张五");
        dataMap.put("mobilephone", "13912341234");
        dataMap.put("cardtype", "1064");*/


        //{"data":{"cardno":"3104831599900091918","downflown":"1911262100000020","orderamt":"100","orderid":"1911262100000020","paytype":"4","upflowno":""},"joininstid":"10000002","joininstssn":"1911262100000020","mchntid":"000000","reqdate":"20191126","reqtime":"085422",

        //消费
        map.put("mchntid","004000305200002");
        dataMap.put("acctype", "01");
        dataMap.put("cardno", "3104831501000000001");
        dataMap.put("orderid", DateUtil.getSystemDateTime("yyyyMMddHHmmss"));
        dataMap.put("orderamt", "1");
        dataMap.put("paytype", "9");
        //dataMap.put("downflown","1911262100000020");
        //dataMap.put("upflowno","");
        //注销
       /* map.put("mchntid","007523305200000");
        dataMap.put("chnltype", "20");
        dataMap.put("acctype", "80");
        dataMap.put("cardno", "3104831599900091925");
        dataMap.put("idtype", "01");
        dataMap.put("idno", "340826199004236616");
        dataMap.put("custname", "朱才结");
        dataMap.put("mobilephone", "18550491168");
        dataMap.put("channel", "1");
        dataMap.put("refundaccno", "12138");
        dataMap.put("accname", "12138");
        dataMap.put("refundtype", "0");*/

        //绑卡
		/*dataMap.put("chnltype", "11");
		dataMap.put("cardno", "3104831501000000015");
		dataMap.put("mobilephone", "15050699841");*/

		//订单
         /*dataMap.put("cardno","3104831501000000015")  ;
         dataMap.put("orderid","b2019112753565055")  ;*/
        //交易
        /*dataMap.put("cardno","3104831501000000015");
        dataMap.put("acctype","81");
        dataMap.put("mchntid","004000305200002");
        dataMap.put("starttxndate","20191127");
        dataMap.put("endtxndate","20191127");*/


        //查询
        //dataMap.put("cardno","3104831501000000015");


        // 把数据放进去
		map.put("data", dataMap);// 根据memopenid来查询

		// 请求
		System.out.println("gson.toJson(map):" + gson.toJson(map));
        //开户
		//String strRet = HttpClientUtil.httpPostJson("http://127.0.0.1:31001" + "/member/info/openYktAcc/V1",gson.toJson(map));

       //消费Consts.URL_HEADER31001
        String strRet = HttpClientUtil.httpPostJson( "http://58.210.88.98:31001"+ "/member/pay/payForNoPasswd/V2",gson.toJson(map));

        //注销
        //String strRet = HttpClientUtil.httpPostJson("http://127.0.01:31001" + "/member/manager/cancelYktAcc/V1",gson.toJson(map));
        //绑卡
        //String strRet = HttpClientUtil.httpPostJson("http://127.0.0.1:31001" + "/member/manager/bindCard/V2",gson.toJson(map));
        //订单
        //String strRet = HttpClientUtil.httpPostJson("http://127.0.0.1:31001" + "/info/query/queryOrderid/V2",gson.toJson(map));
        //交易
        //String strRet = HttpClientUtil.httpPostJson("http://127.0.0.1:31001" + "/info/query/queryTxnInfo/V2",gson.toJson(map));
        //查询
        //String strRet = HttpClientUtil.httpPostJson("http://127.0.0.1:31001/member/query/getYktAccInfo/V2",gson.toJson(map));

        System.out.println("strRet:" + strRet);


	}
}
