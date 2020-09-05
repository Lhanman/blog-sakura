package com.Lhan.personal_blog.vo;

import com.Lhan.personal_blog.common.validator.annotion.IntegerNotNull;
import com.Lhan.personal_blog.common.validator.annotion.NotBlank;
import com.Lhan.personal_blog.validator.InsertSocial;
import com.Lhan.personal_blog.validator.UpdateSocial;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class SocialVo extends BaseVo<SocialVo> {

    @IntegerNotNull(groups = {UpdateSocial.class})
    private Long id;

    /**
     * qq，csdn，email等
     */
    @NotBlank(groups = {InsertSocial.class})
    private String code;

    /**
     * 内容
     */
    private String content;

    /**
     * 展示类型( 1、显示二维码，2、显示账号，3、跳转链接)
     */
    private Integer showType;

    /**
     * 备注
     */
    private String remark;

    private String icon;

    /**
     * 是否删除
     */
    private Integer isEnabled;

    /**
     * 是否主页社交信息
     */
    private Integer isHome;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    private Integer sort;

    private String link;

}
