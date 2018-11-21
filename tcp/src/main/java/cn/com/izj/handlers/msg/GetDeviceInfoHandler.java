package cn.com.izj.handlers.msg;

import cn.com.izj.event.dto.GPSInfo;
import cn.com.izj.handlers.MessageHandler;
import cn.com.izj.protocol.Message;
import cn.com.izj.service.DeviceService;
import cn.com.izj.utils.MessageIds;
import cn.com.izj.utils.MessageUtil;
import cn.com.izj.utils.NettyResult;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.stereotype.Component;

/**
 * 询问终端状态响应
 * Created by GouBo on 2018/7/15 15:59.
 */
@Component
public class GetDeviceInfoHandler implements MessageHandler {
    @Override
    public void handle(ChannelHandlerContext ctx, Message message) {
        String deviceId = message.getDeviceId();
        String idStr = MessageIds.REQUEST_DEVICE_STATUS + "_" + deviceId;

        //车辆位置信息
        GPSInfo gpsInfo = MessageUtil.decodeLocationMsg(message);

        NettyResult nettyResult = DeviceService.result.get(idStr);
        if (nettyResult != null) {
            nettyResult.setMessage(gpsInfo);
            nettyResult.getLatch().countDown();
        }
    }
}
