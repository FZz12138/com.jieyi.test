package com.whcst;

import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		 // 1). 客户端工厂类
		JaxWsProxyFactoryBean proxy = new JaxWsProxyFactoryBean();

		 // 2). 设置了两个属性
		proxy.setAddress("http://27.17.15.59:8027/SiteWebservice/SiteWebservice.asmx");
		proxy.setServiceClass(SiteWebserviceSoap.class);

		// 添加输入&输出日志（可选）
//		proxy.getInInterceptors().add(new LoggingInInterceptor());
//		proxy.getOutInterceptors().add(new LoggingOutInterceptor());

		// 3). 创建会发起一个http请求
		SiteWebserviceSoap  staticClient =  (SiteWebserviceSoap) proxy.create();

		// 4). 调用方法，获得数据
		long start = System.currentTimeMillis();
		//System.out.println(staticClient.sendCode("15601838927", "1"));
		//System.out.println(staticClient.sendCode("15601838927", "2"));
		//System.out.println(staticClient.sendCode("15601838927", "3"));
		System.out.println(staticClient.sendCode("15601838927", "4"));
		//System.out.println(staticClient.sayHelloUser(new User("Tony")));
		long end = System.currentTimeMillis();
		System.out.println("cost:"+(end-start)+"ms");
	}

}
