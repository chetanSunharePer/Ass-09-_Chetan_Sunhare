import java.io.File;
import java.text.ParseException;
import java.util.Scanner;
import java.util.List;

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
					+ "4. Fetch data from databse and display\n"
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
