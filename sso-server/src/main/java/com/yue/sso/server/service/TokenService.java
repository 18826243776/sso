package com.yue.sso.server.service;

import com.yue.sso.server.entity.SingleMap;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

/**
 * Created by 千里明月 on 2018/11/19.
 */
@Service
public class TokenService {

    public String createToken(HttpServletRequest request) {
        System.out.println(request);
        UUID token = UUID.randomUUID();
        SingleMap.getTokenMap().put("token",token.toString());
        return token.toString();
    }

    public boolean verifyToken(HttpServletRequest request) {
        System.out.println(request);
        String token = request.getParameter("token");
        String currentToken = (String) SingleMap.getTokenMap().get("token");
        return token != null && token.equals(currentToken);
    }
}
