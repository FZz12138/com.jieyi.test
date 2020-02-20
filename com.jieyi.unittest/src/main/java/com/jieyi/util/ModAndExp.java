package com.jieyi.util;

import org.apache.commons.codec.binary.Hex;

import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName ModAndExp
 * @Description TODO
 * @Author Administrator
 * @Date 2019/9/9 0009 14:43
 * @Version 1.0
 **/
public class ModAndExp {


    public static void main(String[] args) throws Exception{
        Map<String,String> map = new HashMap<String,String>();
        KeyPair keys = RsaUtil.generateKey(1024);
        // 提供 modules(16进制)，
        String modules = Hex.encodeHexString(((RSAPublicKey) keys.getPublic()).getModulus().toByteArray());
        // 提供 pubExponent(16进制)
        String pubExponent = Hex.encodeHexString(((RSAPublicKey) keys.getPublic()).getPublicExponent().toByteArray());
        // 提供 prvExponent(16进制)，
        String prvExponent = Hex.encodeHexString(((RSAPrivateKey) keys.getPrivate()).getPrivateExponent().toByteArray());
        map.put("rsa_mud", modules);
        map.put("rsa_pub",pubExponent);
        map.put("rsa_prv", prvExponent);
        System.out.println("rsa_mud:"+map.get("rsa_mud"));
        System.out.println("rsa_pub:"+map.get("rsa_pub"));
        System.out.println("rsa_prv:"+map.get("rsa_prv"));

    }
}
