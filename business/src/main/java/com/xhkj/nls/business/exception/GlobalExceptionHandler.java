package com.xhkj.nls.business.exception;


import com.xhkj.nls.business.common.CommonResp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 统一异常处理、数据预处理等
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 所有异常统一处理
     * @param e
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public CommonResp<Object> exceptionHandler(Exception e) {
        log.error("系统异常：", e);
        return CommonResp.builder().success(false).message("系统出现异常，请联系管理员").build();
    }


    /**
     * 所有业务异常统一处理
     * @param e
     * @return
     */
    @ExceptionHandler(value = BusinessException.class)
    @ResponseBody
    public CommonResp<Object> exceptionHandler(BusinessException e) {
        log.error("系统异常：", e);
        return CommonResp.builder().success(false).message(e.getBusinessExceptionEnum().getDesc()).build();
    }

    /**
     * 校验异常统一处理
     * @param e
     * @return
     */
    @ExceptionHandler(value = BindException.class)
    @ResponseBody
    public CommonResp<Object> exceptionHandler(BindException e) {
        log.error("校验异常：{}", e.getBindingResult().getAllErrors().get(0).getDefaultMessage());
        return CommonResp.builder().success(false).message(e.getBindingResult().getAllErrors().get(0).getDefaultMessage()).build();
    }

}
