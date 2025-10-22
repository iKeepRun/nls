package com.xhkj.nls.business.controller.web;

import com.xhkj.nls.business.common.CommonResp;
import com.xhkj.nls.business.req.SmsCodeResetReq;
import com.xhkj.nls.business.service.SmsCodeService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/web/code")
public class SmsCodeController {
     @Resource
    private SmsCodeService smsCodeService;


     @PostMapping(value = "/send-for-register")
     public CommonResp sendCode(String mobile){
         smsCodeService.sendCode4Register(mobile);
         return CommonResp.builder().success(true).build();
     }


    @PostMapping("/send-for-reset")
    public CommonResp<Object> sendForReset(@Valid @RequestBody SmsCodeResetReq req) {

//        // 校验图片验证码，防止短信攻击，不加的话，只能防止同一手机攻击，加上图片验证码，可防止不同的手机号攻击
//        kaptchaService.validCode(req.getImageCode(), req.getImageCodeToken());
//
        smsCodeService.sendCode4Reset(req.getMobile());
        return new CommonResp<>();
    }
}
