package com.xhkj.nls.business.service.impl;


import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xhkj.nls.business.domain.SmsCode;
import com.xhkj.nls.business.enums.SmsCodeStatusEnum;
import com.xhkj.nls.business.exception.BusinessException;
import com.xhkj.nls.business.exception.BusinessExceptionEnum;
import com.xhkj.nls.business.mapper.SmsCodeMapper;
import com.xhkj.nls.business.service.SmsCodeService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

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


    @Override
    public void sendCode(String phone, String use) {
        LocalDateTime now = LocalDateTime.now();
        SmsCode smsCode = new SmsCode();
        String code = RandomUtil.randomNumbers(6);
        smsCode.setCode(code);
        smsCode.setMobile(phone);
        smsCode.setUse(use);
        smsCode.setStatus(SmsCodeStatusEnum.NOT_USED.getCode());

        LambdaQueryWrapper<SmsCode> lambdaQueryWrapper = new LambdaQueryWrapper<SmsCode>()
                .eq(SmsCode::getMobile, phone).orderBy(true, false, SmsCode::getCreatedAt);

        List<SmsCode> smsCodes = smsCodeMapper.selectList(lambdaQueryWrapper);

        if (smsCodes != null && smsCodes.size() > 0) {
            SmsCode smsCode1 = smsCodes.get(0);
            LocalDateTime expireTime = smsCode1.getCreatedAt().plusMinutes(1);
            if (now.isBefore(expireTime)) {
                throw new BusinessException(BusinessExceptionEnum.SMS_CODE_TOO_FREQUENT);
            }
        }

        smsCodeMapper.insert(smsCode);
        //调用阿里云发送短信
//        SmsUtil.sendCode(phone, code);
    }
}




