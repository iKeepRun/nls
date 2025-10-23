package com.xhkj.nls.business.controller.web;


import com.pig4cloud.captcha.ArithmeticCaptcha;
import com.xhkj.nls.business.common.CommonResp;
import com.xhkj.nls.business.enums.SmsCodeUseEnum;
import com.xhkj.nls.business.exception.BusinessException;
import com.xhkj.nls.business.exception.BusinessExceptionEnum;
import com.xhkj.nls.business.req.MemberLoginReq;
import com.xhkj.nls.business.req.MemberRegisterReq;
import com.xhkj.nls.business.service.MemberService;
import com.xhkj.nls.business.service.SmsCodeService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
@RequestMapping("/web/member")
public class MemberController {
    @Resource
    private SmsCodeService smsCodeService;
    @Resource
    private MemberService memberService;
    @Resource
    public StringRedisTemplate stringRedisTemplate;

    @PostMapping(value = "/register")
    public CommonResp register(@Valid @RequestBody MemberRegisterReq memberRegisterReq) {
        log.info("获取到captcha key: {} ",memberRegisterReq.getCaptchaId());
        log.info("获取到captcha: {} ",memberRegisterReq.getCaptcha());

        String cacheResult = stringRedisTemplate.opsForValue().get(memberRegisterReq.getCaptchaId());
        log.info("获取到缓存的计算结果: {} ",cacheResult);
        if(!cacheResult.equals(memberRegisterReq.getCaptcha())){
            throw new BusinessException(BusinessExceptionEnum.IMAGE_CODE_ERROR);
        }
        smsCodeService.checkCode(memberRegisterReq.getMobile(), memberRegisterReq.getCode(), SmsCodeUseEnum.REGISTER.getCode());
        memberService.register(memberRegisterReq);

        return CommonResp.builder().success(true).build();
    }



    //验证码生成接口
    @GetMapping("/captcha")
    public CommonResp captcha(HttpServletResponse response) throws IOException {
        // 设置请求头为输出图片类型
        response.setContentType("image/gif");
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);

        ArithmeticCaptcha captcha = new ArithmeticCaptcha(120, 38);
        captcha.setLen(2);  // 几位数运算，默认是两位
        captcha.getArithmeticString();  // 获取运算的公式：3+2=?
        String result = captcha.text();// 获取运算的结果：5
        log.info("captcha result: " + result);

        String key = UUID.randomUUID().toString();
        // 存入redis并设置过期时间为30分钟
        stringRedisTemplate.opsForValue().set(key, result, 10*60, TimeUnit.SECONDS);
        captcha.supportAlgorithmSign(4); // 可设置支持的算法：2 表示只生成带加减法的公式
        captcha.setDifficulty(50); // 设置计算难度，参与计算的每一个整数的最大值
//        captcha.out(response.getOutputStream());  // 输出验证码
        captcha.toBase64();
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("captcha", captcha.toBase64());
        hashMap.put("key", key);
        return CommonResp.builder().success(true).content(hashMap).build();
    }

    @PostMapping(value = "/login")
    public CommonResp login(@Valid @RequestBody MemberLoginReq memberLoginReq) {
        memberService.login(memberLoginReq);
        return CommonResp.builder().success(true).build();
    }

}
