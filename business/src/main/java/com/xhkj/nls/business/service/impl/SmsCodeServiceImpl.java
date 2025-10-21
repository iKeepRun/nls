package com.xhkj.nls.business.service.impl;


import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xhkj.nls.business.domain.SmsCode;
import com.xhkj.nls.business.enums.SmsCodeStatusEnum;
import com.xhkj.nls.business.enums.SmsCodeUseEnum;
import com.xhkj.nls.business.exception.BusinessException;
import com.xhkj.nls.business.exception.BusinessExceptionEnum;
import com.xhkj.nls.business.mapper.SmsCodeMapper;
import com.xhkj.nls.business.service.MemberService;
import com.xhkj.nls.business.service.SmsCodeService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * @author Administrator
 * @description 针对表【sms_code(短信验证码表)】的数据库操作Service实现
 * @createDate 2025-10-17 13:40:05
 */
@Service
public class SmsCodeServiceImpl extends ServiceImpl<SmsCodeMapper, SmsCode>
        implements SmsCodeService {

    @Resource
    private SmsCodeMapper smsCodeMapper;
    @Resource
    private MemberService memberService;


    private void sendCode(String phone, String use) {
        LocalDateTime now = LocalDateTime.now();
        SmsCode smsCode = new SmsCode();
        String code = RandomUtil.randomNumbers(6);
        smsCode.setCode(code);
        smsCode.setMobile(phone);
        smsCode.setUse(use);
        smsCode.setStatus(SmsCodeStatusEnum.NOT_USED.getCode());

        LambdaQueryWrapper<SmsCode> lambdaQueryWrapper = new LambdaQueryWrapper<SmsCode>()
                .eq(SmsCode::getMobile, phone).orderByDesc(SmsCode::getCreatedAt).last("limit 1");

        SmsCode smsCodeDB = smsCodeMapper.selectOne(lambdaQueryWrapper);

        if (smsCodeDB != null) {
            LocalDateTime nextValidTime = smsCodeDB.getCreatedAt().plusMinutes(1);
            if (now.isBefore(nextValidTime)) {
                throw new BusinessException(BusinessExceptionEnum.SMS_CODE_TOO_FREQUENT);
            }
        }

        smsCodeMapper.insert(smsCode);
        //调用阿里云发送短信
//        SmsUtil.sendCode(phone, code);
    }

    @Override
    public void sendCode4Register(String mobile) {
        boolean exists = memberService.exists(mobile);
            if(exists){
                throw new BusinessException(BusinessExceptionEnum.MEMBER_MOBILE_HAD_REGISTER);
            }
            sendCode(mobile, SmsCodeUseEnum.REGISTER.getCode());
    }
}




