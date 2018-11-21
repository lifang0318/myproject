package cn.com.izj.handlers.msg;

import cn.com.izj.handlers.MessageHandler;
import cn.com.izj.protocol.Message;
import cn.com.izj.service.DeviceService;
import cn.com.izj.utils.ByteArrayUtil;
import cn.com.izj.utils.MessageIds;
import cn.com.izj.utils.NettyResult;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * 车窗控制命令应答处理
 * Created by GouBo on 2018/7/15 19:44.
 */
@Component
public class WindowControlReplyHandler implements MessageHandler {
    @Override
    public void handle(ChannelHandlerContext ctx, Message message) {
        byte[] msgBody = message.getMessageBody();

        short replySerialNo = ByteArrayUtil.getShort(Arrays.copyOfRange(msgBody, 0, 2));
        byte result = msgBody[4];

        String deviceId = message.getDeviceId();
        String idStr = MessageIds.WINDOW_CONTROL + "_" + deviceId + "_" + replySerialNo;

        NettyResult nettyResult = DeviceService.result.get(idStr);
        if (nettyResult != null) {
            nettyResult.setMessage(result);
            nettyResult.getLatch().countDown();
        }
    }
}
