package com.zhuhuix.startup.wechat.miniprogram.service.impl;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.zhuhuix.startup.common.base.Result;
import com.zhuhuix.startup.constant.Constant;
import com.zhuhuix.startup.security.domain.User;
import com.zhuhuix.startup.security.service.UserService;
import com.zhuhuix.startup.tools.domain.UploadFile;
import com.zhuhuix.startup.tools.service.UploadFileTool;
import com.zhuhuix.startup.utils.RedisUtils;
import com.zhuhuix.startup.utils.RepositoryUtil;
import com.zhuhuix.startup.wechat.miniprogram.domain.CrmIndex;
import com.zhuhuix.startup.wechat.miniprogram.domain.Customer;
import com.zhuhuix.startup.wechat.miniprogram.repository.CrmIndexRepository;
import com.zhuhuix.startup.wechat.miniprogram.repository.CustomerRepository;
import com.zhuhuix.startup.wechat.miniprogram.service.WxMiniCrm;
import com.zhuhuix.startup.wechat.miniprogram.service.dto.WxScanDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

/**
 * 微信小程序CRM实现类
 *
 * @author zhuhuix
 * @date 2020-04-20
 * @date 2020-06-17 将最新的上传信息推入Redis队列
 * @date 2020-06-22 记录并将客户信息通过Redis集合缓存
 */
