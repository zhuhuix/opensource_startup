package com.zhuhuix.startup.security.repository;

import com.zhuhuix.startup.security.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

/**
 * 用户DAO接口层
 *
 * @author zhuhuix
 * @date 2020-04-03
 */
public interface UserRepository extends JpaRepository<User,Long>, JpaSpecificationExecutor<User> {

    /**
     * 用于微信注册用户查找：根据openId查找用户
     * @param openId 微信openId
     * @return openId对应的用户
     */
    User findByOpenId(String openId);

    /**
     * 用于注册帐号查找：根据openId查找用户
     * @param userName 注注册帐号册名
     * @return userName对应的用户
     */
    User findByUserName(String userName);

    /**
     * HQL 用于微信注册用户查找
     * @param id id号
     * @return 返回用户
     */
    @Query("select u from User u where u.id=?1" )
    User findByIdHql(Long id);

    /**
     * 原生SQL 用于微信注册用户查找
     * @param id id号
     * @return 返回用户
     */
    @Query(value = "select * from user u where u.id=?1",nativeQuery =true)
    User findByIdNative(Long id);

}
