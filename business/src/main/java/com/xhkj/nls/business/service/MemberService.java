package com.xhkj.nls.business.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xhkj.nls.business.domain.Member;

/**
* @author Administrator
* @description 针对表【member(会员表)】的数据库操作Service
* @createDate 2025-10-20 15:10:01
*/
public interface MemberService extends IService<Member> {
    boolean exists(String mobile);
}
