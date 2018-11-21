package cn.com.izj.handlers.msg;

import cn.com.izj.handlers.MessageHandler;
import cn.com.izj.protocol.Message;
import cn.com.izj.utils.MessageIds;
import cn.com.izj.utils.MessageUtil;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.stereotype.Component;

/**
 * 终端有密码键盘开锁动作命令
 * Created by GouBo on 2018/9/2 15:31.
 */
@Component
public class PasswordOpenHandler implements MessageHandler {
    @Override
    public void handle(ChannelHandlerContext ctx, Message message) {
        byte[] msgBody = message.getMessageBody();
        byte password = msgBody[0];

        Message replyMessage = generateReply(ctx, message);
        ctx.channel().writeAndFlush(replyMessage);
    }

    /**
     * 生成响应消息
     */
    private Message generateReply(ChannelHandlerContext ctx, Message message) {
        Message replyMessage = new Message();
        replyMessage.setId(MessageIds.SERVER_GENERIC_REPLY);
        replyMessage.setDeviceId(message.getDeviceId());
        replyMessage.setBodyLength((short) 1);
        replyMessage.setSerialNo(MessageUtil.generateSerialNo(ctx.channel()));

        replyMessage.setMessageBody(new byte[]{(byte) 0});
        return replyMessage;
    }
}
