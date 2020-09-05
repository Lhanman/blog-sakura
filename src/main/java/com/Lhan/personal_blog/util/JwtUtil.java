package com.Lhan.personal_blog.util;

import com.Lhan.personal_blog.constant.CommonConstant;
import com.Lhan.personal_blog.entity.security.JwtUser;
import com.Lhan.personal_blog.pojo.Role;
import com.Lhan.personal_blog.pojo.User;
import com.Lhan.personal_blog.redis.StringRedisServiceImpl;
import io.jsonwebtoken.*;
import net.sf.json.util.JSONUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Component
public class JwtUtil {

    @Autowired
    StringRedisServiceImpl stringRedisService;


    public JwtUser getUserFromToken(String token)
    {
        JwtUser user;
        try {
            final Claims claims = getClaimsFromToken(token);
            long user_id = getUserIdFromToken(token);
            String username = claims.getSubject();
            List<GrantedAuthority> authorities = new ArrayList<>();
            user = new JwtUser(user_id,username,authorities);
        }
        catch (Exception e)
        {
            user = null;
        }
        return user;
    }

    public Long getUserIdFromToken(String token)
    {
        long user_id;
        try
        {
            final Claims claims = getClaimsFromToken(token);
            user_id = Long.parseLong(String.valueOf(claims.get("id")));
        }
        catch (Exception e)
        {
            user_id = 0L;
        }
        return user_id;
    }

    public Date getCreatedDateFromToken(String token) {
        Date created;
        try {
            final Claims claims = getClaimsFromToken(token);
            created = claims.getIssuedAt();
        } catch (Exception e) {
            created = null;
        }
        return created;
    }

    public Date getExpirationDateFromToken(String token) {
        Date expiration;
        try {
            final Claims claims = getClaimsFromToken(token);
            expiration = claims.getExpiration();
        } catch (Exception e) {
            expiration = null;
        }
        return expiration;
    }




    public String generateAccessToken(JwtUser jwtUser)
    {
        Map<String,Object> claims = generateClaims(jwtUser);
        claims.put(CommonConstant.ROLE_CLAIMS,jwtUser.getAuthorities());
        return generateAccessToken(jwtUser.getUsername(),claims,jwtUser.getPhone());
    }

    public Boolean canTokenBeRefreshed(String token,Date lastPasswordRest)
    {
        final Date created = getCreatedDateFromToken(token);
        return !isCreatedBeforeLastPasswordReset(created,lastPasswordRest)
                            && (!isExpiration(token));
    }

    public String refreshToken(String token)
    {
        String refreshedToken;
        try {
            final Claims claims = getClaimsFromToken(token);
            refreshedToken = generateAccessToken(claims.getSubject(),claims,getPhone(token));
        }catch (Exception e)
        {
            refreshedToken = null;
        }
        return refreshedToken;
    }

    public Boolean validateToken(String token,JwtUser user)
    {
        JwtUser userDetail = (JwtUser) user;
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetail.getUsername())
                            && !isExpiration(token));
    }

    public String generateRefreshToken(JwtUser user)
    {
        Map<String,Object> claims = generateClaims(user);
        //只授权于更新token的权限
        claims.put(CommonConstant.ROLE_CLAIMS,"ROLE_REFRESH_TOKEN");

        return generateRefreshToken(user.getUsername(),claims,user.getPhone());

    }

    public void putToken(String username,String token)
    {
        stringRedisService.set(username,token);
    }

    public void deleteToken(String username)
    {
        stringRedisService.remove(username);
    }

    public boolean containToken(String username,String token)
    {

        if (username != null && stringRedisService.hasKey(username)
                && stringRedisService.get(username).equals(token))
        {
            return true;
        }
        return false;
    }


    private Map<String,Object> generateClaims(JwtUser user)
    {
        Map<String,Object> claims = new HashMap<>(16);
        claims.put("id",user.getId());
        return claims;
    }



    private Claims getClaimsFromToken(String token)
    {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(CommonConstant.APPSECRET_KEY)
                    .parseClaimsJws(token)
                    .getBody();
        }
        catch (Exception e)
        {
            return null;
        }
        return claims;
    }

    public String getUsernameFromToken(String token)
    {
        String username;
        try
        {
            final Claims claims = getClaimsFromToken(token);
            username = claims.getSubject();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            username = null;
        }
        return username;
    }

    private List authoritiesToArray(Collection<? extends GrantedAuthority> authorities)
    {
        List<String> list = new ArrayList<>();
        for (GrantedAuthority grantedAuthority : authorities)
        {
            list.add(grantedAuthority.getAuthority());
        }
        return list;
    }

    private String generateRefreshToken(String subject,Map<String,Object> claims,String phone)
    {
        return generateToken(subject,claims,phone);
    }

    private String generateAccessToken(String subject,Map<String,Object> claims,String phone)
    {
        return generateToken(subject,claims,phone);
    }

    private String generateToken(String subject,Map<String,Object> claims,String phone)
    {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .claim("phone",phone)
                .setExpiration(new Date(System.currentTimeMillis() + CommonConstant.EXPIRE_TIME))
                .compressWith(CompressionCodecs.DEFLATE)
                .signWith(SignatureAlgorithm.HS256,CommonConstant.APPSECRET_KEY)
                .compact();
    }

    private Boolean isCreatedBeforeLastPasswordReset(Date created,Date lastPasswordRest)
    {
        return (lastPasswordRest != null && created.before(lastPasswordRest));
    }

    /**
     * 生成token
     */
    public static String createToken(String username,String role)
    {
        Map<String,Object> map = new HashMap<>();
        map.put(CommonConstant.ROLE_CLAIMS,role);

        String token = Jwts
                .builder()
                .setSubject(username)
                .setClaims(map)
                .claim("username",username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + CommonConstant.EXPIRE_TIME))
                .signWith(SignatureAlgorithm.HS256,CommonConstant.APPSECRET_KEY).compact();
        return token;
    }

    public static Claims checkJWT(String token)
    {
        try
        {
            final Claims claims = Jwts
                    .parser()
                    .setSigningKey(CommonConstant.APPSECRET_KEY)
                    .parseClaimsJws(token)
                    .getBody();
            return claims;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取用户名
     */
    public static String getUsername(String token)
    {
        Claims claims = Jwts
            .parser()
            .setSigningKey(CommonConstant.APPSECRET_KEY)
            .parseClaimsJws(token)
            .getBody();

        return claims.getSubject();
    }

    public  String getPhone(String token)
    {
        Claims claims = Jwts
                .parser()
                .setSigningKey(CommonConstant.APPSECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
        return claims.get("phone").toString();
    }

    /**
     * 获取用户角色
     *
     */
    public static String getUserRole(String token)
    {
        Claims claims = Jwts
                .parser()
                .setSigningKey(CommonConstant.APPSECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
        return claims.get(CommonConstant.ROLE_CLAIMS).toString();
    }

    /**
     * 是否过期
     */
    public static boolean isExpiration(String token)
    {
        Claims claims = Jwts
                .parser()
                .setSigningKey(CommonConstant.APPSECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
        return claims.getExpiration().before(new Date());
    }

}
