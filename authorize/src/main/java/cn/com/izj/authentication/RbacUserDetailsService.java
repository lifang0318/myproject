package cn.com.izj.authentication;

import cn.com.izj.entity.User;
import cn.com.izj.repository.UserRepository;
import cn.com.izj.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by GouBo on 2018/6/9 1:34.
 * Rbac意为基于角色的权限访问控制（Role-Based Access Control）
 */
@Component
@Transactional
public class RbacUserDetailsService implements UserDetailsService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("登录用户名:" + username);
        User user = userRepository.findUserByUsernameAndActive(username, true);
        if (user == null) {
            String regExp = "^[1][3-9][0-9]{9}$";
            Pattern p = Pattern.compile(regExp);
            Matcher m = p.matcher(username);
            if (!m.matches()) {
                throw new UsernameNotFoundException("用户:" + username + "不存在！");
            }

            user = userService.create(username);
        }
        user.getUrls();
        /**
         * 将用户信息存入redis
         */
        redisTemplate.opsForValue().set(username, user);
        return user;
    }

}
