package cn.com.izj.service;

import cn.com.izj.event.DeviceEventPublish;
import cn.com.izj.event.dto.BluePassword;
import cn.com.izj.event.dto.KeyboardPassword;
import cn.com.izj.handlers.ChannelManager;
import cn.com.izj.protocol.Message;
import cn.com.izj.utils.*;
import io.netty.channel.Channel;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Created by GouBo on 2018/7/15 15:43.
 */
@Component
public class DeviceService {
    private static Logger logger = LoggerFactory.getLogger(DeviceService.class);

    /**
     * 请求超时时间
     */
    private static final long TIME_OUT = 10000L;

    private static final TcpResponse NOT_FIND = new TcpResponse(false, "设备没找到");
    private static final TcpResponse SEND_FAILED = new TcpResponse(false, "发送失败,请重试");
    private static final TcpResponse MSG_ERROR = new TcpResponse(false, "消息有误");

    public static ConcurrentHashMap<String, NettyResult> result = new ConcurrentHashMap<>();

    @Autowired
    private DeviceEventPublish deviceEventPublish;

    /**
     * 询问终端状态
     *
     * @param deviceId 设备id
     */
    public Object getDeviceInfo(String deviceId) {
        logger.info("DeviceService.getDeviceInfo()被调用，deviceId:" + deviceId);
        Channel channel = ChannelManager.getCmdChannel(deviceId);
        if (channel == null) {
            logger.error("DeviceService.getDeviceInfo()被调用，设备" + deviceId + "没找到！");
            return NOT_FIND;
        }
        short serialNo = MessageUtil.generateSerialNo(channel);
        String idStr = MessageIds.REQUEST_DEVICE_STATUS + "_" + deviceId;
        try {
            Message message = new Message();
            message.setId(MessageIds.REQUEST_DEVICE_STATUS);
            message.setDeviceId(deviceId);
            message.setBodyLength((short) 0);
            message.setSerialNo(serialNo);

            NettyResult nResult = new NettyResult();
            CountDownLatch latch = new CountDownLatch(1);
            nResult.setLatch(latch);

            result.put(idStr, nResult);
            channel.writeAndFlush(message).sync();
            latch.await(TIME_OUT, TimeUnit.MILLISECONDS);
            latch.countDown();

            Object obj = result.get(idStr).getMessage();
            if (obj == null) {
                return new TcpResponse(false, "发送超时");
            }
            return obj;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            result.remove(idStr);
        }
        return new TcpResponse(true);
    }

