package com.mgh.ad.moviesapi.service;

import java.util.List;
import java.util.Optional;

import com.mgh.ad.moviesapi.model.Movie;

public interface MovieService {
	// Métodos por defecto para el CRUD, definidos en el repositorio
	
	public List<Movie> findAll();
	public Optional<Movie> findById(String id);	
	
	// Métodos adicionales
	public List<Movie> findByTitle(String title);
	public List<Movie> findByYear(Long year);

}

