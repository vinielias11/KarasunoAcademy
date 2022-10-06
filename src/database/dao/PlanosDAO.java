package database.dao;

import database.connection.ConnectionFactory;
import database.connection.EntidadeConexao;
import model.PlanosModel;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlanosDAO extends SistemaDAO {

    private Connection conexao;

    private final String select = "SELECT * from public.planos;";
    private final String insert = "INSERT INTO public.planos(modalidade,plano,valor_mensal)" +
            "VALUES (?,?,?);";
    private final String delete = "DELETE FROM public.planos WHERE id = ?;";
    private final String update = "UPDATE public.planos SET modalidade = ?, plano = ?, valor_mensal = ? WHERE id = ?;";
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
        ResultSet resultadoQuery = pstSelect.executeQuery();
        List <Object> arrayListPlanos = new ArrayList<>();

        while (resultadoQuery.next()){
            PlanosModel planosModel = new PlanosModel();

            planosModel.setId(resultadoQuery.getInt("id"));
            planosModel.setModalidade(resultadoQuery.getString("modalidade"));
            planosModel.setPlano(resultadoQuery.getString("plano"));
            planosModel.setValorMensal(resultadoQuery.getDouble("valor_mensal"));

            arrayListPlanos.add(planosModel);
        }

        return arrayListPlanos;
    }

    @Override
    public Object selectById(Object param) throws SQLException {
        PlanosModel planosModel = (PlanosModel) param;
        pstSelectById.setInt(1, planosModel.getId());

        try {
            ResultSet resultadoQuery = pstSelectById.executeQuery();
            planosModel.setModalidade(resultadoQuery.getString("modalidade"));
            planosModel.setPlano(resultadoQuery.getString("plano"));
            planosModel.setValorMensal(resultadoQuery.getDouble("valor_mensal"));
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ID não encontrado!");
            e.printStackTrace();
        }
        return planosModel;
    }

    @Override
    public void insert(Object param) throws SQLException {
        PlanosModel planosModel = (PlanosModel) param;

        pstInsert.setString(1, planosModel.getModalidade());
        pstInsert.setString(2, planosModel.getPlano());
        pstInsert.setDouble(3,planosModel.getValorMensal());

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

        pstDelete.setInt(1,planosModel.getId());

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

        pstUpdate.setString(1,planosModel.getModalidade());
        pstUpdate.setString(2,planosModel.getPlano());
        pstUpdate.setDouble(3,planosModel.getValorMensal());

        try {
            pstUpdate.execute();
        } catch (SQLException e){
            System.out.println("Houve um erro ao atualizar plano!");
            e.printStackTrace();
        }
    }
}
