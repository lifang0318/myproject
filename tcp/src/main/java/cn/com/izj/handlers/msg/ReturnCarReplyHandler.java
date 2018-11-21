package cn.com.izj.handlers.msg;

import cn.com.izj.handlers.MessageHandler;
import cn.com.izj.protocol.Message;
import cn.com.izj.service.DeviceService;
import cn.com.izj.utils.MessageIds;
import cn.com.izj.utils.NettyResult;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 还车命令应答处理
 * Created by GouBo on 2018/7/15 19:39.
 */
@Component
public class ReturnCarReplyHandler implements MessageHandler {
    private static Logger logger = LoggerFactory.getLogger(ReturnCarReplyHandler.class);

    @Override
    public void handle(ChannelHandlerContext ctx, Message message) {
        byte[] msgBody = message.getMessageBody();

        byte result = msgBody[4];
        logger.info("收到还车设备端响应，响应结果为:" + result);
        String deviceId = message.getDeviceId();
        String idStr = MessageIds.RETURN_CAR + "_" + deviceId;

        NettyResult nettyResult = DeviceService.result.get(idStr);
        if (nettyResult != null) {
            nettyResult.setMessage(result);
            nettyResult.getLatch().countDown();
        }
    }
}
