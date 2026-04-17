package com.mgh.ad.moviesapi.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.Data;

@Setter
@Getter
@Document(value = "movies")
@Data
public class Movie {
	@Id
	private String id;
	@Field
	private String title;
	@Field
	private String year;
	@Field
	private String plot;
	@Field
	private Integer runtime;
	@Field
	private String rated;
	@Field
	private List<String> cast;
	@Field
	private String poster;
	@Field
	private List<String> languages;
	@Field
	private List<String> directors;

}
