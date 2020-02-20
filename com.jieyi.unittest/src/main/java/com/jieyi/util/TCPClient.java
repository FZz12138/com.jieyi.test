package com.jieyi.util;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Scanner;

import jieyi.tools.util.ByteUtil;
import jieyi.tools.util.StringUtil;

public class TCPClient {
    //信道选择器
    private Selector selector;
    //与服务器通信的信道
    SocketChannel socketChannel;
    //要连接的服务器的IP地址
    private String hostIp;
    //要连接的远程服务器在监听的端口
    private int hostListenningPort;
    
    public TCPClient(String hostIp,int hostListenningPort) throws IOException{
        this.hostIp=hostIp;
        this.hostListenningPort=hostListenningPort;
    
        initialize();
    } 
    /**
     * 初始化
     */
    private void initialize() throws IOException{
        //打开监听信道并设置为非阻塞模式
        socketChannel=SocketChannel.open(new InetSocketAddress(hostIp, hostListenningPort));
        socketChannel.configureBlocking(false);
        //打开并注册选择器到信道
        selector=Selector.open();
        socketChannel.register(selector,SelectionKey.OP_READ);//SelectionKey.OP_READ表示读就绪事件
        //启动读取线程
        //new TCPClientThread(selector);
    }    
    /**
     * 发送字符串到服务器
     */
    public void sendMsg(String message) throws IOException{
        ByteBuffer writeBuffer=ByteBuffer.wrap(message.getBytes("UTF-8"));
        socketChannel.write(writeBuffer); 
        
    }
    
    public byte[] sendMsgBytesAndRecv(byte[] message) throws Exception{
        //ByteBuffer writeBuffer=ByteBuffer.wrap(message.getBytes("UTF-8"));
    	byte[] responseMsg = new byte[1024];
    	try{
    		ByteBuffer writeBuffer=ByteBuffer.wrap(message);
            socketChannel.write(writeBuffer); 
            while(selector.isOpen()&&selector.select()>0){
            	//遍历每个有可用IO操作Channel对应的SelectionKey
                for(SelectionKey sk:selector.selectedKeys()){
                    //如果该SelectionKey对应的Channel中有可读的数据
                    if(sk.isReadable()){
                        //使用NIO读取Channel中的数据
                        SocketChannel sc=(SocketChannel)sk.channel();//获取通道信息
                        ByteBuffer buffer=ByteBuffer.allocate(1024);//分配缓冲区大小
                        int count = sc.read(buffer);//读取通道里面的数据
                        responseMsg = new byte[count];
                        buffer.flip();//调用此方法为一系列通道写入或获取操作做好准备
                        byte[] rBufferByte = buffer.array();
                        responseMsg = ByteUtil.subByte(rBufferByte, 0, count);
                        // 将字节转化为为UTF-8的字符串  
                        //String receivedString=Charset.forName("UTF-8").newDecoder().decode(buffer).toString();
                        // 控制台打印出来  
                        //System.out.println("接收到来自服务器" + sc.socket().getRemoteSocketAddress() + "的信息:"  + receivedString);  
                        //System.out.println("Recved msg:" + StringUtil.bytesToHexString(responseMsg));
                        // 为下一次读取作准备  
                        //sk.interestOps(SelectionKey.OP_READ); 
                        System.out.println("结束了吗111");
                    }
                     //删除正在处理的SelectionKey
                    selector.selectedKeys().remove(sk);
                    selector.close();
                    System.out.println("结束了吗222");
                }
        	}
    	}catch(Exception e){
    		e.printStackTrace();
    		throw e;
    	}finally{
    		System.out.println("结束了吗333");
    		socketChannel.close();
    	}
    	
    	return responseMsg;
    	
    }
       
    static boolean flag=true;    
    public static void main(String[] args) throws Exception{
        //创建一个客户端并绑定到IP地址和端口号
        //TCPClient client=new TCPClient("localhost", 8678);
    	
    	long start = System.currentTimeMillis();
		TCPClient client=new TCPClient("59.175.174.197", 8010);
		//byte[] responseMsg = client.sendMsgBytesAndRecv(StringUtil.hexStringToBytes("1001000101001b3130303120202020313032373030303337303431"));
		byte[] responseMsg = client.sendMsgBytesAndRecv(StringUtil.hexStringToBytes("100200010100d6313030322020202020202020202020202020202031383039323031393338303635313335313830393230313933383036353133353030303030303030303030303030303030303031303030303830323731313037313939393333353031393939333335303131303720202020313032373030303337303431323031383039323031393338303641334134373735363030303030303030303030303134202030303030313030303030303130303030413341343737353630393831363431423531353241313632422020202020202020"));
		long end = System.currentTimeMillis();
        System.out.println("recvMsg:"+StringUtil.bytesToHexString(responseMsg)+"|cost:"+(end-start)+"ms");
    	//System.out.println("结束了吗");
    	
    	
//        //静态内部类方法
//        new Thread(){
//             public void run() {  
//                    try {  
//                        client.sendMsg("你好,服务器!!!");  
//                        while (flag) {  
//                            Scanner scanner = new Scanner(System.in);
//                            String string = scanner.next();  
//                            client.sendMsg(string);  
//                        }  
//                    } catch (IOException e) {  
//                        flag = false;  
//                        e.printStackTrace();  
//                    } finally {  
//                        flag = false;  
//                    }  
//                    super.run();  
//                }  
//            }.start();  
    }
}