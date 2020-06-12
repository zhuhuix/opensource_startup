package com.zhuhuix.startup.wechat.miniprogram.rest;

import com.zhuhuix.startup.wechat.miniprogram.service.WxMiniCrm;
import com.zhuhuix.startup.wechat.miniprogram.service.dto.WxScanDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;

/**
 * 微信小程序Crm webApi
 *
 * @author zhuhuix
 * @date 2020-03-30
 */
@Slf4j
@RestController
@RequestMapping("/api/wx-mini")
@Api(tags = "微信小程序Crm接口")
public class WxMiniCrmController {

    private final WxMiniCrm wxMiniCrm;

    public WxMiniCrmController(WxMiniCrm wxMiniCrm) {
        this.wxMiniCrm = wxMiniCrm;
    }

    @ApiOperation(value = "微信小程序端上传文件")
    @PostMapping(value = "/fileUpload")
    public ResponseEntity fileUpload(HttpServletRequest request) {
        MultipartHttpServletRequest req = (MultipartHttpServletRequest) request;

        MultipartFile multipartFile = req.getFile("file");
        String openId = req.getParameter("openId");
        String realName = req.getParameter("realName");
        String json = req.getParameter("json");

        return ResponseEntity.ok(wxMiniCrm.uploadFile(json, openId, realName, multipartFile));

    }

    @ApiOperation(value = "微信小程序端上传CrmIndex")
    @PostMapping(value = "/crmIndexUpload")
    public ResponseEntity crmIndexUpload(HttpServletRequest request) {
        MultipartHttpServletRequest req = (MultipartHttpServletRequest) request;

        MultipartFile multipartFile = req.getFile("file");
        String openId = req.getParameter("openId");
        String realName = req.getParameter("realName");
        String json = req.getParameter("json");

        return ResponseEntity.ok(wxMiniCrm.uploadCrmIndex(json, openId, realName, multipartFile));

    }

    @ApiOperation(value = "微信小程序端扫一扫")
    @PostMapping(value = "/crmScan/{openId}")
    public ResponseEntity crmScan(@RequestBody WxScanDto wxScanDto, @PathVariable String openId) {

        return ResponseEntity.ok(wxMiniCrm.wxScan(wxScanDto, openId));

    }

    @ApiOperation(value = "微信小程序端获取客户列表")
    @GetMapping(value = "/crmGetCustoms/{openId}")
    public ResponseEntity crmGetCustoms(@PathVariable String openId) {

        return ResponseEntity.ok(wxMiniCrm.crmGetCustoms(openId));

    }

    @ApiOperation(value = "获取线索列表")
    @GetMapping(value = {"/getCrmIndex/{employeeCode}/{createTimeStart}/{createTimeEnd}/{downloaded}","/getCrmIndex/{employeeCode}/{createTimeStart}/{createTimeEnd}"})
    public ResponseEntity getCrmIndex(@PathVariable String employeeCode, @PathVariable Long createTimeStart, @PathVariable Long createTimeEnd, @PathVariable(required = false) Boolean downloaded) {

        Timestamp startTimeStamp = new Timestamp(createTimeStart);
        Timestamp endTimeStamp = new Timestamp(createTimeEnd);

        return ResponseEntity.ok(wxMiniCrm.getCrmIndex(employeeCode, startTimeStamp, endTimeStamp, downloaded));

    }

    @ApiOperation(value = "更新线索为已下载")
    @PostMapping(value = {"/updateCrmIndexDownloaded/{id}/{downloader}"})
    public ResponseEntity updateCrmIndexDownloaded(@PathVariable Long id,@PathVariable String downloader) {

        return ResponseEntity.ok(wxMiniCrm.updateCrmIndexDownloaded(id,downloader));

    }
}
