package com.zhuhuix.startup.wechat.miniprogram.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.sql.Update;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * CRM移动端上传索引信息表
 *
 * @author zhuhuix
 * @date 2020-04-29
 */
@Entity
@Getter
@Setter
@ToString
@Table(name = "crm_index")
public class CrmIndex implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull(groups = Update.class)
    private Long id;

    @NotNull
    @Column(name="open_id")
    private String openId;

    /**
     * 员工工号
     */
    @NotNull
    @Column(name="employee_code")
    private String employeeCode;

    /**
     * 客户编号
     */
    @NotNull
    @Column(name="customer_code")
    private String customerCode;


    /**
     * 客户名称
     */
    @NotNull
    @Column(name="customer_name")
    private String customerName;


    /**
     * 类型
     */
    private String type;

    /**
     * 信息
     */
    private String json;

    /**
     * 原始信息对应的文件路径
     */
    private String path;

    /**
     * 信息创建时间
     */
    @Column(name = "create_time")
    private Timestamp createTime;

    /**
     * 信息上传时间
     */
    @Column(name = "upload_time")
    @CreationTimestamp
    private Timestamp uploadTime;

    /**
     *是否下载
     */
    private Boolean downloaded;

    /**
     *下载时间
     */
    @Column(name = "download_time")
    private Timestamp downloadTime;

    /**
     *下载人
     */
    private String downloader;




}
