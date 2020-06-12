package com.zhuhuix.startup.security.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.sql.Update;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 用户表
 *
 * @author zhuhuix
 * @date 2020-04-03
 */
@ApiModel(value = "用户信息")
@Entity
@Getter
@Setter
@Table(name = "user")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull(groups = Update.class)
    private Long id;


    @Column(name = "user_name", unique = true)
    private String userName;

    @JsonIgnore
    private String password;

    /**
     * 微信openId
     */
    @Column(unique = true, name = "open_id")
    private String openId;

    /**
     * 用户昵称
     */
    @Column(name="nick_name")
    private String nickName;

    /**
     * 性别 0-未知 1-male,2-female
     */
    private Integer gender;

    /**
     * 头像地址
     */
    @Column(name = "avatar_url")
    private String avatarUrl;

    @Column(name = "union_id")
    private String unionId;

    private String country;

    private String province;

    private String city;

    private String language;

    @Email
    private String email;

    private String phone;

    private String remarks;

    private Boolean enabled;

    @JsonIgnore
    @Column(name = "last_password_reset_time")
    private Timestamp lastPasswordResetTime;

    @JsonIgnore
    @Column(name = "create_time")
    @CreationTimestamp
    private Timestamp createTime;

    @JsonIgnore
    @Column(name = "update_time")
    @UpdateTimestamp
    private Timestamp updateTime;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userName='" + userName + '\'' +

                ", nickName='" + nickName + '\'' +

                '}';
    }
}
