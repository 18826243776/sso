package com.yue.sso.client1.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by 千里明月 on 2018/11/21.
 */
@RestController
@RequestMapping("/sso/client1/user")
public class UserController {

    @GetMapping("/listUser")
    public Collection listUser() {
        List<Integer> list = new ArrayList<>(12);
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);
        return list;
    }
}
