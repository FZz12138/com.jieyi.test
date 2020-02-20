package com.jieyi.pay;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayUserAgreementPageSignRequest;
import com.alipay.api.response.AlipayUserAgreementPageSignResponse;

public class AlipaySign {
	public static void main(String[] args) throws Exception {
//		String privateKey = "MIIEugIBADANBgkqhkiG9w0BAQEFAASCBKQwggSgAgEAAoIBAQCUK/kmr8hY63YQojnjb5/+CemSOTPiO8tGif9OPHjRGFPGMSf1coq4gcIsxDoc5bvqDWB6PWGHsUZqeZ+3Q3pjzjrO1bIMdOfSYtdllQJHLk3oyX3G8T8S5cBvJVWfSNu/KTI33IRdyUAI6gnQNSFlffCUbJ1qlrYaNu/Bcf2pjTZdYnEypuAIdy5bvINmolzNfRK4cATCe8eVeO3N4Mxdm17GlImk8bcF3uY/JXQvofPuLeW6f7cp2KumMVZ3XFOU/z/KWD1DYdCW5mrCw43AVo2gGHefV7usiZ38ew+BFkeO+jZPOvqPmv8hZJjqW1p0FdhJAfc2n3HySA1YOeUjAgMBAAECggEAIc1yVpANjbORyLOprygdANB0F6azHm5zrlD3DHshONpq6jD2o7kQyozXpmzRsRv75aZNlzV/lXwsy0eZ1y4usqwuoFcB+nOFG2rvJr56uZBnt66X4hXRRNdjMjf7M45ki1SznyjQjbDGygHSYUxtpQC5LLMmqlVdj9dulNwTeWVjxnViLRAxgb4CGqMmSlvYKkIe57xOuTHy/KXx8dCvUMm/D0oZyqDakhLHIYBUWb2Az8iVZqLrzzdZuxQxNeJjvFbM2yprV0IeoACImLZrQuS/5pnEZ3p/fTfC6loLRV8r4uSB7gWYPKFd6trdk5eVY4fQhx+7Xpa6UoVk1xBh8QKBgQDMIv2NLGUeyhjcYszd9AA5ptDHU/mFvLCdp2neDatAyYf4R2bcPuI4UWY6icKfkhOe4PrFihzDE62ezjDPWrZhDU7kHxR7JOEHOhkyENCjR+3NY9KgGIs3Of0NlyYTW4XDKSh32QYkTjl7K9mmSTiIlu6kZsQ598eG3u8C2G0kKQKBgQC50Qk4+qiVXAh4WahH0A1Md1UsAljDc/uvpa7JwfqoirEInOrqOQi+x2uNI9GmiY8tzTkQalLrSIGAymh0/rDGfs9qRnEtBWS5gUDBKHRCrj4RWzgTQ1rKJCy1nlESAVNVbelb7eJngKfc4+A+xAvXcaMIRgb5A9ZYMZL16PaIawJ/GLgj+/RkSlYurIFKTZY+cLzCi2Cf1pIC0RR0vW7qTe4ZiPccfY8f1bIFrxD/gFMwDQ0xULpowTagz5ooTvOtIhN6eiVJVaAhn75pTqa6nQsYwMZVuw09znI3Mm4bLjzm9bsGK30cQUk3rLKiqIA1WFTukPngySQ4BRz51F5g2QKBgBCdZFm38AMK66b3PVjOfaHsuazpuM4hPMwD4JEhyyvu2s2OWmL/Ho0Qf8lq/oAJpMfPpd6V4zwui4Xck2KCs4PMKvexvMTtyofbytJ6r4qW0e3zXhw61Y9llKnYsh8OB0mIPml959ShaGET7yUtmnF1M8K1oEH72dqg9CcQ4ZppAoGAR4sAPr5dy5tLg9Xghts0ENKhmxkL58GlAI8x0yAdSWliKFVgA9JA2vuOiptcH78++CrZcURbXFLPXT04KnOUPeY8uASmJbzofqbSkV8pIC7PtGENyy0vPopUphB9eESTdtMcXWKAdI7qM57EGZdOfO01B4uJKqJmWKCq1z5teEo=";
//		String aliPublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAu46AZNKy515bLRLJbysQOH3dLQUJ1qdd9KcWris8zKxxL+Fj0CIY3eNXNkb33guZfA+jkfv3jsxI41f3DdHh5ddRVAx9yI5EAce8PQgP6u4O35YXoh9XYMs1tj1BQNdZ75Pp2FWPjhVfLTHUzkDlTASynuPwr4gGmDw3Eln7SwLE/V9tSQCvIQGrbnsoT6eHTyAewW++ieknk5PlJYiI1+7WpXMGtHRWCW9NJhUxuSLJRTzH35PbCqcQeYlAtyajUYaWYO0RzjXn+m+KO7ZReFrtOvm0wwK6BjXoUk2+4UdS2+KGWSx61LzRKDTN6tl1TPvt5LV1Q2EqqgVllceLqwIDAQAB";
//		String appId = "2018090761261691";
		
		String privateKey = "MIIEugIBADANBgkqhkiG9w0BAQEFAASCBKQwggSgAgEAAoIBAQCUK/kmr8hY63YQojnjb5/+CemSOTPiO8tGif9OPHjRGFPGMSf1coq4gcIsxDoc5bvqDWB6PWGHsUZqeZ+3Q3pjzjrO1bIMdOfSYtdllQJHLk3oyX3G8T8S5cBvJVWfSNu/KTI33IRdyUAI6gnQNSFlffCUbJ1qlrYaNu/Bcf2pjTZdYnEypuAIdy5bvINmolzNfRK4cATCe8eVeO3N4Mxdm17GlImk8bcF3uY/JXQvofPuLeW6f7cp2KumMVZ3XFOU/z/KWD1DYdCW5mrCw43AVo2gGHefV7usiZ38ew+BFkeO+jZPOvqPmv8hZJjqW1p0FdhJAfc2n3HySA1YOeUjAgMBAAECggEAIc1yVpANjbORyLOprygdANB0F6azHm5zrlD3DHshONpq6jD2o7kQyozXpmzRsRv75aZNlzV/lXwsy0eZ1y4usqwuoFcB+nOFG2rvJr56uZBnt66X4hXRRNdjMjf7M45ki1SznyjQjbDGygHSYUxtpQC5LLMmqlVdj9dulNwTeWVjxnViLRAxgb4CGqMmSlvYKkIe57xOuTHy/KXx8dCvUMm/D0oZyqDakhLHIYBUWb2Az8iVZqLrzzdZuxQxNeJjvFbM2yprV0IeoACImLZrQuS/5pnEZ3p/fTfC6loLRV8r4uSB7gWYPKFd6trdk5eVY4fQhx+7Xpa6UoVk1xBh8QKBgQDMIv2NLGUeyhjcYszd9AA5ptDHU/mFvLCdp2neDatAyYf4R2bcPuI4UWY6icKfkhOe4PrFihzDE62ezjDPWrZhDU7kHxR7JOEHOhkyENCjR+3NY9KgGIs3Of0NlyYTW4XDKSh32QYkTjl7K9mmSTiIlu6kZsQ598eG3u8C2G0kKQKBgQC50Qk4+qiVXAh4WahH0A1Md1UsAljDc/uvpa7JwfqoirEInOrqOQi+x2uNI9GmiY8tzTkQalLrSIGAymh0/rDGfs9qRnEtBWS5gUDBKHRCrj4RWzgTQ1rKJCy1nlESAVNVbelb7eJngKfc4+A+xAvXcaMIRgb5A9ZYMZL16PaIawJ/GLgj+/RkSlYurIFKTZY+cLzCi2Cf1pIC0RR0vW7qTe4ZiPccfY8f1bIFrxD/gFMwDQ0xULpowTagz5ooTvOtIhN6eiVJVaAhn75pTqa6nQsYwMZVuw09znI3Mm4bLjzm9bsGK30cQUk3rLKiqIA1WFTukPngySQ4BRz51F5g2QKBgBCdZFm38AMK66b3PVjOfaHsuazpuM4hPMwD4JEhyyvu2s2OWmL/Ho0Qf8lq/oAJpMfPpd6V4zwui4Xck2KCs4PMKvexvMTtyofbytJ6r4qW0e3zXhw61Y9llKnYsh8OB0mIPml959ShaGET7yUtmnF1M8K1oEH72dqg9CcQ4ZppAoGAR4sAPr5dy5tLg9Xghts0ENKhmxkL58GlAI8x0yAdSWliKFVgA9JA2vuOiptcH78++CrZcURbXFLPXT04KnOUPeY8uASmJbzofqbSkV8pIC7PtGENyy0vPopUphB9eESTdtMcXWKAdI7qM57EGZdOfO01B4uJKqJmWKCq1z5teEo=";
		String aliPublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAu46AZNKy515bLRLJbysQOH3dLQUJ1qdd9KcWris8zKxxL+Fj0CIY3eNXNkb33guZfA+jkfv3jsxI41f3DdHh5ddRVAx9yI5EAce8PQgP6u4O35YXoh9XYMs1tj1BQNdZ75Pp2FWPjhVfLTHUzkDlTASynuPwr4gGmDw3Eln7SwLE/V9tSQCvIQGrbnsoT6eHTyAewW++ieknk5PlJYiI1+7WpXMGtHRWCW9NJhUxuSLJRTzH35PbCqcQeYlAtyajUYaWYO0RzjXn+m+KO7ZReFrtOvm0wwK6BjXoUk2+4UdS2+KGWSx61LzRKDTN6tl1TPvt5LV1Q2EqqgVllceLqwIDAQAB";
		String appId = "2018090761261691";
		
		AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do",appId,privateKey,"json","GBK",aliPublicKey,"RSA2");
				AlipayUserAgreementPageSignRequest request = new AlipayUserAgreementPageSignRequest();
				request.setBizContent("{" +
				"\"external_logon_id\":\"13852852877\"," +
				"\"personal_product_code\":\"GENERAL_WITHHOLDING_P\"," +
				"\"sign_scene\":\"INDUSTRY|CARRENTAL\"," +
				"\"external_agreement_no\":\"test\"," +
				"\"third_party_type\":\"PARTNER\"," +
				"\"sign_validity_period\":\"2m\"," +
				"\"product_code\":\"GENERAL_WITHHOLDING\"," +
				"\"zm_auth_params\":{" +
				"\"buckle_app_id\":\"1001164\"," +
				"\"buckle_merchant_id\":\"268820000000414397785\"" +
				" }," +
				"\"prod_params\":{" +
				"\"auth_biz_params\":\"{\\\"platform\\\":\\\"taobao\\\"}\"" +
				" }," +
				"\"promo_params\":\"{\\\"key\\\":\\\"value\\\"}\"," +
				"\"access_params\":{" +
				"\"channel\":\"ALIPAYAPP\"" +
				" }," +
				"\"identity_params\":{" +
				"\"user_name\":\"张三\"," +
				"\"cert_no\":\"61102619921108888\"" +
				" }" +
				" }");
				AlipayUserAgreementPageSignResponse response = alipayClient.execute(request);
				if(response.isSuccess()){
				System.out.println("调用成功");
				} else {
				System.out.println("调用失败");
				}
				
	}
}
