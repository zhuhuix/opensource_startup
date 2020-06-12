package com.zhuhuix.startup.security.rest;

import com.zhuhuix.startup.security.service.AuthService;
import com.zhuhuix.startup.security.service.dto.AuthUserDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * api登录授权
 *
 * @author zhuhuix
 * @date 2020-03-30
 */
@Slf4j
@RestController
@RequestMapping("/api/auth")
@Api(tags = "系统授权接口")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @ApiOperation("登录授权")
    @PostMapping(value = "/login")
    public ResponseEntity login(@RequestBody AuthUserDto authUserDto, HttpServletRequest request) {
        return ResponseEntity.ok(authService.login(authUserDto, request));
    }

}
