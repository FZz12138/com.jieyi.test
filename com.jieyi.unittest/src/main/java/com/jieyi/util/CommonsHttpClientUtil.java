package com.jieyi.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.ArrayList;
import java.util.List;

import jieyi.tools.algorithmic.RSAUtil;
import jieyi.tools.algorithmic.SignatureUtil;
import jieyi.tools.util.StringUtil;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.StringPart;
import org.apache.commons.httpclient.params.HttpMethodParams;

public class CommonsHttpClientUtil {
	public static void uploadFile(String filePath,String fileName,String url,List<StringPart> sps) {
		File file = new File(filePath+File.separator+fileName);
		//PostMethod filePost = new PostMethod(URL_STR);
		PostMethod filePost = new PostMethod(url);
		HttpClient client = new HttpClient();

		try {
			// 通过以下方法可以模拟页面参数提交
			filePost.setParameter("userName", "userName111");
			filePost.setParameter("passwd", "userName222");

			filePost.getParams().setBooleanParameter(HttpMethodParams.USE_EXPECT_CONTINUE,true);
			//Part[] parts = { new FilePart(file.getName(), file) };
			//Part[] parts = { new FilePart("file", file) };
			Part[] parts = new Part[sps.size()+1];
			parts[0] = new FilePart("file", file);
			for(int i=0;i<sps.size();i++){
				parts[i+1] = sps.get(i);
			}
			//parts[2] = new FilePart("fil31", file);
			filePost.setRequestEntity(new MultipartRequestEntity(parts,
					filePost.getParams()));

			client.getHttpConnectionManager().getParams()
					.setConnectionTimeout(5000);

			int status = client.executeMethod(filePost);
			if (status == HttpStatus.SC_OK) {
				try {
					/** 读取服务器返回过来的json字符串数据 **/
					//String str = EntityUtils.toString(client.);
					
					InputStream in = filePost.getResponseBodyAsStream();
					BufferedReader reader = new BufferedReader(new InputStreamReader(in));
	                String line = null;
	                String result = "";
	                while ((line = reader.readLine()) != null) {
	                	result += line;
	                }
	                System.out.println("result:"+result.toString());
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				//System.out.println("Upload success!");
			} else {
				System.out.println("Upload fail!");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			filePost.releaseConnection();
		}
	}
	
	public static void main(String[] args) throws Exception {
		String modulus = "CF958C3C946B331B624C915D3D417234834FFA8082A9C5C7F17FA106FAC65B544C24C19A9801D59DCA191D5996BFC5122ED1923A3E7BD68BE5FA2BF69F472359";
		String exponent = "958B49ECF68B47D5297AB99A2F1FEFD36EE9F630107197FF53B63B5E58D8FF3F2D4A4266530C3F675B5ADBDB09934803CD9B366B0A66DCE3E4D0867E36099E01";
		//modulus = "A48D3AA7253648A7B056188D8289F1DF50208451FAB4E713EBA6A13D98C8F8F55BF7C2C73380F1293D541FA7856CFE7E6B4065370087E93E42BED4072DB34CA621B01D901D86A6DE6006B3F58DC6C758D6923AE6795D7920B2C0A7727CEE037AE7F3C1301A7045ED2195ECCF031B75EC696EE1BC9F14DEC88656873B0FBC9437";
		//exponent = "112B4331696A8E8E2091FB21D8BAF7E7AAFE998FCC2ECFE57E32F6A3ECAB1B7ECE47BDA4F734BEF4E497406E4437A91E1BA6AE68DCBF5190D37A01B976053D3BF54320F1776180B2DA350C4AD27E27185C2ED5453FBBD3406DCEAC9F5A898B0021567903797696F8405AB6241BDC03B5CF534E244D5C91C1CC436A3916BF79A1";
		
		RSAPublicKey publicKey;
		RSAPrivateKey privateKey;
		try {
			publicKey = RSAUtil.loadPublicKey(modulus, "10001", 16);
			privateKey = RSAUtil.loadPrivateKey(modulus, exponent, 16);
			System.out.println(publicKey.toString());
			System.out.println(privateKey.toString());
			
//			String data = "{\"txncode\":\"charge\",\"cardno\":\"2253123456781234\"}";
//			data = "{\"instid\":\"10000001\",\"imei\":\"865411025586127\",\"voucherno\":\"420984198704153330\",\"pagerecnum\":\"10\",\"city\":\"2253\",\"seid\":\"0114911019999099\",\"pageseq\":\"0\",\"mchntid\":\"100000010000001\",\"acctype\":\"00\",\"paypasswd\":\"0d9651a85466c6320cf25afc79eba8d7930f9e2986beff055fbea23a14d6bbf189daac77e7f751bc49d144130b2add6eaa35c5311ced561d4719c185e782ae911476eadacfafeae15b18e266de9589a4c62f2bea9f7984ff4999cd6b9bf274d13664e37b5bb8e2cc24faaca360d83e903cf7e7da410219e32b92d0661c259e97\",\"cardno\":\"2253000150000194\",\"begindate\":\"20150119\",\"enddate\":\"20150109\",\"txncode\":\"AccTxnQry\",\"syssesq\":\"1234567890\"}";
			
			String instid = "00000000";
			String mchntid = "10000001";
			String picname = "111111.jpg";
			String data = instid+mchntid+picname;
			
			System.out.println("计算签名的数据=["+data+"]");
			
			byte[] signData = SignatureUtil.signature(data,"UTF-8", privateKey, "");
			String sign = StringUtil.bytesToHexString(signData).toUpperCase();
			System.out.println("签名数据=["+sign+"]");
//			boolean signVerify = SignatureUtil.signVerified(data,"UTF-8", signData, publicKey, "");
//			System.out.println("验签结果=["+signVerify+"]");
			
			List<StringPart> sps = new ArrayList<StringPart>();
			sps.add(new StringPart("instid", instid));
			sps.add(new StringPart("mchntid", mchntid));
			sps.add(new StringPart("sign", sign));
			//sps.add(new StringPart("sign", "73A1877D5E0BEA2B51D1143E5E8FD15F95831A2A96C10F16BBBB2B50DA7B8837682A45D42FB56D0DB2E88DBF3CAA7D8F7295C9BEF65A39CAEAB961305CEE1D01"));
			sps.add(new StringPart("passdate", "20171026"));
			
			long start = System.currentTimeMillis();
			
			//CommonsHttpClientUtil.uploadFile("E://", "222222.jpg", "http://127.0.0.1:20000/uploadimg/carpass",sps);
			//CommonsHttpClientUtil.uploadFile("E://", "444444.jpg", "http://101.132.43.104:20013/testuploadimg?validcode11=22",sps);
			CommonsHttpClientUtil.uploadFile("E://", "111111.jpg", "http://101.132.43.104:20016/uploadimg/carpass",sps);
			
			long end = System.currentTimeMillis();
			System.out.println("cost:"+(end-start)+"ms");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
		
		
	}
}
