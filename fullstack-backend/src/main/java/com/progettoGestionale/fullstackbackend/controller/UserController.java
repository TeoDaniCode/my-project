package com.progettoGestionale.fullstackbackend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.progettoGestionale.fullstackbackend.exception.UserNotFoundException;
import com.progettoGestionale.fullstackbackend.model.User;
import com.progettoGestionale.fullstackbackend.repository.UserRepository;
import com.progettoGestionale.fullstackbackend.service.UserService;

/*In qualsiasi modo sia stata generata la vostra Request, la risposta ad essa sarà sempre generata da un Controller.
  I Controller sono un pezzo fondamentale del paradigma MVC ed hanno il compito di rispondere alle Request, 
  generando una Response.
  Il Controller è un po’ il “punto di ingresso” di un’applicazione web. 
  Ogni controller è una classe Java che ha uno o più metodi, ognuno dei quali è associato ad un diverso indirizzo URL. 
  Quando arriva una request verso quell’indirizzo verrà eseguito il metodo corrispondente. 
 
  Questo controller (prendiamo ad esempio il metodo getUserById)definisce una route(percorso) all'indirizzo "/user/{id}" 
  in modalita GET, accetta un parametro chiamato id e restituisce una Response che con l'utilizzo dell'interfaccia UserRepository,
  precedentemente inniettata tramite annotazione @autowired, dove utilizzando il metodo dell'interfaccia .findById(id) andiamo a 
  restituire un istanza della classe User contenente l'utente che ha l'id = a quello passato dall'url.
  
  La mappatura di un URL avviene attraverso le annotations @GetMapping e @RequestMapping, che vanno lette insieme. 
  Annotando l’intero controller si fornisce una “base” per tutti gli URL che sono definiti sui singoli metodi. 
  E’ una pratica comune ma non obbligatoria. Quello che conta di più sono le annotazioni sul singolo metodo, poiché è così che 
  si indica al sistema quale “pezzo di codice” eseguire a fronte di una Request su quell’indirizzo.
  
 * */


/* Nei Controller gestisco le chiamate al server */

@RestController
@CrossOrigin("http://localhost:3000")
public class UserController {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/user")
	ResponseEntity newUser(@RequestBody User newUser) {
		
		Pair<HttpStatus, Object> result = userService.newUser(newUser);
		
		return ResponseEntity.status(result.getFirst()).body(result.getSecond());
		//return userRepository.save(newUser);
	}
	
	//Questo è un EndPoint
	@GetMapping("/users")
	List<User> getAllUsers(){
		return userRepository.findAll();
	}
	
	/* La lambda Function viene utilizzata in modo che non si debba istanziare una variabile di tipo UserNotFoundException 
	   per poi richiamarne il metodo passando l'id, 
	   ma possiamo direttamente istanziarla e richiamare il metodo in una riga di codice
	   
	   L'annotation @ExceptionHandler (presente nella classe UserNotFoundAdvice) permette di definire un metodo 
	   all'interno del controller che verrà invocato automaticamente quando verrà lanciata un eccezione, all'interno del 
	   controller specifico. 
	   In questo caso quando parte l'eccezione .orElseThrow(()->new UserNotFoundException(id))
	   andiamo a invocare UserNotFoundAdvice passandogli come parametro new UserNotFoundException(id).
	*/
	
	@GetMapping("/user/{id}")
	User getUserById(@PathVariable Long id) {
		return userRepository.findById(id)
				.orElseThrow(()->new UserNotFoundException(id));
	}
}
