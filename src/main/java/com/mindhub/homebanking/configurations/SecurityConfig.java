package com.mindhub.homebanking.configurations;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/web/images/*","/web/index.html","/web/pagesCss/*","/web/pagesJs/*","/web/pages/*").permitAll()
                .requestMatchers("/web/**","/api/clients/current","/api/accounts/*/transactions")
                .hasAnyAuthority("CLIENT","ADMIN")
                .requestMatchers("/h2-console/**").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.POST, "/api/clients","/api/login").permitAll()
                .requestMatchers(HttpMethod.POST,"/api/clients/current/accounts","/api/clients/current/cards",
                        "api/clients/current/accounts/first","/api/transactions/transfer").hasAuthority("CLIENT")
                .anyRequest().denyAll());

        http.csrf(httpSecurityCsrfConfigurer -> httpSecurityCsrfConfigurer.disable());

        http.headers(httpSecurityHeadersConfigurer -> httpSecurityHeadersConfigurer.frameOptions(
                frameOptionsConfig -> frameOptionsConfig.disable()));

        http.formLogin(formLogin -> formLogin
                .loginPage("/web/index.html")
                .loginProcessingUrl("/api/login")
                .usernameParameter("email")
                .passwordParameter("password")
                .successHandler((request, response, authentication) -> {
                    if(request.isUserInRole("ADMIN")){
                        System.out.println("User is in role ADMIN");
                        response.sendRedirect("/h2-console/");
                        clearAuthenticationAttributes(request);
                    } else if (request.isUserInRole("CLIENT")){
                        System.out.println("User is in role CLIENT");
                        response.sendRedirect("/web/accounts.html");
                        clearAuthenticationAttributes(request);
                    } else {
                        System.out.println("User has no recognized role");
                    }
                })
                .failureHandler((request, response, exception) -> response.sendError(401))
                .permitAll()
        );

        http.exceptionHandling( exceptionHandlingConfigurer ->
                exceptionHandlingConfigurer.authenticationEntryPoint((request, response, authException) -> response
                        .sendError(401)));

        http.logout(httpSecurityLogoutConfigurer ->
                httpSecurityLogoutConfigurer
                        .logoutUrl("/api/logout")
                        .logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler())
                        .deleteCookies("JSESSIONID"));

        http.rememberMe(Customizer.withDefaults());


        return http.build();
    }

    private void clearAuthenticationAttributes(HttpServletRequest request) {

        HttpSession session = request.getSession(false);

        if (session != null) {

            session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);

        }

    }

}
