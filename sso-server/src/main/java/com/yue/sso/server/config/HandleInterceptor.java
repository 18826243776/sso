package com.yue.sso.server.config;

import com.yue.sso.server.entity.SingleMap;
import com.yue.sso.server.service.LoginService;
import com.yue.sso.server.service.TokenService;
import jdk.nashorn.internal.parser.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.net.URL;

/**
 * Created by 千里明月 on 2018/11/19.
 */
@Component
public class HandleInterceptor implements HandlerInterceptor {
    @Autowired
    private LoginService loginService;
    @Autowired
    private TokenService tokenService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

//        1.	拦截所有来自子系统的请求，如未登录，则跳至登录页面，
//        2.	如已经登录但没有令牌，就附上令牌，并返回子系统的目标地址
//        3.	如已经登录也有令牌，则直接放行


        HttpSession session = request.getSession();
        // 获取子系统的目标地址
        String gotoUrl = (String) request.getParameter("gotoUrl");
        //获取认证中心登录状态
        Object isLogin = session.getAttribute("isLogin");
        if (isLogin == null || !(boolean) isLogin) {
            //未登录  返回登录页面
            response.sendRedirect("/login.html?gotoUrl=" + gotoUrl);
            System.out.println("返回登录页面");
            return false;
        }

        //已登录 有令牌
        if (tokenService.verifyToken(request)) {
            return true;
        }

        //已登录 没有令牌
        String token = (String) SingleMap.getTokenMap().get("token");
        URL url = new URL(gotoUrl);
        String query = url.getQuery();
        if (query == null) {
            gotoUrl = (gotoUrl + "?token=" + token);
        } else {
            gotoUrl = (gotoUrl + "&token=" + token);
        }
        response.sendRedirect(gotoUrl);
        return false;
    }

}
