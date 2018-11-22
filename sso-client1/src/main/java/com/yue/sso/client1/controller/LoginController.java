package com.yue.sso.client1.controller;

import com.yue.sso.client1.entity.SingleMap;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by 千里明月 on 2018/11/22.
 */
@RestController
@RequestMapping("/sso/client1")
public class LoginController {
    @Value("${cas-server-url}")
    private String casServer;

    @GetMapping("/logout")
    public void logout() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        Object isLogin = session.getAttribute("isLogin");
        if (isLogin == null || (boolean) isLogin == false) {
            return;
        }
        session.setAttribute("isLogin", false);
    }

    @GetMapping("/logoutToServer")
    public void logoutToServer(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String token = (String) SingleMap.getTokenMap().get("token");
        response.sendRedirect(casServer + "/sso/server/logout?token="+token);
    }
}
