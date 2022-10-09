package database.dao;

import database.connection.ConnectionFactory;
import database.connection.EntidadeConexao;
import database.util.DbUtil;
import model.GraduacoesModel;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GraduacoesDAO extends SistemaDAO {

    private Connection conexao;
    private DbUtil dbUtil = new DbUtil();

    private final String select = "SELECT G.*, M.nome AS nome_modalidade FROM graduacoes G INNER JOIN modalidades M ON G.id_modalidade = M.id ORDER BY G.id;";
    private final String insert = "INSERT INTO public.graduacoes(id_modalidade,nome)" +
            "VALUES (?,?);";
    private final String delete = "DELETE FROM public.graduacoes WHERE id = ?;";
    private final String update = "UPDATE public.graduacoes SET id_modalidade = ?, nome = ? WHERE id = ?;";
    private final String selectById = "SELECT G.*, M.nome AS nome_modalidade FROM graduacoes G INNER JOIN modalidades M ON G.id_modalidade = M.id WHERE G.id = ? ORDER BY G.id;";


    private final PreparedStatement pstSelect;
    private final PreparedStatement pstInsert;
    private final PreparedStatement pstDelete;
    private final PreparedStatement pstUpdate;
    private final PreparedStatement pstSelectById;


    public GraduacoesDAO() {
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
        List <Object> arrayListGraduacoes = new ArrayList<>();

        try {
            ResultSet resultadoQuery = pstSelect.executeQuery();
            while (resultadoQuery.next()){
                GraduacoesModel graduacoesModel = new GraduacoesModel();

                graduacoesModel.setId(resultadoQuery.getInt("id"));
                graduacoesModel.setIdModalidade(resultadoQuery.getInt("id_modalidade"));
                graduacoesModel.setNome(resultadoQuery.getString("nome"));
                graduacoesModel.setNomeModalidade(resultadoQuery.getString("nome_modalidade"));

                arrayListGraduacoes.add(graduacoesModel);
            }
        }catch (SQLException e){
            System.out.println("Houve um erro ao recuperar as graduações!");
            e.printStackTrace();
        } finally {
            dbUtil.fecharConexaoEPrpdStatement(conexao, pstSelect);
        }

        return arrayListGraduacoes;
    }

    @Override
    public GraduacoesModel selectById(Object param) throws SQLException {
        GraduacoesModel graduacoesModel = (GraduacoesModel) param;
        pstSelectById.setInt(1, graduacoesModel.getId());
        List <Object> arrayListGraduacoes = new ArrayList<>();

        try {
            ResultSet resultadoQuery = pstSelect.executeQuery();
            while (resultadoQuery.next()){
                graduacoesModel.setId(resultadoQuery.getInt("id"));
                graduacoesModel.setIdModalidade(resultadoQuery.getInt("id_modalidade"));
                graduacoesModel.setNome(resultadoQuery.getString("nome"));
                graduacoesModel.setNomeModalidade(resultadoQuery.getString("nome_modalidade"));

                arrayListGraduacoes.add(graduacoesModel);
            }
        } catch (SQLException e) {
            System.out.println("Houve um erro ao selecionar o id!");
            e.printStackTrace();
        } finally {
            dbUtil.fecharConexaoEPrpdStatement(conexao, pstInsert);
        }

        return graduacoesModel;
    }

    @Override
    public void insert(Object param) throws SQLException {
        GraduacoesModel graduacoesModel = (GraduacoesModel) param;

        pstInsert.setInt(1, graduacoesModel.getIdModalidade());
        pstInsert.setString(2, graduacoesModel.getNome());

        try {
            pstInsert.execute();
        } catch (SQLException e) {
            System.out.println("Houve um erro ao inserir graduação!");
            e.printStackTrace();
        } finally {
            dbUtil.fecharConexaoEPrpdStatement(conexao, pstInsert);
        }
    }

    @Override
    public void delete(Object param) throws SQLException {
        GraduacoesModel graduacoesModel = (GraduacoesModel) param;

        pstDelete.setInt(1,graduacoesModel.getId());

        try {
            pstDelete.execute();
        } catch (SQLException e) {
            System.out.println("Houve um erro ao excluir graduação!");
            e.printStackTrace();
        }finally {
            dbUtil.fecharConexaoEPrpdStatement(conexao, pstInsert);
        }
    }

    @Override
    public void update(Object param) throws SQLException {
        GraduacoesModel graduacoesModel = (GraduacoesModel) param;

        pstUpdate.setInt(1,graduacoesModel.getIdModalidade());
        pstUpdate.setString(2,graduacoesModel.getNome());
        pstUpdate.setInt(3, graduacoesModel.getId());

        try {
            pstUpdate.execute();
        } catch (SQLException e){
            System.out.println("Houve um erro ao atualizar a graduação!");
            e.printStackTrace();
        }finally {
            dbUtil.fecharConexaoEPrpdStatement(conexao, pstInsert);
        }
    }

}
