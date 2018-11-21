package cn.com.izj.service;

import cn.com.izj.dto.UserInfo;
import cn.com.izj.entity.RoleUser;
import cn.com.izj.entity.User;
import cn.com.izj.event.UserRegisterEventPublish;
import cn.com.izj.properties.AuthorizeConstants;
import cn.com.izj.repository.RoleRepository;
import cn.com.izj.repository.RoleUserRepository;
import cn.com.izj.repository.UserRepository;
import cn.com.izj.repository.support.QueryResultConverter;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 用户服务
 * Created by GouBo on 2018/6/9 1:34.
 */
@Service
@Transactional
public class UserService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private RoleUserRepository roleUserRepository;

    @Autowired
    private UserRegisterEventPublish userRegisterEventPublish;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private SmsService smsService;

    private static final String REGISTER_TIPS = "亲爱的用户你好，欢迎加入你行你开，请赶快下载app开始认证，用车吧！";

    /**
     * 创建用户
     */
    public UserInfo create(Map<String, String> params) {
        String mobile = params.get("mobile");
        String referral = params.get("referral");

        User oldUser = userRepository.findUserByUsername(mobile);
        if (oldUser != null) {
            throw new RuntimeException("用户名已经存在！");
        }

        User user = new User();
        user.setUsername(mobile);
        user.setPassword(passwordEncoder.encode("123456"));
        user.setReferral(referral);
        userRepository.save(user);
        createRoleUser(user);

        //推送用户注册事件
        userRegisterEventPublish.publishUserRegisterEvent(user);

        //发送短信给用户
        smsService.smsSend(new String[]{mobile}, REGISTER_TIPS);

        RoleUser roleUser = (RoleUser) user.getRoles().toArray()[0];

        UserInfo userInfo = new UserInfo();
        userInfo.setId(user.getId());
        userInfo.setUsername(user.getUsername());
        userInfo.setCreatedTime(user.getCreatedTime());
        userInfo.setRoleId(roleUser.getRole().getId());
        return userInfo;
    }

    /**
     * 通过手机号码注册普通用户
     */
    public User create(String mobile) {
        logger.info("用户名:" + mobile + "注册！");
        User user = new User();
        user.setUsername(mobile);
        user.setPassword(passwordEncoder.encode("123456"));
        userRepository.save(user);
        createRoleUser(user);

        //推送用户注册事件
        userRegisterEventPublish.publishUserRegisterEvent(user);
        return user;
    }

    /**
     * 通过用户名密码创建管理员
     */
    public UserInfo createAdmin(String username, String password) {
        User oldUser = userRepository.findUserByUsername(username);
        if (oldUser != null) {
            throw new RuntimeException("用户名已经存在！");
        }
        if (StringUtils.isBlank(password)) {
            password = "123456";
        }
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
        createRoleAdmin(user);

        RoleUser roleUser = (RoleUser) user.getRoles().toArray()[0];

        UserInfo userInfo = new UserInfo();
        userInfo.setId(user.getId());
        userInfo.setUsername(user.getUsername());
        userInfo.setCreatedTime(user.getCreatedTime());
        userInfo.setRoleId(roleUser.getRole().getId());
        return userInfo;
    }

    /**
     * 修改用户
     */
    public UserInfo update(UserInfo userInfo) {
        User user = userRepository.findOne(userInfo.getId());
        BeanUtils.copyProperties(userInfo, user);
        createRoleUser(userInfo, user);
        return userInfo;
    }

    /**
     * 更新用户信息
     */
    public UserInfo update(User user) {
        userRepository.save(user);
        UserInfo userInfo = new UserInfo();
        BeanUtils.copyProperties(user, userInfo);
        RoleUser roleUser = (RoleUser) user.getRoles().toArray()[0];
        userInfo.setRoleId(roleUser.getRole().getId());
        return userInfo;
    }

    /**
     * 创建角色用户关系数据。
     */
    private void createRoleUser(UserInfo userInfo, User user) {
        if (CollectionUtils.isNotEmpty(user.getRoles())) {
            roleUserRepository.delete(user.getRoles());
        }
        RoleUser roleUser = new RoleUser();
        roleUser.setRole(roleRepository.getOne(userInfo.getRoleId()));
        roleUser.setUser(user);
        roleUserRepository.save(roleUser);
    }

    /**
     * 创建普通用户角色用户关系
     */
    private void createRoleUser(User user) {
        if (CollectionUtils.isNotEmpty(user.getRoles())) {
            roleUserRepository.delete(user.getRoles());
        }
        RoleUser roleUser = new RoleUser();
        roleUser.setRole(roleRepository.findByName(AuthorizeConstants.GENERAL));
        roleUser.setUser(user);
        roleUserRepository.save(roleUser);
        user.getRoles().add(roleUser);
    }

    /**
     * 创建管理员角色用户关系
     */
    private void createRoleAdmin(User user) {
        if (CollectionUtils.isNotEmpty(user.getRoles())) {
            roleUserRepository.delete(user.getRoles());
        }
        RoleUser roleUser = new RoleUser();
        roleUser.setRole(roleRepository.findByName(AuthorizeConstants.SYS_MANAGER));
        roleUser.setUser(user);
        roleUserRepository.save(roleUser);
        user.getRoles().add(roleUser);
    }

    /**
     * 删除用户
     */
    public void delete(Long id) {
        userRepository.delete(id);
    }

    /**
     * 获取用户详细信息
     */
    public UserInfo getInfo(Long id) {
        User user = userRepository.findOne(id);
        UserInfo info = new UserInfo();
        BeanUtils.copyProperties(user, info);
        return info;
    }

    /**
     * 根据用户名获取用户详细信息
     */
    public List<UserInfo> getInfo(String username) {
        List<User> users = userRepository.findByUsernameLike("%" + username + "%");
        List<UserInfo> userInfos = new ArrayList<>();
        for (User user : users) {
            UserInfo info = new UserInfo();
            BeanUtils.copyProperties(user, info);
            userInfos.add(info);
        }
        return userInfos;
    }

    public User findUserByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }

    /**
     * 判断用户是否存在
     */
    public boolean isExist(String username) {
        User user = userRepository.findUserByUsername(username);
        return user != null;
    }

//    /**
//     * 分页查询用户
//     */
//    public Page<UserInfo> query(UserCondition condition, Pageable pageable) {
//        Page<User> users = baseUserRepository.findAll(new UserSpec(condition), pageable);
//        return QueryResultConverter.convert(users, UserInfo.class, pageable);
//    }

    public List<UserInfo> findByRoleId(Long roleId) {
        List<User> users = userRepository.findByRoleId(roleId);
        return QueryResultConverter.convert(users, UserInfo.class);
    }

    public Page<UserInfo> queryByRole(Long roleId, Pageable pageable) {
        Page<User> users = userRepository.findByRoleId(roleId, pageable);
        return QueryResultConverter.convert(users, UserInfo.class, pageable);
    }
}
