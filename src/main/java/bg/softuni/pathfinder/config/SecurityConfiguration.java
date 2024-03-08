package bg.softuni.pathfinder.config;

import bg.softuni.pathfinder.service.UserService;
import bg.softuni.pathfinder.service.impl.PathfinderUserDetailServiceImpl;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity.authorizeHttpRequests(
                // Define which URLs are visible by which users
                authorizeRequest -> authorizeRequest
                        // ALL static resources which are situated in js, images, css are available for everyone
                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                        // Allow anyone to see the home page, the registration page and the login form
                        .requestMatchers("/").permitAll()
                        .requestMatchers("/users/login", "/users/register", "/users/login-error").anonymous()
                        .requestMatchers("/admin").hasRole("ADMIN")
                        .anyRequest().authenticated()
        ).formLogin(
                formLogin ->
                        formLogin
                                .loginPage("/users/login")
                                .usernameParameter("username")
                                .passwordParameter("password")
                                .defaultSuccessUrl("/")
                                .failureUrl("/users/login?error=true")
        ).logout(
                logout -> logout
                        .logoutUrl("/users/logout")
                        .logoutSuccessUrl("/")
                        .invalidateHttpSession(true)
        );

        return httpSecurity.build();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return Pbkdf2PasswordEncoder.defaultsForSpringSecurity_v5_8();
    }


    @Bean
    public UserDetailsService userDetailsService(UserService userService) {
        return new PathfinderUserDetailServiceImpl(userService);
    }

}
