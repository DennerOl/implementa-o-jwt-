package com.devsuperior.demo.services;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.devsuperior.demo.entities.Role;
import com.devsuperior.demo.entities.User;
import com.devsuperior.demo.projections.UserDetailsProjection;
import com.devsuperior.demo.repositories.UserRepository;

public class UserService implements UserDetailsService {

	private UserRepository repository;
	
/* da um nome de usuario eu busco o usuario
 *  correspondente 
 *  Se o username não existir lanço a exceção UsernameNotFoundException
 */
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		List<UserDetailsProjection> result = repository.searchUserAndRolesByEmail(username);
		if (result.size() == 0) {
			throw new UsernameNotFoundException("User not found");
		}
// salvo um usuario com os dados da projection	
		
		User user = new User();
		user.setEmail(username);
		user.setPassword(result.get(0).getPassword());
		
/* tenho que pegar os Role também
 * pois um usuario está associado ao um role 
 * para cada elemento da minha lista result faço os gets
 */		
		for(UserDetailsProjection projection : result) {
			user.addRole(new Role(projection.getRoleId(), projection.getAuthority()));
		}
		return user;
	}

	
}
