package com.Lhan.personal_blog.service;

import com.Lhan.personal_blog.common.base.Result;
import com.Lhan.personal_blog.pojo.FriendshipLink;
import com.Lhan.personal_blog.util.DataMap;
import com.Lhan.personal_blog.vo.FriendshipLinkVo;

public interface FriendshipLinkService {

    Result getFriendshipLinkMap(FriendshipLinkVo friendshipLinkVo);

    int findFriendLinkNum();

    DataMap findFriendLinkListByPage(int pageNum,int pageSize);

    void addFriendLink(FriendshipLinkVo friendshipLinkVo);

    FriendshipLink findFriendLinkById(Long link_id);

    void updateFriendLink(FriendshipLinkVo friendshipLinkVo);

    void deleteFriendLink(Long link_id);
}
