package cn.com.izj.event;

import org.springframework.context.ApplicationEvent;

/**
 * 用户注册事件
 * Created by GouBo on 2018/7/7 13:55.
 */
public class UserRegisterEvent extends ApplicationEvent {
    public UserRegisterEvent(Object source) {
        super(source);
    }
}
