package com.Lhan.personal_blog.service;

import com.Lhan.personal_blog.common.base.Result;
import com.Lhan.personal_blog.pojo.User;
import com.Lhan.personal_blog.util.DataMap;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {

    User getUsernameAndRolesByPhone(String phone);

    /**
     * 更新最近登录时间
     * @param username
     * @param recentlyLanded
     */
    void updateRecentlyLanded(String username,String recentlyLanded);

    boolean usernameIsExist(String username);

    DataMap insert(User user);

    boolean userIsExist(String phone);

    Long findUserIdByPhone(String phone);

    void insertRole(Long userId,Long roleId);

    User findUserByPhone(String phone);

    void updatePasswordByPhone(String phone,String password);

    Long findUserIdByUsername(String username);

    String findUsernameByUserId(Long userId);

    String findAvatarImgUrlByUserId(Long userId);

    int findUserNum();

    DataMap findAllUserByPage(int pageNum,int pageSize);

    void lockUser(Long user_id);

    void unlockUser(Long user_id);

    User findUserByUserId(Long user_id);

    void updateUser(User user);

    DataMap updateAvatarImgByUserId(Long user_id, MultipartFile file);

    DataMap findAllUserRoleByPage(int pageNum,int pageSize);

    DataMap upUserRight(Long user_id);

    DataMap downUserRight(Long user_id);

    void deleteRole(Long user_id);

    User getUserPersonalInfoByUsername(String username);

    Result savePersonalData(User user,String username);

    boolean isSuperAdmin(String username);

    Result getMasterUserInfo();

    Result findUserExistByPhone(String phone);

}
