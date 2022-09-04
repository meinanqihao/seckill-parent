package com.seckill.framework;

import com.seckill.util.Result;
import com.seckill.util.StatusCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.PrintWriter;
import java.io.StringWriter;

/*****
 * @Author: 黑马训练营
 * @Description: com.changgou.framework.exception
 ****/
@ControllerAdvice   //所有请求路径，都将被该类处理->过滤器/(拦截器)
public class BaseExceptionHandler {

    private static Logger logger = LoggerFactory.getLogger(BaseExceptionHandler.class);

    /***
     * 异常处理
     * 当前请求发生了指定异常，则执行该方法处理异常
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result error(Exception ex){
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        ex.printStackTrace(writer);
        ex.printStackTrace();
        logger.error(stringWriter.toString());
        return new Result(false, StatusCode.ERROR,ex.getMessage(),stringWriter.toString());
    }
}
