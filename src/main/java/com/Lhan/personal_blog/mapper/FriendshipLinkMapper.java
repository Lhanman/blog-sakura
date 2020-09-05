package com.Lhan.personal_blog.mapper;

import com.Lhan.personal_blog.pojo.FriendshipLink;
import com.Lhan.personal_blog.pojo.FriendshipLinkExample;
import java.util.List;

public interface FriendshipLinkMapper {
    int deleteByPrimaryKey(Long id);

    int insert(FriendshipLink record);

    int insertSelective(FriendshipLink record);

    List<FriendshipLink> selectByExample(FriendshipLinkExample example);

    FriendshipLink selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(FriendshipLink record);

    int updateByPrimaryKey(FriendshipLink record);
}