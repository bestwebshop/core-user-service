package tech.bestwebshop.api.userservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;

@SpringBootApplication
@EnableDiscoveryClient
public class UserServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);
    }

    @Configuration
    @EnableResourceServer
    public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

        @Override
        public void configure(HttpSecurity httpSecurity) throws Exception {
            httpSecurity
                    .authorizeRequests()
                    .antMatchers(HttpMethod.GET, "/users/").permitAll()
                    .antMatchers(HttpMethod.POST, "/users/").permitAll()
                    .and()
                    .authorizeRequests()
                    .antMatchers("/users/{id}")
                    .authenticated()
                    .and()
                    /**/.exceptionHandling()
                    .accessDeniedHandler(new OAuth2AccessDeniedHandler());
        }
    }

    @Configuration
    @EnableGlobalMethodSecurity(securedEnabled = true, jsr250Enabled = true)
    public class MethodSecurityConfig extends GlobalMethodSecurityConfiguration {

    }
}
