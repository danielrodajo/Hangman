package com.daniel.hangman.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.daniel.hangman.models.AppUser;
import com.daniel.hangman.repositories.UserRepository;

@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	UserRepository repositorio;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		//Trataremos como nombre de usuario el propio correo electronico
		AppUser user = repositorio.findByNameIgnoreCase(username).orElse(null);
		
		UserBuilder builder = null;
		
		System.out.println("PRUEBA"+user);
		
		//Si encontramos al usuario, vamos montando el builder con la información de este
		if (user != null) {
			//Asignamos nombre de usuario
			builder = User.withUsername(username);
			//Lo habilitamos
			builder.disabled(false);
			//Asignamos la contraseña
			builder.password(user.getPassword());
			//Asignamos los permisos
			builder.authorities(new SimpleGrantedAuthority("ROLE_USER"));
		} else 
			throw new UsernameNotFoundException("Usuario no encontrado");
				
		return builder.build();
		
	}

}
