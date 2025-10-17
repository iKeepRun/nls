package com.xhkj.nls.business.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xhkj.nls.business.domain.SmsCode;
import com.xhkj.nls.business.mapper.SmsCodeMapper;
import com.xhkj.nls.business.service.SmsCodeService;
import org.springframework.stereotype.Service;

/**
* @author Administrator
* @description 针对表【sms_code(短信验证码表)】的数据库操作Service实现
* @createDate 2025-10-17 13:40:05
*/
@Service
public class SmsCodeServiceImpl extends ServiceImpl<SmsCodeMapper, SmsCode>
    implements SmsCodeService{

}




