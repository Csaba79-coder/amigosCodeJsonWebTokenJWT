package com.csaba79coder.amigoscodejsonwebtokenjwt.security;

import com.csaba79coder.amigoscodejsonwebtokenjwt.filter.CustomAuthenticationFilter;
import com.csaba79coder.amigoscodejsonwebtokenjwt.filter.CustomAuthorizationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor // makes dep injection!
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // super.configure(auth);
        // checks for user Authentication: auth.jdbcAuthentication() or auth.inMemoryAuthentication() depends on the database!
        // BUT we have JPA, and that does this job!
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        // how to override the login page!
        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authenticationManagerBean());
        customAuthenticationFilter.setFilterProcessesUrl("/api/login");
        // we wanna use the token system!
        // we have to configure the http security for that reason!
        // super.configure(httpSecurity);
        httpSecurity.csrf().disable();
        httpSecurity.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        // the order bellow matters!!! follow this construction!
        // original before override
        // httpSecurity.authorizeRequests().antMatchers("/login").permitAll(); // all the antPatterns here are the sites we don't want to be in secure, and does not needed authentication!
        httpSecurity.authorizeRequests().antMatchers("/api/login/**", "api/token/refresh/**").permitAll();
        httpSecurity.authorizeRequests().antMatchers(GET, "/api/user/**").hasAnyAuthority("ROLE_USER"); // ** means that everything that comes after that!
        httpSecurity.authorizeRequests().antMatchers(POST, "/api/user/save/**").hasAnyAuthority("ROLE_ADMIN");
        httpSecurity.authorizeRequests().anyRequest().authenticated(); // if we don't use authenticated, and we use permitAll, we can through it out on the window!
        // httpSecurity.authorizeRequests().anyRequest().permitAll(); // we allow everyone to access the application! // I have no security yet, as it is permitting all!
        // httpSecurity.addFilter(new CustomAuthenticationFilter(authenticationManagerBean()));
        httpSecurity.addFilter(customAuthenticationFilter);
        httpSecurity.addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class); // we need the addFilterBefore! before is important!
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
