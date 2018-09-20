package com.chenyilei.config;

import com.chenyilei.aspect.SellerLoginInteceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author chenyilei
 * @date 2018/9/12/0012-18:46
 * hello everyone
 */
@Configuration
public class MVCConfig extends WebMvcConfigurerAdapter {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new SellerLoginInteceptor())
        .addPathPatterns("/**")
        .excludePathPatterns("/static/**")
        .excludePathPatterns("/seller/to_login");
        super.addInterceptors(registry);
    }
}
