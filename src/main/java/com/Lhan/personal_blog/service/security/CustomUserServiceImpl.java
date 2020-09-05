package com.Lhan.personal_blog.service.security;

import com.Lhan.personal_blog.entity.security.JwtUser;
import com.Lhan.personal_blog.mapper.UserMapper;
import com.Lhan.personal_blog.pojo.Role;
import com.Lhan.personal_blog.pojo.User;
import com.Lhan.personal_blog.pojo.UserExample;
import com.Lhan.personal_blog.service.UserService;
import com.Lhan.personal_blog.util.DateFormatUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomUserServiceImpl implements UserDetailsService {

    @Autowired
    UserService userService;

    private static final String RoleName = "ROLE_";

    @Override
    public UserDetails loadUserByUsername(String phone) throws UsernameNotFoundException {
        User user = userService.getUsernameAndRolesByPhone(phone);
        if (user == null)
        {
            throw new UsernameNotFoundException("用户不存在");
        }
        DateFormatUtil dateFormatUtil = new DateFormatUtil();
        String recentlyLanded = dateFormatUtil.getFormatDateForSix();
        userService.updateRecentlyLanded(user.getUsername(),recentlyLanded);
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();

        for (Role role : user.getRoles())
        {
            authorities.add(new SimpleGrantedAuthority(RoleName+role.getName()));
        }

        return new JwtUser(user.getUsername(),user.getPassword(),user.getPhone(),user.getAvatarimgUrl()
                ,authorities);
    }
}
