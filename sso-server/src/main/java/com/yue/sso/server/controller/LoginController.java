package com.yue.sso.server.controller;

import com.yue.sso.server.service.LoginService;
import com.yue.sso.server.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by 千里明月 on 2018/11/9.
 */
@RestController
@RequestMapping("/sso/server")
public class LoginController {

    @Autowired
    private LoginService loginService;
    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public boolean login(HttpServletRequest request, HttpServletResponse response, @RequestParam String username, @RequestParam String password, String gotoUrl) {
        System.out.println(gotoUrl);
        return loginService.login(username, password, request, response);
    }

    @PostMapping("verifyToken")
    public boolean verifyToken(HttpServletRequest request) {
        return tokenService.verifyToken(request);
    }

    @GetMapping("/logout")
    public void logout(HttpServletRequest request,HttpServletResponse response){
        loginService.logout(request,response);
    }



}
