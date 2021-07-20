package com.example.util;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * 【名称】</br>
 * RequestとResponse情報取得
 * 
 * @author eptsz01
 *
 */
public class RequestContextHolderUtil {
    /**
     * 【名称】</br>
     * HttpServletRequestを取得する
     * 
     * @return HttpServletRequest
     */
    public static HttpServletRequest getRequest() {
        return getRequestAttributes().getRequest();
    }

    /**
     * 【名称】</br>
     * HttpServletResponseを取得する
     * 
     * @return HttpServletResponse
     */
    public static HttpServletResponse getResponse() {
        return getRequestAttributes().getResponse();
    }

    /**
     * 【名称】</br>
     * HttpSessionを取得する
     * 
     * @return HttpSession
     */
    public static HttpSession getSession() {
        return getRequest().getSession();
    }

    /**
     * 【名称】</br>
     * ServletRequestAttributesを取得する
     * 
     * @return ServletRequestAttributes
     */
    public static ServletRequestAttributes getRequestAttributes() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes());
    }

    /**
     * 【名称】</br>
     * ServletContextを取得する
     * 
     * @return ServletContext
     */
    public static ServletContext getServletContext() {
        return ContextLoader.getCurrentWebApplicationContext().getServletContext();
    }

}
