package config;

import interceptor.ResourcesInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

import java.util.List;

@Configuration
//开启包扫描，等同于<context:component-scan base-package="controller">
@ComponentScan({"controller"})
//配置注解驱动，等同于<mvc:annotation-driver/>，但不完全相同
@EnableWebMvc
public class SpringMvcConfig implements WebMvcConfigurer {

    /**
     * 开启对静态资源的访问
     * 类似在Spring MVC的配置文件中设置<mvc:default-servlet-handler/>元素
     */
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    /**
     * 配置视图解析器，前缀设置为"/admin/",后缀设置为".jsp"
     * @param registry
     */
    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        registry.jsp("/admin/",".jsp");
    }

    @Value("#{'${ignoreUrl}'.split(',')}")
    private List<String> ignoreUrl;
    @Bean
    public ResourcesInterceptor resourcesInterceptor() {
        return new ResourcesInterceptor(ignoreUrl);
    }
    /**
     * 在拦截器注册类中添加自定义拦截器
     * addPathPatterns()方法设置拦截的路径
     * excludePathPatterns()方法设置不拦截的路径
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(resourcesInterceptor()).addPathPatterns("/**").excludePathPatterns("/css/**","/js/**","/img/**");
    }

}
