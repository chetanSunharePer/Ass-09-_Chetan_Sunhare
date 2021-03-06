import java.io.File;
import java.text.ParseException;

import com.psl.training.assignment.service.MovieService;

public class MovieTester {

	public static void main(String[] args) throws ParseException {
		// TODO Auto-generated method stub
		MovieService service = new MovieService();
		service.populateMovies(new File("D:\\java\\ELTP_Training\\Ass-09-_Chetan_Sunhare\\Ass(09)_Chetan_Sunhare\\src\\Movies.txt"));
		service.Display();
	}

}
