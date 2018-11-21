package cn.com.izj.utils;

import cn.com.izj.base.CONSTANT;
import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import com.qiniu.util.UrlSafeBase64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author: 朱鸿平
 * @date: 2018/7/24 23:17
 */
public class QiNiuUploadFile {
    private static Logger logger = LoggerFactory.getLogger(QiNiuUploadFile.class);
    // 密钥配置
    private static final Auth AUTH = Auth.create(CONSTANT.QINIU_ACCESS_KEY, CONSTANT.QINIU_SECRET_KEY);
    public static final StringMap STRING_MAP = new StringMap().put("returnBody",
            "{\"key\": $(key), \"hash\": $(etag), \"w\": $(imageInfo.width), \"h\": $(imageInfo.height)}");

    public static String fileUpload(InputStream stream, String name) {
        //构造一个带指定Zone对象的配置类
        Configuration cfg = new Configuration(Zone.zone0());
        //...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
        //...生成上传凭证，然后准备上传
        String accessKey = CONSTANT.QINIU_ACCESS_KEY;
        String secretKey = CONSTANT.QINIU_SECRET_KEY;
        String bucket = CONSTANT.QINIU_BUKET;
        //默认不指定key的情况下，以文件内容的hash值作为文件名
        try {
            ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
            byte[] buff = new byte[100];
            int rc;
            while ((rc = stream.read(buff, 0, 100)) > 0) {
                swapStream.write(buff, 0, rc);
            }
            byte[] uploadBytes = swapStream.toByteArray();
            ByteArrayInputStream byteInputStream = new ByteArrayInputStream(uploadBytes);
            Auth auth = Auth.create(accessKey, secretKey);
            String upToken = auth.uploadToken(bucket,name);
            try {
                Response response = uploadManager.put(byteInputStream, name, upToken, null, null);
                //解析上传成功的结果
                DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
                if (putRet != null) {
                    logger.info("上传文件成功：orderNum: " + name);
                    return CONSTANT.PREFIX_URL + name;
                }
            } catch (QiniuException ex) {
                Response r = ex.response;
                logger.error("上传失败：orderNum: " + name + r.getInfo());
            }
        } catch (IOException e) {
            logger.error("上传失败：orderNum: " + name + e.getMessage());
        }
        return "";
    }

    public static String getIdNumber(String url) throws Exception {
        String method = "POST";
        String path = "/v1/ocr/sari/idcard";
        String host = "argus.atlab.ai";
        String contentType = "application/json";
        String bodyStr = "{\"data\": {\"uri\":\"" + url + "\"}}";
        String data = method + " " + path + "\nHost: " + host + "\nContent-Type: " + contentType + "\n\n" + bodyStr;
        return "Qiniu " + CONSTANT.QINIU_ACCESS_KEY + ":" + sign(data);
    }

    private static String sign(String data) throws Exception {
        String ENCODING = "UTF-8";
        String ALGORITHM = "HmacSHA1";
        Mac mac = Mac.getInstance(ALGORITHM);
        String acKey = CONSTANT.QINIU_SECRET_KEY;
        mac.init(new SecretKeySpec(acKey.getBytes(ENCODING), ALGORITHM));
        byte[] signData = mac.doFinal(data.getBytes(ENCODING));
        return UrlSafeBase64.encodeToString(signData);
    }
}
