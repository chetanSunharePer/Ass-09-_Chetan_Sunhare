package com.psl.training.assignment.beans;

import java.util.List;

public class Movies {
	private int movieId; 
	private String movieName; 
	private Category movieType;
	private Language language; 
	private String releaseDate; 
	private List<String> casting;
	private double rating;
	private double totalBusinessDone;
	
	public Movies(int movieId, String movieName, Category movieType, Language language, String releaseDate,
			List<String> casting, double rating, double totalBusinessDone) {
		super();
		this.movieId = movieId;
		this.movieName = movieName;
		this.movieType = movieType;
		this.language = language;
		this.releaseDate = releaseDate;
		this.casting = casting;
		this.rating = rating;
		this.totalBusinessDone = totalBusinessDone;
	}

	public int getMovieId() {
		return movieId;
	}

	public String getMovieName() {
		return movieName;
	}

	public Category getMovieType() {
		return movieType;
	}

	public Language getLanguage() {
		return language;
	}

	public String getReleaseDate() {
		return releaseDate;
	}

	public List<String> getCasting() {
		return casting;
	}

	public double getRating() {
		return rating;
	}

	public double getTotalBusinessDone() {
		return totalBusinessDone;
	}

	public void setMovieId(int movieId) {
		this.movieId = movieId;
	}

	public void setMovieName(String movieName) {
		this.movieName = movieName;
	}

	public void setMovieType(Category movieType) {
		this.movieType = movieType;
	}

	public void setLanguage(Language language) {
		this.language = language;
	}

	public void setReleaseDate(String releaseDate) {
		this.releaseDate = releaseDate;
	}

	public void setCasting(List<String> casting) {
		this.casting = casting;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}

	public void setTotalBusinessDone(double totalBusinessDone) {
		this.totalBusinessDone = totalBusinessDone;
	}
	
}
