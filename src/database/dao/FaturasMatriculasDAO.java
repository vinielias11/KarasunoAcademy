package database.dao;

import database.connection.ConnectionFactory;
import database.connection.EntidadeConexao;
import model.FaturasMatriculasModel;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FaturasMatriculasDAO extends SistemaDAO {

    private Connection conexao;

    private final String select = "SELECT * from public.faturas_matriculas;";
    private final String insert = "INSERT INTO public.faturas_matriculas(codigo_matricula,data_vencimento,valor,data_pagamento,data_cancelamento)" +
            "VALUES (?,?,?,?,?);";
    private final String delete = "DELETE FROM public.faturas_matriculas WHERE id = ?;";
    private final String update = "UPDATE public.faturas_matriculas SET codigo_matricula = ?, data_vencimento = ?, valor = ?, data_pagamento = ? data_cancelamento = ? " +
            "WHERE id = ?;";
    private final String selectById = "SELECT * from public.faturas_matriculas WHERE id = ?;";


    private final PreparedStatement pstSelect;
    private final PreparedStatement pstInsert;
    private final PreparedStatement pstDelete;
    private final PreparedStatement pstUpdate;
    private final PreparedStatement pstSelectById;


    public FaturasMatriculasDAO() {
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
        List <Object> arrayListFaturasMatriculas = new ArrayList<>();

        while (resultadoQuery.next()){
            FaturasMatriculasModel faturasMatriculasModel = new FaturasMatriculasModel();

            faturasMatriculasModel.setCodigoMatricula(resultadoQuery.getInt("codigo_matricula"));
            faturasMatriculasModel.setDataVencimento(resultadoQuery.getDate("data_vencimento"));
            faturasMatriculasModel.setValor(resultadoQuery.getDouble("valor"));
            faturasMatriculasModel.setDataPagamento(resultadoQuery.getTimestamp("data_pagamento"));
            faturasMatriculasModel.setDataCancelamento(resultadoQuery.getDate("data_cancelamento"));

            arrayListFaturasMatriculas.add(faturasMatriculasModel);
        }

        return arrayListFaturasMatriculas;
    }

    @Override
    public Object selectById(Object param) throws SQLException {
        FaturasMatriculasModel faturasMatriculasModel = (FaturasMatriculasModel) param;
        pstSelectById.setInt(1, faturasMatriculasModel.getId());

        try {
            ResultSet resultadoQuery = pstSelectById.executeQuery();
            faturasMatriculasModel.setCodigoMatricula(resultadoQuery.getInt("codigo_matricula"));
            faturasMatriculasModel.setDataVencimento(resultadoQuery.getDate("data_vencimento"));
            faturasMatriculasModel.setValor(resultadoQuery.getDouble("valor"));
            faturasMatriculasModel.setDataPagamento(resultadoQuery.getTimestamp("data_pagamento"));
            faturasMatriculasModel.setDataCancelamento(resultadoQuery.getDate("data_cancelamento"));
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ID não encontrado!");
            e.printStackTrace();
        }
        return faturasMatriculasModel;
    }

    @Override
    public void insert(Object param) throws SQLException {
        FaturasMatriculasModel faturasMatriculasModel = (FaturasMatriculasModel) param;

        pstInsert.setInt(1, faturasMatriculasModel.getCodigoMatricula());
        pstInsert.setDate(2, (Date) faturasMatriculasModel.getDataVencimento());
        pstInsert.setDouble(3, faturasMatriculasModel.getValor());
        pstInsert.setDate(4, faturasMatriculasModel.getDataPagamento());
        pstInsert.setDate(5, (Date) faturasMatriculasModel.getDataCancelamento());

        try {
            pstInsert.execute();
        } catch (SQLException e) {
            System.out.println("Houve um erro ao inserir fatura!");
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Object param) throws SQLException {
        FaturasMatriculasModel faturasMatriculasModel = (FaturasMatriculasModel) param;

        pstDelete.setInt(1,faturasMatriculasModel.getId());

        try {
            pstDelete.execute();
        } catch (SQLException e) {
            System.out.println("Houve um erro ao excluir fatura!");
            e.printStackTrace();
        }
    }

    @Override
    public void update(Object param) throws SQLException {
        FaturasMatriculasModel faturasMatriculasModel = (FaturasMatriculasModel) param;

        pstUpdate.setInt(1,faturasMatriculasModel.getCodigoMatricula());
        pstUpdate.setDate(2, (Date) faturasMatriculasModel.getDataVencimento());
        pstUpdate.setDouble(3, faturasMatriculasModel.getValor());
        pstUpdate.setDate(4, faturasMatriculasModel.getDataPagamento());
        pstUpdate.setDate(5, (Date) faturasMatriculasModel.getDataCancelamento());

        try {
            pstUpdate.execute();
        } catch (SQLException e){
            System.out.println("Houve um erro ao atualizar fatura!");
            e.printStackTrace();
        }
    }
}
