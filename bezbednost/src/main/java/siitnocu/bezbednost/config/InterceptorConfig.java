package siitnocu.bezbednost.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import siitnocu.bezbednost.controllers.RateLimitInterceptor;

@Component
public class InterceptorConfig implements WebMvcConfigurer {
    @Autowired
    private RateLimitInterceptor interceptor;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(interceptor);
    }
}
