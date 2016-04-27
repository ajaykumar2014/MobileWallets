package com.wallet.app.security.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import com.wallet.app.security.services.TokenAuthenticationService;

public class StatelessAuthenticationFilter extends GenericFilterBean {

	private final TokenAuthenticationService tokenAuthenticationService;

	public StatelessAuthenticationFilter(TokenAuthenticationService taService) {
		this.tokenAuthenticationService = taService;
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		SecurityContextHolder.getContext()
				.setAuthentication(tokenAuthenticationService.getAuthentication((HttpServletRequest) req));
		chain.doFilter(req, res); // always continue
	}

	/*private User getUserFromHeaderToken(HttpServletRequest req, HttpServletResponse res) {
		try {
            HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
            String authToken = httpServletRequest
                    .getHeader(TokenAuthenticationService.AUTH_HEADER_NAME);

            if (StringUtils.hasText(authToken)) {
                String username = this.tokenUtils
                        .getUserNameFromToken(authToken);

                UserDetails details = this.detailsService
                        .loadUserByUsername(username);
                if (this.tokenUtils.validateToken(authToken, details)) {
                    UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                            details.getUsername(), details.getPassword(),
                            details.getAuthorities());

                    token.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                    Authentication authentication = this.authenticationManager
                            .authenticate(token);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    log.info("========================> " + SecurityContextHolder.getContext().getAuthentication().getName() + " , " + SecurityContextHolder.getContext().getAuthentication().isAuthenticated());
                }
            }

            filterChain.doFilter(servletRequest, servletResponse);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
	}*/
}
