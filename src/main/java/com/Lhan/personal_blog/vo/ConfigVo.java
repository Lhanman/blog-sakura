package com.Lhan.personal_blog.vo;

import com.Lhan.personal_blog.common.validator.annotion.NotBlank;
import com.Lhan.personal_blog.validator.UpdateConfig;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ConfigVo extends BaseVo<ConfigVo> {

    private Integer type;

    @NotBlank(groups = {UpdateConfig.class})
    private String configKey;

    @NotBlank(groups = {UpdateConfig.class})
    private String configValue;

}
