package com.pharmacy.v3.Security;

import com.pharmacy.v3.Security.Service.UserDetailsServiceImpl;
import com.pharmacy.v3.Security.jwt.AuthEntryPointJwt;
import com.pharmacy.v3.Security.jwt.AuthTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    private AuthEntryPointJwt unauthorizedHandler;
    private AuthTokenFilter filter;

    @Autowired
    public WebSecurityConfig(UserDetailsServiceImpl userDetailsService, AuthEntryPointJwt unauthorizedHandler, AuthTokenFilter filter) {
        this.userDetailsService = userDetailsService;
        this.unauthorizedHandler = unauthorizedHandler;
        this.filter = filter;
    }

    @Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
                .authorizeRequests().antMatchers("/api/auth/**").permitAll()
                .antMatchers("/api/items/itemsAll").permitAll()
                .antMatchers("/api/items/**").permitAll()
                .antMatchers("/api/category/**").permitAll()
                .antMatchers("/api/requests/**").permitAll()
                .antMatchers("/api/inquiry/**").permitAll()
                .antMatchers("/api/fav/**").permitAll()
                .antMatchers("/api/rate/items-all/**").permitAll()
                .antMatchers("/api/rate/item/rate/**").permitAll()
                .antMatchers("/api/items/category/item-all/**").permitAll()
                .antMatchers("/api/otp/password-change/**").permitAll()
                .antMatchers("/api/otp/**").permitAll()
                .antMatchers("/**").permitAll()
                .anyRequest().authenticated().and()

                .formLogin()
                .loginPage("/Home")
                .loginProcessingUrl("/authenticate")
                .usernameParameter("username")
                .passwordParameter("password")
                .defaultSuccessUrl("/SuccessLogin")
                .permitAll()
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .and().logout()
                .logoutUrl("/logout")
                .invalidateHttpSession(true)
                .logoutSuccessUrl("/Home")
                .permitAll();

                //.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
      /*  http
                .csrf().disable()

                .authorizeRequests()
                .antMatchers("/webjars/**", "/CSS/**", "/Images/**", "/visitor/authorize")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .loginPage("/api/auth/Home")
                .loginProcessingUrl("/authenticate")
                .usernameParameter("email")
                .passwordParameter("password")
                .defaultSuccessUrl("/SuccessLogin")
                .permitAll()
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .and().logout()
                .logoutUrl("/logout")
                .invalidateHttpSession(true)
                .logoutSuccessUrl("/Home")
                .permitAll();


        http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);*/

     /*
        */
    }
}
