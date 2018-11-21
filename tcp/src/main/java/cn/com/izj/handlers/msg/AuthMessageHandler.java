package cn.com.izj.handlers.msg;

import cn.com.izj.event.DeviceEventPublish;
import cn.com.izj.event.dto.DeviceOnline;
import cn.com.izj.handlers.ChannelManager;
import cn.com.izj.handlers.MessageHandler;
import cn.com.izj.protocol.Message;
import cn.com.izj.service.DeviceService;
import cn.com.izj.utils.*;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

/**
 * 处理终端鉴权
 * Created by GouBo on 2018/7/12 19:43.
 */
@Component
public class AuthMessageHandler implements MessageHandler {

    private static Logger logger = LoggerFactory.getLogger(AuthMessageHandler.class);

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private DeviceEventPublish deviceEventPublish;

    @Override
    public void handle(ChannelHandlerContext ctx, Message message) {
        String deviceId = message.getDeviceId();
        ChannelManager.addCmdChannel(deviceId, ctx.channel());
        logger.info("收到终端鉴权消息,设备id为:" + message.getDeviceId());

        byte[] messageBody = message.getMessageBody();

        //鉴权码
        int authCode = ByteArrayUtil.getInt(Arrays.copyOfRange(messageBody, 0, 4));
        //设备编号
        int deviceNo = ByteArrayUtil.getInt(Arrays.copyOfRange(messageBody, 4, 8));
        //车辆类型
        byte carType = messageBody[8];
        //手机卡ccid
        String ccid = new String(Arrays.copyOfRange(messageBody, 9, 29));
//        String ccid = HexUtil.convertHexToString(HexUtil.encode(Arrays.copyOfRange(messageBody, 9, 29)));

        deviceEventPublish.publishDeviceOnlineEvent(new DeviceOnline(deviceId, carType, ccid));

        Message replyMessage = generateReply(ctx, message);
        ctx.channel().writeAndFlush(replyMessage);

        //随机生成蓝牙密码
        deviceService.resetBluePassword(deviceId, RandomStringUtil.randomStr(8));
    }

    /**
     * 生成响应消息
     */
    private Message generateReply(ChannelHandlerContext ctx, Message message) {
        Message replyMessage = new Message();
        replyMessage.setId(MessageIds.DEVICE_AUTH_REPLY);
        replyMessage.setDeviceId(message.getDeviceId());
        replyMessage.setBodyLength((short) 11);
        replyMessage.setSerialNo(MessageUtil.generateSerialNo(ctx.channel()));

        String dateStr = new SimpleDateFormat("yyMMddHHmmss").format(new Date());

        byte[] serialNo = ByteArrayUtil.getBytes(message.getSerialNo());
        byte[] msgId = ByteArrayUtil.getBytes(message.getId());
        byte[] result = {0};
        byte[] time = HexUtil.decodeToByteArray(dateStr);

        byte[] body = ByteArrayUtil.byteMergerAll(serialNo, msgId, result, time);

        replyMessage.setMessageBody(body);

        return replyMessage;
    }
}
