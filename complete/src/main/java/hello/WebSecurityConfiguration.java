package hello;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.AuthenticationRegistry;
import org.springframework.security.config.annotation.web.EnableWebSecurity;
import org.springframework.security.config.annotation.web.ExpressionUrlAuthorizations;
import org.springframework.security.config.annotation.web.HttpConfiguration;
import org.springframework.security.config.annotation.web.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpConfiguration http) throws Exception {
        ExpressionUrlAuthorizations expressionUrlAuthorizations = http.authorizeUrls();
        expressionUrlAuthorizations.antMatchers("/hello").hasRole("USER");
        expressionUrlAuthorizations.antMatchers("/", "/home", "/login").permitAll();
        http.formLogin().defaultSuccessUrl("/hello").permitAll();
        http.logout().logoutSuccessUrl("/");
    }

    @Override
    protected void registerAuthentication(AuthenticationRegistry registry) throws Exception {
        registry.inMemoryAuthentication()
                .withUser("user").password("password").roles("USER");
    }
}
