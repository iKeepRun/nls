package com.xhkj.nls.business.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xhkj.nls.business.domain.Member;
import com.xhkj.nls.business.mapper.MemberMapper;
import com.xhkj.nls.business.service.MemberService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
* @author Administrator
* @description 针对表【member(会员表)】的数据库操作Service实现
* @createDate 2025-10-20 15:10:01
*/
@Service
public class MemberServiceImpl extends ServiceImpl<MemberMapper, Member>
    implements MemberService{
    @Resource
    private MemberMapper memberMapper;

    public boolean exists(String mobile){
      return   memberMapper.exists(new LambdaQueryWrapper<Member>().eq(Member::getMobile, mobile));
    }
}




