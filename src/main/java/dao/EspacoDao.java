package dao;

//Bibliotecas SQL
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

//Classes do projeto
import connection.SingleConnection;
import model.EspacoModel;

public class EspacoDao {
    private Connection connection_BD;

    //Método construtor
    public EspacoDao() {
        connection_BD = SingleConnection.getConnection();
    }

    //Método Create
    public void CreateEspaco(EspacoModel espaco) {
        String sql = "insert into espaco (nome, numero, IdtipoEspacoFK, metragem) VALUES (?,?,?,?)";

        try (PreparedStatement insert = connection_BD.prepareStatement(sql)){
            insert.setString(1, espaco.getNome());
            insert.setString(2, espaco.getNumero());
            insert.setLong(3, espaco.getIdTipoEspacoFK());
            insert.setDouble(4, espaco.getMetragem());
            insert.execute();
            connection_BD.commit();

            System.out.println("Cadastro de espaço efetuado com sucesso");
        } catch (Exception e) {
            try {
                System.out.println("Cadastro não realizado, tente novamente");
                connection_BD.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            System.out.println("Erro, informações não salvas: " + e.getMessage());
            e.printStackTrace();
        }
    }

    //Método List
    public List<EspacoModel> listar() throws SQLException {
        List<EspacoModel> list = new ArrayList<>();
        String sql = "SELECT * FROM espaco";

        try (PreparedStatement listagem = connection_BD.prepareStatement(sql)) {
            ResultSet resultado = listagem.executeQuery();
            while (resultado.next()) {
                EspacoModel model = new EspacoModel();
                model.setIdEspaco(resultado.getLong("idEspaco"));
                model.setNome(resultado.getString("nome"));
                model.setNumero(resultado.getString("numero"));
                model.setIdTipoEspacoFK(resultado.getLong("IdtipoEspacoFK"));
                model.setMetragem(resultado.getDouble("metragem"));
                list.add(model);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

//Método FindtById
    public EspacoModel FindById(long id) throws SQLException {
        EspacoModel espaco = null;
        String sql = "SELECT * FROM espaco WHERE idEspaco = ?";
        try (PreparedStatement select = connection_BD.prepareStatement(sql)) {
            select.setLong(1, id);
            try (ResultSet resultado = select.executeQuery()) {
                if (resultado.next()) {
                    espaco = new EspacoModel();
                    espaco.setIdEspaco(resultado.getLong("idEspaco"));
                    espaco.setNome(resultado.getString("nome"));
                    espaco.setNumero(resultado.getString("numero"));
                    espaco.setIdTipoEspacoFK(resultado.getLong("IdtipoEspacoFK"));
                    espaco.setMetragem(resultado.getDouble("metragem"));
                }
            }
        }
        return espaco;
    }

    //Método Update
    public void updateEspaco(EspacoModel espaco) throws SQLException {
        String sql = "UPDATE espaco SET nome = ?, numero = ?, IdtipoEspacoFK = ?, metragem = ? WHERE idEspaco = ?";

        try (PreparedStatement update = connection_BD.prepareStatement(sql)) {
            update.setString(1, espaco.getNome());
            update.setString(2, espaco.getNumero());
            update.setLong(3, espaco.getIdTipoEspacoFK());
            update.setDouble(4, espaco.getMetragem());
            update.setLong(5, espaco.getIdEspaco());

            update.execute();
            connection_BD.commit();
        } catch (SQLException e) {
            connection_BD.rollback();
            throw e;
        }
    }

    //Método Delete
    public void Delete(Long id) throws SQLException {
        String sql = "DELETE from espaco WHERE idEspaco = ?";
        try (PreparedStatement delete = connection_BD.prepareStatement(sql)) {
            delete.setLong(1, id);
            delete.execute();
            connection_BD.commit();
        }catch (Exception e){
            connection_BD.rollback();
            e.printStackTrace();
        }
    }
}

