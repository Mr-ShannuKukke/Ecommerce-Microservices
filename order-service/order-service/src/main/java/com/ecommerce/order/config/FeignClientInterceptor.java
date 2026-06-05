package com.ecommerce.order.config;

import com.ecommerce.order.security.JwtAuthenticationFilter;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.stereotype.Component;

@Component
public class FeignClientInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate template){
        String token= JwtAuthenticationFilter.JWT_TOKEN.get();

        if(token!=null){
            template.header("Authorization", "Bearer "+token);
        }
    }
}
