package cn.com.izj.service;

import cn.com.izj.utils.HttpClientService;
import cn.com.izj.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 短信发送
 * Created by GouBo on 2018/9/2 23:32.
 */
@Service
public class SmsService {
    @Autowired
    private HttpClientService httpClientService;

    /**
     * 发送短信
     */
    public void smsSend(String[] mobiles, String content) {
        String sendContent = "【你行你开】" + content;

        String url = "http://118.178.86.194:8888/sms.aspx";
        Map<String, String> params = new HashMap<>();
        params.put("action", "send");
        params.put("userid", "233");
        params.put("account", "dxyz01");
        params.put("password", "123456abcd");
        params.put("mobile", StringUtil.arrayToCommaStr(mobiles));
        params.put("content", sendContent);

        try {
            httpClientService.doPost(url, params);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
