package com.jieyi.termfront;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import jieyi.tools.util.StringUtil;

public class Test1000 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		InputStream is = null;
	    OutputStream os = null;
	    try{
	        Socket socket = new Socket("192.168.1.39", 33100);//创建Socket对象，指明需要连接的服务器的地址和端口号
	        is = socket.getInputStream();
	        os = socket.getOutputStream();
	        DataInputStream dis = new DataInputStream(is);
	        DataOutputStream dos = new DataOutputStream(os);
	        
	        /**********************拼装发送的数据Start************************/
//	        String sendMsg = "004A10100000001010101234567890123456789012000000000000000020180905150000000000000100011234567890123456123456789012345600000000FFFFFFFFFFFFFFFFFFFFFF0000";
	        String msgHead = "10";//报文版本	N2		BCD	M	M	要求根据接口规范进行填写，当前该接口默认为10版
	        msgHead += "1000";//报文类型	N4		BCD	M	M	该字段需要根据各交易填写实际值，亦可参考附录的交易类型定义
	        msgHead+="01";//闸机所属厂商	N2		B	M	M	01-武汉公用；02-雄帝
	        msgHead+="09031200";//闸机设备编号	N8		B	M	M	线路编码（B2）+车站编码（B2）+设备类型（B2）+设备编码（B2）
	        msgHead+="0007827010";//设备编号	N10		B	M	M	填写设备物理编号，不足前补0从PSAM卡中读取
	        msgHead+="101010101010";//PSAM卡编号	N12		B	M	M	填写PSAM卡编号
	        msgHead+="0101010101010101";//操作员编号	N16		B	M	M	对应操作员编号，如无则默认填写全0
	        msgHead+="20180905174197";//数据传输时间	N14	YYYYMMDDHH24MISS	B	M	M	格式为YYYYMMDDHH24MISS注意请求时为闸机时间；应答时系统会将本地时间赋值到该字段中，可以用来实现闸机的时间同步操作。
	        msgHead+="10111101";//交易唯一键	N8		B	M	M	交易唯一键，由车载机负责带上来，系统不对该字段进行调整将原样返回车载机。
	        msgHead+="00";//数据传输结束标志	N2		B	M	M	00：还有后续数据，需要再次请求；01：表示数据传输完成；99：表示单一交易的长连接操作，如应答为99表示系统链接未关闭，允许继续请求。
	        msgHead+="01";//交易应答码	N2		B	M	M	交易应答码见附录表
	        String appData="01";// 1	参数版本组数	N2		B	与下面的参数版本域相对应参数版本循环记录体（对应编号2）
	        appData+=StringUtil.bytesToHexString("TM2018091318140A".getBytes());//2	参数版本	N32		ANS	对应闸机内的当前参数版本，分别对应      终端密钥文件版本（1030_YYMMDD_XXXX）   XXX文件版本（1031_YYMMDD_XXXX）
	        appData+="123456";//  3	未传记录数	N6		B	当前机具内未上传交易记录（交通部刷卡记录+二维码刷码记录+银联ODA记录）
	        appData+="00";//  4	设备状态	N2		B	对应闸机当前的设备状态，可参考附件的设备状态部分
	        appData+="FFFFFF";// 5	机具当前所在经度	N6		B	比如255.255255，则上送FFFFFF
	        appData+="FFFFFF";//  6	机具当前所在纬度	N6		B	比如255.255255，则上送FFFFFF
	        appData+="FFFFFFFFF0"; // 7	保留使用	N10		B	暂时保留使用（可默认填写全F）
	        String crc="0000";//CRC校验码
	        String msgLen = StringUtil.addLeftZero(Integer.toString((msgHead+appData+crc).length()/2,16),4);
			String sendMsg=msgLen+msgHead+appData+crc;
	        
	        
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
