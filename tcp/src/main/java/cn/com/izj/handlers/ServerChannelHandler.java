package cn.com.izj.handlers;

import cn.com.izj.handlers.msg.*;
import cn.com.izj.protocol.Message;
import cn.com.izj.utils.MessageIds;
import cn.com.izj.utils.SpringContextUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServerChannelHandler extends SimpleChannelInboundHandler<Message> {

    private static Logger logger = LoggerFactory.getLogger(ServerChannelHandler.class);

    private AuthMessageHandler authMessageHandler;
    private HeartbeatHandler heartbeatHandler;
    private LocationUpHandler locationUpHandler;
    private GetDeviceInfoHandler getDeviceInfoHandler;
    private DeviceGenericReplyHandler deviceGenericReplyHandler;
    private OpenOrCloseDoorReplyHandler openOrCloseDoorReplyHandler;
    private PowerControlReplyHandler powerControlReplyHandler;
    private ReturnCarReplyHandler returnCarReplyHandler;
    private PasswordOpenHandler passwordOpenHandler;
    private WindowControlReplyHandler windowControlReplyHandler;
    private ReportBluePasswordHandler reportBluePasswordHandler;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Message message) throws Exception {
        logger.info("收到消息,消息id为:" + String.format("%04x", message.getId()));
        switch (message.getId()) {
            case MessageIds.DEVICE_AUTH:                // 终端鉴权
                authMessageHandler.handle(ctx, message);
                break;
            case MessageIds.HEART_BEAT:                 //心跳处理
                heartbeatHandler.handle(ctx, message);
                break;
            case MessageIds.LOCATION_UP:                //位置信息上传
                locationUpHandler.handle(ctx, message);
                break;
            case MessageIds.REQUEST_DEVICE_STATUS_REPLY://询问终端状态
                getDeviceInfoHandler.handle(ctx, message);
                break;
            case MessageIds.OPEN_OR_CLOSE_DOOR_REPLY:   //开/关门应答
                openOrCloseDoorReplyHandler.handle(ctx, message);
                break;
            case MessageIds.POWER_CONTROL_REPLY:        //控制汽车供电终端应答
                powerControlReplyHandler.handle(ctx, message);
                break;
            case MessageIds.RETURN_CAR_REPLY:           //还车命令应答
                returnCarReplyHandler.handle(ctx, message);
                break;
            case MessageIds.PASSWORD_OPEN:              //终端有密码键盘开锁动作命令
                passwordOpenHandler.handle(ctx, message);
                break;
            case MessageIds.WINDOW_CONTROL_REPLY:       //车窗控制命令应答
                windowControlReplyHandler.handle(ctx, message);
                break;
            case MessageIds.DEVICE_GENERIC_REPLY:       //终端通用应答
                deviceGenericReplyHandler.handle(ctx, message);
                break;
            case MessageIds.REMOTE_UPGRADE_REPLY:       //远程升级命令应答
                break;
            case MessageIds.REPORT_BLUE_PASSWORD:       //设备上传蓝牙密码
                reportBluePasswordHandler.handle(ctx, message);
                break;
            default:
                break;
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        ChannelManager.removeCmdChannel(ctx.channel());
        ctx.channel().close();
        super.channelInactive(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.error("exception caught", cause);
        super.exceptionCaught(ctx, cause);
    }

    /**
     * 构造器
     */
    public ServerChannelHandler() {
        authMessageHandler = SpringContextUtil.getBean(AuthMessageHandler.class);
        heartbeatHandler = SpringContextUtil.getBean(HeartbeatHandler.class);
        locationUpHandler = SpringContextUtil.getBean(LocationUpHandler.class);
        getDeviceInfoHandler = SpringContextUtil.getBean(GetDeviceInfoHandler.class);
        deviceGenericReplyHandler = SpringContextUtil.getBean(DeviceGenericReplyHandler.class);
        openOrCloseDoorReplyHandler = SpringContextUtil.getBean(OpenOrCloseDoorReplyHandler.class);
        powerControlReplyHandler = SpringContextUtil.getBean(PowerControlReplyHandler.class);
        returnCarReplyHandler = SpringContextUtil.getBean(ReturnCarReplyHandler.class);
        passwordOpenHandler = SpringContextUtil.getBean(PasswordOpenHandler.class);
        windowControlReplyHandler = SpringContextUtil.getBean(WindowControlReplyHandler.class);
        reportBluePasswordHandler = SpringContextUtil.getBean(ReportBluePasswordHandler.class);
    }
}