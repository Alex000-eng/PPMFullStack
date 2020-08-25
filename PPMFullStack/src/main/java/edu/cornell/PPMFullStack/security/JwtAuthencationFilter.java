package edu.cornell.PPMFullStack.security;

import java.io.IOException;
import java.util.Collections;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import edu.cornell.PPMFullStack.domain.User;
import edu.cornell.PPMFullStack.services.CustomerUserDetailsService;

public class JwtAuthencationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private CustomerUserDetailsService customerUserDetailsService;

    public String getJWTFromFromRequest(HttpServletRequest request) {
        String bearerTokenString= request.getHeader(SecurityConstants.HEADER_STRING);

        if (StringUtils.hasText(bearerTokenString) &&
            bearerTokenString.startsWith(SecurityConstants.TOKEN_PREFIX)) {
            return bearerTokenString.substring(7);
        }

        return null;

    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
        FilterChain filterChain) throws ServletException, IOException {
        // TODO Auto-generated method stub
        try {
            String jwt= getJWTFromFromRequest(request);
            if (StringUtils.hasText(jwt) && jwtTokenProvider.validateToken(jwt)) {
                Long userId= jwtTokenProvider.getUserIdFromJWT(jwt);

                User userDetails= customerUserDetailsService.loadUserById(userId);

                UsernamePasswordAuthenticationToken authentication= new UsernamePasswordAuthenticationToken(
                    userDetails, null, Collections.emptyList());

                authentication
                    .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authentication);

            }
        } catch (Exception e) {
            logger.error("Could not set user authentication in security context", e);
        }

        filterChain.doFilter(request, response);
    }
}
