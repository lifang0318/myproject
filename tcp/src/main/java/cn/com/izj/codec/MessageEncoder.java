package cn.com.izj.codec;

import cn.com.izj.protocol.Message;
import cn.com.izj.utils.ByteArrayUtil;
import cn.com.izj.utils.CodecUtil;
import cn.com.izj.utils.HexUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MessageEncoder extends MessageToByteEncoder<Message> {

    private static Logger logger = LoggerFactory.getLogger(MessageEncoder.class);

    @Override
    protected void encode(ChannelHandlerContext ctx, Message msg, ByteBuf out) throws Exception {
        logger.info(">>>>>>>>>>>>>>>>>>>>>>发送消息逻辑开始>>>>>>>>>>>>>>>>>>>>>>");
        byte[] bytes = messageToByteArray(msg);

        byte verifyCode = CodecUtil.calculationVerifyCode(bytes);

        String msgStr = HexUtil.encode(bytes);
        String verifyCodeStr = HexUtil.encode(verifyCode);

        String msgHex = msgStr + verifyCodeStr;
        logger.info("消息转码完成,准备转义,消息内容为:" + msgHex);

        msgHex = msgHex.replace(CodecUtil.ORIGIN_7D, CodecUtil.ESCAPE_7D);
        msgHex = msgHex.replace(CodecUtil.ORIGIN_7E, CodecUtil.ESCAPE_7E);
        msgHex = CodecUtil.ORIGIN_7E + msgHex + CodecUtil.ORIGIN_7E;
        logger.info("消息转义完成,准备发送,消息内容为:" + msgHex);

        out.writeBytes(HexUtil.decodeToByteArray(msgHex));
        logger.info("消息发送完成");
    }


    /**
     * 将message对象转为byte数
     */
    private byte[] messageToByteArray(Message msg) {
        byte[] msgId = ByteArrayUtil.getBytes(msg.getId());
        byte[] bodyLength = ByteArrayUtil.getBytes(msg.getBodyLength());
        byte[] deviceId = HexUtil.decodeToByteArray(msg.getDeviceId());
        byte[] serialNo = ByteArrayUtil.getBytes(msg.getSerialNo());
        byte[] messageBody = msg.getBodyLength() == 0 ? new byte[0] : msg.getMessageBody();

        return ByteArrayUtil.byteMergerAll(msgId, bodyLength, deviceId, serialNo, messageBody);
    }


}