package com.zhuhuix.startup.wechat.miniprogram.repository;

import com.zhuhuix.startup.wechat.miniprogram.domain.CrmIndex;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.sql.Timestamp;
import java.util.List;

/**
 * CrmIndex仓库类
 *
 * @author zhuhuix
 * @date 2020-04-29
 */
public interface CrmIndexRepository extends JpaRepository<CrmIndex, Long>, JpaSpecificationExecutor<CrmIndex> {

    /**
     * 通过员工号、起始的创建日期，结束的创建日期查找线索记录
     * @param openId 微信openId
     * @param createTimeStart 起始的创建日期
     * @param createTimeEnd 结束的创建日期
     * @return 线索记录列表
     */
    List<CrmIndex> findByOpenIdAndCreateTimeBetween(String openId, Timestamp createTimeStart,Timestamp createTimeEnd);

    /**
     * 通过员工号、起始的创建日期，结束的创建日期查找线索记录(只查询未下载过的)
     * @param openId 微信openId
     * @param createTimeStart 起始的创建日期
     * @param createTimeEnd 结束的创建日期
     * @return
     */
    List<CrmIndex> findByOpenIdAndCreateTimeBetweenAndDownloadedFalse(String openId, Timestamp createTimeStart,Timestamp createTimeEnd);


}
