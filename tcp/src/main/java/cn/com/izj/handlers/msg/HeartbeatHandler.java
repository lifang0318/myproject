package cn.com.izj.handlers.msg;

import cn.com.izj.handlers.ChannelManager;
import cn.com.izj.handlers.MessageHandler;
import cn.com.izj.protocol.Message;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 心跳处理
 * Created by GouBo on 2018/9/9 22:15.
 */
@Component
public class HeartbeatHandler implements MessageHandler {

    private static Logger logger = LoggerFactory.getLogger(HeartbeatHandler.class);

    @Override
    public void handle(ChannelHandlerContext ctx, Message message) {
        String deviceId = message.getDeviceId();
        Channel channel = ChannelManager.getCmdChannel(deviceId);
        if (channel == null) {
            logger.error("设备" + deviceId + "出现掉线，重新上线...........");
            ChannelManager.addCmdChannel(deviceId, ctx.channel());
        }
    }
}
