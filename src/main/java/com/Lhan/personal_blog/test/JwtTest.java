package com.Lhan.personal_blog.test;

import com.Lhan.personal_blog.entity.security.JwtUser;
import com.Lhan.personal_blog.util.JwtUtil;

/**
 * 测试解析token
 */
public class JwtTest {
    public static void main(String[] args) {
        JwtUtil jwtUtil = new JwtUtil();
        JwtUser jwtUser = jwtUtil.getUserFromToken("eyJhbGciOiJIUzI1NiIsInppcCI6IkRFRiJ9.eNqqViouTVKyUvLJSMzzTcxT0lHKTFGyyivNydFRSq0oULIyNLWwtDQxMjQ11lEqys9RsopWCvL3cY0PDXYNAqoGsx1dfD39YBy3IE9XPxel2FoAAAAA__8.yC1I0TM84OVMt6XXkpFR7-WSf9wwu74WsCxkf9T2Gos");
        System.out.println(jwtUser);
    }
}
