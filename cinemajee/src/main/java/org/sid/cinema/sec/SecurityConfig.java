package org.sid.cinema.sec;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	@Autowired
	BCryptPasswordEncoder bCrye;
	@Autowired
	private DataSource dataSource;
@Override
protected void configure(AuthenticationManagerBuilder auth) throws Exception {
	BCryptPasswordEncoder bcpe=getBCPE();
	
	  auth.inMemoryAuthentication().withUser("admin").password(bcpe.encode("1234"))
	  .roles("ADMIN","USER");
	  auth.inMemoryAuthentication().withUser("user").password(bcpe.encode("1234")).
	  roles("USER"); 
	  auth.inMemoryAuthentication().passwordEncoder(new
	  BCryptPasswordEncoder());

		}

@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.formLogin();
http.authorizeRequests().antMatchers("/AddCinema","/deleteVille","/AjouterVille","/deleteCinema","/AjouterCinema","/deleteSalle","/AddFilm","/deleteFilm","/ModifierVille").hasRole("ADMIN");
		http.exceptionHandling().accessDeniedPage("/403");
	}
	@Bean
	BCryptPasswordEncoder getBCPE() {
		return new BCryptPasswordEncoder();
	}

}
