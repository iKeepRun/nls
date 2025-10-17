package com.xhkj.nls.business.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 短信验证码表
 * @TableName sms_code
 */
@TableName(value ="sms_code")
@Data
public class SmsCode implements Serializable {
    /**
     * id
     */
    @TableId(value = "id")
    private Long id;

    /**
     * 手机号
     */
    @TableField(value = "mobile")
    private String mobile;

    /**
     * 验证码
     */
    @TableField(value = "code")
    private String code;

    /**
     * 用途|枚举[SmsCodeUseEnum]：REGISTER("0", "注册"), FORGET_PASSWORD("1", "忘记密码")
     */
    @TableField(value = "use")
    private String use;

    /**
     * 状态|枚举[SmsCodeStatusEnum]：USED("1", "已使用"), NOT_USED("0", "未使用")
     */
    @TableField(value = "status")
    private String status;

    /**
     * 创建时间
     */
    @TableField(value = "created_at")
    private Date created_at;

    /**
     * 修改时间
     */
    @TableField(value = "updated_at")
    private Date updated_at;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}