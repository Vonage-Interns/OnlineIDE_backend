package com.stackblitz.OnlineIDE.config;


import com.stackblitz.OnlineIDE.common.ApiResponse;
import com.stackblitz.OnlineIDE.service.CustomUserDetailsService;
import com.stackblitz.OnlineIDE.service.JWTservice;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
@RequiredArgsConstructor
public class jwtFilter extends OncePerRequestFilter {

    private final JWTservice JWTservice;

    private final CustomUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        try {
            final String authHeader = request.getHeader("Authorization");
            String userid = null;
            String token = null;

            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                token = authHeader.substring(7);
                userid = JWTservice.extractuserID(token);
            }

            if (userid != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userDetailsService.loadUserByUserid(userid);
                if (JWTservice.validateToken(token, userDetails)) {
                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authToken.setDetails(
                            new WebAuthenticationDetailsSource().buildDetails(request)
                    );
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }



            filterChain.doFilter(request, response);

        }catch (ExpiredJwtException e){
            ApiResponse<?> apiResponse = new ApiResponse<>("JWT token has expired. Please login again.", null);
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401 status
            response.getWriter().write(String.valueOf(apiResponse));
        }
    }

}
