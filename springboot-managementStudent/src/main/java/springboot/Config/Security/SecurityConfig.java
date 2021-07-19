package springboot.Config.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import springboot.Service.UserService;
import springboot.Config.Security.filter.JwtFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {


	@Autowired
	private UserService userSer;
	
	@Autowired
	private JwtFilter jwtFilter;
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// TODO Auto-generated method stub
		auth.userDetailsService(userSer);
	}
	
//	 @Override
//	    public void configure(WebSecurity web) throws Exception {
//	        web.ignoring().antMatchers("/v2/api-docs",
//	                                   "/configuration/ui",
//	                                   "/swagger-resources/**",
//	                                   "/configuration/security",
//	                                   "/swagger-ui.html",
//	                                   "/webjars/**");
//	    }
//	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// TODO Auto-generated method stub
		
		http.csrf().disable().authorizeRequests()
		.antMatchers("/api/login", "/api/sign-up","/v2/api-docs",
                "/configuration/ui",
                "/swagger-resources/**",
                "/configuration/security",
                "/swagger-ui.html",
                "/webjars/**","/api/public/user/forgot-password/**", "/api/public/user/reset-password/**" ).permitAll()
		.antMatchers("/api/public/user/**").access("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN') or hasRole('ROLE_STAFF')")
		.antMatchers("/api/public/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_STAFF')")
		.antMatchers("/api/**").access("hasRole('ROLE_ADMIN')")
//		.antMatchers("/api/**").permitAll()
//		.anyRequest().authenticated()
		.and().exceptionHandling()
 		.and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
			
		http.addFilterBefore(jwtFilter,  UsernamePasswordAuthenticationFilter.class);
	}
	
	
	@Bean(name= BeanIds.AUTHENTICATION_MANAGER)
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		// TODO Auto-generated method stub
		return super.authenticationManagerBean();
	}
	
	
	
	
	
	
	
	
}
