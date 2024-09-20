package com.progettoGestionale.fullstackbackend.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.progettoGestionale.fullstackbackend.model.User;
import com.progettoGestionale.fullstackbackend.repository.UserRepository;


/* Nei service implemento la logica */
@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	public void getUserFromExternalProvider() {
		
		//CHIAMA GOOGLE
		
		this.newUser(new User());
	}
	
	
	public Pair newUser(User newUser) {
		// Performance
		// Sicurezza - errori di codice e errori di integrità dei dati
		if(newUser.getUsername() == null)
			Pair.of(HttpStatus.BAD_REQUEST, "");
		
		List<User> users = userRepository.findUsersByEmail(newUser.getEmail());
		
		if(users.size() > 0) {
			return Pair.of(HttpStatus.INTERNAL_SERVER_ERROR, Map.of("esito", String.format("Errore user già presente: %s", 
					users.stream().map(User::getName).collect(Collectors.joining(",")))));
		}
		
		return Pair.of(HttpStatus.CREATED, userRepository.save(newUser));
	}
	
	// Creare password prov - comunicarla - salvarla
	// Aggionare password ogni 30 minuti
	
}
