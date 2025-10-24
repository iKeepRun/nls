package com.xhkj.nls.business.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xhkj.nls.business.domain.Member;
import com.xhkj.nls.business.exception.BusinessException;
import com.xhkj.nls.business.exception.BusinessExceptionEnum;
import com.xhkj.nls.business.mapper.MemberMapper;
import com.xhkj.nls.business.req.MemberLoginReq;
import com.xhkj.nls.business.req.MemberRegisterReq;
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

     //明文：1234
    //密码传参：9e0d8f309682f8bdddf15b85ebef56a3
    //数据库：f10fc6a760ee2610
    @Override
    public void register(MemberRegisterReq memberRegisterReq) {
        Member member = BeanUtil.toBean(memberRegisterReq, Member.class);

        member.setPassword(DigestUtil.md5Hex16(member.getPassword()));
        //生成昵称
        StringBuilder sb = new StringBuilder();
        for (int i = 3; i > 0; i--) {
            sb.append(RandomUtil.randomChinese());
        }
        sb.append(RandomUtil.randomString(6));
        sb.append(System.currentTimeMillis());
        member.setName(sb.toString());
        memberMapper.insert(member);
    }


    @Override
    public Member login(MemberLoginReq memberLoginReq) {

        Member member = memberMapper.selectOne(new LambdaQueryWrapper<Member>()
                .eq(Member::getMobile, memberLoginReq.getMobile())
                .eq(Member::getPassword, DigestUtil.md5Hex16(memberLoginReq.getPassword())));
        if (member == null) {
            throw new BusinessException(BusinessExceptionEnum.MEMBER_LOGIN_ERROR);
        }
       return member;
    }

    public static void main(String[] args) {
        System.out.println(DigestUtil.md5Hex16("9E0D8F309682F8BDDDF15B85EBEF56A3"));
    }

}




