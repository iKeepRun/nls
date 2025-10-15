package com.xhkj.nls.business.controller;

import com.xhkj.nls.business.common.CommonResp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class TestController {

    @RequestMapping("/test")
    public CommonResp test() {
        log.info("测试成功");
        return CommonResp.builder().success(true).message("测试成功").build();
    }
}

