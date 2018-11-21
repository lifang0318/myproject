package cn.com.izj.repository;

import cn.com.izj.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by GouBo on 2018/6/9 1:34.
 */
@Repository
public interface UserRepository extends DefaultRepository<User> {

    User findUserByUsername(String username);

    User findUserByUsernameAndActive(String username, boolean active);

    @Query(value = "select username from user where id= :userId ", nativeQuery = true)
    String findUsernameById(@Param("userId") Long userId);

    @Query(value = "select * from user where id in (select user_id from role_user where role_id = :roleId) /*#pageable*/",
            countQuery = "select count(*) from user where id in (select user_id from role_user where role_id = :roleId)",
            nativeQuery = true)
    Page<User> findByRoleId(@Param("roleId") Long roleId, Pageable pageable);

    @Query(value = "select * from user where id in (select user_id from role_user where role_id = :roleId) ", nativeQuery = true)
    List<User> findByRoleId(@Param("roleId") Long roleId);

    List<User> findByUsernameLike(String username);
}
