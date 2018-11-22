package com.yue.sso.client.entity;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 千里明月 on 2018/11/20.
 */
public class SingleMap {

    private SingleMap() {
    }

    private static volatile Map<String, String> tokenMap = new HashMap<>(3);

    public static Map getTokenMap() {
        return tokenMap;
    }


}
