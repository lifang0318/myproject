package cn.com.izj.utils;

import cn.com.izj.amap.StaticMapParams;
import cn.com.izj.base.CONSTANT;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.tomcat.util.bcel.Const;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.io.*;
import java.util.List;

/**
 * 高德地图操作工具类
 *
 * @author lifang
 */
@Component
public class AMapUtil {

    private static CloseableHttpClient httpClient;

    static {
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
        connectionManager.setMaxTotal(200);//设置最大连接数200
        connectionManager.setDefaultMaxPerRoute(20);//设置每个路由默认连接数
        httpClient = HttpClients.createMinimal(connectionManager);
    }

    /**
     * 行程订单轨迹图片文件生成，以订单号为文件名
     *
     * @param orderNumber    订单号，作为文件名，后缀为.png,如orderNumber为1，则图片文件名为1.png
     * @param params         配置参数，例如图片尺寸、折线颜色等
     * @param contrailPoints 参与绘制的坐标点
     */
    public String tripContrailImageGen(String orderNumber, StaticMapParams params, List<JSONObject> contrailPoints) {
        Assert.notEmpty(contrailPoints, "contrailPoints is required,it can not be empty");
        StringBuilder locations = new StringBuilder();
        for (JSONObject jo : contrailPoints) {
            locations.append(jo.get("lon")).append(",").append(jo.get("lat")).append(";");
        }
        String locationsStr = locations.substring(0, locations.length() - 1);

        String url = CONSTANT.AMAP_TRIPCONTRAIL_IMAGE_URL + "?" + "size=500*500"
                + "&paths=" + params.getWeight() + "," + params.getColor() + "," + params.getTransparency() + ",,:" + locationsStr
                + "&key=" + CONSTANT.AMAP_KEY;
        // 创建http GET请求
        HttpGet httpGet = new HttpGet(url);
        CloseableHttpResponse response = null;
        OutputStream os = null;
        try {
//            os = new FileOutputStream(CONSTANT.TRIPCONTRAIL_IMAGE_SAVE_PATH + orderNumber + ".png");
            response = httpClient.execute(httpGet);
            InputStream is = response.getEntity().getContent();
            return QiNiuUploadFile.fileUpload(is, orderNumber + ".png");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
//            if (os != null) {
//                try {
//                    os.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return "";
    }

}
