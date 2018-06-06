package com.util;


import java.util.Base64;
import java.util.Properties;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
//解码database
public class EncryptablePropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer {

	@Override
	protected void processProperties(ConfigurableListableBeanFactory beanFactoryToProcess, Properties props)
			throws BeansException {
		String attribute[]={"user","password","url","redis.pass"};
		for (String str : attribute) {
			Object object = props.get(str);
			props.setProperty(str, new String(safeUrlBase64Decode(object.toString())));
		}
		
		super.processProperties(beanFactoryToProcess, props);
	}
	   public static String safeUrlBase64Encode(byte[] data){
	        String encodeBase64 = Base64.getEncoder().encodeToString(data);
	        String safeBase64Str = encodeBase64.replace('+', '-');
	        safeBase64Str = safeBase64Str.replace('/', '_');
	        safeBase64Str = safeBase64Str.replaceAll("=", "");
	        return safeBase64Str;
	    }

	    public static byte[] safeUrlBase64Decode(final String safeBase64Str){
	        String base64Str = safeBase64Str.replace('-', '+');
	        base64Str = base64Str.replace('_', '/');
	        int mod4 = base64Str.length()%4;
	        if(mod4 > 0){
	            base64Str = base64Str + "====".substring(mod4);
	        }
	        return Base64.getDecoder().decode(base64Str);
	    }
	    public static void main(String[] args) {
//	    	String safeUrlBase64Decode = safeUrlBase64Encode("jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=utf-8&zeroDateTimeBehavior=convertToNull&transformedBitIsBoolean=true&useSSL=false".getBytes());
//	    	System.out.println(safeUrlBase64Decode);
	    	byte[] safeUrlBase64Decode = safeUrlBase64Decode("amRiYzpteXNxbDovLzEwMy40NS4xMDEuMTA5OjQ0MDYvdGVzdD91c2VVbmljb2RlPXRydWUmY2hhcmFjdGVyRW5jb2Rpbmc9dXRmLTgmemVyb0RhdGVUaW1lQmVoYXZpb3I9Y29udmVydFRvTnVsbCZ0cmFuc2Zvcm1lZEJpdElzQm9vbGVhbj10cnVlJnVzZVNTTD1mYWxzZQ");
	    	System.out.println(new String(safeUrlBase64Decode));
	    }
	    
		    
}