package com.yue.sso.client1.config;


import com.yue.sso.client1.entity.SingleMap;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by 千里明月 on 2018/11/19.
 */
@Component
public class HandleInter implements HandlerInterceptor {
    @Value("${cas-server-url}")
    private String casServerUrl;
    @Value("${cas-server-verify-url}")
    private String casVerifyUrl;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
 //        1.	拦截所有非静态资源的请求，如已经登录，则放行
//        2.	如未登录且没有令牌，则跳至s
//        3.	如未登录但是有令牌，则验证令牌，令牌合法就创建会话保存登录状态。

        System.out.println("cas-client1拦截器生效");
        HttpSession session = request.getSession();
        Object isLogin = session.getAttribute("isLogin");
        if (isLogin != null && (boolean) isLogin) {
            return true;
        }
        //未登录 有令牌
        String token = request.getParameter("token");
        if (token != null) {
            boolean remark = verifyToken(token);
            if (remark) {
                SingleMap.getTokenMap().put("token",token);
                session.setAttribute("isLogin", true);
                return true;
            } else {
                return false;
            }
        }
        //未登录 无令牌
        System.out.println("client1未登录 无令牌");
        StringBuffer requestURL = request.getRequestURL();
        String queryString = request.getQueryString();
        String url = requestURL.toString();
        if (queryString != null && !queryString.isEmpty()) {
            url = requestURL + "?" + queryString;
        }
        response.sendRedirect(casServerUrl + "?gotoUrl=" + url);
        return false;
    }

    private boolean verifyToken(String token) throws IOException {
        URL url = new URL(casVerifyUrl + token);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setConnectTimeout(30000);
        connection.setReadTimeout(30000);
        connection.setRequestProperty("Charset", "UTF-8");

        connection.setDoOutput(true);   //需要输出
//        connection.setDoInput(true);   //需要输入
        //连接,也可以不用明文connect，使用下面的httpConn.getOutputStream()会自动connect
        connection.connect();

        //获得响应状态
        int resultCode = connection.getResponseCode();
        if (HttpURLConnection.HTTP_OK == resultCode) {
            try (
                    InputStream inputStream = connection.getInputStream();
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ) {
                int i = 0;
                byte[] buffer = new byte[100];
                while ((i = (inputStream.read(buffer))) != -1) {
                    byteArrayOutputStream.write(buffer);
                }
                String result = byteArrayOutputStream.toString("UTF-8");
                System.out.println(result);
                if (Boolean.valueOf(result.trim())) {
                    return true;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }


}
