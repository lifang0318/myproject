package cn.com.izj.utils;

import java.util.concurrent.CountDownLatch;

/**
 * Created by GouBo on 2018/7/15 15:53.
 */
public class NettyResult {
    private Object message;
    private CountDownLatch latch;

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }

    public CountDownLatch getLatch() {
        return latch;
    }

    public void setLatch(CountDownLatch latch) {
        this.latch = latch;
    }
}
