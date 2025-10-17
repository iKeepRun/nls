package com.xhkj.nls.business.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xhkj.nls.business.domain.SmsCode;

/**
* @author Administrator
* @description 针对表【sms_code(短信验证码表)】的数据库操作Service
* @createDate 2025-10-17 13:40:05
*/
public interface SmsCodeService extends IService<SmsCode> {
    void sendCode(String phone, String use);
}
