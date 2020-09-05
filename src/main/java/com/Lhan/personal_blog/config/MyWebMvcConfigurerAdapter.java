package com.Lhan.personal_blog.config;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.*;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;

@Configuration

public class MyWebMvcConfigurerAdapter implements WebMvcConfigurer {


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //配置静态资源映射
        registry.addResourceHandler("/article/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/templates/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/category/**").addResourceLocations("classpath:/static/");
//        registry.addResourceHandler("/admin/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/admin/**").addResourceLocations("classpath:/templates/admin/");
//        registry.addResourceHandler("/api/blog/**").addResourceLocations("classpath:/static/");
    }

    @Bean
    public HttpMessageConverter<String> responseBodyConverter()
    {
        StringHttpMessageConverter converter = new StringHttpMessageConverter(Charset.forName("UTF-8"));
        return converter;
    }

    @Bean
    public FastJsonHttpMessageConverter DateFormatConverter()
    {
        FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
        FastJsonConfig config = new FastJsonConfig();
        //保留空的字段
        config.setSerializerFeatures(SerializerFeature.WriteMapNullValue);
        config.setDateFormat("yyyy-MM-dd");
        converter.setFastJsonConfig(config);
        converter.setDefaultCharset(Charset.forName("UTF-8"));
        converter.setSupportedMediaTypes(Arrays.asList(MediaType.APPLICATION_JSON));
        return converter;
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters)
    {
        converters.add(responseBodyConverter());
        converters.add(DateFormatConverter());
    }

    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer)
    {
        configurer.favorPathExtension(false);
    }

//    @Override
//    public void addCorsMappings(CorsRegistry registry)
//    {
//        registry.addMapping("/**")
//                .allowedOrigins("http://localhost:3000")
//                .allowedMethods("*")
//                .allowedHeaders("*");
//    }
}
