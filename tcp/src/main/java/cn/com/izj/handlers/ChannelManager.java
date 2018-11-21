package cn.com.izj.handlers;

import cn.com.izj.event.DeviceEventPublish;
import cn.com.izj.utils.Constants;
import cn.com.izj.utils.SpringContextUtil;
import io.netty.channel.Channel;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by GouBo on 2018/7/12 19:25.
 */
public class ChannelManager {

    private static Logger logger = LoggerFactory.getLogger(ChannelManager.class);

    private static Map<String, Channel> cmdChannels = new ConcurrentHashMap<>();

    public static void addCmdChannel(String deviceId, Channel channel) {
        if (StringUtils.isBlank(deviceId)) {
            throw new IllegalArgumentException("deviceId can not be null");
        }
        logger.info("设备:" + deviceId + ",addCmdChannel");
        channel.attr(Constants.CHANNEL_DEVICE_ID).set(deviceId);
        channel.attr(Constants.CHANNEL_SERIAL_NO).set(0);
        cmdChannels.put(deviceId, channel);
    }

    public static Channel getCmdChannel(String deviceId) {
        if (StringUtils.isBlank(deviceId)) {
            return null;
        }
        return cmdChannels.get(deviceId);
    }

    public static void removeCmdChannel(String deviceId) {
        cmdChannels.remove(deviceId);
    }

    public static void removeCmdChannel(Channel channel) {
        String deviceId = channel.attr(Constants.CHANNEL_DEVICE_ID).get();
        logger.info("设备:" + deviceId + ",removeCmdChannel");
        if (StringUtils.isNotBlank(deviceId)) {
            logger.info("设备:" + deviceId + ",断开连接！");
            DeviceEventPublish eventPublish = SpringContextUtil.getBean(DeviceEventPublish.class);
            //推动设备下线事件
            eventPublish.publishDeviceOfflineEvent(deviceId);
            cmdChannels.remove(deviceId);
        }
    }

    /**
     * 获取所有在线设备channel
     */
    public static Map<String, Channel> allChannel() {
        return cmdChannels;
    }

    /**
     * 获取所有在线设备id
     */
    public static List<String> getDeviceIds() {
        Set<String> macs = cmdChannels.keySet();
        return new ArrayList<>(macs);
    }
}
