import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import dao.CargoDao;
import model.CargoModel;

public class TesteDao {
	public static void main(String[] args) throws ParseException {
		
		CargoModel cargo = new CargoModel();
		cargo.setNomeCargo("GESTOR");
		
		CargoDao cargoDao = new CargoDao();
		cargoDao.salvar(cargo);
		
		List<CargoModel> list = cargoDao.listarTodos();
		for (CargoModel cargoModel : list) {
			System.out.println(cargoModel);
			System.out.println();
		}
	
    
	}
}
