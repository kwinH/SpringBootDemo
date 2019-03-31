package com.example.handle;

import com.example.enums.ResultEnum;
import com.example.exception.ResultException;
import com.example.response.Result;
import com.example.utils.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@ControllerAdvice
public class exceptionHandle {

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Result handle(Exception e) {

        if (e instanceof ResultException) {
            ResultException resultException = (ResultException) e;
            return ResultUtil.error(resultException.getCode(), resultException.getMessage());
        } else {
            log.error("【系统异常】{}", e);

            ResultEnum unkonwError = ResultEnum.UNKONW_ERROR;
            return ResultUtil.error(unkonwError.getCode(), unkonwError.getMsg());
        }
    }
}
