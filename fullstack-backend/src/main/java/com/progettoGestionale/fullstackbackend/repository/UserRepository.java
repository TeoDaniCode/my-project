package com.progettoGestionale.fullstackbackend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.progettoGestionale.fullstackbackend.model.User;

/* Nei Repository implemento le query e l'interazione con i DB */

@Repository
public interface UserRepository extends JpaRepository<User,Long>{

	/*due modi per arrivare allo stesso risultato.*/
	List<User> findUsersByEmail(String email);
	
	@Query("select u from User u where u.email = :email")
	List<User> trovaUtentiDaMail(@Param("email") String email);
	/***************************************************/
	
	
}
