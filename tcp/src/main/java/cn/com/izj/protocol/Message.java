package cn.com.izj.protocol;

/**
 * 车载设备与服务器消息交换协议
 */
public class Message {

    /**
     * 消息id
     */
    private short id;

    /**
     * 消息体长度
     */
    private short bodyLength;

    /**
     * 设备id,最长12个数字,例如:201800698542
     */
    private String deviceId;

    /**
     * 消息流水号,按发送顺序从0开始循环累加
     */
    private short serialNo;

    /**
     * 消息体
     */
    private byte[] messageBody;


    public short getId() {
        return id;
    }

    public void setId(short id) {
        this.id = id;
    }

    public short getBodyLength() {
        return bodyLength;
    }

    public void setBodyLength(short bodyLength) {
        this.bodyLength = bodyLength;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public short getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(short serialNo) {
        this.serialNo = serialNo;
    }

    public byte[] getMessageBody() {
        return messageBody;
    }

    public void setMessageBody(byte[] messageBody) {
        this.messageBody = messageBody;
    }
}
