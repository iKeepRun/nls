package com.xhkj.nls.business.controller.web;

import com.xhkj.nls.business.common.CommonResp;
import com.xhkj.nls.business.enums.SmsCodeUseEnum;
import com.xhkj.nls.business.service.SmsCodeService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/web")
public class SmsCodeController {
     @Resource
    private SmsCodeService smsCodeService;


     @RequestMapping(value = "/send-code",method = RequestMethod.POST)
     public CommonResp sendCode(String phone){
         smsCodeService.sendCode(phone,  SmsCodeUseEnum.REGISTER.getCode());
         return CommonResp.builder().success(true).build();
     }
}
