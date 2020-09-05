package com.Lhan.personal_blog.service.impl;

import com.Lhan.personal_blog.common.base.PageInfo;
import com.Lhan.personal_blog.common.base.Result;
import com.Lhan.personal_blog.mapper.FriendshipLinkMapper;
import com.Lhan.personal_blog.pojo.FriendshipLink;
import com.Lhan.personal_blog.pojo.FriendshipLinkExample;
import com.Lhan.personal_blog.service.FriendshipLinkService;
import com.Lhan.personal_blog.util.DataMap;
import com.Lhan.personal_blog.validator.UpdateLink;
import com.Lhan.personal_blog.vo.FriendshipLinkVo;
import com.github.pagehelper.PageHelper;
import net.sf.json.JSONArray;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class FriendshipLinkServiceImpl implements FriendshipLinkService {

    @Resource
    FriendshipLinkMapper friendshipLinkMapper;

    @Override
    public Result getFriendshipLinkMap(FriendshipLinkVo friendshipLinkVo) {
        Map<String, List<FriendshipLinkVo>> resultMap = getFriendshipLinkVoList(friendshipLinkVo,null)
                .stream().filter(s ->!StringUtils.isBlank(s.getTitle()))
                .collect(Collectors.groupingBy(FriendshipLinkVo::getTitle,Collectors.toList()));
        return Result.createWithModels(null).setExtra(resultMap);
    }

    @Override
    public int findFriendLinkNum() {
        FriendshipLinkExample example = new FriendshipLinkExample();
        example.or();
        return friendshipLinkMapper.selectByExample(example).size();
    }

    @Override
    public DataMap findFriendLinkListByPage(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);

        List<FriendshipLinkVo> friendshipLinkVoList = new ArrayList<>();

        FriendshipLinkExample example = new FriendshipLinkExample();
        example.or();
        List<FriendshipLink> friendshipLinkList = friendshipLinkMapper.selectByExample(example);
        com.github.pagehelper.PageInfo<FriendshipLink> pageInfo = new com.github.pagehelper.PageInfo<>(friendshipLinkList);

        friendshipLinkList.forEach(friendshipLink -> {
            friendshipLinkVoList.add(new FriendshipLinkVo().setId(friendshipLink.getId())
                                                            .setName(friendshipLink.getName())
                                                            .setTitle(friendshipLink.getTitle())
                                                            .setLogo(friendshipLink.getLogo())
                                                            .setDescription(friendshipLink.getDescription())
                                                            .setHref(friendshipLink.getHref()));
        });
        JSONArray friendLinkArray = JSONArray.fromObject(friendshipLinkVoList);
        return DataMap.success().setData(friendLinkArray);
    }

    @Override
    @Transactional
    public void addFriendLink(FriendshipLinkVo friendshipLinkVo) {
        FriendshipLink friendshipLink = v2p(friendshipLinkVo);
        friendshipLinkMapper.insert(friendshipLink);
    }

    @Override
    public FriendshipLink findFriendLinkById(Long link_id) {
        return friendshipLinkMapper.selectByPrimaryKey(link_id);
    }

    @Override
    @Transactional
    public void updateFriendLink(@Validated({UpdateLink.class}) @RequestBody FriendshipLinkVo friendshipLinkVo) {
        FriendshipLink friendshipLink = v2p(friendshipLinkVo);
        friendshipLinkMapper.updateByPrimaryKey(friendshipLink);
    }

    @Override
    @Transactional
    public void deleteFriendLink(Long link_id) {
        friendshipLinkMapper.deleteByPrimaryKey(link_id);
    }


    //===============================通用方法分割线=========================//
    private List<FriendshipLinkVo> getFriendshipLinkVoList(FriendshipLinkVo friendshipLinkVo, PageInfo pageInfo)
    {
        if (StringUtils.isNotBlank(friendshipLinkVo.getKeywords()))
        {
            friendshipLinkVo.setKeywords("%" + friendshipLinkVo.getKeywords() + "%");
        }
        if (StringUtils.isNotBlank(friendshipLinkVo.getHref()))
        {
            friendshipLinkVo.setHref("%" + friendshipLinkVo.getHref() + "%");
        }
        List<FriendshipLink> friendshipLinkList;
        if (pageInfo == null)
        {
            FriendshipLinkExample example = new FriendshipLinkExample();
            example.or();
            example.setOrderByClause("sort desc");
            friendshipLinkList = friendshipLinkMapper.selectByExample(example);
        }
        //之后考虑要不要写分页处理
        else
        {
            friendshipLinkList = new ArrayList<>();
        }
        List<FriendshipLinkVo> friendshipLinkVoList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(friendshipLinkList))
        {
            friendshipLinkList.forEach(friendshipLink -> {
                friendshipLinkVoList.add(new FriendshipLinkVo()
                .setName(friendshipLink.getName())
                .setTitle(friendshipLink.getTitle())
                .setDescription(friendshipLink.getDescription())
                .setHref(friendshipLink.getHref())
                .setLogo(friendshipLink.getLogo())
                .setId(friendshipLink.getId())
                );
            });
        }
        return friendshipLinkVoList;
    }

    private FriendshipLink v2p(FriendshipLinkVo friendshipLinkVo)
    {
        FriendshipLink friendshipLink = new FriendshipLink();
        friendshipLink.setId(friendshipLinkVo.getId());
        friendshipLink.setName(friendshipLinkVo.getName());
        friendshipLink.setTitle(friendshipLinkVo.getTitle());
        friendshipLink.setLogo(friendshipLinkVo.getTitle());
        friendshipLink.setSort(0);
        friendshipLink.setDescription(friendshipLinkVo.getDescription());
        friendshipLink.setHref(friendshipLinkVo.getHref());
        return friendshipLink;
    }
}
