package cn.anytec.anguan.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.HttpPutFormContentFilter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by imyzt on 2018/8/24 17:13
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {

    /** 线上环境swagger.show=false.全部拦截.关闭swagger */
    @Value("${swagger.show}")
    private String swaggerShow;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        if (Boolean.parseBoolean(swaggerShow)) {
            registry.addResourceHandler("/js/**").addResourceLocations("classpath:/js/");
            registry.addResourceHandler("swagger-ui.html")
                    .addResourceLocations("classpath:/META-INF/resources/");
            registry.addResourceHandler("/webjars/**")
                    .addResourceLocations("classpath:/META-INF/resources/webjars/");
        }
    }

    @Bean
    public HttpPutFormContentFilter httpPutFormContentFilter() {
        return new HttpPutFormContentFilter();
    }
}
