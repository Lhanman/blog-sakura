package com.Lhan.personal_blog.util;

import com.Lhan.personal_blog.common.base.PageInfo;
import com.Lhan.personal_blog.constant.CommonConstant;
import com.Lhan.personal_blog.vo.BaseVo;

public class PageUtil {

    public static PageInfo checkAndInitPage(BaseVo baseVo)
    {
        if (baseVo.getPage() == null)
        {
            baseVo.setPage(CommonConstant.DEFAULT_PAGE_INDEX);
        }
        if (baseVo.getSize() == null)
        {
            baseVo.setSize(CommonConstant.DEFAULT_PAGE_SIZE);
        }
        PageInfo pageInfo = new PageInfo();
        pageInfo.setPage(baseVo.getPage());
        pageInfo.setSize(baseVo.getSize());
        return pageInfo;
    }
    public static PageInfo initPageInfo() {
        PageInfo pageInfo = new PageInfo();
        pageInfo.setPage(CommonConstant.DEFAULT_PAGE_INDEX);
        pageInfo.setSize(CommonConstant.DEFAULT_PAGE_SIZE);
        return pageInfo;
    }

    public static PageInfo initPageInfo(Integer pageNum,Integer pageSize)
    {
        PageInfo pageInfo = new PageInfo();
        pageInfo.setPage(pageNum);
        pageInfo.setSize(pageSize);
        return pageInfo;
    }

    public static PageInfo initPageInfo(Integer pageNum,Integer pageSize, Integer count)
    {
        if (count != null)
        {
            return initPageInfo(pageNum,pageSize).setTotal(count);
        }
        return null;
    }
}
