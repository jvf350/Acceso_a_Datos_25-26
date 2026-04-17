package com.mgh.ad.moviesapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.mgh.ad.moviesapi.model.Movie;
import com.mgh.ad.moviesapi.service.MovieService;


@RestController 
@RequestMapping("/api/v1")
public class MovieController {
	
	@Autowired
	private MovieService movieService;
	//private final MovieService movieService=null;
		
	@GetMapping("/movies")
	public List<Movie> findAll(){
		return movieService.findAll();
	}
	
	@GetMapping("/movie/{id}")
	public Movie findById(@PathVariable String id) {
		return movieService.findById(id).get();
	}
		
	// Otros métodos
	
	@GetMapping("/movie/bytitle/{title}")
	public List<Movie> findByTitle(@PathVariable String title) {
		return movieService.findByTitle(title);
	}
	
	@GetMapping("/movie/byyear/{year}")
	public List<Movie> findByYear(@PathVariable Long year) {
		return movieService.findByYear(year);
	}

	/*@PostMapping("/movie")
	public ResponseEntity<Movie> saveMovie(@RequestBody Movie movie) {
		Movie savedMovie = movieService.save(movie);
		return new ResponseEntity<>(savedMovie, HttpStatus.CREATED);
	}*/
	
}
