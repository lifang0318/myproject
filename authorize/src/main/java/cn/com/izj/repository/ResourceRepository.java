package cn.com.izj.repository;

import cn.com.izj.entity.Resource;
import org.springframework.stereotype.Repository;

/**
 * Created by GouBo on 2018/6/9 1:34.
 */
@Repository
public interface ResourceRepository extends DefaultRepository<Resource> {
    Resource findByName(String name);
}
