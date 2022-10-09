package database.dao;

import database.connection.ConnectionFactory;
import database.connection.EntidadeConexao;
import database.util.DbUtil;
import model.AlunosModel;
import model.PlanosModel;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlanosDAO extends SistemaDAO {

    private Connection conexao;
    private DbUtil dbUtil = new DbUtil();

    private final String select = "SELECT P.*, M.nome AS nome_modalidade FROM public.planos P INNER JOIN modalidades M ON P.id_modalidade = M.id ORDER BY P.id;";
    private final String insert = "INSERT INTO public.planos(id_modalidade, nome, valor_mensal) VALUES (?, ?, ?);";
    private final String delete = "DELETE FROM public.planos WHERE id = ?;";
    private final String update = "UPDATE public.planos SET id_modalidade = ?, nome = ?, valor_mensal = ? WHERE id = ?;";
    private final String selectById = "SELECT * from public.planos WHERE id = ?;";


    private final PreparedStatement pstSelect;
    private final PreparedStatement pstInsert;
    private final PreparedStatement pstDelete;
    private final PreparedStatement pstUpdate;
    private final PreparedStatement pstSelectById;

    public PlanosDAO() {
        try {
            this.conexao = ConnectionFactory.getConection(new EntidadeConexao());
            pstSelect = this.conexao.prepareStatement(select);
            pstInsert = this.conexao.prepareStatement(insert);
            pstDelete = this.conexao.prepareStatement(delete);
            pstUpdate = this.conexao.prepareStatement(update);
            pstSelectById = this.conexao.prepareStatement(selectById);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Houve um erro ao inicializar os comandos SQL.");
            throw new RuntimeException(e);
        }
    }


    @Override
    public List<Object> select() throws SQLException {
        List <Object> arrayListPlanos = new ArrayList<>();

        try {
            ResultSet resultadoQuery = pstSelect.executeQuery();

            while (resultadoQuery.next()){
                PlanosModel planosModel = new PlanosModel();

                planosModel.setId(resultadoQuery.getInt("id"));
                planosModel.setIdModalidade(resultadoQuery.getInt("id_modalidade"));
                planosModel.setNomeModalidade(resultadoQuery.getString("nome_modalidade"));
                planosModel.setNome(resultadoQuery.getString("nome"));
                planosModel.setValorMensal(resultadoQuery.getDouble("valor_mensal"));

                arrayListPlanos.add(planosModel);
            }

        } catch (SQLException e) {
            System.out.println("Houve um erro ao recuperar os planos!");
            e.printStackTrace();
        } finally {
            dbUtil.fecharConexaoEPrpdStatement(conexao, pstSelect);
        }

        return arrayListPlanos;
    }

    @Override
    public Object selectById(Object param) throws SQLException {
        PlanosModel planosModel = (PlanosModel) param;
        List<PlanosModel> arrayListPlanos = new ArrayList<>();

        pstSelectById.setInt(1, planosModel.getId());

        try {
            ResultSet resultadoQuery = pstSelectById.executeQuery();

            planosModel.setIdModalidade(resultadoQuery.getInt("id_modalidade"));
            planosModel.setNome(resultadoQuery.getString("nome"));
            planosModel.setValorMensal(resultadoQuery.getDouble("valor_mensal"));

            arrayListPlanos.add(planosModel);

        } catch (SQLException e) {
            System.out.println("Houve um erro ao recuperar plano!");
            e.printStackTrace();
        } finally {
            dbUtil.fecharConexaoEPrpdStatement(conexao, pstSelectById);
        }

        return arrayListPlanos.get(0);
    }

    @Override
    public void insert(Object param) throws SQLException {
        PlanosModel planosModel = (PlanosModel) param;

        pstInsert.setInt(1, planosModel.getIdModalidade());
        pstInsert.setString(2, planosModel.getNome());
        pstInsert.setDouble(3, planosModel.getValorMensal());

        try {
            pstInsert.execute();
        } catch (SQLException e) {
            System.out.println("Houve um erro ao inserir plano!");
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Object param) throws SQLException {
        PlanosModel planosModel = (PlanosModel) param;

        pstDelete.setInt(1, planosModel.getId());

        try {
            pstDelete.execute();
        } catch (SQLException e) {
            System.out.println("Houve um erro ao excluir plano!");
            e.printStackTrace();
        }
    }

    @Override
    public void update(Object param) throws SQLException {
        PlanosModel planosModel = (PlanosModel) param;

        pstUpdate.setInt(1, planosModel.getIdModalidade());
        pstUpdate.setString(2, planosModel.getNome());
        pstUpdate.setDouble(3, planosModel.getValorMensal());

        try {
            pstUpdate.execute();
        } catch (SQLException e){
            System.out.println("Houve um erro ao atualizar plano!");
            e.printStackTrace();
        }
    }
}
