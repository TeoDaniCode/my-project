package com.progettoGestionale.fullstackbackend.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;


/**/
@ControllerAdvice
public class UserNotFoundAdvice {
	
	@ResponseBody
	@ExceptionHandler(UserNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	
	/*Questo metodo riceve un oggetto UserNotFoundException contenente il risultato del metodo invocato dal controller. 
	  l'annotazione ResponseStatus forza un particolare codice di ritorno */
	
	public Map<String,String> exceptionHandler(UserNotFoundException exception){
		
		Map<String,String> errorMap = new HashMap<>();
		errorMap.put("errorMessage", exception.getMessage());
		
		return errorMap;
	}

}
