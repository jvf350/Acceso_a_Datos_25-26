package com.mgh.ad.moviesapi.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.mgh.ad.moviesapi.model.Movie;

@Repository
public interface MovieRepository extends MongoRepository<Movie, String>{
	
	// Los métodos CRUD se heredan de MongoRepository (CRUDRepository), 
	// por lo que solamente hace falta definir aquellos método que 
	// no sean de este tipo.

	@Query(value="{ title : ?0}")
	List<Movie> findByTitle(String title);
	
	@Query(value="{ year : ?0}")
	List<Movie> findByYear(Long year);
}
