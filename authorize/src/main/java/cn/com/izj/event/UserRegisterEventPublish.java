package cn.com.izj.event;

import cn.com.izj.entity.User;
import com.alibaba.fastjson.JSON;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Component;

/**
 * Created by GouBo on 2018/7/7 15:08.
 */
@Component
public class UserRegisterEventPublish implements ApplicationEventPublisherAware {

    private ApplicationEventPublisher tradeEventPublisher;

    /**
     * 推送用户注册事件
     */
    public void publishUserRegisterEvent(User user) {
//        String str = JSON.toJSONString(user);
        this.tradeEventPublisher.publishEvent(new UserRegisterEvent(user));
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.tradeEventPublisher = applicationEventPublisher;
    }
}
