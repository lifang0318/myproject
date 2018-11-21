package cn.com.izj.codec;

import cn.com.izj.protocol.Message;
import cn.com.izj.utils.ByteArrayUtil;
import cn.com.izj.utils.CodecUtil;
import cn.com.izj.utils.HexUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

public class MessageDecoder extends DelimiterBasedFrameDecoder {

    private static Logger logger = LoggerFactory.getLogger(MessageDecoder.class);

    /**
     * max packet is 2M.
     */
    private static final int MAX_FRAME_LENGTH = 2 * 1024 * 1024;

    private static final ByteBuf DELIMITER = Unpooled.copiedBuffer(new byte[]{0x7e});

    public MessageDecoder() {
        super(MAX_FRAME_LENGTH, DELIMITER);
    }

    @Override
    protected Message decode(ChannelHandlerContext ctx, ByteBuf in2) throws Exception {
        ByteBuf in = (ByteBuf) super.decode(ctx, in2);

        if (in == null || in.writerIndex() == 0) {
            return null;
        }
        String hex = ByteBufUtil.hexDump(in);
        logger.info("<<<<<<<<<<<<<<<<<<<<<<接收消息逻辑开始<<<<<<<<<<<<<<<<<<<<<<");
        logger.info("收到消息,消息内容为:" + hex);

        hex = hex.replace(CodecUtil.ESCAPE_7E, CodecUtil.ORIGIN_7E);
        hex = hex.replace(CodecUtil.ESCAPE_7D, CodecUtil.ORIGIN_7D);
        logger.info("转义还原,消息内容为:" + hex);

        if (StringUtils.isEmpty(hex) || hex.length() < 25) {
            logger.error("消息格式错误,消息内容为:" + hex);
            return null;
        }

        //校验码
        byte verifyCode = HexUtil.decodeToByte(StringUtils.substring(hex, -2));
        //消息主体
        String body = hex.substring(0, hex.length() - 2);
        byte[] bytes = HexUtil.decodeToByteArray(body);

        if (!checkVerifyCode(verifyCode, bytes)) {
            logger.error("验证校验码失败！");
            return null;
        }

        short id = ByteArrayUtil.getShort(Arrays.copyOfRange(bytes, 0, 2));
        short bodyLength = ByteArrayUtil.getShort(Arrays.copyOfRange(bytes, 2, 4));
        String deviceId = HexUtil.encode(Arrays.copyOfRange(bytes, 4, 10));
        short serialNo = ByteArrayUtil.getShort(Arrays.copyOfRange(bytes, 10, 12));
        byte[] msgBody = Arrays.copyOfRange(bytes, 12, bytes.length);

        Message message = new Message();
        message.setId(id);
        message.setBodyLength(bodyLength);
        message.setDeviceId(deviceId);
        message.setSerialNo(serialNo);
        message.setMessageBody(msgBody);
        return message;
    }

    /**
     * 验证校验码
     */
    private boolean checkVerifyCode(byte verifyCode, byte[] bytes) {
        byte msgVerifyCode = CodecUtil.calculationVerifyCode(bytes);
        return verifyCode == msgVerifyCode;
    }
}