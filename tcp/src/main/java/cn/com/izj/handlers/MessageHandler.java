package cn.com.izj.handlers;

import cn.com.izj.protocol.Message;
import io.netty.channel.ChannelHandlerContext;

/**
 * 具体消息处理
 * Created by GouBo on 2018/7/12 19:44.
 */
public interface MessageHandler {
    void handle(ChannelHandlerContext ctx, Message message);
}