    /**
     * 服务器下发找车
     *
     * @param deviceId 设备id
     * @param code     标示，1表示鸣笛2〜3次,2表示车灯闪烁2〜3次,3,鸣笛和车灯同时作用
     */
    public TcpResponse findCar(String deviceId, int code) {
        logger.info("DeviceService.findCar()被调用，deviceId:" + deviceId);
        Channel channel = ChannelManager.getCmdChannel(deviceId);
        if (channel == null) {
            logger.error("DeviceService.findCar()被调用，设备" + deviceId + "没找到！");
            return NOT_FIND;
        }
        short serialNo = MessageUtil.generateSerialNo(channel);
        String idStr = MessageIds.FIND_CAR + "_" + deviceId + "_" + serialNo;
        try {
            Message message = new Message();
            message.setId(MessageIds.FIND_CAR);
            message.setDeviceId(deviceId);
            message.setBodyLength((short) 1);
            message.setSerialNo(serialNo);

            message.setMessageBody(new byte[]{(byte) code});

            NettyResult nResult = new NettyResult();
            CountDownLatch latch = new CountDownLatch(1);
            nResult.setLatch(latch);

            result.put(idStr, nResult);
            channel.writeAndFlush(message).sync();
            latch.await(TIME_OUT, TimeUnit.MILLISECONDS);
            latch.countDown();

            Object obj = result.get(idStr).getMessage();
            if (obj == null) {
                return new TcpResponse(false, "发送超时");
            }
            byte res = (byte) obj;
            if (res == 1) {
                return SEND_FAILED;
            } else if (res == 2) {
                return MSG_ERROR;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            result.remove(idStr);
        }
        return new TcpResponse(true);
    }

    /**
     * 服务器强制开/关门
     *
     * @param deviceId 设备id
     * @param code     开门关门标示，7表示开锁，8表示关锁
     */
    public TcpResponse OpenOrCloseDoor(String deviceId, int code) {
        logger.info("DeviceService.OpenOrCloseDoor()被调用，deviceId:" + deviceId);
        Channel channel = ChannelManager.getCmdChannel(deviceId);
        if (channel == null) {
            logger.error("DeviceService.OpenOrCloseDoor()被调用，设备" + deviceId + "没找到！");
            return NOT_FIND;
        }
        short serialNo = MessageUtil.generateSerialNo(channel);
        String idStr = MessageIds.OPEN_OR_CLOSE_DOOR + "_" + deviceId + "_" + serialNo;
        try {
            Message message = new Message();
            message.setId(MessageIds.OPEN_OR_CLOSE_DOOR);
            message.setDeviceId(deviceId);
            message.setBodyLength((short) 1);
            message.setSerialNo(serialNo);

            message.setMessageBody(new byte[]{(byte) code});

            NettyResult nResult = new NettyResult();
            CountDownLatch latch = new CountDownLatch(1);
            nResult.setLatch(latch);

            result.put(idStr, nResult);
            channel.writeAndFlush(message).sync();
            latch.await(TIME_OUT, TimeUnit.MILLISECONDS);
            latch.countDown();

            Object obj = result.get(idStr).getMessage();
            if (obj == null) {
                return new TcpResponse(false, "发送超时");
            }
            byte res = (byte) obj;

            if (res == 1) {
                return SEND_FAILED;
            } else if (res == 2) {
                return MSG_ERROR;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            result.remove(idStr);
        }
        return new TcpResponse(true);
    }

    /**
     * 设置服务器ip和端口
     *
     * @param deviceId    设备id
     * @param ip          服务器ip
     * @param alternateIp 备用服务器ip
     * @param port        服务器端口
     */
    public TcpResponse setServerIpAndPort(String deviceId, String ip, String alternateIp, short port) {
        logger.info("DeviceService.setServerIpAndPort()被调用，deviceId:" + deviceId);
        Channel channel = ChannelManager.getCmdChannel(deviceId);
        if (channel == null) {
            logger.error("DeviceService.setServerIpAndPort()被调用，设备" + deviceId + "没找到！");
            return NOT_FIND;
        }

        short serialNo = MessageUtil.generateSerialNo(channel);
        String idStr = MessageIds.SET_DEVICE_INFO + "_" + deviceId + "_" + serialNo;
        try {
            Message message = new Message();
            message.setId(MessageIds.SET_DEVICE_INFO);
            message.setDeviceId(deviceId);
            message.setSerialNo(serialNo);

            byte[] paramCount = {3};

            byte[] param1Id = ByteArrayUtil.getBytes(0x0013);
            byte[] param1Length = {(byte) ip.length()};
            byte[] param1Data = ip.getBytes();

            byte[] param2Id = ByteArrayUtil.getBytes(0x0017);
            byte[] param2Length = {(byte) alternateIp.length()};
            byte[] param2Data = alternateIp.getBytes();

            byte[] param3Id = ByteArrayUtil.getBytes(0x0018);
            byte[] param3Length = {2};
            byte[] param3Data = ByteArrayUtil.getBytes(port);

            byte[] body = ByteArrayUtil.byteMergerAll(paramCount,
                    param1Id, param1Length, param1Data,
                    param2Id, param2Length, param2Data,
                    param3Id, param3Length, param3Data);

            message.setBodyLength((short) body.length);

            message.setMessageBody(body);

            NettyResult nResult = new NettyResult();
            CountDownLatch latch = new CountDownLatch(1);
            nResult.setLatch(latch);

            result.put(idStr, nResult);
            channel.writeAndFlush(message).sync();
            latch.await(TIME_OUT, TimeUnit.MILLISECONDS);
            latch.countDown();

            Object obj = result.get(idStr).getMessage();
            if (obj == null) {
                return new TcpResponse(false, "发送超时");
            }
            byte res = (byte) obj;

            if (res == 1) {
                return SEND_FAILED;
            } else if (res == 2) {
                return MSG_ERROR;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            result.remove(idStr);
        }
        return new TcpResponse(true);
    }

    /**
     * 控制汽车供电
     *
     * @param deviceId 设备id
     * @param code     1:供电导通,默认值,表示可以正常点火启动,2:供电断开,当汽车有速度时,不断开,等车速为0时,马上断开。
     */
    public TcpResponse powerControl(String deviceId, int code) {
        logger.info("DeviceService.powerControl()被调用，deviceId:" + deviceId);
        Channel channel = ChannelManager.getCmdChannel(deviceId);
        if (channel == null) {
            logger.error("DeviceService.powerControl()被调用，设备" + deviceId + "没找到！");
            return NOT_FIND;
        }

        short serialNo = MessageUtil.generateSerialNo(channel);
        String idStr = MessageIds.POWER_CONTROL + "_" + deviceId + "_" + serialNo;
        try {
            Message message = new Message();
            message.setId(MessageIds.POWER_CONTROL);
            message.setDeviceId(deviceId);
            message.setBodyLength((short) 1);
            message.setSerialNo(serialNo);

            message.setMessageBody(new byte[]{(byte) code});

            NettyResult nResult = new NettyResult();
            CountDownLatch latch = new CountDownLatch(1);
            nResult.setLatch(latch);

            result.put(idStr, nResult);
            channel.writeAndFlush(message).sync();
            latch.await(TIME_OUT, TimeUnit.MILLISECONDS);
            latch.countDown();

            Object obj = result.get(idStr).getMessage();
            if (obj == null) {
                return new TcpResponse(false, "发送超时");
            }
            byte res = (byte) obj;
            if (res == 1) {
                return new TcpResponse(false, "车辆启动行驶中,失败");
            } else if (res == 2) {
                return new TcpResponse(false, "车辆熄火后，断电成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            result.remove(idStr);
        }
        return new TcpResponse(true);
    }

    /**
     * 还车
     *
     * @param deviceId 设备id
     */
    public TcpResponse returnCar(String deviceId) {
        logger.info("DeviceService.returnCar()被调用，deviceId:" + deviceId);
        Channel channel = ChannelManager.getCmdChannel(deviceId);
        if (channel == null) {
            logger.error("DeviceService.returnCar()被调用，设备" + deviceId + "没找到！");
            return NOT_FIND;
        }

        short serialNo = MessageUtil.generateSerialNo(channel);
        String idStr = MessageIds.RETURN_CAR + "_" + deviceId;
        try {
            Message message = new Message();
            message.setId(MessageIds.RETURN_CAR);
            message.setDeviceId(deviceId);
            message.setBodyLength((short) 0);
            message.setSerialNo(serialNo);

            NettyResult nResult = new NettyResult();
            CountDownLatch latch = new CountDownLatch(1);
            nResult.setLatch(latch);

            result.put(idStr, nResult);
            channel.writeAndFlush(message).sync();
            latch.await(TIME_OUT, TimeUnit.MILLISECONDS);
            latch.countDown();

            Object obj = result.get(idStr).getMessage();
            if (obj == null) {
                logger.info("还车消息超时……");
                return new TcpResponse(false, "发送超时");
            }

            logger.info("DeviceService拿到还车响应，结果为:" + obj);

            byte res = (byte) obj;
            String errMsg = "失败";
            switch (res) {
                case 0:
                    return new TcpResponse(true);
                case 1:
                    errMsg = "车门未关";
                    break;
                case 2:
                    errMsg = "车辆未熄火";
                    break;
                case 3:
                    errMsg = "车灯未关";
                    break;
                case 4:
                    errMsg = "手刹没有拉起";
                    break;
                case 5:
                    errMsg = "车钥匙没有拔掉";
                    break;
                case 6:
                    errMsg = "车窗未关闭";
                    break;
                case 7:
                    errMsg = "尾箱未关闭";
                    break;
                case 9:
                    errMsg = "其他原因";
                    break;
            }
            return new TcpResponse(false, errMsg);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("还车出现异常：" + e.getMessage());
        } finally {
            result.remove(idStr);
        }
        return new TcpResponse(false, "失败");
    }

    /**
     * 设置密码键盘密码
     *
     * @param deviceId 设备id
     * @param password 密码(纯数字密码)
     */
    public TcpResponse setKeyboardPassword(String deviceId, String password) {
        logger.info("DeviceService.setKeyboardPassword()被调用，deviceId:" + deviceId);
        Channel channel = ChannelManager.getCmdChannel(deviceId);
        if (channel == null) {
            logger.error("DeviceService.setKeyboardPassword()被调用，设备" + deviceId + "没找到！");
            return NOT_FIND;
        }

        if (StringUtils.isBlank(password)) {
            return MSG_ERROR;
        }

        short serialNo = MessageUtil.generateSerialNo(channel);
        String idStr = MessageIds.SET_PASSWORD + "_" + deviceId + "_" + serialNo;
        try {
            Message message = new Message();
            message.setId(MessageIds.SET_PASSWORD);
            message.setDeviceId(deviceId);
            message.setBodyLength((short) password.length());
            message.setSerialNo(serialNo);

            message.setMessageBody(HexUtil.strPasswordToByteArray(password));

            NettyResult nResult = new NettyResult();
            CountDownLatch latch = new CountDownLatch(1);
            nResult.setLatch(latch);

            result.put(idStr, nResult);
            channel.writeAndFlush(message).sync();
            latch.await(TIME_OUT, TimeUnit.MILLISECONDS);
            latch.countDown();

            Object obj = result.get(idStr).getMessage();
            if (obj == null) {
                return new TcpResponse(false, "发送超时");
            }
            byte res = (byte) obj;
            if (res == 1) {
                return new TcpResponse(false, "设置密码失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            result.remove(idStr);
        }
        //发送键盘密码事件
        deviceEventPublish.publishKeyboardPasswordEvent(new KeyboardPassword(deviceId, password));
        return new TcpResponse(true);
    }

    /**
     * 车窗控制(经测试设备对此功能不支持)
     *
     * @param deviceId 设备id
     * @param code     开窗关窗标示,1表示关闭车窗,2表示打开车窗
     */
    public TcpResponse windowControl(String deviceId, int code) {
        logger.info("DeviceService.windowControl()被调用，deviceId:" + deviceId);
        Channel channel = ChannelManager.getCmdChannel(deviceId);
        if (channel == null) {
            logger.error("DeviceService.windowControl()被调用，设备" + deviceId + "没找到！");
            return NOT_FIND;
        }

        short serialNo = MessageUtil.generateSerialNo(channel);
        String idStr = MessageIds.WINDOW_CONTROL + "_" + deviceId + "_" + serialNo;
        try {
            Message message = new Message();
            message.setId(MessageIds.WINDOW_CONTROL);
            message.setDeviceId(deviceId);
            message.setBodyLength((short) 1);
            message.setSerialNo(serialNo);

            message.setMessageBody(new byte[]{(byte) code});

            NettyResult nResult = new NettyResult();
            CountDownLatch latch = new CountDownLatch(1);
            nResult.setLatch(latch);

            result.put(idStr, nResult);
            channel.writeAndFlush(message).sync();
            latch.await(TIME_OUT, TimeUnit.MILLISECONDS);
            latch.countDown();

            Object obj = result.get(idStr).getMessage();
            if (obj == null) {
                return new TcpResponse(false, "发送超时");
            }
            byte res = (byte) obj;
            if (res == 1) {
                return new TcpResponse(false, "关闭车窗失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            result.remove(idStr);
        }
        return new TcpResponse(true);
    }

    /**
     * 重置蓝牙密码
     *
     * @param deviceId 设备id
     * @param password 密码(纯数字密码)
     */
    public TcpResponse resetBluePassword(String deviceId, String password) {
        logger.info("DeviceService.resetBluePassword()被调用，deviceId:" + deviceId);
        Channel channel = ChannelManager.getCmdChannel(deviceId);
        if (channel == null) {
            logger.error("DeviceService.resetBluePassword()被调用，设备" + deviceId + "没找到！");
            return NOT_FIND;
        }

        if (StringUtils.isBlank(password) && password.length() != 8) {
            return MSG_ERROR;
        }

        short serialNo = MessageUtil.generateSerialNo(channel);
        String idStr = MessageIds.RESET_BLUE_PASSWORD + "_" + deviceId + "_" + serialNo;
        try {
            Message message = new Message();
            message.setId(MessageIds.RESET_BLUE_PASSWORD);
            message.setDeviceId(deviceId);
            message.setBodyLength((short) password.length());
            message.setSerialNo(serialNo);

            message.setMessageBody(HexUtil.bluePasswordToByteArrayAndEncode(password));

            NettyResult nResult = new NettyResult();
            CountDownLatch latch = new CountDownLatch(1);
            nResult.setLatch(latch);

            result.put(idStr, nResult);
            channel.writeAndFlush(message).sync();
            latch.await(TIME_OUT, TimeUnit.MILLISECONDS);
            latch.countDown();

            Object obj = result.get(idStr).getMessage();
            if (obj == null) {
                return new TcpResponse(false, "发送超时");
            }
            byte res = (byte) obj;
            if (res == 1) {
                return new TcpResponse(false, "重置密码失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            result.remove(idStr);
        }
        //发送蓝牙密码上报事件
        deviceEventPublish.publishReportBluePasswordEvent(new BluePassword(deviceId, password));
        return new TcpResponse(true);
    }
}
