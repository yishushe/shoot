package cn.bdqn.photography.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

//WebMvcConfigSuppot 类关联到mvc的默认配置 如果集成它则会完全接管springmvc的默认配置 也就没有什么 习惯大于配置的说法 谨记

@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {

    @Value("${file.resourceHandler}")
    private String resourceHandler;

    @Value("${file.commonPath}")
    private String commonPath;

    @Value("${file.userPath}")
    private String userPath;

    @Value("${file.infoPath}")
    private String infoPath;

    /**
     * 完成访问路劲与保存路劲的映射
     *
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(resourceHandler).addResourceLocations("file:///" + commonPath, "file:///" + userPath, "file:///" + infoPath);
        super.addResourceHandlers(registry);
    }

}