@Slf4j
@AllArgsConstructor
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class WxMiniCrmImpl implements WxMiniCrm {

    private final UploadFileTool uploadFileTool;

    private final CrmIndexRepository crmIndexRepository;

    private final CustomerRepository customerRepository;

    private final UserService userService;

    private final RedisUtils redisUtils;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<UploadFile> uploadFile(String json, String openId, String realName, MultipartFile multipartFile) {
        return new Result<UploadFile>().ok(uploadFileTool.upload(openId, realName, multipartFile));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<CrmIndex> uploadCrmIndex(String json, String openId, String realName, MultipartFile multipartFile) {
        try {
            JSONObject jsonObject = JSONObject.parseObject(json);

            String createTime = jsonObject.getString("create");
            String employeeCode = jsonObject.getString("employeeCode");
            String customerCode = jsonObject.getString("customerCode");
            String customerName = jsonObject.getString("customerName");
            String type = jsonObject.getString("type");

            if (StringUtils.isEmpty(createTime) || StringUtils.isEmpty(employeeCode) || StringUtils.isEmpty(customerCode)
                    || StringUtils.isEmpty(customerName) || StringUtils.isEmpty(type)) {
                throw new RuntimeException("上传信息中缺少关键资料");
            }

            UploadFile uploadFile = uploadFileTool.upload(openId, realName, multipartFile);
            if (uploadFile == null) {
                throw new RuntimeException("上传文件失败！");
            }
            CrmIndex crmIndex = new CrmIndex();
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
            crmIndex.setCreateTime(Timestamp.valueOf(LocalDateTime.parse(createTime, dateTimeFormatter)));
            crmIndex.setEmployeeCode(employeeCode);
            crmIndex.setCustomerCode(customerCode);
            crmIndex.setCustomerName(customerName);
            crmIndex.setType(type);
            crmIndex.setJson(json);
            crmIndex.setOpenId(openId);
            crmIndex.setPath(uploadFile.getPath());

            // 将最新10条上传的信息放入redis缓存
            if (redisUtils.size(Constant.REDIS_UPLOAD_QUEUE_NAME) >= Constant.REDIS_UPLOAD_QUEUE_COUNT) {
                log.warn(Constant.REDIS_UPLOAD_QUEUE_NAME.concat("队列已满，移除最旧上传信息:") + redisUtils.rightPop(Constant.REDIS_UPLOAD_QUEUE_NAME));
            }
            log.info(Constant.REDIS_UPLOAD_QUEUE_NAME.concat("队列增加上传信息:").concat(crmIndex.toString()));
            redisUtils.leftPush(Constant.REDIS_UPLOAD_QUEUE_NAME, crmIndex);

            return new Result<CrmIndex>().ok(crmIndexRepository.save(crmIndex));

        } catch (JSONException ex) {
            throw new RuntimeException("json转换失败:" + ex.getMessage());
        }

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<WxScanDto> wxScan(WxScanDto wxScanDto, String openId) {

        //微信扫一扫保存用户信息
        if (Constant.SAVE_USER_INFO.equals(wxScanDto.getScanType()) && wxScanDto.getJsonObject() != null) {
            try {
                User user = JSONObject.parseObject(wxScanDto.getJsonObject().toJSONString(), User.class);
                Result<User> result = userService.update(user, openId);
                if (result != null && result.getSuccess()) {
                    wxScanDto.setReturnObject(result.getModule());
                    return new Result<WxScanDto>().ok(wxScanDto);
                }
            } catch (JSONException ex) {
                throw new RuntimeException("json转换失败:" + ex.getMessage());
            }

        }

        //微信扫一扫保存客户信息
        if (Constant.SAVE_CUSTOMER_INFO.equals(wxScanDto.getScanType()) && wxScanDto.getJsonObject() != null) {
            try {
                Customer customer = JSONObject.parseObject(wxScanDto.getJsonObject().toJSONString(), Customer.class);
                Customer target = customerRepository.findByCustomerCodeAndOpenId(customer.getCustomerCode(), openId);
                if (target != null) {
                    BeanUtils.copyProperties(customer, target, RepositoryUtil.getNullPropertyNames(customer));
                } else {
                    target = customer;
                    target.setOpenId(openId);
                }
                wxScanDto.setReturnObject(customerRepository.save(target));
                // 将用户增加的客户信息添加到redis集合中
                redisUtils.setAdd(openId.concat("_customer"),customer.toString());

                return new Result<WxScanDto>().ok(wxScanDto);
            } catch (JSONException ex) {
                throw new RuntimeException("json转换失败:" + ex.getMessage());
            }

        }
        return new Result<WxScanDto>().error("无法处理扫一扫功能");
    }

    @Override
    public Result<List<Customer>> crmGetCustoms(String openId) {
        return new Result<List<Customer>>().ok(customerRepository.findAllByOpenId(openId));
    }

    @Override
    public Result<List<CrmIndex>> getCrmIndex(String userName, Timestamp createTimeStart, Timestamp createTimeEnd, Boolean downloaded) {
        //根据注册用户名获取微信openId
        Result<User> user = userService.findByUserName(userName);
        if (user == null || user.getSuccess() == null || !user.getSuccess() || user.getModule() == null) {
            throw new RuntimeException("用户名:" + userName + "无法找到对应的注册帐号");
        }
        if (downloaded == null) {
            return new Result<List<CrmIndex>>().ok(crmIndexRepository.findByOpenIdAndCreateTimeBetween(user.getModule().getOpenId(), createTimeStart, createTimeEnd));
        } else {
            return new Result<List<CrmIndex>>().ok(crmIndexRepository.findByOpenIdAndCreateTimeBetweenAndDownloadedFalse(user.getModule().getOpenId(), createTimeStart, createTimeEnd));
        }
    }

    @Override
    public Result<CrmIndex> updateCrmIndexDownloaded(Long id, String downloader) {
        Optional<CrmIndex> optional = crmIndexRepository.findById(id);
        if (!optional.isPresent()) {
            throw new RuntimeException("无法找到对应的记录");
        }
        CrmIndex crmIndex = optional.get();
        crmIndex.setDownloaded(true);
        crmIndex.setDownloader(downloader);
        crmIndex.setDownloadTime(Timestamp.valueOf(LocalDateTime.now()));

        return new Result<CrmIndex>().ok(crmIndexRepository.save(crmIndex));

    }
}
