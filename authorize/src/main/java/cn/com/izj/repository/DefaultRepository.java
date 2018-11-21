package cn.com.izj.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * Created by GouBo on 2018/6/9 1:34.
 */
@NoRepositoryBean
public interface DefaultRepository<T> extends JpaRepository<T, Long>, JpaSpecificationExecutor<T> {
}
