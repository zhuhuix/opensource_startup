package com.zhuhuix.startup.tools.repository;

import com.zhuhuix.startup.tools.domain.UploadFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 上传文件DAO接口层
 *
 * @author zhuhuix
 * @date 2020-04-03
 */
public interface UploadFileRepository extends JpaRepository<UploadFile, Long>, JpaSpecificationExecutor<UploadFile> {
}
