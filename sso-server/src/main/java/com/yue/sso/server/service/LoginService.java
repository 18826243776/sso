package com.yue.sso.server.service;

import com.yue.sso.server.entity.SingleMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by 千里明月 on 2018/11/9.
 */
@Service
public class LoginService {
    @Value("${cas-client-url}")
    private String casClient;
    @Value("${cas-client1-url}")
    private String casClient1;

    @Autowired
    private TokenService tokenService;

    public static final String DEFAULT_USERNAME = "username";
    public static final String DEFAULT_PASSWORD = "password";

    public boolean login(String username, String password, HttpServletRequest request, HttpServletResponse response) {
        String gotoUrl = request.getParameter("gotoUrl");
        boolean remark = check(username, password);
        if (!remark) {
            return false;
        }
        request.getSession().setAttribute("isLogin", true);
//        Cookie cookie = new Cookie("sso-server", "sso");
//        cookie.setPath("/");
//        response.addCookie(cookie);
        String token = tokenService.createToken(request);
        if (gotoUrl != null && !gotoUrl.isEmpty()) {
            try {
                URL url = new URL(gotoUrl);
                String query = url.getQuery();
                if (query != null && !query.isEmpty()) {
                    gotoUrl += ("&token=" + token);
                } else {
                    gotoUrl += ("?token=" + token);
                }
                response.sendRedirect(gotoUrl);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return remark;
    }

    public boolean check(String username, String password) {
        return DEFAULT_USERNAME.equals(username) && DEFAULT_PASSWORD.equals(password);
    }

    public void logout(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        Object isLogin = session.getAttribute("isLogin");
        if (isLogin != null) {
            session.setAttribute("isLogin", false);
        }
        Object token = SingleMap.getTokenMap().get("token");
        if (token != null) {
            SingleMap.getTokenMap().put("token", null);
        }

        try {
            //向所有子系统发起注销申请
            logoutToAll(casClient);
            logoutToAll(casClient1);
            //跳转至登录页面
            response.sendRedirect("/login.html");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void logoutToAll( String subUrl) throws IOException {
        URL url = new URL(subUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Charset", "UTF-8");
        connection.connect();
        //获得响应状态
        int resultCode = connection.getResponseCode();
    }


//    public boolean checkCookie() {
//        Cookie[] cookies = request.getCookies();
//        for (Cookie cookie : cookies) {
//            if (cookie.getName().equals("sso-server") && cookie.getValue().equals("sso")) {
//                return true;
//            }
//        }
//        return false;
//    }
}
