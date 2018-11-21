package cn.com.izj.handlers.msg;

import cn.com.izj.base.CONSTANT;
import cn.com.izj.event.DeviceEventPublish;
import cn.com.izj.event.dto.GPSInfo;
import cn.com.izj.handlers.ChannelManager;
import cn.com.izj.handlers.MessageHandler;
import cn.com.izj.protocol.Message;
import cn.com.izj.utils.MessageUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * 位置信息上传消息处理
 * Created by GouBo on 2018/7/13 21:52.
 */
@Component
public class LocationUpHandler implements MessageHandler {

    private static Logger logger = LoggerFactory.getLogger(LocationUpHandler.class);

    @Autowired
    private DeviceEventPublish deviceEventPublish;

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    @Override
    public void handle(ChannelHandlerContext ctx, Message msg) {
        logger.info("收到位置信息上传,设备id为:" + msg.getDeviceId());

        String deviceId = msg.getDeviceId();
        Channel channel = ChannelManager.getCmdChannel(deviceId);
        if (channel == null) {
            logger.error("设备" + deviceId + "出现掉线，重新上线...........");
            ChannelManager.addCmdChannel(deviceId, ctx.channel());
        }

        GPSInfo gpsInfo = MessageUtil.decodeLocationMsg(msg);
        //上报定位信息
        deviceEventPublish.publishReportCarInfoEvent(gpsInfo);

        //位置信息存入redis
        redisTemplate.opsForValue().set(buildKey(msg.getDeviceId()), gpsInfo, 60, TimeUnit.SECONDS);

        Message replyMessage = MessageUtil.generateServerGenericReply(ctx, msg.getDeviceId(), msg.getSerialNo(), msg.getId());
        ctx.channel().writeAndFlush(replyMessage);
    }

    private String buildKey(String deviceId) {
        return CONSTANT.GPS_INFO + deviceId;
    }
}
