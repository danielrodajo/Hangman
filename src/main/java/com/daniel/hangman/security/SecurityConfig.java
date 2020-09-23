package com.daniel.hangman.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
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
	 UserDetailsService userDetailsService;
	
	/**
	 * AUTENTICACION:
	 * La realizamos haciendo uso del servicio de los detalles del usuario, y le ciframos la contraseña
	 */
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}
	
	/**
	 * Bean para cifrar contraseñas
	 * @return
	 */
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();	
	}
	
	/**
	 * AUTORIZACION
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		.authorizeRequests()
		//No se requiere de autenticacion para acceder a estos recursos
		.antMatchers("/webjars/**", "/css/**", "/img/**", "/auth/**").permitAll()
		//Se requiere autenticacion para acceder al resto de recursos
		.anyRequest().authenticated()
		.and()
		//En lo que respecta al formulario de login
		.formLogin()
		//Esta será la ruta para acceder
		.loginPage("/auth/login")
		//En caso de ser autenticados, redirigirnos a esta otra
		.defaultSuccessUrl("/index", true)
		.loginProcessingUrl("/auth/login-post")
		.permitAll()
		.and()
		//En lo que respecta al logout
		.logout()
		//Esta será la ruta al logout
		.logoutUrl("/auth/logout")
		//Y, en caso de haberse deslogueado correctamente, redirigirnos a esta pagina
		.logoutSuccessUrl("/index");
			
		http.csrf().disable();
		http.headers().frameOptions().disable();
	}
	
}
