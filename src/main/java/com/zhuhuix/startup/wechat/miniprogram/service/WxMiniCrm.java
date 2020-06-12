package com.zhuhuix.startup.wechat.miniprogram.service;

import com.zhuhuix.startup.common.base.Result;
import com.zhuhuix.startup.tools.domain.UploadFile;
import com.zhuhuix.startup.wechat.miniprogram.domain.CrmIndex;
import com.zhuhuix.startup.wechat.miniprogram.domain.Customer;
import com.zhuhuix.startup.wechat.miniprogram.service.dto.WxScanDto;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;
import java.util.Iterator;
import java.util.List;

/**
 * 微信小程序CRM服务接口定义
 *
 * @author zhuhuix
 * @date 2020-04-20
 */
public interface WxMiniCrm {

    /**
     * 将微信小程序传入的json对象写入数据库，并同时将文件上传至服务端
     *
     * @param json          微信端传入json对象
     * @param openId        上传人
     * @param realName      文件实际名称
     * @param multipartFile 上传文件
     * @return 返回上传信息
     */
    Result<UploadFile> uploadFile(String json, String openId, String realName, MultipartFile multipartFile);

    /**
     * 将微信小程序传入的crmIndex对象写入数据库，并同时将文件上传至服务端
     *
     * @param json          微信端传入crmIndex对象
     * @param openId        上传人
     * @param realName      文件实际名称
     * @param multipartFile 上传文件
     * @return 返回上传信息
     */
    Result<CrmIndex> uploadCrmIndex(String json, String openId, String realName, MultipartFile multipartFile);

    /**
     * 微信扫一扫功能，根据传输的数据进行业务处理
     *
     * @param wxScanDto 传输数据
     * @param openId    微信openId
     * @return 返回扫描后的信息
     */
    Result<WxScanDto> wxScan(WxScanDto wxScanDto, String openId);

    /**
     * 获取客户列表
     *
     * @param openId 微信openId
     * @return 客户列表
     */
    Result<List<Customer>> crmGetCustoms(String openId);

    /**
     * 通过员工号、起始的创建日期，结束的创建日期查找线索记录
     * @param userName 用户帐号
     * @param createTimeStart 起始的创建日期
     * @param createTimeEnd 结束的创建日期
     * @param downloaded 是否已下载
     * @return 线索记录列表
     */
    Result<List<CrmIndex>> getCrmIndex(String userName, Timestamp createTimeStart,Timestamp createTimeEnd,Boolean downloaded);

    /**
     * 更新线索记录为已下载过
     * @param id 线索记录id列表
     * @param downloader 下载人
     * @return 返回更新的记录
     */
    Result<CrmIndex> updateCrmIndexDownloaded(Long id, String downloader);

}
