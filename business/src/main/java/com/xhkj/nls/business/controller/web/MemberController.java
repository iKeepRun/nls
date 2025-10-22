package com.xhkj.nls.business.controller.web;

import com.xhkj.nls.business.common.CommonResp;
import com.xhkj.nls.business.enums.SmsCodeUseEnum;
import com.xhkj.nls.business.req.MemberLoginReq;
import com.xhkj.nls.business.req.MemberRegisterReq;
import com.xhkj.nls.business.service.MemberService;
import com.xhkj.nls.business.service.SmsCodeService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/web/member")
public class MemberController {
    @Resource
    private SmsCodeService smsCodeService;
    @Resource
    private MemberService memberService;

    @PostMapping(value = "/register")
    public CommonResp register(@Valid @RequestBody MemberRegisterReq memberRegisterReq) {
        smsCodeService.checkCode(memberRegisterReq.getMobile(), memberRegisterReq.getCode(), SmsCodeUseEnum.REGISTER.getCode());
        memberService.register(memberRegisterReq);

        return CommonResp.builder().success(true).build();
    }



    @PostMapping(value = "/login")
    public CommonResp login(@Valid @RequestBody MemberLoginReq memberLoginReq) {
        memberService.login(memberLoginReq);
        return CommonResp.builder().success(true).build();
    }

}
