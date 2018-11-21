package cn.com.izj.utils;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 微信相关
 *
 * @author lifang
 */
public class WeChatUtil {

    /**
     * 生成给定长度的随机字符串
     *
     * @param length
     * @return
     */
    public static String getRandomString(int length) {
        StringBuffer buffer = new StringBuffer("0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ");
        StringBuffer sb = new StringBuffer();
        Random random = new Random();
        int range = buffer.length();
        for (int i = 0; i < length; i++) {
            sb.append(buffer.charAt(random.nextInt(range)));
        }
        return sb.toString();
    }

    /**
     * 创建sign签名
     *
     * @param characterEncoding 编码格式
     * @param parameters        请求参数
     * @param clienteScret      API密钥
     * @return
     */
    public static String createSign(String clienteScret, String characterEncoding, SortedMap<String, String> parameters) {
        StringBuffer stringSignTemp = new StringBuffer();
        Set es = parameters.entrySet();
        Iterator it = es.iterator();
        while (it.hasNext()) {
            Map.Entry<String, String> entry = (Map.Entry<String, String>) it.next();
            String key = entry.getKey();
            Object val = entry.getValue();
            if (null != val && !"".equals(val) && !"sign".equals(key) && !"key".equals(key)) {
                stringSignTemp.append(key + "=" + val + "&");
            }
        }
        stringSignTemp.append("key=" + clienteScret);
        return WeMd5.encode(stringSignTemp.toString(), characterEncoding).toUpperCase();
    }

    /**
     * 生成xml
     *
     * @param reqMap
     * @return
     */
    public static String creatXml(Map<String, String> reqMap) {
        StringBuffer sb = new StringBuffer();
        sb.append("<xml>");
        Set es = reqMap.entrySet();
        Iterator it = es.iterator();
        while (it.hasNext()) {
            Map.Entry<String, String> entry = (Map.Entry<String, String>) it.next();
            String key = entry.getKey();
            String val = entry.getValue();
            if ("attach".equalsIgnoreCase(key) || "body".equalsIgnoreCase(key) || "sign".equalsIgnoreCase(key)) {
                sb.append("<" + key + ">" + "<![CDATA[" + val + "]]></" + key + ">");
            } else {
                sb.append("<" + key + ">" + val + "</" + key + ">");
            }
        }
        sb.append("</xml>");
        return sb.toString();
    }

    /**
     * xml转json字符串
     *
     * @param xml
     * @return
     */
    public static String xml2JSON(String xml) {
        JSONObject obj = new JSONObject();
        try {
            InputStream is = new ByteArrayInputStream(xml.getBytes("utf-8"));
            SAXBuilder sb = new SAXBuilder();
            Document doc = sb.build(is);
            Element root = doc.getRootElement();
            obj.put(root.getName(), iterateElement(root));
            return obj.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取xml编码字符集
     *
     * @param element
     * @return
     */
    private static SortedMap iterateElement(Element element) {
        List jiedian = element.getChildren();
        Element et;
        SortedMap obj = new TreeMap();
        List list = null;
        for (int i = 0; i < jiedian.size(); i++) {
            list = new LinkedList();
            et = (Element) jiedian.get(i);
            if (et.getTextTrim().equals("")) {
                if (et.getChildren().size() == 0) {
                    continue;
                }
                if (obj.containsKey(et.getName())) {
                    list = (List) obj.get(et.getName());
                }
                list.add(iterateElement(et));
                if (list.size() == 1) {
                    obj.put(et.getName(), list.get(0));
                } else {
                    obj.put(et.getName(), list);
                }
            } else {
                if (obj.containsKey(et.getName())) {
                    list = (List) obj.get(et.getName());
                }
                list.add(et.getTextTrim());
                if (list.size() == 1) {
                    obj.put(et.getName(), list.get(0));
                } else {
                    obj.put(et.getName(), list);
                }
            }
        }
        return obj;
    }

    /**
     * 签名校验
     *
     * @param map
     * @param clientSecret
     * @return
     */
    public static boolean verifySign(SortedMap<String, String> map, String clientSecret) {
        String sign = createSign(clientSecret, "UTF-8", map);
        if (sign.equals(map.get("sign"))) {
            return true;
        }
        return false;
    }

    /**
     * xml转SortedMap
     * @param xml
     * @return
     */
    public static SortedMap xmlToSortedMap(String xml){
        SortedMap sortedMap = new TreeMap();
        try {
            InputStream is = new ByteArrayInputStream(xml.getBytes("utf-8"));
            SAXBuilder sb = new SAXBuilder();
            Document doc = sb.build(is);
            Element root = doc.getRootElement();
            sortedMap.put(root.getName(), iterateElement(root));
            return sortedMap;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     *
     * @param dateStr 格式：yyyyMMddHHmmss
     * @return
     */
    public static Date convertByDateStr(String dateStr) throws ParseException {
        Date date = null;
        if(StringUtils.isNotEmpty(dateStr) && dateStr.length() == 14){
            String year = dateStr.substring(0,4);
            String month = dateStr.substring(4,6);
            String day = dateStr.substring(6,8);
            String hour = dateStr.substring(8,10);
            String minute = dateStr.substring(10,12);
            String seconds = dateStr.substring(12,14);
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            date = format.parse(year+"-" + month +"-"+ day +" "+ hour +":"+ minute +":"+ seconds);
        }
       return date;
    }
}
