package org.sid.sec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private UserDetailsService userDetailsService;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
@Override
protected void configure(AuthenticationManagerBuilder auth) throws Exception {
	// TODO Auto-generated method stub
	//super.configure(auth);
	/*auth.inMemoryAuthentication().withUser("admin").password("{noop}1234").roles("USER","ADMIN")
	.and()
	.withUser("user").password("{noop}1234").roles("USER");*/
	auth.userDetailsService(userDetailsService)
	.passwordEncoder(bCryptPasswordEncoder);
}

//@Override
	protected void configure(HttpSecurity http) throws Exception {
		// TODO Auto-generated method stub
		//super.configure(http);
	http.formLogin();
	//http.authorizeRequests().antMatchers("/user/*").hasRole("USER");
	//http.authorizeRequests().antMatchers("/admin/*").hasRole("ADMIN");
	http.exceptionHandling().accessDeniedPage("/403");
	}
}
