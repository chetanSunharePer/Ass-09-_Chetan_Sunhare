import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.psl.training.assignment.beans.Category;
import com.psl.training.assignment.beans.Language;
import com.psl.training.assignment.beans.Movies;
import com.psl.training.assignment.service.MovieService;

public class MovieTester {

	public static void main(String[] args) throws ParseException {
		// TODO Auto-generated method stub
		MovieService service = new MovieService();
		List<Movies> movieList = null; 
		Scanner sc = new Scanner(System.in);
		boolean check = true;
		while(check) {
			System.out.println("Menu : ");
			System.out.println("1. Read All Data From movies.txt\n"
					+ "2. Display All Data From local movie list\n"
					+ "3. Insert all movie data into Database\n"
					+ "4. Add New Movie\n"
					+ "5. Find Movies Redleased in Year\n"
					+ "6. Exit ");
			int choice = Integer.parseInt(sc.nextLine());
			switch (choice) {
			case 1:
				movieList = service.populateMovies(new File("D:\\java\\ELTP_Training\\Ass-09-_Chetan_Sunhare\\Ass(09)_Chetan_Sunhare\\src\\Movies.txt"));
				System.out.println("Data Read SuccessFully");
				break;
			case 2:
				service.Display();
				break;
			case 3:
				try {
				if (service.allAllMoviesInDb(movieList)) {
					System.out.println("Data Inserted..!!!!");
				}else {
					System.out.println("Something went wrong...!!!");
				}
				}catch (Exception e) {
					System.out.println("Insert Data First");
				}
				break;
			case 4:
				DateFormat format= new SimpleDateFormat("dd/MM/yyyy");
				System.out.println("Enter Movie ID : ");
				int movieId = Integer.parseInt(sc.nextLine());
				System.out.println("Enter Movie Name : ");
				String movieName = sc.nextLine();
				System.out.println("Enter Movie Category : ");
				Category movieType = Category.valueOf(sc.nextLine().toUpperCase());
				System.out.println("Enter Movie Language : ");
				Language language = Language.valueOf(sc.nextLine().toUpperCase());
				System.out.println("Enter Movie Release Date (DD/MM/YYYY) : ");
				String releaseDate = format.format(format.parse(sc.nextLine()));
				ArrayList<String> casting = new ArrayList<>();
				System.out.println("Enter Movie Cast (Seperate names by '/' ) : ");
				casting.addAll(Arrays.asList(sc.nextLine().split("/")));
				System.out.println("Enter Movie Ratings (between 0 - 10) : ");
				double rating = Double.valueOf(sc.nextLine());
				System.out.println("Enter Movie total Business Done ( In Billions ): ");
				double totalBusinessDone = Double.valueOf(sc.nextLine());
				Movies movie = new Movies(movieId, movieName, movieType, language, releaseDate, casting, rating, totalBusinessDone);
				service.addMovie(movie, movieList);
				break;
			case 5:
				System.out.println("Enter Year : ");
				int year = Integer.parseInt(sc.nextLine());
				List<Movies> movieList1 = service.getMoviesRealeasedInYear(year);
				break;
			case 6:
				check = false;
				System.out.println("Exiting");
				break;
			default:
				break;
			}
		}
		sc.close();
	}

}
