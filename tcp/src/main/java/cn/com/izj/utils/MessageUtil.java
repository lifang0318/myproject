package cn.com.izj.utils;

import cn.com.izj.event.dto.GPSInfo;
import cn.com.izj.protocol.Message;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

/**
 * Created by GouBo on 2018/7/13 22:00.
 */
public class MessageUtil {

    /**
     * 生成平台通用应答(0x8001)
     *
     * @param deviceId 车辆管理ID
     * @param serialNo 终端消息的流水号
     * @param msgId    对应的终端消息的ID
     * @return 平台通用应答
     */
    public static Message generateServerGenericReply(ChannelHandlerContext ctx, String deviceId, short serialNo, short msgId) {
        Message replyMessage = new Message();
        replyMessage.setId(MessageIds.SERVER_GENERIC_REPLY);
        replyMessage.setDeviceId(deviceId);
        replyMessage.setBodyLength((short) 5);
        replyMessage.setSerialNo(generateSerialNo(ctx.channel()));

        byte[] serialNoArr = ByteArrayUtil.getBytes(serialNo);
        byte[] msgIdArr = ByteArrayUtil.getBytes(msgId);
        byte[] result = {0};

        byte[] body = ByteArrayUtil.byteMergerAll(serialNoArr, msgIdArr, result);
        replyMessage.setMessageBody(body);
        return replyMessage;
    }


    /**
     * 解码位置信息消息
     */
    public static GPSInfo decodeLocationMsg(Message msg) {
        GPSInfo gpsInfo = new GPSInfo();

        byte[] msgBody = msg.getMessageBody();

        int latitude = ByteArrayUtil.getInt(Arrays.copyOfRange(msgBody, 8, 12));
        int longitude = ByteArrayUtil.getInt(Arrays.copyOfRange(msgBody, 12, 16));
        short altitude = ByteArrayUtil.getShort(Arrays.copyOfRange(msgBody, 16, 18));

        short speed = ByteArrayUtil.getShort(Arrays.copyOfRange(msgBody, 18, 20));
        short direction = ByteArrayUtil.getShort(Arrays.copyOfRange(msgBody, 20, 22));
        String timeStr = HexUtil.encode(Arrays.copyOfRange(msgBody, 22, 28));
        try {
            Date time = new SimpleDateFormat("yyMMddHHmmss").parse(timeStr);
            gpsInfo.setTime(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        byte battery = msgBody[28];
        String remainderRange = HexUtil.encode(Arrays.copyOfRange(msgBody, 29, 32));

        int totalMileage = ByteArrayUtil.getInt(Arrays.copyOfRange(msgBody, 32, 36));
        short voltage = ByteArrayUtil.getShort(Arrays.copyOfRange(msgBody, 36, 38));
        byte gpsSignalIntensity = msgBody[42];

        gpsInfo.setDeviceId(msg.getDeviceId());
        double[] point = GPSUtil.wgs84ToGcj02(StringUtil.getDouble(divideTenThousand(longitude)), StringUtil.getDouble(divideTenThousand(latitude)));

        gpsInfo.setLongitude(new BigDecimal(point[0]));
        gpsInfo.setLatitude(new BigDecimal(point[1]));
        gpsInfo.setAltitude(altitude);
        gpsInfo.setSpeed(speed / 10);
        gpsInfo.setDirection(direction);
        gpsInfo.setBatteryRemaining(battery);
        gpsInfo.setRemainderRange(Integer.parseInt(remainderRange) / 10);
        gpsInfo.setTotalMileage(totalMileage / 10);
        gpsInfo.setVoltage(voltage / 10);
        gpsInfo.setGpsSignalIntensity(gpsSignalIntensity);

        return gpsInfo;
    }

    /**
     * 除以一万,返回BigDecimal
     *
     * @param i
     * @return
     */
    private static BigDecimal divideTenThousand(int i) {
        BigDecimal dividend = BigDecimal.valueOf(i);
        BigDecimal divisor = BigDecimal.valueOf(1000000);
        return dividend.divide(divisor);
    }


    /**
     * 生成流水号
     */
    public static short generateSerialNo(Channel channel) {
        int serialNo = channel.attr(Constants.CHANNEL_SERIAL_NO).get();
        if (serialNo >= 255) {
            serialNo = 0;
        } else {
            serialNo++;
        }
        channel.attr(Constants.CHANNEL_SERIAL_NO).set(serialNo);
        return (short) serialNo;
    }
}
