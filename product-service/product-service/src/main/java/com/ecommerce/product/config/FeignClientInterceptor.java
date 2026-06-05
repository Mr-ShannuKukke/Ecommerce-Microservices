package com.ecommerce.product.config;

import com.ecommerce.product.security.JwtAuthenticationFilter;
import org.springframework.stereotype.Component;
import feign.RequestInterceptor;
import feign.RequestTemplate;

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