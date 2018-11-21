package cn.com.izj.repository;

import cn.com.izj.entity.Role;
import org.springframework.stereotype.Repository;

/**
 * Created by GouBo on 2018/6/9 1:34.
 */
@Repository
public interface RoleRepository extends DefaultRepository<Role> {
    Role findByName(String name);
}
