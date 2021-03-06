package com.psl.training.assignment.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import com.psl.training.assignment.beans.Category;
import com.psl.training.assignment.beans.Language;
import com.psl.training.assignment.beans.Movies;

public class MovieService {
	List<Movies> movieList = new ArrayList<>();
	public List<Movies> populateMovies(File file) throws ParseException {
		
		try(Scanner sc=new Scanner(new FileInputStream(file))) {
			while(sc.hasNextLine()) {
				String[] line = sc.nextLine().split(",");
				DateFormat format= new SimpleDateFormat("dd/MM/yyyy");
				int movieId = Integer.parseInt(line[0]);
				String movieName = line[1];
				Category movieType = Category.valueOf(line[2].toUpperCase());
				Language language = Language.valueOf(line[3].toUpperCase());
				String releaseDate = format.format(format.parse(line[4]));
				ArrayList<String> casting = new ArrayList<>();
				casting.addAll(Arrays.asList(line[5].split("/")));
				double rating = Double.valueOf(line[6]);
				double totalBusinessDone = Double.valueOf(line[7]);
				movieList.add(new Movies(movieId, movieName, movieType, language, releaseDate, casting, rating, totalBusinessDone));
				
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return movieList;
	}
	public void Display() {
		for (Movies movies : movieList) {
			System.out.println(movies.getMovieId());
		}
	}
}
