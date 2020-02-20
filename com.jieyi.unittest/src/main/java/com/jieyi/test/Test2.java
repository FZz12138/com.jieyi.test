package com.jieyi.test;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.google.gson.Gson;
import com.jieyi.util.Consts;
import com.jieyi.util.HttpClientUtil;

import java.util.SortedMap;
import java.util.TreeMap;

import jieyi.tools.algorithmic.MD5;
import jieyi.tools.util.StringUtil;

public class Test2 {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		//System.out.println(new String(StringUtil.hexStringToBytes("3337413639313344324133304232424139394438453938393437323245393530")));
		// 1. 拼接字符串
		SortedMap<Object, Object> packageParams = new TreeMap<Object, Object>();
		
		packageParams.put("cardNo","3104831599900020020");
		packageParams.put("cardType", "1064");
		packageParams.put("disCount", 200);
		packageParams.put("downStation", "昆山火车站");
		packageParams.put("downTime", "1515637434000");
		packageParams.put("flowNo","201712211328210");
		packageParams.put("lineId","01");
		packageParams.put("lineName","42路公交");
		packageParams.put("mobile","13800138000");
		packageParams.put("orderNo","1000000000000584");
		packageParams.put("payAmt",5000);
		packageParams.put("payMethod","昆山市民卡电子公交卡账户");
		packageParams.put("payMode","11");
		packageParams.put("payNo","");
		packageParams.put("qrcodek","00000000000000000000000000000000000000000018817122");
		packageParams.put("sign","0000000");
		packageParams.put("transStatus","02");
		packageParams.put("txnAmt",5000);
		packageParams.put("txnCode","02");
		packageParams.put("txnNo","1000000000000870205345321470703");
		packageParams.put("txnTime","1515637434000");
		packageParams.put("upStation","昆山体育馆");
		packageParams.put("upTime","1515637434000");
		
		String key = "2FC9EDC2BB2A12CF3AD14B825C88784D";//充值签名密钥
		String str = getSignParams("UTF-8", packageParams,key);
		//sign=md5(str,"UTF-8").toUpperCase;
		String sign = MD5.getMD5String(str, "UTF-8");
		
		Map<Object, Object> params = new HashMap<Object, Object>();
		params.put("txnCode", "02");
		params.put("mobile", "13800138000");
		params.put("cardNo", "3104831599900020020");
		params.put("cardType", "1064");
		params.put("txnAmt", 100);
		params.put("payAmt", 100);
		params.put("disCount", 200);
		params.put("orderNo", "1000000000000514");
		params.put("payNo", "P00000000000000001");
		params.put("payMode", "wechat");
		params.put("txnTime", "1515637434000");
		params.put("transStatus", "SUCCESS");
		params.put("qrcodek", "00000000000000000000000000000000000000000018817122");
		params.put("txnNo", "1000000000000870205345321470703");
		params.put("flowNo", "201712211328210");
		params.put("lineId", "01");
		params.put("lineName", "42路公交");
		params.put("upStation", "昆山体育馆");
		params.put("upTime", "1515637434000");
		params.put("downStation", "昆山火车站");
		params.put("downTime", "1515637434000");
		params.put("payMethod", "ALIPAY");
		params.put("sign", "111111111111112222222222222222222");
		Gson gson = new Gson();
		
		System.out.println("gson.toJson(params):"+gson.toJson(params));
		
		long start = System.currentTimeMillis();
		String strRet = HttpClientUtil.postJsonByHttps("https://t-kssmk.100qu.net/ecard/order/callback",
				gson.toJson(params));
		long end = System.currentTimeMillis();
		System.out.println("cost:" + (end-start)+"ms");
		System.out.println("strRet:" + strRet);
		
	}

	private static String  getSignParams(String string, SortedMap<Object, Object> packageParams, String key) throws Exception {
		// TODO Auto-generated method stub
		String str ="";
		 for (Entry<Object, Object> entry : packageParams.entrySet()) {
			 str+=entry.getKey()+"="+entry.getValue()+"&";
	            //System.out.println(entry.getKey() + " " + entry.getValue());
	        }
		 str+="key="+key;
		 str = new String(str.getBytes(),string);
		 //System.out.println(str);
		 return str;
		 
	}

}
