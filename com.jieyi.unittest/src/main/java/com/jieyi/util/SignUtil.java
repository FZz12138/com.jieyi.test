package com.jieyi.util;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

/**
 * @ClassName SignUtil
 * @Description TODO
 * @Author Administrator
 * @Date 2019/11/20 0020 10:49
 * @Version 1.0
 **/
public  class SignUtil {
    public  static  String buildSigndata(Map<String,Object> map) {
        StringBuffer signdata = new StringBuffer();
        //signdata.append("data=").append(StringUtils.stripToEmpty(map.get("data").toString()));
        signdata.append("&instid=").append(StringUtils.stripToEmpty(map.get("instid").toString()));
        signdata.append("&instseq=").append(StringUtils.stripToEmpty(map.get("instseq").toString()));
        signdata.append("&mchntid=").append(StringUtils.stripToEmpty(map.get("mchntid").toString()));
        signdata.append("&timestamp=").append(StringUtils.stripToEmpty(map.get("timestamp").toString()));
        signdata.append("&txncode=").append(StringUtils.stripToEmpty(map.get("txncode").toString()));

        return signdata.toString();
    }


}
