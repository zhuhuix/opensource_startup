package com.zhuhuix.startup.wechat.miniprogram.repository;

import com.zhuhuix.startup.wechat.miniprogram.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * Customer仓库类
 *
 * @author zhuhuix
 * @date 2020-05-04
 */
public interface CustomerRepository extends JpaRepository<Customer, Long>, JpaSpecificationExecutor<Customer> {

    /**
     * 通过客户代码与微信openId查找客户信息
     *
     * @param customerCode 客户代码
     * @param openId       微信openId
     * @return 客户信息
     */
    Customer findByCustomerCodeAndOpenId(String customerCode, String openId);

    /**
     * 通过微信openId查找客户列表
     *
     * @param openId 微信openId
     * @return 客户列表
     */
    List<Customer> findAllByOpenId(String openId);
}
