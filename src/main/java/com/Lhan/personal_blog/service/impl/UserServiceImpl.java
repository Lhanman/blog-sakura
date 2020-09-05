package com.Lhan.personal_blog.service.impl;

import com.Lhan.personal_blog.common.base.Result;
import com.Lhan.personal_blog.common.enums.ErrorEnum;
import com.Lhan.personal_blog.constant.CodeType;
import com.Lhan.personal_blog.constant.OSSClientConstants;
import com.Lhan.personal_blog.constant.RoleConstant;
import com.Lhan.personal_blog.dto.UserDto;
import com.Lhan.personal_blog.mapper.RoleMapper;
import com.Lhan.personal_blog.mapper.UserAndRoleMapper;
import com.Lhan.personal_blog.mapper.UserMapper;
import com.Lhan.personal_blog.pojo.*;
import com.Lhan.personal_blog.service.UserService;
import com.Lhan.personal_blog.util.DataMap;
import com.Lhan.personal_blog.util.DateFormatUtil;
import com.Lhan.personal_blog.util.FileUtil;
import com.Lhan.personal_blog.util.StringUtil;
import com.Lhan.personal_blog.vo.AuthUserVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import net.sf.json.JSONArray;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {


    @Resource
    RoleMapper roleMapper;

    @Resource
    UserAndRoleMapper userAndRoleMapper;

    @Resource
    UserMapper userMapper;

    /**
     * 通过手机号获得用户名和角色
     * @param phone
     * @return
     */
    @Override
    public User getUsernameAndRolesByPhone(String phone) {
        UserExample example = new UserExample();
        example.or().andPhoneEqualTo(phone);
        User user = userMapper.selectByExample(example).get(0);
        UserAndRoleExample example1 = new UserAndRoleExample();
        example1.or().andUserIdEqualTo(user.getId());
        List<UserAndRole> userAndRoleList = userAndRoleMapper.selectByExample(example1);
        List<Role> roleList = new ArrayList<>();
        for (UserAndRole userAndRole :userAndRoleList)
        {
            Role role = new Role();
            role = roleMapper.selectByPrimaryKey(userAndRole.getRoleId());
            roleList.add(role);
        }
        user.setRoles(roleList);
        return user;
    }

    /**
     * 更新用户最近登录的时间
     * @param username
     * @param recentlyLanded
     */
    @Override
    public void updateRecentlyLanded(String username, String recentlyLanded) {
        UserExample example = new UserExample();
        example.or().andUsernameEqualTo(username);
        User user = userMapper.selectByExample(example).get(0);
        user.setRecentlyLanded(recentlyLanded);
        userMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    public boolean usernameIsExist(String username) {
        UserExample example = new UserExample();
        example.or().andUsernameEqualTo(username);
        boolean flag=false;
        if (!userMapper.selectByExample(example).isEmpty())
        {
            flag = true;
        }
        return flag;
    }

    @Override
    public DataMap insert(User user) {

        user.setUsername(user.getUsername().trim().replaceAll(" ", StringUtil.BLANK));
        String username = user.getUsername();

        if (username.length() > 25 || StringUtil.BLANK.equals(username))
        {
            return DataMap.fail(CodeType.USERNAME_FORMAT_ERROR);
        }
        if (userIsExist(user.getPhone()))
        {
            return DataMap.fail(CodeType.PHONE_EXIST);
        }

        if ("male".equals(user.getGender()))
        {
            user.setAvatarimgUrl(OSSClientConstants.DEFAULT_MALE_AVATAR);
        }
        else
        {
            user.setAvatarimgUrl(OSSClientConstants.DEFAULT_FEMALE_AVATAR);
        }

        userMapper.insertSelective(user);
        Long userId = findUserIdByPhone(user.getPhone());
        insertRole(userId, RoleConstant.ROLE_USER);

        return DataMap.success();
    }

    @Override
    public boolean userIsExist(String phone) {
        UserExample example = new UserExample();
        example.or().andPhoneEqualTo(phone);
        boolean flag=false;
        if (!userMapper.selectByExample(example).isEmpty())
        {
            flag = true;
        }
        return flag;
    }

    @Override
    public Long findUserIdByPhone(String phone) {
        UserExample example = new UserExample();
        example.or().andPhoneEqualTo(phone);
        User user = userMapper.selectByExample(example).get(0);
        return user.getId();
    }

    /**
     * 增加用户权限
     * @param userId
     * @param roleId
     */
    @Override
    public void insertRole(Long userId, Long roleId) {
        UserAndRole userAndRole = new UserAndRole();
        userAndRole.setUserId(userId);
        userAndRole.setRoleId(roleId);
        userAndRoleMapper.insert(userAndRole);
    }

    @Override
    public User findUserByPhone(String phone) {
        UserExample example = new UserExample();
        example.or().andPhoneEqualTo(phone);
        if (!userMapper.selectByExample(example).isEmpty())
        {
            return userMapper.selectByExample(example).get(0);
        }
        return null;
    }

    @Override
    @Transactional
    public void updatePasswordByPhone(String phone, String password) {
        UserExample example = new UserExample();
        example.or().andPhoneEqualTo(phone);
        User user = userMapper.selectByExample(example).get(0);
        user.setPassword(password);
        userMapper.updateByPrimaryKeySelective(user);
        SecurityContextHolder.getContext().setAuthentication(null);
    }

    @Override
    public Long findUserIdByUsername(String username) {
        UserExample example = new UserExample();
        example.or().andUsernameEqualTo(username);
        User user = userMapper.selectByExample(example).get(0);
        return user.getId();
    }

    @Override
    public String findUsernameByUserId(Long userId) {
        User user = userMapper.selectByPrimaryKey(userId);
        return user.getUsername();
    }

    @Override
    public String findAvatarImgUrlByUserId(Long userId) {
        User user = userMapper.selectByPrimaryKey(userId);
        return user.getAvatarimgUrl();
    }

    @Override
    public int findUserNum() {
        return userMapper.findUserNum();
    }

    @Override
    public DataMap findAllUserByPage(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);

        List<UserDto> userDtoList = new ArrayList<>();

        UserExample example = new UserExample();
        example.or();
        List<User> userList = userMapper.selectByExample(example);
        PageInfo<User> pageInfo = new PageInfo<>(userList);

        for (User user : userList)
        {
            UserDto userDto = new UserDto();
            userDto.setId(user.getId());
            userDto.setUsername(user.getUsername());
            userDto.setBirthday(user.getBirthday());
            userDto.setAvatarImg_url(user.getAvatarimgUrl());
            userDto.setEmail(user.getEmail());
            userDto.setGender(user.getGender());
            userDto.setPersonal_brief(user.getPersonalBrief());
            userDto.setRecently_landed(user.getRecentlyLanded());
            userDto.setPhone(user.getPhone());
            userDto.setIs_locked(user.getIsLocked());
            userDtoList.add(userDto);
        }
        JSONArray userJsonArray = JSONArray.fromObject(userDtoList);
        return DataMap.success().setData(userJsonArray);
    }

    @Override
    @Transactional
    public void lockUser(Long user_id) {
        User user = userMapper.selectByPrimaryKey(user_id);
        user.setLocked(true);
        userMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    @Transactional
    public void unlockUser(Long user_id) {
        User user = userMapper.selectByPrimaryKey(user_id);
        user.setLocked(false);
        userMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    public User findUserByUserId(Long user_id) {
        User user = userMapper.selectByPrimaryKey(user_id);
        return user;
    }

    @Override
    @Transactional
    public void updateUser(User user) {
        user.setAvatarimgUrl(userMapper.selectByPrimaryKey(user.getId()).getAvatarimgUrl());
        if (user.getGender().equals("male") && user.getAvatarimgUrl().contains("default"))
        {
            user.setAvatarimgUrl(OSSClientConstants.DEFAULT_MALE_AVATAR);
        }
        else if (user.getGender().equals("female") && user.getAvatarimgUrl().contains("default"))
        {
            user.setAvatarimgUrl(OSSClientConstants.DEFAULT_FEMALE_AVATAR);
        }
        userMapper.updateByPrimaryKeySelective(user);
    }


    @Override
    @Transactional
    public DataMap updateAvatarImgByUserId(Long user_id, MultipartFile file)
    {
        User user = userMapper.selectByPrimaryKey(user_id);
        FileUtil fileUtil = new FileUtil();


        String pictureUrl = user.getAvatarimgUrl();
        pictureUrl = pictureUrl.substring(pictureUrl.indexOf("user"));
        if (!pictureUrl.contains("default"))
        {
            fileUtil.deleteFile(pictureUrl);
        }
        DateFormatUtil dateFormatUtil = new DateFormatUtil();
//        String filePath = this.getClass().getResource("/").getPath().substring(1) + "user/";
        String fileContentType = file.getContentType();
        String fileExtension = fileContentType.substring(fileContentType.indexOf("/") +1);
        String fileName = dateFormatUtil.getLongTime()+"."+fileExtension;
        String subCatalog = "user/"+user.getUsername()+"/"+DateFormatUtil.getDateString(new Date());
        String newAvatarImgUrl = fileUtil.uploadFile(file,subCatalog,fileName);
        user.setAvatarimgUrl(newAvatarImgUrl);
        userMapper.updateByPrimaryKeySelective(user);
        return DataMap.success().setData(newAvatarImgUrl);
    }

    @Override
    public DataMap findAllUserRoleByPage(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);

        List<UserDto> userDtoList = new ArrayList<>();

        UserExample example = new UserExample();
        example.or();
        List<User> userList = userMapper.selectByExample(example);
        PageInfo<User> pageInfo = new PageInfo<>(userList);

        for (User user : userList)
        {
            UserDto userDto = new UserDto();
            userDto.setId(user.getId());
            userDto.setUsername(user.getUsername());
            userDto.setAvatarImg_url(user.getAvatarimgUrl());

            UserAndRoleExample example1 = new UserAndRoleExample();
            example1.or().andUserIdEqualTo(user.getId());
            List<UserAndRole> userAndRoleList = userAndRoleMapper.selectByExample(example1);
            StringBuilder builder = new StringBuilder();
            Role role;
            for (int i=0; i<userAndRoleList.size(); i++)
            {

                role = roleMapper.selectByPrimaryKey(userAndRoleList.get(i).getRoleId());
                builder.append(role.getName());
                if (i != userAndRoleList.size()-1)
                {
                    builder.append(",");
                }
            }
            userDto.setRole(builder.toString());
            userDtoList.add(userDto);
        }
        JSONArray userJsonArray = JSONArray.fromObject(userDtoList);
        return DataMap.success().setData(userJsonArray);
    }

    @Override
    public DataMap upUserRight(Long user_id) {
        User user = findUserByUserId(user_id);
        UserAndRoleExample example = new UserAndRoleExample();
        example.or().andUserIdEqualTo(user.getId());
        List<UserAndRole> userAndRoleList = userAndRoleMapper.selectByExample(example);
        List<Role> roleList = new ArrayList<>();
        for (UserAndRole userAndRole : userAndRoleList)
        {
            Role role = new Role();
            role = roleMapper.selectByPrimaryKey(userAndRole.getRoleId());
            roleList.add(role);
        }
        for (Role role : roleList)
        {
            if (role.getName().equals("ADMIN") || role.getName().equals("FRIEND"))
            {
                return DataMap.fail();
            }
        }
        insertRole(user_id,Long.parseLong("3"));
        return DataMap.success();
    }

    @Override
    public DataMap downUserRight(Long user_id) {
        User user = findUserByUserId(user_id);
        UserAndRoleExample example = new UserAndRoleExample();
        example.or().andUserIdEqualTo(user.getId());
        List<UserAndRole> userAndRoleList = userAndRoleMapper.selectByExample(example);
        List<Role> roleList = new ArrayList<>();
        for (UserAndRole userAndRole : userAndRoleList)
        {
            Role role = new Role();
            role = roleMapper.selectByPrimaryKey(userAndRole.getRoleId());
            roleList.add(role);
        }
        for (Role role : roleList)
        {
            if (role.getName().equals("FRIEND"))
            {
                deleteRole(user_id);
                return DataMap.success();
            }
        }
        return DataMap.fail();
    }

    @Override
    public void deleteRole(Long user_id) {
        userAndRoleMapper.downUserRight(user_id,Long.parseLong("3"));
    }

    @Override
    public User getUserPersonalInfoByUsername(String username) {
        UserExample example = new UserExample();
        example.or().andUsernameEqualTo(username);
        User user = userMapper.selectByExample(example).get(0);
        return user;
    }

    @Override
    @Transactional
    public Result savePersonalData(User user, String username) {
        user.setUsername(user.getUsername().trim().replaceAll(" ",StringUtil.BLANK));
        String newName = user.getUsername();
        if (newName.length() > 35)
        {
            return Result.createWithErrorMessage(ErrorEnum.USERNAME_FORMAT_ERROR);
        }
        else if (StringUtil.BLANK.equals(newName))
        {
            return Result.createWithErrorMessage(ErrorEnum.USERNAME_IS_NULL);
        }

        String message;
        //若更改昵称
        if (!newName.equals(username))
        {
            if (usernameIsExist(newName))
            {
                return Result.createWithErrorMessage(ErrorEnum.ACCOUNT_EXIST);
            }
                message = ErrorEnum.USERNAME_UPDATE_SUCCESS.getZhMsg();

        }
        //若没有更改昵称
        else
        {
            message = ErrorEnum.USERNAME_UPDATE_SUCCESS.getZhMsg();
        }

        updateUser(user);
        return Result.createWithSuccessMessage(message);
    }

    @Override
    public boolean isSuperAdmin(String username) {
        UserExample example = new UserExample();
        example.or().andUsernameEqualTo(username);
        User user = userMapper.selectByExample(example).get(0);
        UserAndRoleExample example1 = new UserAndRoleExample();
        example1.or().andUserIdEqualTo(user.getId());
        List<UserAndRole> userAndRoleList = userAndRoleMapper.selectByExample(example1);
        for (UserAndRole userAndRole : userAndRoleList)
        {
            if (userAndRole.getRoleId() == 2)
            {
                return true;
            }
        }
        return false;
    }

    @Override
    public Result getMasterUserInfo() {
        UserAndRoleExample example = new UserAndRoleExample();
        example.or().andRoleIdEqualTo(2L);
        UserAndRole userAndRole = userAndRoleMapper.selectByExample(example).get(0);
        User user = userMapper.selectByPrimaryKey(userAndRole.getUserId());
        AuthUserVo authUserVo = new AuthUserVo();
        if (user != null)
        {
            authUserVo.setName(user.getUsername())
                    .setAvatar(user.getAvatarimgUrl())
                    .setIntroduction(user.getPersonalBrief());
        }
        return Result.createWithModel(authUserVo);
    }

    @Override
    public Result findUserExistByPhone(String phone) {
        UserExample example = new UserExample();
        example.or().andPhoneEqualTo(phone);
        if (!userMapper.selectByExample(example).isEmpty())
        {
            return Result.createWithSuccessMessage();
        }
        return Result.createWithError();
    }


}
