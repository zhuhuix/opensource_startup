package com.zhuhuix.startup.security.service;

import com.zhuhuix.startup.common.base.Result;
import com.zhuhuix.startup.security.domain.User;

/**
 * 用户信息接口
 *
 * @author zhuhuix
 * @date 2020-04-03
 */
public interface UserService {

    /**
     * 增加用户
     *
     * @param user 待新增的用户
     * @return 增加成功的用户
     */
    Result<User> create(User user);

    /**
     * 删除用户
     *
     * @param user 待删除的用户
     */
    void delete(User user);

    /**
     * 修改用户
     *
     * @param user 待修改的用户
     * @return 修改成功的用户
     */
    Result<User> update(User user);

    /**
     * 修改用户--支持微信端
     *
     * @param user 待修改的用户
     * @param openId 微信openId
     * @return 修改成功的用户
     */
    Result<User> update(User user,String openId);


    /**
     * 根据id查找用户
     *
     * @param id 用户id
     * @return id对应的用户
     */
    Result<User> findById(Long id);

    /**
     * 用于微信注册用户查找：根据openId查找用户
     *
     * @param openId 微信openId
     * @return openId对应的用户
     */
    Result<User> findByOpenId(String openId);

    /**
     * 用于微信注册用户查找：根据userName查找用户
     *
     * @param userName 用户帐号
     * @return 用户帐号对应的用户
     */
    Result<User> findByUserName(String userName);
}
