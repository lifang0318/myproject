package cn.com.izj.utils;

/**
 * tcp消息id
 * Created by GouBo on 2018/7/7 17:34.
 */
public interface MessageIds {
    /**
     * 终端鉴权
     */
    short DEVICE_AUTH = 0x0102;
    /**
     * 平台鉴权应答
     */
    short DEVICE_AUTH_REPLY = (short) 0x8102;
    /**
     * 位置信息上传
     */
    short LOCATION_UP = 0x0200;
    /**
     * 平台通用应答
     */
    short SERVER_GENERIC_REPLY = (short) 0x8001;
    /**
     * 终端通用应答
     */
    short DEVICE_GENERIC_REPLY = 0x0001;
    /**
     * 询问终端状态
     */
    short REQUEST_DEVICE_STATUS = (short) 0x8320;
    /**
     * 询问终端状态
     */
    short REQUEST_DEVICE_STATUS_REPLY = (short) 0x0320;
    /**
     * 服务器下发找车
     */
    short FIND_CAR = (short) 0x8322;
    /**
     * 服务器强制开/关门
     */
    short OPEN_OR_CLOSE_DOOR = (short) 0x8323;
    /**
     * 开/关门应答
     */
    short OPEN_OR_CLOSE_DOOR_REPLY = 0x0323;
    /**
     * 设置终端参数
     */
    short SET_DEVICE_INFO = (short) 0x8329;
    /**
     * 服务器控制汽车供电
     */
    short POWER_CONTROL = (short) 0x8325;
    /**
     * 控制汽车供电终端应答
     */
    short POWER_CONTROL_REPLY = 0x0325;
    /**
     * 远程升级命令
     */
    short REMOTE_UPGRADE = (short) 0x8330;
    /**
     * 远程升级命令应答
     */
    short REMOTE_UPGRADE_REPLY = 0x0331;
    /**
     * 还车命令
     */
    short RETURN_CAR = (short) 0x8410;
    /**
     * 还车命令应答
     */
    short RETURN_CAR_REPLY = 0x0410;

    /**
     * 设置密码键盘密码命令
     */
    short SET_PASSWORD = (short) 0x8420;

    /**
     * 终端有密码键盘开锁动作命令
     */
    short PASSWORD_OPEN = 0x0421;

    /**
     * 终端心跳包
     */
    short HEART_BEAT = 0x0506;
    /**
     * 车窗控制命令
     */
    short WINDOW_CONTROL = (short) 0x8450;
    /**
     * 车窗控制命令应答
     */
    short WINDOW_CONTROL_REPLY = 0x0450;

    /*********************以下为蓝牙部分**************************/

    /**
     * 设备上传蓝牙密码
     */
    short REPORT_BLUE_PASSWORD = 0x0601;

    /**
     * 设备上传蓝牙密码服务端响应
     */
    short REPORT_BLUE_PASSWORD_REPLY = (short) 0x8601;

    /**
     * 服务端重置蓝牙密码
     */
    short RESET_BLUE_PASSWORD = (short) 0x8620;
}
