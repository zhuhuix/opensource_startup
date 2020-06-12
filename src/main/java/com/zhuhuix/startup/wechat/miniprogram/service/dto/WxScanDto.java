package com.zhuhuix.startup.wechat.miniprogram.service.dto;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 微信扫一扫传输对象
 *
 * @author zhuhuix
 * @date 2020-05-04
 */
@ApiModel(value = "微信扫一扫传输对象")
@Getter
@Setter
public class WxScanDto implements Serializable {

    @ApiModelProperty(value = "扫描类型")
    private String scanType;

    @ApiModelProperty(value = "传输对象")
    private JSONObject jsonObject;

    @ApiModelProperty(value = "返回对象")
    private Object returnObject;
}
