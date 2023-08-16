package com.example.star_contractor;

import com.example.star_contractor.Services.UserDetailsLoader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    private UserDetailsLoader usersLoader;

    public SecurityConfiguration(UserDetailsLoader usersLoader) {
        this.usersLoader = usersLoader;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests((requests) -> requests
                        /* Pages that require authentication
                         * only authenticated users can create and edit ads */

                        .requestMatchers("/jobs/{id}/myjobs", "/jobs/{id}/comment", "/jobs/{jobId}/comment/{commentId}/delete").authenticated()
                        .requestMatchers("/jobs/remove/{id}", "/jobs/apply/{id}", "/jobs/{id}", "/jobs/editjob/{id}", "/jobs/delete/{id}", "/jobs/complete/{id}", "/webjars/font-awesome/6.4.0/css/all.css", "/webjars/font-awesome/6.4.0/webfonts/fa-solid-900.ttf", "/webjars/font-awesome/6.4.0/webfonts/fa-solid-900.woff2").authenticated()
                        .requestMatchers("/profile/edit/{userId}", "/profile/edits/{userId}", "/profile/remove/{id}", "/profile/add/{id}", "/profile/accept-friend-request/{requestId}", "/profile/deny-friend-request/{requestId}").authenticated()
                        .requestMatchers("/profile/{id}")
                        .authenticated()

                        /* Pages that do not require authentication
                         * anyone can visit the home page, register, login, and view ads */
                        .requestMatchers("/resetpassword/{token}/{emailReset}", "/resetpassword", "/", "/jobs", "/signup", "/login","/register", "/webjars/font-awesome/6.4.0/css/all.css").permitAll()
                        // allow loading of static resources
                        .requestMatchers("/css/**", "/js/**", "assets/**").permitAll()
                )
                /* Login configuration */
                .formLogin((login) -> login.loginPage("/login").defaultSuccessUrl("/"))
                /* Logout configuration */
                .logout(logout -> {
                    logout.logoutSuccessUrl("/");
                    // Allow logout with GET and POST requests
                    logout.logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"));
                })
                .httpBasic(withDefaults());
        return http.build();
    }

}
