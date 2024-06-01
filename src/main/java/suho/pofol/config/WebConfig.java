package suho.pofol.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/suho/**")
                .addResourceLocations("file:C:/suho/IntelliJ/portfolio/pofol/pofol/upload");
        WebMvcConfigurer.super.addResourceHandlers(registry);
    }
}
