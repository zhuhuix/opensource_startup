package com.zhuhuix.startup.security.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Jwt参数配置类
 * @author zhuhuix
 * @date 2020-03-25
 */

@Data
@Configuration
@ConfigurationProperties(prefix = "jwt")
public class JwtSecurityProperties {

    /** Request Headers ： Authorization */
    private String header;

    /** 令牌前缀，最后留个空格 Bearer */
    private String tokenStartWith;

    /** 必须使用最少88位的Base64对该令牌进行编码 */
    private String base64Secret;

    /** 令牌过期时间 此处单位/毫秒 */
    private Long tokenValidityInSeconds;

    /** 在线用户 key，根据 key 查询 redis 中在线用户的数据 */
    private String onlineKey;

    /** 验证码 key */
    private String codeKey;

    /**返回令牌前缀 */
    public String getTokenStartWith() {
        return tokenStartWith + " ";
    }
}
