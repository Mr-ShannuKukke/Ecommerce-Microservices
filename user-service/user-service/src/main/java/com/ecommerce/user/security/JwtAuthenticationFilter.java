package com.ecommerce.user.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    public JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException{
        try {
            String token=getTokenFromRequest(request);
            if (token !=null && jwtTokenProvider.validateToken(token)){
                Long userId=jwtTokenProvider.getUserIdFromToken(token);
                String email=jwtTokenProvider.getEmailFromToken(token);

                UsernamePasswordAuthenticationToken authentication=new UsernamePasswordAuthenticationToken
                        (userId, null, null);

                authentication.setDetails(email);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        catch (Exception e){
            logger.error("Cannot set user authentication: "+e.getMessage());
        }

        filterChain.doFilter(request, response);
    }

    /* Extract JWT token from Authorization header */

    private String getTokenFromRequest(HttpServletRequest request){
        String bearerToken=request.getHeader("Authorization");
        if(bearerToken !=null && bearerToken.startsWith("Bearer ")){
            return bearerToken.substring(7);
        }
        return null;
    }
}
