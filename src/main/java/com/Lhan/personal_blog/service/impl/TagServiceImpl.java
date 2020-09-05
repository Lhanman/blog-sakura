package com.Lhan.personal_blog.service.impl;

import com.Lhan.personal_blog.common.base.Result;
import com.Lhan.personal_blog.constant.CodeType;
import com.Lhan.personal_blog.dto.ArticleTagDto;
import com.Lhan.personal_blog.dto.TagDto;
import com.Lhan.personal_blog.mapper.ArticleInfoMapper;
import com.Lhan.personal_blog.mapper.ArticleTagMapper;
import com.Lhan.personal_blog.pojo.ArticleInfo;
import com.Lhan.personal_blog.pojo.ArticleInfoExample;
import com.Lhan.personal_blog.pojo.ArticleTag;
import com.Lhan.personal_blog.pojo.ArticleTagExample;
import com.Lhan.personal_blog.service.TagService;
import com.Lhan.personal_blog.util.DataMap;
import com.Lhan.personal_blog.util.DateFormatUtil;
import com.Lhan.personal_blog.vo.TagsVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 主要用于获取标签云
 */
@Service
public class TagServiceImpl implements TagService {

    @Resource
    ArticleTagMapper articleTagMapper;


    @Resource
    ArticleInfoMapper articleInfoMapper;


    /**
     * 获得标签云
     * @return
     */
    @Override
    public DataMap findTagCloud() {
        List<String> tagsName = articleTagMapper.findTagNameDistinct();
        //获取标签总数
        Integer tagNum = tagsName.size();

//        List<ArticleTag> articleTagList;
        List<ArticleTagDto> articleTagDtos = new ArrayList<>();
        for (String tagName : tagsName)
        {
            //通过tagName查一个标签有多少个博客
            int tag_size = getPostQuantityByTagName(tagName);

            //将前端需要的字段填入包装类中
            ArticleTagDto articleTagDto = new ArticleTagDto();
            articleTagDto.setTag_name(tagName);
            articleTagDto.setTag_size(tag_size);
            articleTagDtos.add(articleTagDto);
        }

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("result", JSONArray.fromObject(articleTagDtos));
        jsonObject.put("tagsNum",tagNum);

        return DataMap.success(CodeType.FIND_TAG_CLOUD).setData(jsonObject);
    }

    @Override
    public List<ArticleTagDto> findTagCloudForIndex() {
        List<String> tagsName = articleTagMapper.findTagNameDistinct();
        List<ArticleTagDto> articleTagDtos = new ArrayList<>();
        for (String tagName : tagsName)
        {
            //通过tagName查一个标签有多少个博客
            int tag_size = getPostQuantityByTagName(tagName);

            //将前端需要的字段填入包装类中
            ArticleTagDto articleTagDto = new ArticleTagDto();
            articleTagDto.setTag_name(tagName);
            articleTagDto.setTag_size(tag_size);
            articleTagDtos.add(articleTagDto);
        }
        return articleTagDtos;
    }

    public List<String> findTagsNameByArticleId(Long article_id)
    {
        ArticleTagExample example = new ArticleTagExample();
        example.or().andArticleIdEqualTo(article_id);
        List<ArticleTag> articleTagList =articleTagMapper.selectByExample(example);
        List<String> articleTagsName = new ArrayList<>();
        for (ArticleTag articleTag : articleTagList) {
            String tagName = articleTag.getTagName();
            articleTagsName.add(tagName);
        }
        return articleTagsName;
    }

    @Override
    public int tagNum() {
       return articleTagMapper.findTagNum();
    }

    @Override
    public DataMap findAllTagByPage(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);

        List<TagDto> tagDtoList = new ArrayList<>();

        //查询所有标签的基础信息
        ArticleTagExample example = new ArticleTagExample();
        example.or();
        List<ArticleTag> articleTagList = articleTagMapper.selectByExample(example);
        PageInfo<ArticleTag> pageInfo = new PageInfo<>(articleTagList);

        for (ArticleTag articleTag : articleTagList)
        {
            TagDto tagDto = new TagDto();
            tagDto.setArticle_id(articleTag.getArticleId());
            tagDto.setId(articleTag.getId());
            tagDto.setCreate_by(DateFormatUtil.getDateString(articleTag.getCreateBy()));
            tagDto.setModified_by(DateFormatUtil.getDateString(articleTag.getModifiedBy()));
            tagDto.setTag_name(articleTag.getTagName());
            //填充博客标题信息
            ArticleInfo articleInfo = articleInfoMapper.selectByPrimaryKey(articleTag.getArticleId());
            tagDto.setTitle(articleInfo.getTitle());

            tagDtoList.add(tagDto);
        }

        JSONArray tagJsonArray = JSONArray.fromObject(tagDtoList);
        return DataMap.success().setData(tagJsonArray);
    }

    @Override
    @CacheEvict(value = "article",key = "#articleTag.articleId")
    @Transactional
    public void deleteTagByTag_id(ArticleTag articleTag) {
        //删除tbl_article_tag表
        articleTagMapper.deleteByPrimaryKey(articleTag.getId());

    }

    @Override
    @CacheEvict(value = "article",key = "#articleTag.articleId")
    @Transactional
    public void insertTag(ArticleTag articleTag) {
        articleTagMapper.insert(articleTag);
    }

    @Override
    @Transactional
    @CacheEvict(value = "article",key = "#articleTag.articleId")
    public void updateTag(ArticleTag articleTag) {
        articleTagMapper.updateByPrimaryKeySelective(articleTag);
    }

    @Override
    public Result<TagsVo> getTagsAndArticleQuantityList(TagsVo tagsVo) {
        List<ArticleTag> articleTagList = articleTagMapper.findTagsDistinct();

        List<TagsVo> tagsVoList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(articleTagList))
        {
            articleTagList.forEach(articleTag -> {
                Integer total = getPostQuantityByTagName(articleTag.getTagName());
                TagsVo tagsVo1 = new TagsVo();
                tagsVo1.setName(articleTag.getTagName());
                tagsVo1.setPostsTotal(total);
                tagsVo1.setId(articleTag.getId());
                tagsVoList.add(tagsVo1);
            });
        }
        return Result.createWithModels(tagsVoList);
    }

    @Override
    public int getPostQuantityByTagName(String tagName) {
        //通过tagName查一个标签有多少个博客
        List<ArticleTag> articleTagList;
        ArticleTagExample example1 = new ArticleTagExample();
        example1.or().andTagNameEqualTo(tagName);
        articleTagList = articleTagMapper.selectByExample(example1);
        return articleTagList.size();
    }

    @Override
    public String findTagNameByTag_id(Long tag_id) {
        ArticleTag articleTag = articleTagMapper.selectByPrimaryKey(tag_id);
        return articleTag.getTagName();
    }


}
