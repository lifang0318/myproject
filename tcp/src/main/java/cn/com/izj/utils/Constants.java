package cn.com.izj.utils;

import io.netty.util.AttributeKey;

public interface Constants {

    AttributeKey<String> CHANNEL_DEVICE_ID = AttributeKey.newInstance("channel_device_id");

    AttributeKey<Integer> CHANNEL_SERIAL_NO = AttributeKey.newInstance("channel_serial_no");
}
