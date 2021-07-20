package com.example.handle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 【名称】</br>
 * 異常捕獲用Exception
 *
 * @author eptsz01
 */
@Configuration
public class GlobalExceptionHandle implements HandlerExceptionResolver {

    // log
    private final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandle.class);

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
                                         Exception ex) {
        ModelAndView mv = new ModelAndView();

        logger.error("【緊急】異常が発生しました", ex);
        mv.setViewName("error/500");

        return mv;
    }

}
