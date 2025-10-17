package com.xhkj.nls.business.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

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
    @TableId(value = "id", type= IdType.ASSIGN_ID)
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
    @TableField(value = "`use`")
    private String use;

    /**
     * 状态|枚举[SmsCodeStatusEnum]：USED("1", "已使用"), NOT_USED("0", "未使用")
     */
    @TableField(value = "status")
    private String status;

    /**
     * 创建时间
     */
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
//    @DateTimeFormat(fallbackPatterns = "yyyy/MM/dd HH:mm:ss")
    @TableField(value = "created_at", fill = FieldFill.INSERT)
    private LocalDateTime createdAt;

    /**
     * 修改时间
     */
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
//    @DateTimeFormat(fallbackPatterns = "yyyy/MM/dd HH:mm:ss")
    @TableField(value = "updated_at", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedAt;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}