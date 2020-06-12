package com.zhuhuix.startup.tools.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.sql.Update;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 文件信息表
 *
 * @author zhuhuix
 * @date 2020-04-20
 */
@Entity
@Getter
@Setter
@Table(name = "upload_file")
public class UploadFile implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull(groups = Update.class)
    private Long id;

    /**
     * 文件实际名称
     */
    @Column(name = "real_name")
    private String realName;

    /**
     * 文件名
     */
    @NotNull
    @Column(name = "file_name")
    private String fileName;

    /**
     * 文件主名称
     */
    @NotNull
    @Column(name = "primary_name")
    private String primaryName;

    /**
     * 文件扩展名
     */
    @NotNull
    private String extension;

    /**
     * 存放路径
     */
    @NotNull
    private String path;

    /**
     * 文件类型
     */
    private String type;

    /**
     * 文件大小
     */
    private Long size;

    /**
     * 上传人
     */
    private String uploader;

    @JsonIgnore
    @Column(name = "create_time")
    @CreationTimestamp
    private Timestamp createTime;

    public UploadFile(String realName, @NotNull String fileName, @NotNull String primaryName, @NotNull String extension, @NotNull String path, String type, Long size, String uploader) {
        this.realName = realName;
        this.fileName = fileName;
        this.primaryName = primaryName;
        this.extension = extension;
        this.path = path;
        this.type = type;
        this.size = size;
        this.uploader = uploader;
    }

    @Override
    public String toString() {
        return "UploadFile{" +
                "fileName='" + fileName + '\'' +
                ", uploader='" + uploader + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}
