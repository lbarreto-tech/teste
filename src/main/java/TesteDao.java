import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import dao.CargoDao;
import dao.EmailDao;
import model.CargoModel;
import model.EmailModel;

public class TesteDao {
	public static void main(String[] args) throws ParseException {
		
		CargoDao cargoDao = new CargoDao();
		System.out.println(cargoDao.verificaCargo("ana@empresa.com", "senha123"));

		EmailDao dao = new EmailDao();
		List<EmailModel> list3 = dao.listarTodos();
		for (EmailModel emailModel : list3) {
			System.out.println(emailModel);
		}
    
	}
}
