package org.example.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // 모든 주소에 대해서
                .allowedOrigins("http://localhost:3000") // 리액트 개발 서버(3000번)만 허용
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowCredentials(true) // 인증 정보(토큰 등) 주고받기 허용
                .maxAge(3000); // 3000초 동안 허가 기억
    }
}
