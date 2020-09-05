package com.Lhan.personal_blog.service.impl;

import com.Lhan.personal_blog.common.base.Result;
import com.Lhan.personal_blog.constant.CommonConstant;
import com.Lhan.personal_blog.mapper.ConfigMapper;
import com.Lhan.personal_blog.pojo.Config;
import com.Lhan.personal_blog.pojo.ConfigExample;
import com.Lhan.personal_blog.service.ConfigService;
import com.Lhan.personal_blog.vo.ConfigVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ConfigServiceImpl implements ConfigService {

    @Resource
    ConfigMapper configMapper;

    @Override
    public Result getConfigList(int type) {
        ConfigExample example = new ConfigExample();
        final List<Config> configList;
        if (type == CommonConstant.ONE || type == CommonConstant.FOUR || type == CommonConstant.FIVE)
        {
            example.or().andTypeIn(Arrays.stream(new int[]{type, 3}).boxed().collect(Collectors.toList()));
            configList = configMapper.selectByExample(example);
        }
        else
        {
            example.or().andTypeEqualTo(type);
            configList = configMapper.selectByExample(example);
        }
        final List<ConfigVo> configVoList = new ArrayList<>();
        configList.forEach(config -> {
            final ConfigVo configVo = new ConfigVo();
            configVoList.add(configVo.setConfigKey(config.getConfigkey())
                    .setConfigValue(config.getConfigvalue())
            );
        });
        return Result.createWithModels(configVoList);
    }

    @Override
    public Result getConfigBaseList() {
        return getConfigList(0);
    }
}
