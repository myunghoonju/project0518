package com.ddc2.project0518.security;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.DefaultSecurityFilterChain;
/**
흔히 만드는 security-context.xml작업을 이곳에 한다.
SecurityConfig의 WebSecurity 객체는
springSecurityFilterChain이라는 이름의 DelegatingFilterProxy Bean 객체를 생성
DelegatingFilterProxy Bean은 많은 Spring Security Filter Chain에 역할을 위임
**/
import org.springframework.security.web.FilterChainProxy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextPersistenceFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends  WebSecurityConfigurerAdapter{
	
		@Inject
		private UserDetailsService userDetailsService;

		@Override
		public void configure(WebSecurity web) throws Exception{
			web.ignoring().antMatchers("/");
			web.ignoring().antMatchers("favicon.ico");
			
		
		}	
		@Override
		public void configure(HttpSecurity http) throws Exception{
			super.configure(http);
			http.csrf().disable();
			
			http.authorizeRequests().antMatchers("/resources/**").permitAll();

			http.authorizeRequests().antMatchers("/users/*").authenticated()
				.and()
					.formLogin()
					.loginPage("/signin")
					.loginProcessingUrl("/users/auth")
					.failureUrl("/signin?result=fail") //실패하면 리다이렉트
					.defaultSuccessUrl("/",true)
					.usernameParameter("userid")
					.passwordParameter("password");
			
			http
				.logout()
				.logoutRequestMatcher(new AntPathRequestMatcher("/users/signout"))
				.logoutSuccessUrl("/")
				.invalidateHttpSession(true);
			
			
		}
		@Override
		public void configure(AuthenticationManagerBuilder auth) throws Exception{
			super.configure(auth);
			auth.userDetailsService(userDetailsService);
		}
		
		/*
		@Bean(name = "springSecurityFilterChain")
		public FilterChainProxy filterChainProxy() {
			List<SecurityFilterChain> filterChains = new ArrayList<SecurityFilterChain>();
			filterChains.add(new DefaultSecurityFilterChain(new AntPathRequestMatcher("/"),securityContextPersistenceFilter()));
			
			return new FilterChainProxy(filterChains);
		}
		
		@Bean
		public SecurityContextPersistenceFilter securityContextPersistenceFilter() {
			return new SecurityContextPersistenceFilter(new HttpSessionSecurityContextRepository());
		}
		*/

		
}
