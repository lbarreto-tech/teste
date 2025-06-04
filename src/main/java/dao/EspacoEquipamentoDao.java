package dao;

//Bibliotecas SQL
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

//Classes do projeto
import connection.SingleConnection;
import model.EspacoEquipamendoModel;

public class EspacoEquipamentoDao {
    private Connection connection_BD;

    public EspacoEquipamentoDao(){
        connection_BD = SingleConnection.getConnection();
    }

    public void associarEquipamento (EspacoEquipamendoModel associacao) throws SQLException{
        String sql = "INSERT INTO espaco_equipamento VALUES (?, ?)";
        try (PreparedStatement associar = connection_BD.prepareStatement(sql)){
            associar.setLong (1, associacao.getIdEquipamendoFk());
            associar.setLong(2, associacao.getIdEspacoFk());
            associar.execute();
            connection_BD.commit();
        } catch (SQLException e) {
            connection_BD.rollback();
            throw e;
        } catch (Exception e) {
            connection_BD.rollback();
            e.printStackTrace();
        }
    }
    public void desassociarEquipamento(Long idEquipamento, Long idEspaco) throws SQLException {
        String sql = "DELETE FROM espaco_equipamento WHERE idEquipamentoFk = ? AND idEspacoFk = ?";

        try (PreparedStatement desassociar = connection_BD.prepareStatement(sql)) {
            desassociar.setLong(1, idEquipamento);
            desassociar.setLong(2, idEspaco);
            desassociar.execute();
            connection_BD.commit();
        } catch (SQLException e) {
            connection_BD.rollback();
            throw e;
        }
    }
}
