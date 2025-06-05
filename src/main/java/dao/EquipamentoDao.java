package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import connection.SingleConnection;
import model.EquipamentoModel;

public class EquipamentoDao {
    private Connection connection_BD;

    public EquipamentoDao() {
        connection_BD = SingleConnection.getConnection();
    }

    // Método Create
    public void create(EquipamentoModel equipamento) throws SQLException {
        String sql = "INSERT INTO equipamento (nome_Equipamento) VALUES (?)";

        try (PreparedStatement statement = connection_BD.prepareStatement(sql)) {
            statement.setString(1, equipamento.getNomeEquipamento());
            statement.execute();
            connection_BD.commit();
        } catch (SQLException e) {
            connection_BD.rollback();
            throw e;
        }
    }

    // Método List
    public List<EquipamentoModel> readAll() throws SQLException {
        List<EquipamentoModel> equipamentos = new ArrayList<>();
        String sql = "SELECT * FROM equipamento";

        try (PreparedStatement statement = connection_BD.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                EquipamentoModel equipamento = new EquipamentoModel();
                equipamento.setIdEquipamento(resultSet.getLong("idEquipamento"));
                equipamento.setNomeEquipamento(resultSet.getString("nome_Equipamento"));
                equipamentos.add(equipamento);
            }
        }

        return equipamentos;
    }

    // Método FindById
    public EquipamentoModel readById(long id) throws SQLException {
        EquipamentoModel equipamento = null;
        String sql = "SELECT * FROM equipamento WHERE idEquipamento = ?";

        try (PreparedStatement statement = connection_BD.prepareStatement(sql)) {
            statement.setLong(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    equipamento = new EquipamentoModel();
                    equipamento.setIdEquipamento(resultSet.getLong("idEquipamento"));
                    equipamento.setNomeEquipamento(resultSet.getString("nome_Equipamento"));
                }
            }
        }

        return equipamento;
    }

    // Método Update
    public void update(EquipamentoModel equipamento) throws SQLException {
        String sql = "UPDATE equipamento SET nome_Equipamento = ? WHERE idEquipamento = ?";

        try (PreparedStatement statement = connection_BD.prepareStatement(sql)) {
            statement.setString(1, equipamento.getNomeEquipamento());
            statement.setLong(2, equipamento.getIdEquipamento());
            statement.execute();
            connection_BD.commit();
        } catch (SQLException e) {
            connection_BD.rollback();
            throw e;
        }
    }

    // Método delete
    public void delete(long id) throws SQLException {
        String sql = "DELETE FROM equipamento WHERE idEquipamento = ?";

        try (PreparedStatement statement = connection_BD.prepareStatement(sql)) {
            statement.setLong(1, id);
            statement.execute();
            connection_BD.commit();
        } catch (SQLException e) {
            connection_BD.rollback();
            throw e;
        }
    }
}