package cn.com.izj.event;

import cn.com.izj.base.entity.preferential.Preferential;
import cn.com.izj.dao.PreferentialDao;
import cn.com.izj.dao.UserPreferentialDao;
import cn.com.izj.entity.User;
import cn.com.izj.service.UserPreferentialService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author: 朱鸿平
 * @date: 2018/7/19 22:01
 */
@Component
public class UserRegisterListener implements ApplicationListener<UserRegisterEvent> {
    private static Logger logger = LoggerFactory.getLogger(UserRegisterListener.class);
    @Autowired
    private PreferentialDao preferentialDao;
    @Autowired
    private UserPreferentialDao userPreferentialDao;
    @Autowired
    private UserPreferentialService preferentialService;

    private ExecutorService executorService = Executors.newFixedThreadPool(20);

    @Override
    @Transactional
    public void onApplicationEvent(UserRegisterEvent event) {
        executorService.execute(() -> {
            try {
                User user = (User) event.getSource();
                Assert.notNull(user, "获取用户信息失败");
                Long userId = user.getId();
                //添加用户认证信息
                userPreferentialDao.insertUserAuth(user.getId());
                //添加用户余额数据
                userPreferentialDao.insertUserFund(user.getId());
                //查询是否有注册优惠券
                List<Preferential> list = preferentialDao.findRegisterPreferential(Preferential.TYPE_REGISTER);
                preferentialService.insertPreferential(userId,list);
            } catch (Exception ex) {
                logger.error("添加用户认证信息失败" + ex.getMessage());
            }
        });
    }
}
