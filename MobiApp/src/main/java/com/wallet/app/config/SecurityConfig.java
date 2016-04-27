package com.wallet.app.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;

import com.wallet.app.security.RestAccessDeniedHandler;
import com.wallet.app.security.RestAuthenticationFailureHandler;
import com.wallet.app.security.RestUnauthorizedEntryPoint;
import com.wallet.app.security.filter.LoginAuthenticationFilter;
import com.wallet.app.security.filter.StatelessAuthenticationFilter;
import com.wallet.app.security.services.TokenAuthenticationService;
import com.wallet.app.security.services.UserDetailsService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@ComponentScan(basePackages = {"com.wallet.app.security"})
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	// private static final Logger logger =
	// LoggerFactory.getLogger(SecurityConfig.class);

	public static final String REMEMBER_ME_KEY = "rememberme_key";
	
	private final String LOGIN_URI_PATH="/authenticate";

	public SecurityConfig() {
		super();
		// logger.info("loading SecurityConfig
		// ................................................ ");
	}
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private TokenAuthenticationService tokenAuthenticationService;

	@Autowired
	private AuthenticationSuccessHandler restAuthenticationSuccessHandler;

	@Autowired
	public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
		// auth.jdbcAuthentication().dataSource(dataSource);
		auth.userDetailsService(userDetailsService);
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().
			antMatchers("/resources/**", "/index.html", "/login.html", "/partials/**", "/template/**", "/",
				"/error/**");
	}

	@Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .headers().disable()
            .csrf().disable()
            .authorizeRequests()
                .antMatchers("/failure", "/favicon.ico","/auth/**","/signup/**","/verify/**").permitAll()
              //allow anonymous GETs to API
				.antMatchers(HttpMethod.GET, "/**").permitAll()
                .antMatchers("/**").authenticated()
                .and()
            .exceptionHandling()
                .authenticationEntryPoint(new RestUnauthorizedEntryPoint())
                .accessDeniedHandler(new RestAccessDeniedHandler())
                .and()
            .formLogin()
                .loginProcessingUrl(LOGIN_URI_PATH)
                .successHandler(restAuthenticationSuccessHandler)
                .failureHandler(new RestAuthenticationFailureHandler())
                .usernameParameter("username")
                .passwordParameter("password")
                .permitAll()
                .and()
            
            .logout()
                .logoutUrl("/logout")
                .logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler())
                .deleteCookies("JSESSIONID")
                .permitAll()
                .and()
			// custom JSON based authentication by POST of {"username":"<name>","password":"<password>"} which sets the token header upon authentication
			.addFilterBefore(new LoginAuthenticationFilter(LOGIN_URI_PATH, tokenAuthenticationService, userDetailsService, authenticationManager()), UsernamePasswordAuthenticationFilter.class)

			// custom Token based authentication based on the header previously given to the client
			.addFilterBefore(new StatelessAuthenticationFilter(tokenAuthenticationService), UsernamePasswordAuthenticationFilter.class);
                
    }
	
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
	}

	@Override
	protected UserDetailsService userDetailsService() {
		return userDetailsService;
	}

}
