package cn.com.izj.init;

import cn.com.izj.entity.*;
import cn.com.izj.properties.AuthorizeConstants;
import cn.com.izj.repository.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 默认的系统数据初始化器，永远在其他数据初始化器之前执行
 * Created by GouBo on 2018/6/9 1:34.
 */
@Component
public class UserDataInitializer extends AbstractDataInitializer {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleUserRepository roleUserRepository;

    @Autowired
    protected ResourceRepository resourceRepository;

    @Autowired
    private RoleResourceRepository roleResourceRepository;

    @Override
    public Integer getIndex() {
        return Integer.MIN_VALUE;
    }


    @Override
    protected void doInit() {
        Role sysManager = initRole(AuthorizeConstants.SYS_MANAGER); //系统管理员

        Role parkManager = initRole(AuthorizeConstants.PARK_MANAGER);//停车场管理员
        Role carOwner = initRole(AuthorizeConstants.CAR_OWNER);//车主
        Role general = initRole(AuthorizeConstants.GENERAL);//普通用户

        initAdmin(sysManager);

        initResource();

        initAdminResources(sysManager);
        initParkManagerResources(parkManager);
        initMobileResources(carOwner, general);
    }

    /**
     * 初始化角色数据
     */
    private Role initRole(String name) {
        Role role = new Role();
        role.setName(name);
        roleRepository.save(role);
        return role;
    }

    /**
     * 初始化管理员
     */
    private void initAdmin(Role role) {
        User admin = new User();
        admin.setUsername("admin");
        admin.setPassword(passwordEncoder.encode("123456"));
        userRepository.save(admin);

        RoleUser roleUser = new RoleUser();
        roleUser.setRole(role);
        roleUser.setUser(admin);
        roleUserRepository.save(roleUser);
    }

    /**
     * 初始化菜单数据
     */
    private void initResource() {
        Resource root = createRoot(AuthorizeConstants.ROOT_NODE);

        createResource(AuthorizeConstants.SYSTEM, AuthorizeConstants.UI_SYSTEM, "system", root);
        createResource(AuthorizeConstants.PARK_SYSTEM, AuthorizeConstants.UI_PARK_SYSTEM, "park_system", root);
        createResource(AuthorizeConstants.MOBILE, AuthorizeConstants.UI_MOBILE, "mobile", root);
    }

    /**
     * 初始化系统管理员资源
     */
    private void initAdminResources(Role admin) {
        List<Resource> resources = resourceRepository.findAll();
        for (Resource resource : resources) {
            if (resource.getParent() != null) {
                RoleResource roleResource = new RoleResource();
                roleResource.setResource(resource);
                roleResource.setRole(admin);
                roleResourceRepository.save(roleResource);
            }
        }
    }

    /**
     * 初始化停车场管理员资源
     */
    private void initParkManagerResources(Role role) {
        List<Resource> resources = resourceRepository.findAll();
        for (Resource resource : resources) {
            if (resource.getParent() != null && !AuthorizeConstants.UI_SYSTEM.equals(resource.getLink())) {
                RoleResource roleResource = new RoleResource();
                roleResource.setResource(resource);
                roleResource.setRole(role);
                roleResourceRepository.save(roleResource);
            }
        }
    }

    /**
     * 初始化移动端资源
     */
    private void initMobileResources(Role carOwner, Role general) {
        List<Resource> resources = resourceRepository.findAll();
        for (Resource resource : resources) {
            if (resource.getParent() != null &&
                    !AuthorizeConstants.UI_SYSTEM.equals(resource.getLink()) &&
                    !AuthorizeConstants.UI_PARK_SYSTEM.equals(resource.getLink())) {
                RoleResource carOwnerResource = new RoleResource();
                carOwnerResource.setResource(resource);
                carOwnerResource.setRole(carOwner);

                RoleResource generalResource = new RoleResource();
                generalResource.setResource(resource);
                generalResource.setRole(general);
                roleResourceRepository.save(generalResource);
            }
        }
    }

    @Override
    protected boolean isNeedInit() {
        return roleRepository.count() == 0;
    }

    private Resource createRoot(String name) {
        Resource node = new Resource();
        node.setName(name);
        resourceRepository.save(node);
        return node;
    }

    protected Resource createResource(String name, Resource parent) {
        return createResource(name, null, null, parent);
    }

    private Resource createResource(String name, String link, String permUrl, Resource parent) {
        Resource node = new Resource();
        node.setName(name);
        node.setLink(link);
        node.setParent(parent);
        if (parent != null) {
            String parentLink = parent.getLink();
            int modifiedLink = 0;
            try {
                modifiedLink = Integer.parseInt(parentLink) + 1;
            } catch (Exception ignored) {
            }
            parent.setLink(String.valueOf(modifiedLink));
        }
        if (StringUtils.isNotBlank(permUrl)) {
            String url = "/" + permUrl + "/**";
            if (parent != null && parent.getUrl() != null) {
                if (StringUtils.endsWith(parent.getUrl(), "**")) {
                    parent.setUrl(StringUtils.removeEnd(parent.getUrl(), "**"));
                    Resource resource = resourceRepository.findOne(parent.getId());
                    BeanUtils.copyProperties(parent, resource);
                }
                url = parent.getUrl() + permUrl + "/**";
            }
            node.setUrl(url);
        }
        resourceRepository.save(node);
        return node;
    }
}
