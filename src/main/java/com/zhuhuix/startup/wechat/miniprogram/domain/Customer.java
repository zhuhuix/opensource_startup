package com.zhuhuix.startup.wechat.miniprogram.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.sql.Update;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * CRM客户信息
 *
 * @author zhuhuix
 * @date 2020-05-04
 */
@Entity
@Getter
@Setter
@Table(name = "customer")
public class Customer implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull(groups = Update.class)
    private Long id;

    @Column(name="open_id")
    private String openId;

    /**
     * 客户代码
     */
    @Column(name="customer_code")
    private String customerCode;

    /**
     * 客户名称
     */
    @Column(name="customer_name")
    private String customerName;

    /**
     * 首字母
     */
    @Column(name="first_letter")
    private String firstLetter;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    @CreationTimestamp
    private Timestamp createTime;


    /**
     * 更新时间
     */
    @Column(name = "update_time")
    @UpdateTimestamp
    private Timestamp updateTime;

}
