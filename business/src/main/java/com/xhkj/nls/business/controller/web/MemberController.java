package com.xhkj.nls.business.controller.web;


import com.xhkj.nls.business.common.CommonResp;
import com.xhkj.nls.business.domain.Member;
import com.xhkj.nls.business.enums.SmsCodeUseEnum;
import com.xhkj.nls.business.exception.BusinessException;
import com.xhkj.nls.business.exception.BusinessExceptionEnum;
import com.xhkj.nls.business.req.MemberLoginReq;
import com.xhkj.nls.business.req.MemberRegisterReq;
import com.xhkj.nls.business.service.MemberService;
import com.xhkj.nls.business.service.SmsCodeService;
import com.xhkj.nls.business.util.JwtUtil;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

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
        log.info("获取到captcha key: {} ", memberRegisterReq.getCaptchaId());
        log.info("获取到captcha: {} ", memberRegisterReq.getCaptcha());

        String cacheResult = stringRedisTemplate.opsForValue().get(memberRegisterReq.getCaptchaId());
        log.info("获取到缓存的计算结果: {} ", cacheResult);
        if (!cacheResult.equals(memberRegisterReq.getCaptcha())) {
            throw new BusinessException(BusinessExceptionEnum.IMAGE_CODE_ERROR);
        }
        smsCodeService.checkCode(memberRegisterReq.getMobile(), memberRegisterReq.getCode(), SmsCodeUseEnum.REGISTER.getCode());
        memberService.register(memberRegisterReq);

        return CommonResp.builder().success(true).build();
    }


    //验证码生成接口


    @PostMapping(value = "/login")
    public CommonResp login(@Valid @RequestBody MemberLoginReq memberLoginReq) {
        Member member = memberService.login(memberLoginReq);

        String token = JwtUtil.createToken(Map.of("mobile", memberLoginReq.getMobile()),30);

        Map<String, Object> map = new HashMap<>();
        map.put("name", member.getName());
        map.put("token", token);


        return CommonResp.builder().success(true).content(map).build();
    }
}
