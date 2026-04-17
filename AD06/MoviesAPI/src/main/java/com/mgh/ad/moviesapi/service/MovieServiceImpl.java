package com.mgh.ad.moviesapi.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mgh.ad.moviesapi.model.Movie;
import com.mgh.ad.moviesapi.repository.MovieRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {
	
	@Autowired
	private MovieRepository movieRepository;
	
	
	@Override
	public List<Movie> findAll(){
		return movieRepository.findAll();
	}
	
	@Override
	public Optional<Movie> findById(String id) {
		// retorna un optional, que indica si existe o no
		// el valor que estamos retornando 
		return movieRepository.findById(id);
	}

	// Métodos adicionales
	@Override
	public List<Movie> findByTitle(String title){
		return movieRepository.findByTitle(title);
	}
	
	@Override
	public List<Movie> findByYear(Long year){
		return movieRepository.findByYear(year);
	}
}

