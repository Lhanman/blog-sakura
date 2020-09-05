package com.Lhan.personal_blog.service.impl;

import com.Lhan.personal_blog.common.base.PageInfo;
import com.Lhan.personal_blog.common.base.Result;
import com.Lhan.personal_blog.mapper.SocialMapper;
import com.Lhan.personal_blog.pojo.ArticleInfo;
import com.Lhan.personal_blog.pojo.Social;
import com.Lhan.personal_blog.pojo.SocialExample;
import com.Lhan.personal_blog.service.SocialService;
import com.Lhan.personal_blog.util.PageUtil;
import com.Lhan.personal_blog.vo.SocialVo;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SocialServiceImpl implements SocialService {

    @Resource
    SocialMapper socialMapper;


    @Override
    public Result<SocialVo> getSocialInfo() {
        SocialExample example = new SocialExample();
        example.or().andIsHomeEqualTo(1).andIsEnableEqualTo(1);
        example.setOrderByClause("sort");
        List<Social> socialList = socialMapper.selectByExample(example);
        List<SocialVo> socialVoList = new ArrayList<>();
        socialList.forEach(social -> {
            socialVoList.add(new SocialVo()
            .setIcon(social.getIcon())
            .setCode(social.getCode())
            .setShowType(social.getShowType())
            .setContent(social.getContent())
            .setRemark(social.getRemark())
            .setIsEnabled(social.getIsEnable())
            .setIsHome(social.getIsHome())
            .setUpdateTime(LocalDateTime.now())
            .setLink(social.getLink()));
        });
        return Result.createWithModels(socialVoList);
    }

    @Override
    public Result<SocialVo> getSocialList(SocialVo socialVo) {
        socialVo = Optional.ofNullable(socialVo).orElse(new SocialVo());
        PageInfo pageInfo = Optional.of(PageUtil.checkAndInitPage(socialVo)).orElse(PageUtil.initPageInfo());
        int socialNum = getSocialCount();
        List<SocialVo> socialVoList = new ArrayList<>();
        if (!StringUtils.isEmpty(socialVo.getKeywords()))
        {
            socialVo.setKeywords("%" + socialVo.getKeywords() + "%");
        }
        if (socialVo.getCode().trim().equals("reward"))
        {
            SocialExample example = new SocialExample();
            example.or().andCodeEqualTo(socialVo.getCode()).andIsEnableEqualTo(socialVo.getIsEnabled()).andIsHomeEqualTo(0);
            List<Social> socialList = socialMapper.selectByExample(example);
            socialList.forEach(social -> {
                socialVoList.add(new SocialVo()
                .setContent(social.getContent())
                .setRemark(social.getRemark()));
            });
            return Result.createWithModels(socialVoList);
        }
        pageInfo.setTotal(socialNum);
        return Result.createWithPaging(getSocialListByPage(pageInfo),pageInfo);
    }

    @Override
    public int getSocialCount() {
        SocialExample example = new SocialExample();
        example.or();
        List<Social> socialList = socialMapper.selectByExample(example);
        return socialList.size();
    }

    @Override
    public List<SocialVo> getSocialListByPage(PageInfo pageInfo) {
        int pageNum = pageInfo.getPage();
        int pageSize = pageInfo.getSize();
        PageHelper.startPage(pageNum,pageSize);
        List<SocialVo> socialVoList = new ArrayList<>();

        SocialExample example = new SocialExample();
        example.or();
        List<Social> socialList = socialMapper.selectByExample(example);
        com.github.pagehelper.PageInfo<Social> pageInfo2 = new com.github.pagehelper.PageInfo<>(socialList);

        socialList.forEach(social -> {
            socialVoList.add(new SocialVo()
            .setRemark(social.getRemark())
            .setContent(social.getContent())
            .setIcon(social.getIcon())
            .setIsHome(social.getIsHome())
            .setIsEnabled(social.getIsEnable())
            .setId(social.getId())
            .setCode(social.getCode()));
        });

        return socialVoList;
    }

}
