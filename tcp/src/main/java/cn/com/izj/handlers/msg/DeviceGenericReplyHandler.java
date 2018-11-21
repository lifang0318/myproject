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
 * 终端通用应答处理
 * Created by GouBo on 2018/7/15 18:06.
 */
@Component
public class DeviceGenericReplyHandler implements MessageHandler {
    @Override
    public void handle(ChannelHandlerContext ctx, Message message) {
        byte[] msgBody = message.getMessageBody();

        short replySerialNo = ByteArrayUtil.getShort(Arrays.copyOfRange(msgBody, 0, 2));
        short replyId = ByteArrayUtil.getShort(Arrays.copyOfRange(msgBody, 2, 4));
        byte result = msgBody[4];

        if (MessageIds.OPEN_OR_CLOSE_DOOR == replyId ||
                MessageIds.RETURN_CAR == replyId ||
                MessageIds.WINDOW_CONTROL == replyId) {
            //这几类操作另外有单独的响应
            return;
        }

        String deviceId = message.getDeviceId();
        String idStr = replyId + "_" + deviceId + "_" + replySerialNo;

        NettyResult nettyResult = DeviceService.result.get(idStr);
        if (nettyResult != null) {
            nettyResult.setMessage(result);
            nettyResult.getLatch().countDown();
        }
    }
}
