package cn.com.izj.handlers.msg;

import cn.com.izj.event.DeviceEventPublish;
import cn.com.izj.event.dto.BluePassword;
import cn.com.izj.handlers.MessageHandler;
import cn.com.izj.protocol.Message;
import cn.com.izj.utils.MessageIds;
import cn.com.izj.utils.MessageUtil;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 蓝牙密码上报处理
 * Created by GouBo on 2018/9/2 16:31.
 */
@Component
public class ReportBluePasswordHandler implements MessageHandler {
    private static final byte[] seed = new byte[]{1, 2, 3, 4, 5, 6, 7, 8};

    @Autowired
    private DeviceEventPublish deviceEventPublish;

    @Override
    public void handle(ChannelHandlerContext ctx, Message message) {
        StringBuilder stringBuilder = new StringBuilder();

        byte[] body = message.getMessageBody();
        Message replyMessage;
        if (body.length != 8) {
            replyMessage = generateReply(ctx, message, 1);
        } else {
            for (int i = 0; i < 8; i++) {
                stringBuilder.append(body[i] ^ seed[i]);
            }
            String deviceId = message.getDeviceId();
            String bluePassword = stringBuilder.toString();

            //发送蓝牙密码上报事件
            deviceEventPublish.publishReportBluePasswordEvent(new BluePassword(deviceId, bluePassword));

            replyMessage = generateReply(ctx, message, 0);
        }
        ctx.channel().writeAndFlush(replyMessage);
    }

    /**
     * 生成响应消息
     */
    private Message generateReply(ChannelHandlerContext ctx, Message message, int code) {
        Message replyMessage = new Message();
        replyMessage.setId(MessageIds.REPORT_BLUE_PASSWORD_REPLY);
        replyMessage.setDeviceId(message.getDeviceId());
        replyMessage.setBodyLength((short) 1);
        replyMessage.setSerialNo(MessageUtil.generateSerialNo(ctx.channel()));

        replyMessage.setMessageBody(new byte[]{(byte) code});
        return replyMessage;
    }
}
