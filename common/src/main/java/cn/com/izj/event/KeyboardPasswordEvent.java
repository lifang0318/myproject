package cn.com.izj.event;

import cn.com.izj.event.dto.KeyboardPassword;
import org.springframework.context.ApplicationEvent;

/**
 * Created by GouBo on 2018/9/2 17:25.
 */
public class KeyboardPasswordEvent extends ApplicationEvent {
    public KeyboardPasswordEvent(KeyboardPassword keyboardPassword) {
        super(keyboardPassword);
    }
}
