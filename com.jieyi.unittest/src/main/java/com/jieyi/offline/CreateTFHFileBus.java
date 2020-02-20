package com.jieyi.offline;

import jieyi.tools.util.DateUtil;
import jieyi.tools.util.FileUtil;
import jieyi.tools.util.StringUtil;

/**
 * 创建测试的TFH的公交文件
 * @author feiwe
 *
 */
public class CreateTFHFileBus {
	private static int recordNum = 1;
	private static String filePath = "E:\\111\\wuhanoffline\\datafile\\recvtmp";
	
	public static void main(String[] args) throws Exception {
		try{
			System.out.println("开始生成...");
			String fileName = "TFH"+"12345678"+"10000001001"+"000000000"+DateUtil.getSystemDateTime("yyyyMMddHHmmss").substring(2,13)+"B";
            
            FileUtil.getInstant().writeLineInFile("01", filePath, fileName, "GBK");
            FileUtil.getInstant().writeLineInFile("123456789123456"+"10000001"+"       "+StringUtil.addLeftZero(recordNum+"", 5)+"0665"+"1"+"FFFFFFFFFFFFFFFFFFFF", filePath, fileName, "GBK" );
            
            
            for(int i=0;i<recordNum;i++){
                StringBuffer sb = new StringBuffer("");
                sb.append("9451");//暂时先用这个
                sb.append("06");
                sb.append(StringUtil.getRandomStringAccordingSystemtimeForNumberFlag(20, 0));
                sb.append("100000010001"+"      ");
                sb.append("100000000001");//Psam卡编号
                sb.append("000000000002");//终端流水号
                sb.append("900000000000000000000000000009");//终端主板号
                sb.append("70000000000000000007");//操作员编号
                sb.append("100000000000001");//发卡机构ID
                sb.append("80000000000000000008");//卡号
                sb.append("20180703104301186610");//账户号
                sb.append("8000");//卡账户类型
                sb.append("00");//发码渠道
                sb.append("258");//二维码明文 前3位表示长度 发码渠道为00-自建码时必填
                sb.append("574854544f4b454e0000760000000032303138303931303135343833363334323754303130323235595836374e443620202020202020200003e8315b963cb1025822a1c2d126c558a4a2d2026b6cd2dd6f7779ca04c6024875aaf1be0c6de3f596bb2f1bf2e8c541daf0298d488e32675ff5f986c9876de744a355aec3057f9f58");//二维码明文 前3位表示长度 发码渠道为00-自建码时必填
                sb.append("0000000000");//交易前余额（分）  十进制
                sb.append("00000010");//交易金额（分）
                sb.append("00000010");//应收金额（分）
                sb.append("20180621");//交易发生日期 YYYYMMDD
                sb.append("160154");//交易发生时间 Hhmmss
                sb.append("156 ");//交易货币代码
                sb.append("00000000");//其它金额（分）
                sb.append("156 ");//国家代码
                sb.append("01");//计次票类型 00-公交；01-地铁
                sb.append("1                               ");//终端密钥版本
                sb.append("20000002");//终端TAC
                sb.append("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF");//保留域
                
                //特有数据
                StringBuffer str = new StringBuffer("");
                str.append("0001");//细目记录数
                str.append("1001");//细目记录类型
                str.append("00");//补采标志 00-非补采；01-补采
                str.append("100.000001"+"          ");//longitude	ANS20	经度
                str.append("101.000001"+"          ");//latitude	ANS20	纬度
                str.append("1000000001"+"          ");//售票员员工号 不足20位右补空格
                str.append("00");//本次优惠类型
                str.append("00000");//换乘后公交被优惠金额
                str.append("00000");//本次换乘优惠金额
                str.append("000");//连续已换乘优惠次数
                str.append("3000000003"+"        ");//换乘优惠上次交易设备编号
                str.append("123456");//换乘优惠上次交易线路编号
                str.append("20180621145358");//换乘优惠上次交易交易时间
                str.append("00");//换乘优惠上次行业信息
                str.append("00");//当前行业信息
                str.append("FFFFFFFFFF");//换乘信息保留字段
                str.append("FF");//方向 00-上行；01-下行；FF-未知
                str.append("20180621145758");//上车时间
                str.append("100000010001"+"      ");//上车机具编号
                str.append("10000001");//上车车号
                str.append("200002");//上车线路
                str.append("300003");//上车站号
                str.append("40000004");//下车车号
                str.append("500005");//下车线路号
                str.append("600006");//下车站点号
                str.append("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF");//保留字段

                sb.append(str);//特有数据
                FileUtil.getInstant().writeLineInFile(sb.toString(), filePath, fileName, "GBK");
            }
            
            System.out.println("生成完成！");
        }catch(Exception e){
            e.printStackTrace();
        }
	}
}
