package com.xhkj.nls.business.service.impl;


import cn.hutool.core.util.RandomUtil;
import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.dysmsapi20170525.models.SendSmsResponse;
import com.aliyun.teaopenapi.models.Config;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xhkj.nls.business.domain.SmsCode;
import com.xhkj.nls.business.enums.SmsCodeStatusEnum;
import com.xhkj.nls.business.mapper.SmsCodeMapper;
import com.xhkj.nls.business.service.SmsCodeService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import static com.alibaba.fastjson.JSON.toJSONString;

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

    public void alyCode(String phone) throws Exception {
        Config config = new Config()
                // 配置 AccessKey ID，请确保代码运行环境设置了环境变量 ALIBABA_CLOUD_ACCESS_KEY_ID。
                .setAccessKeyId(System.getenv("ALIBABA_CLOUD_ACCESS_KEY_ID"))
                // 配置 AccessKey Secret，请确保代码运行环境设置了环境变量 ALIBABA_CLOUD_ACCESS_KEY_SECRET。
                .setAccessKeySecret(System.getenv("ALIBABA_CLOUD_ACCESS_KEY_SECRET"));

        // 配置 Endpoint
        config.endpoint = "dysmsapi.aliyuncs.com";

        Client client = new Client(config);
        // 构造请求对象，请填入请求参数值
        SendSmsRequest sendSmsRequest = new SendSmsRequest()
                .setPhoneNumbers(phone)
                .setSignName("阿里云")
                .setTemplateCode("SMS_15305****")
                .setTemplateParam("{\"name\":\"张三\",\"number\":\"1390000****\"}");

        // 获取响应对象
        SendSmsResponse sendSmsResponse = client.sendSms(sendSmsRequest);

        // 响应包含服务端响应的 body 和 headers
        System.out.println(toJSONString(sendSmsResponse));

    }


    @Override
    public void sendCode(String phone, String use) {
        SmsCode smsCode = new SmsCode();
        String code = RandomUtil.randomNumbers(6);
        smsCode.setCode(code);
        smsCode.setMobile(phone);
        smsCode.setUse(use);
        smsCode.setStatus(SmsCodeStatusEnum.NOT_USED.getCode());
        smsCodeMapper.insert(smsCode);
    }
}




