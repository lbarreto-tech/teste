import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import dao.CargoDao;
import dao.EquipamentoDao;
import model.CargoModel;
public class TesteDao{
public static void main(String[] args) throws SQLException{

		EquipamentoDao equipamentoDao = new EquipamentoDao();

		System.out.println(equipamentoDao.readAll());

}
	}