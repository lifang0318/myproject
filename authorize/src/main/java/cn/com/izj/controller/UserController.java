package cn.com.izj.controller;

import cn.com.izj.dto.UpdatePasswordForm;
import cn.com.izj.dto.UserForm;
import cn.com.izj.dto.UserInfo;
import cn.com.izj.dto.UserIsExist;
import cn.com.izj.entity.RoleUser;
import cn.com.izj.entity.User;
import cn.com.izj.json.UserJson;
import cn.com.izj.service.UserService;
import cn.com.izj.support.ErrorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by GouBo on 2018/6/9 1:34.
 */
@RestController
@RequestMapping("/system/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    /**
     * 获取当前登录的用户信息
     */
    @GetMapping("/me")
    public Object me(Authentication authentication) {
        String username = (String) authentication.getPrincipal();
        User user = (User) redisTemplate.opsForValue().get(username);
        if (user == null) {
            return new ErrorResponse("100021", "获取用户信息失败,请重新登录");
        }
        UserJson userJson = new UserJson();
        userJson.setId(user.getId());
        userJson.setUsername(user.getUsername());
        userJson.setCreatedTime(user.getCreatedTime());
        Set<RoleUser> roles = user.getRoles();
        for (RoleUser roleUser : roles) {
            userJson.getRolesId().add(roleUser.getRole().getId());
        }
        return userJson;
    }

    /**
     * 创建用户
     */
    @PostMapping("/register")
    public UserInfo create(@RequestParam Map<String, String> params) {
        return userService.create(params);
    }

    /**
     * 创建管理员
     */
    @PostMapping("/create_admin")
    public UserInfo createAdmin(@RequestBody UserForm userForm) {
        return userService.createAdmin(userForm.getUsername(), userForm.getPassword());
    }

    /**
     * 修改密码
     */
    @PostMapping("/update_password")
    public Object updatePassword(@RequestBody UpdatePasswordForm updatePasswordForm) {
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = ((ServletRequestAttributes) ra).getRequest();
        String username = request.getRemoteUser();
        User user = (User) redisTemplate.opsForValue().get(username);
        if (user == null) {
            user = userService.findUserByUsername(username);
        }
        if (!passwordEncoder.matches(updatePasswordForm.getOldPassword(), user.getPassword())) {
            return new ErrorResponse("10008", "原密码不正确！");
        }
        user.setPassword(passwordEncoder.encode(updatePasswordForm.getNewPassword()));
        return userService.update(user);
    }


    /**
     * 修改用户信息
     */
    @PutMapping
    public UserInfo update(@RequestBody UserInfo userInfo) {
        return userService.update(userInfo);
    }

    /**
     * 删除用户
     */
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        userService.delete(id);
    }

    /**
     * 获取用户详情
     */
    @GetMapping("/{id}")
    public UserInfo getInfo(@PathVariable Long id) {
        return userService.getInfo(id);
    }

    /**
     * 根据用户名获取用户信息
     */
    @GetMapping("/name/{username}")
    public List<UserInfo> getInfo(@PathVariable String username) {
        return userService.getInfo(username);
    }

    /**
     * 检查用户是否存在
     */
    @GetMapping("/check/{username}")
    public Object isExist(@PathVariable String username) {
        return new UserIsExist(userService.isExist(username));
    }

//    /**
//     * 分页查询用户
//     */
//    @GetMapping
//    public Page<UserInfo> query(UserCondition condition, Pageable pageable) {
//        return userService.query(condition, pageable);
//    }

    /**
     * 按角色分页查询用户
     */
    @GetMapping("/role/{roleId}")
    public Page<UserInfo> queryByRole(@PathVariable Long roleId, Pageable pageable) {
        return userService.queryByRole(roleId, pageable);
    }
}
