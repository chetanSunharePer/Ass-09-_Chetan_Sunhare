package com.psl.training.assignment.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import com.psl.training.assignment.beans.Category;
import com.psl.training.assignment.beans.Language;
import com.psl.training.assignment.beans.Movies;
import com.psl.training.assignment.beans.MyDatabase;

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
	public boolean allAllMoviesInDb(List<Movies> movies) {
		try {
			Connection con = MyDatabase.getConnection();
			for (Movies movie : movies) {
				String movieData = "Insert INTO NEW_MOVIES VALUES(?,TO_DATE(?),?,?,?,?,?)";
				PreparedStatement stm = con.prepareStatement(movieData);
				stm.setInt(1, movie.getMovieId());
				stm.setString(2, movie.getReleaseDate());
				stm.setString(3, movie.getMovieName());
				stm.setString(4, ""+movie.getMovieType());
				stm.setString(5, ""+movie.getLanguage());
				stm.setString(6, ""+movie.getRating());
				stm.setString(7, ""+movie.getTotalBusinessDone());
				if(stm.executeUpdate()>0) {
					for (String cast : movie.getCasting()) {
						String movieCasting = "Insert INTO NEW_MOVIES_CASTING VALUES(?,?)";
						PreparedStatement stm2 = con.prepareStatement(movieCasting);
						stm2.setInt(1, movie.getMovieId());
						stm2.setString(2,cast);
						stm2.executeUpdate();
						stm2.close();
					}	
				}
				stm.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		MyDatabase.close();
		return true;
	}
}
