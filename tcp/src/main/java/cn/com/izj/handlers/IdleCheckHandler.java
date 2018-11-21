package cn.com.izj.handlers;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 空闲检测
 * Created by GouBo on 2018/7/16 23:32.
 */
public class IdleCheckHandler extends IdleStateHandler {

    private static final int READ_IDLE_TIME = 60;

    private static final int WRITE_IDLE_TIME = 60;

    private static final int ALL_IDLE_TIME = 0;

    private static Logger logger = LoggerFactory.getLogger(IdleCheckHandler.class);

    public IdleCheckHandler() {
        super(READ_IDLE_TIME, WRITE_IDLE_TIME, ALL_IDLE_TIME);
    }

    @Override
    protected void channelIdle(ChannelHandlerContext ctx, IdleStateEvent evt) throws Exception {
        if (IdleStateEvent.FIRST_READER_IDLE_STATE_EVENT == evt) {
            logger.warn("channel read timeout {}", ctx.channel());
            ctx.channel().close();
        }
        super.channelIdle(ctx, evt);
    }
}