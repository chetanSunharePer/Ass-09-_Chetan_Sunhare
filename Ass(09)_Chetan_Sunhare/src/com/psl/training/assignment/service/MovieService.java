package com.psl.training.assignment.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
	public void serialize(Movies mo,File path) {
		for (Movies movies : movieList) {
			
		}
	}
	public void addMovie(Movies movie,List<Movies> movieList) {
		try 
		{
			Connection con=MyDatabase.getConnection();
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
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void Display() {
		System.out.println("All moives : ");
		System.out.println();
		for (Movies movies : movieList) {
			System.out.println("ID : "+movies.getMovieId());
			System.out.println("Name : "+movies.getMovieName());
			System.out.println("Release Date : "+movies.getReleaseDate());
			System.out.println("Type : "+movies.getMovieType());
			System.out.println("Language :"+movies.getLanguage());
			System.out.println("Cast : ");
			for (String cast : movies.getCasting()) {
				System.out.println("\t"+cast);
			}
			System.out.println("Ratings( by IMDb) : "+movies.getRating());
			System.out.println("Box Office Collection(in Billions) : "+movies.getTotalBusinessDone()+"B");
			System.out.println("------------------------------------------------------------------------");
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
				System.out.println(movie.getReleaseDate());
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
	public List<Movies> getMoviesRealeasedInYear(int year){
		List<Movies> newList = new ArrayList<>();
		DateFormat format= new SimpleDateFormat("yyyy-mm-dd");
		try {
			Connection con = MyDatabase.getConnection();
			String s="SELECT * FROM NEW_MOVIES WHERE TO_CHAR(RELEASE_DATE,'YYYY') = ?";
			PreparedStatement stm = con.prepareStatement(s);
			stm.setInt(1,year);
			ResultSet rs= stm.executeQuery();
			while(rs.next()) {
				
				int movieId = rs.getInt(1);
				String s1="SELECT * FROM NEW_MOVIES_CASTING WHERE ID = ?";
				PreparedStatement stm1 = con.prepareStatement(s1);
				stm1.setInt(1,movieId);
				ResultSet rs1= stm1.executeQuery();
				List<String> casting = new ArrayList<>();
				while(rs1.next()) {
					casting.add(rs1.getString(2));
				}
				String releaseDate = format.format(format.parse(rs.getString(2).split(" ")[0]));
				String movieName = rs.getString(3);
				Category movieType = Category.valueOf(rs.getString(4).toUpperCase());
				Language language = Language.valueOf(rs.getString(5).toUpperCase());
				double rating = Double.valueOf(rs.getString(6));
				double totalBusinessDone = Double.valueOf(rs.getString(7));
				newList.add(new Movies(movieId, movieName, movieType, language, releaseDate, casting, rating, totalBusinessDone));
			}
		} catch (SQLException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (Movies movies : newList) {
			System.out.println("ID : "+movies.getMovieId());
			System.out.println("Name : "+movies.getMovieName());
			System.out.println("Release Date : "+movies.getReleaseDate());
			System.out.println("Type : "+movies.getMovieType());
			System.out.println("Language :"+movies.getLanguage());
			System.out.println("Cast : ");
			for (String cast : movies.getCasting()) {
				System.out.println("\t"+cast);
			}
			System.out.println("Ratings( by IMDb) : "+movies.getRating());
			System.out.println("Box Office Collection(in Billions) : "+movies.getTotalBusinessDone()+"B");
			System.out.println("------------------------------------------------------------------------");
		}
		return newList;
	}
	
}
