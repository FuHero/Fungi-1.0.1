package com.alipay.config;

/* *
 *类名：AlipayConfig
 *功能：基础配置类
 *详细：设置帐户有关信息及返回路径
 *版本：3.3
 *日期：2012-08-10
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
	
 *提示：如何获取安全校验码和合作身份者ID
 *1.用您的签约支付宝账号登录支付宝网站(www.alipay.com)
 *2.点击“商家服务”(https://b.alipay.com/order/myOrder.htm)
 *3.点击“查询合作者身份(PID)”、“查询安全校验码(Key)”

 *安全校验码查看时，输入支付密码后，页面呈灰色的现象，怎么办？
 *解决方法：
 *1、检查浏览器配置，不让浏览器做弹框屏蔽设置
 *2、更换浏览器或电脑，重新登录查询。
 */

public class AlipayConfig {
	
	//↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
	// 合作身份者ID，以2088开头由16位纯数字组成的字符串
	//↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
	// 合作身份者ID，以2088开头由16位纯数字组成的字符串
	public static String partner = "2088511911837552";
	
	// 交易安全检验码，由数字和字母组成的32位字符串
	// 如果签名方式设置为“MD5”时，请设置该参数
	public static String key = "k108h5vvnh2y1j6vv3f27nkreo1p6m11";
	
    // 商户的私钥
    // 如果签名方式设置为“0001”时，请设置该参数
	public static String private_key = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAJ/DOyCXOqohba5NCkRllx6DQKFHnvFYCTsm+BAHXfcjExnMuJIGb7LpLhJ3+BI+dLHe+cbqZ+kRC8UygAS9D3EHcVNV3Z16kgVCCfCJeY1MqTl/lJ7i6yAgT5LjiQJAxM0xKzAWUTFGO/NMzgkw+GbK1OfaTmlGgYGEhdDkjfH9AgMBAAECgYANBv5rSemetdsbu2x6503LTyiV/iEXzPXzvNm9dNxUqqeEEw152syA6kK3ftDbq/wYBPaZIcQXXSx4iAWoXUJE9DpRV9yIkhS4Npb5hQ7UH5nxCdjCzy1gM6iLlI8ZpKYW9BDOO0tqYRSXyldlx+7VBJX0OVDBRJFfumrEAuPIAQJBAMz29ZSLtXlG+xCAXybLg5oKCkvOixu6kDGR24frMY2hOlWPFbSLnPFt9S8fTEDipG1P7Tp/XwgnJ605CQEUU20CQQDHivYtflc1iu4PFdN932sdQODXVAiq0lEIYcbTcK4HdpASbHjYHK9TUiqfQSCcUbCSpYPbWsvnnZggYMjEcW7RAkEAtS4o7+J/zHDeRjvz4ItODcCKUXCGr0A/blIMyR0ydIvs0Ozmtu4jEZetgLYd5c1yo95fSbKGAuL5w7oDJ50cEQJAbvAfNePL8ISxHcnwr0aLctQjLRQXTYFoc9SIif1Xg+ay6stL1Dr+bsB72daHnuD73kwPJuEwBDFl5npopG68QQJAFE641Dk7tAS1yWTBdTWvAQspFeb2xBNINvXMVBd3tqkCVvUp8ylaCywVefMAKFQ0YrIRlZnXjyv4JPVugOg8vg==";	// 支付宝的公钥
    // 如果签名方式设置为“0001”时，请设置该参数
	public static String ali_public_key = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCngl/OTH/gS/jRxd5Xc2u/PwklN240ittOVhm5 n9LDGVyTRBGUGz70g3BXymxgqkYPJB3Ynvsl3jDf6P/xAHpME+tGYFNGfDauCbFV7p56e1QD5ABS gqn2yzHV2Tg7DxDKswrf35eDXpLmy3xyomVFsMU+NMTB2PiiD0IDHNopXwIDAQAB";

	//↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑
	

	// 调试用，创建TXT日志文件夹路径
	public static String log_path = "D:\\";

	// 字符编码格式 目前支持 gbk 或 utf-8
	public static String input_charset = "utf-8";
	
	// 签名方式 不需修改
	public static String sign_type = "RSA";

}
