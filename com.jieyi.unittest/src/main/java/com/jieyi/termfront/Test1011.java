package com.jieyi.termfront;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import jieyi.tools.util.DateUtil;
import jieyi.tools.util.StringUtil;

public class Test1011 {

	public static void main(String[] args) throws Exception {
		InputStream is = null;
	    OutputStream os = null;
	    try{
	        Socket socket = new Socket("192.168.1.39", 33100);//创建Socket对象，指明需要连接的服务器的地址和端口号
	        System.out.println(socket.isConnected());//检验连接是否成功
	        is = socket.getInputStream();
	        os = socket.getOutputStream();
	        DataInputStream dis = new DataInputStream(is);
	        DataOutputStream dos = new DataOutputStream(os);
	        
	        /**********************拼装发送的数据Start************************/
//	        String sendMsg = "004A10100000001010101234567890123456789012000000000000000020180905150000000000000100011234567890123456123456789012345600000000FFFFFFFFFFFFFFFFFFFFFF0000";
	        String msgHead = "10";//报文版本	N2		BCD	M	M	要求根据接口规范进行填写，当前该接口默认为10版
	        msgHead += "1011";//报文类型	N4		BCD	M	M	该字段需要根据各交易填写实际值，亦可参考附录的交易类型定义
	        msgHead+="01";//闸机所属厂商	N2		B	M	M	01-武汉公用；02-雄帝
	        msgHead+="09031200";//闸机设备编号	N8		B	M	M	线路编码（B2）+车站编码（B2）+设备类型（B2）+设备编码（B2）
	        msgHead+="0007827010";//设备编号	N10		B	M	M	填写设备物理编号，不足前补0从PSAM卡中读取
	        msgHead+="101010101010";//PSAM卡编号	N12		B	M	M	填写PSAM卡编号
	        msgHead+="0101010101010101";//操作员编号	N16		B	M	M	对应操作员编号，如无则默认填写全0
	        msgHead+=DateUtil.getSystemDateTime("yyyyMMddHHmmss");//数据传输时间	N14	YYYYMMDDHH24MISS	B	M	M	格式为YYYYMMDDHH24MISS注意请求时为闸机时间；应答时系统会将本地时间赋值到该字段中，可以用来实现闸机的时间同步操作。
	        msgHead+="10111101";//交易唯一键	N8		B	M	M	交易唯一键，由车载机负责带上来，系统不对该字段进行调整将原样返回车载机。
	        msgHead+="01";//数据传输结束标志	N2		B	M	M	00：还有后续数据，需要再次请求；01：表示数据传输完成；99：表示单一交易的长连接操作，如应答为99表示系统链接未关闭，允许继续请求。
	        msgHead+="01";//交易应答码	N2		B	M	M	交易应答码见附录表
	        String appData="0001";//数据记录数
	        appData += "06";//业务类型类型	N2		B	要求与1011交易中的交易类型保持一致 公交的时候，此处06-普通消费做地铁时：01-进站02-出站
	        appData+="0000000000000000";//操作员编号	N16		B
	        appData+="0081";//二维码明文长度	N4		B	二维码明文长度
	        appData+="574854544f4b454e0000760000000032303138303932303130323033343932315141343445575a36564c4e30334b5520202020202020200003e8015ba356290258a163c5043af882211791cb8a36efa0cb47ee6ff6fccbdfda3d8c8cdbe8194a32a07b8567b7002bce5b6f31c18eae826be8197988e2b3e2ebad645506bdfbd76c";//二维码明文	LLLVAR		B	二维码，变长，根据前面二维码明文长度确定
	        appData+="000100";//交易金额	N6		B	单位为分
	        appData+="000200";//应收金额	N6		B	单位为分
	        appData+="20180905174197";//交易时间	N14		B	交易日期+交易时间保持一致。
	        appData+=StringUtil.getRandomStringAccordingSystemtimeForNumberFlag(20,0).substring(10);//交易流水号	N10		B	交易中的设备交易流水号。
	        //appData+="2018123412";
	        //	        appData+=StringUtil.bytesToHexString("1030_180906_0001".getBytes());//终端密钥版本	N16		ANS	1030_YYMMDD_XXXX。
	        appData+="0000000000000000";
	        appData+="FFFFFF";// 5	机具当前所在经度	N6		B	比如255.255255，则上送FFFFFF
	        appData+="FFFFFF";//  6	机具当前所在纬度	N6		B	比如255.255255，则上送FFFFFF
	        appData+="000114";//上车线路号	N6		B	
	        appData+="090202";// 上车站点号	N6		B	不足左补0
	        appData+="00";//00-上行；01-下行；FF-未知
	        appData+="09020200";//上车车辆号	N8		B	不足左补0
	        appData+="01";//测试标志位	N2		B	00：正式01：测试
	        appData+="09020200";// TAC	N8		B	
	        
	       
//	        appData += "06";//业务类型类型	N2		B	要求与1011交易中的交易类型保持一致 公交的时候，此处06-普通消费做地铁时：01-进站02-出站
//	        appData+="0000000000000000";//操作员编号	N16		B
//	        appData+="0081";//二维码明文长度	N4		B	二维码明文长度
//	        appData+="574854544f4b454e0000760000000032303138303932303130323033343932315141343445575a36564c4e30334b5520202020202020200003e8015ba356290258a163c5043af882211791cb8a36efa0cb47ee6ff6fccbdfda3d8c8cdbe8194a32a07b8567b7002bce5b6f31c18eae826be8197988e2b3e2ebad645506bdfbd76c";//二维码明文	LLLVAR		B	二维码，变长，根据前面二维码明文长度确定
//	        appData+="000100";//交易金额	N6		B	单位为分
//	        appData+="000200";//应收金额	N6		B	单位为分
//	        appData+="20180905174197";//交易时间	N14		B	交易日期+交易时间保持一致。
//	        appData+=StringUtil.getRandomStringAccordingSystemtimeForNumberFlag(20,0).substring(10);//交易流水号	N10		B	交易中的设备交易流水号。
//	        //appData+="2918181811";
//	        //	        appData+=StringUtil.bytesToHexString("1030_180906_0001".getBytes());//终端密钥版本	N16		ANS	1030_YYMMDD_XXXX。
//	        appData+="0000000000000000";
//	        appData+="FFFFFF";// 5	机具当前所在经度	N6		B	比如255.255255，则上送FFFFFF
//	        appData+="FFFFFF";//  6	机具当前所在纬度	N6		B	比如255.255255，则上送FFFFFF
//	        appData+="090202";//上车线路号	N6		B	
//	        appData+="090202";// 上车站点号	N6		B	不足左补0
//	        appData+="00";//00-上行；01-下行；FF-未知
//	        appData+="09020200";//上车车辆号	N8		B	不足左补0
//	        appData+="01";//测试标志位	N2		B	00：正式01：测试
//	        appData+="09020200";// TAC	N8		B	
	        
	        
	        
	        String crc="0000";//CRC校验码
	        String msgLen = StringUtil.addLeftZero(Integer.toString((msgHead+appData+crc).length()/2,16),4);
			String sendMsg=msgLen+msgHead+appData+crc;
	        System.out.println(sendMsg);
	        
	        /**********************拼装发送的数据End************************/
	        
	        byte[] sendMsgByte = StringUtil.hexStringToBytes(sendMsg);
	        
	        dos.write(sendMsgByte, 0, sendMsgByte.length);
	        //dos.writeUTF("hello");//连接建立后，通过输出流向服务器端发送请求信息
//	        String s = null;
//	        if((s = dis.readUTF()) != null){
//	            System.out.println(s);//通过输入流获取服务器响应的信息
//	        }
	        
	        
	        byte[] recvByte = new byte[1024];
	        int count = 0;
	        while(dis.read(recvByte)!=-1){
	        	count++;
	        }
	        System.out.println(count);
	        String recvMsg = StringUtil.bytesToHexString(recvByte);
	        System.out.println("recvMsg:"+recvMsg);
	        
	        dos.close();
	        dis.close();
	        socket.close();
	    } catch (UnknownHostException e){
	        e.printStackTrace();
	    } catch (IOException e){
	        e.printStackTrace();
	    }

	}

}
