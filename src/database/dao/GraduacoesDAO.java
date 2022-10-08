package database.dao;

import database.connection.ConnectionFactory;
import database.connection.EntidadeConexao;
import model.GraduacoesModel;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GraduacoesDAO extends SistemaDAO {

    private Connection conexao;

    private final String select = "SELECT * from public.graduacoes;";
    private final String insert = "INSERT INTO public.graduacoes(modalidade,graduacao)" +
            "VALUES (?,?);";
    private final String delete = "DELETE FROM public.graduacoes WHERE id = ?;";
    private final String update = "UPDATE public.graduacoes SET modalidade = ?, graduacao = ? WHERE id = ?;";
    private final String selectById = "SELECT * from public.graduacoes WHERE id = ?;";


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
        ResultSet resultadoQuery = pstSelect.executeQuery();
        List <Object> arrayListGraduacoes = new ArrayList<>();

        while (resultadoQuery.next()){
            GraduacoesModel graduacoesModel = new GraduacoesModel();

            graduacoesModel.setId(resultadoQuery.getInt("id"));
            graduacoesModel.setModalidade(resultadoQuery.getString("modalidade"));
            graduacoesModel.setGraduacao(resultadoQuery.getString("graduacao"));

            arrayListGraduacoes.add(graduacoesModel);
        }

        return arrayListGraduacoes;
    }

    @Override
    public Object selectById(Object param) throws SQLException {
        GraduacoesModel graduacoesModel = (GraduacoesModel) param;
        pstSelectById.setInt(1, graduacoesModel.getId());

        try {
            ResultSet resultadoQuery = pstSelectById.executeQuery();
            graduacoesModel.setModalidade(resultadoQuery.getString("modalidade"));
            graduacoesModel.setGraduacao(resultadoQuery.getString("graduacao"));
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ID não encontrado!");
            e.printStackTrace();
        }
        return graduacoesModel;
    }

    @Override
    public void insert(Object param) throws SQLException {
        GraduacoesModel graduacoesModel = (GraduacoesModel) param;

        pstInsert.setString(1, graduacoesModel.getModalidade());
        pstInsert.setString(2, graduacoesModel.getGraduacao());

        try {
            pstInsert.execute();
        } catch (SQLException e) {
            System.out.println("Houve um erro ao inserir graduação!");
            e.printStackTrace();
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
        }
    }

    @Override
    public void update(Object param) throws SQLException {
        GraduacoesModel graduacoesModel = (GraduacoesModel) param;

        pstUpdate.setString(1,graduacoesModel.getModalidade());
        pstUpdate.setString(2,graduacoesModel.getGraduacao());

        try {
            pstUpdate.execute();
        } catch (SQLException e){
            System.out.println("Houve um erro ao atualizar plano!");
            e.printStackTrace();
        }
    }
}
