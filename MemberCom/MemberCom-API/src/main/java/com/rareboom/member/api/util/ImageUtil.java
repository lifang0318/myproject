package com.rareboom.member.api.util;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import com.rareboom.member.util.PropertyConfigurer;
import sun.misc.BASE64Decoder;

/**
 * 图片下载工具类
 * @author wudi
 * imageInfo  图片二进制流
 * path 虚拟路径
 */
public class ImageUtil {
	
	public static String imageDownloaded(String imageInfo,String path,String imageName){
		
		String localpath = PropertyConfigurer.getContextProperty("file.localpath");
		String resultpath = PropertyConfigurer.getContextProperty("file.resultpath");
		String fileName = localpath + path+ "\\"+ imageName +".png";//2.拼写图片存储地址
		String refileName = resultpath + path+ "/"+ imageName +".png";//3.拼写图片访问地址
		
        if(imageInfo != null && imageInfo.length() > 0){
            try {
                 
                // 将字符串转换成二进制，用于显示图片  
                // 将上面生成的图片格式字符串 imgStr，还原成图片显示  
                byte[] imgByte = base64byte2(imageInfo);  
                if(null == imgByte){
                	return "0000";
                }
                InputStream in = new ByteArrayInputStream(imgByte);
     
                String fileN = localpath + path;
        		File file = new File(fileN);//可以是任何图片格式.jpg,.png等
        		
        		//判断是否存在文件夹 没有则创建
        		if(!file.exists()){
        			 file.mkdirs();
        		}
                FileOutputStream fos=new FileOutputStream(new File(fileName));
                   
                byte[] b = new byte[1024];
                int nRead = 0;
                while ((nRead = in.read(b)) != -1) {
                    fos.write(b, 0, nRead);
                }
                fos.flush();
                fos.close();
                in.close();
            } catch (Exception e) {
                return "0000";
            } finally {
            }
        }
        return refileName;
	}

	/**
     * 二进制转字符串
     * @param b
     * @return
     */
    public static String byte2hex(byte[] b) // 二进制转字符串
    {
        StringBuffer sb = new StringBuffer();
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            stmp = Integer.toHexString(b[n] & 0XFF);
            if (stmp.length() == 1) {
                sb.append("0" + stmp);
            } else {
                sb.append(stmp);
            }
        }
        return sb.toString();
    }
 
    /**
     * 字符串转二进制
     * @param str 要转换的字符串
     * @return  转换后的二进制数组
     */
    public static byte[] hex2byte(String str) { // 字符串转二进制
        if (str == null)
            return null;
        str = str.trim();
        int len = str.length();
        if (len == 0 || len % 2 == 1)
            return null;
        byte[] b = new byte[len / 2];
        try {
            for (int i = 0; i < str.length(); i += 2) {
                b[i / 2] = (byte) Integer
                        .decode("0X" + str.substring(i, i + 2)).intValue();
            }
            return b;
        } catch (Exception e) {
            return null;
        }
    }
    
    /**
     * base64转二进制
     * @param base64
     * @return
     */
    public static byte[] base64byte2(String base64) // 二进制转字符串
    {
    	//新建一个BASE64Decoder 
    	BASE64Decoder decode = new BASE64Decoder(); 
    	//将base64转换为byte[]
    	byte[] b;
		try {
			b = decode.decodeBuffer(base64);
			return b;
		} catch (IOException e) {
			return null;
		}
    	
    }
}
