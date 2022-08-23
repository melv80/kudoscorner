package com.kudos.server.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired
  private AppConfig config;

  @Override
  protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
				.antMatchers("/admin", "/api/**", "/create").hasRole("ADMIN")
				.and()
			.formLogin()
				.loginPage("/login")
				.and()
			.logout()
				.logoutSuccessUrl("/login");
  }


  @Bean
  @Override
  public InMemoryUserDetailsManager userDetailsService() {
    List<UserDetails> userDetailsList = new ArrayList<>();
    userDetailsList.add(User.withUsername("admin").password(config.getPasswordHash())
        .roles("ADMIN", "USER").build());
    return new InMemoryUserDetailsManager(userDetailsList);
  }

  @Override
  public void configure(WebSecurity web) throws Exception {
    web
        .ignoring().antMatchers("/resources/**");
  }
}