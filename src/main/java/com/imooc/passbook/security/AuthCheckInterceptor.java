package com.imooc.passbook.security;

import com.imooc.passbook.constant.Constants;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//权限拦截器
@Component
public class AuthCheckInterceptor implements HandlerInterceptor {

    //http处理请求先拦截 前置
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest,
                             HttpServletResponse httpServletResponse, Object o) throws Exception {

        String token = httpServletRequest.getHeader(Constants.TOKEN_STRING);
        if (StringUtils.isEmpty(token)) {
            throw new Exception("Header 中缺少 " + Constants.TOKEN_STRING);
        }
        if (!Constants.TOKEN.equals(token)) {
            throw new Exception("Header 中 " + Constants.TOKEN_STRING + "错误");
        }

        AccessContext.setToken(token);
        return false;
    }

    //http处理完请求拦截 后置
    @Override
    public void postHandle(HttpServletRequest httpServletRequest,
                           HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    //完成后 即是抛出异常 也会走这个方法
    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest,
                                HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        AccessContext.clearAccessKey();
    }
}
