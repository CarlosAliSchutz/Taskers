package br.com.cwi.crescer.api.security.config;

import br.com.cwi.crescer.api.security.google.login.CustomOAuth2UserService;
import br.com.cwi.crescer.api.security.service.LoginOAuth2GoogleHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    protected SecurityFilterChain configure(
            HttpSecurity http,
            LoginOAuth2GoogleHandler loginOAuth2GoogleHandler,
            CustomOAuth2UserService oauthUserService) throws Exception {
        http
                    .csrf().disable().cors()
                .and()
                    .authorizeRequests().antMatchers("/auth/**","/oauth/**").permitAll()
                .and()
                    .authorizeRequests().anyRequest().authenticated()
                .and()
                    .httpBasic().authenticationEntryPoint((request, response, authException) -> response.setStatus(UNAUTHORIZED.value()))
                .and()
                    .logout().logoutUrl("/auth/logout").logoutSuccessHandler((request, response, authentication) -> response.setStatus(OK.value()))
                .and()
                    .formLogin().permitAll()
                .and()
                    .oauth2Login()
                    .userInfoEndpoint()
                    .userService(oauthUserService)
                .and()
                    .successHandler(loginOAuth2GoogleHandler)
        ;
        return http.build();
    }

}