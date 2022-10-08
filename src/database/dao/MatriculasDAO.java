package database.dao;

import database.connection.ConnectionFactory;
import database.connection.EntidadeConexao;
import model.MatriculasModel;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MatriculasDAO extends SistemaDAO {
    private Connection conexao;

    private final String select = "SELECT * from public.matriculas;";
    private final String insert = "INSERT INTO public.matriculas(codigo_matricula,codigo_aluno,data_matricula,dia_vencimento,data_encerramento)" +
            "VALUES (?,?,?,?,?);";
    private final String delete = "DELETE FROM public.matriculas WHERE id = ?;";
    private final String update = "UPDATE public.matriculas SET codigo_matricula = ?, codigo_aluno = ?, data_matricula = ?, dia_vencimento = ? data_encerramento = ? " +
            "WHERE id = ?;";
    private final String selectById = "SELECT * from public.matriculas WHERE id = ?;";


    private final PreparedStatement pstSelect;
    private final PreparedStatement pstInsert;
    private final PreparedStatement pstDelete;
    private final PreparedStatement pstUpdate;
    private final PreparedStatement pstSelectById;


    public MatriculasDAO() {
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
        List <Object> arrayListMatriculas = new ArrayList<>();

        while (resultadoQuery.next()){
            MatriculasModel matriculasModel = new MatriculasModel();

            matriculasModel.setCodigoMatricula(resultadoQuery.getInt("codigo_matricula"));
            matriculasModel.setCodigoAluno(resultadoQuery.getInt("codigo_aluno"));
            matriculasModel.setDataMatricula(resultadoQuery.getDate("data_matricula"));
            matriculasModel.setDiaVencimento(resultadoQuery.getInt("dia_vencimento"));
            matriculasModel.setDataEncerramento(resultadoQuery.getDate("data_encerramento"));

            arrayListMatriculas.add(matriculasModel);
        }

        return arrayListMatriculas;
    }

    @Override
    public Object selectById(Object param) throws SQLException {
        MatriculasModel matriculasModel = (MatriculasModel) param;
        pstSelectById.setInt(1, matriculasModel.getId());

        try {
            ResultSet resultadoQuery = pstSelectById.executeQuery();
            matriculasModel.setCodigoMatricula(resultadoQuery.getInt("codigo_matricula"));
            matriculasModel.setCodigoAluno(resultadoQuery.getInt("codigo_aluno"));
            matriculasModel.setDataMatricula(resultadoQuery.getDate("data_matricula"));
            matriculasModel.setDiaVencimento(resultadoQuery.getInt("dia_vencimento"));
            matriculasModel.setDataEncerramento(resultadoQuery.getDate("data_encerramento"));
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ID não encontrado!");
            e.printStackTrace();
        }
        return matriculasModel;
    }

    @Override
    public void insert(Object param) throws SQLException {
        MatriculasModel matriculasModel = (MatriculasModel) param;

        pstInsert.setInt(1, matriculasModel.getCodigoMatricula());
        pstInsert.setInt(2, matriculasModel.getCodigoAluno());
        pstInsert.setDate(3, (Date) matriculasModel.getDataMatricula());
        pstInsert.setInt(4, matriculasModel.getDiaVencimento());
        pstInsert.setDate(5, (Date) matriculasModel.getDataEncerramento());

        try {
            pstInsert.execute();
        } catch (SQLException e) {
            System.out.println("Houve um erro ao inserir matrícula!");
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Object param) throws SQLException {
        MatriculasModel matriculasModel = (MatriculasModel) param;

        pstDelete.setInt(1,matriculasModel.getId());

        try {
            pstDelete.execute();
        } catch (SQLException e) {
            System.out.println("Houve um erro ao excluir matrícula!");
            e.printStackTrace();
        }
    }

    @Override
    public void update(Object param) throws SQLException {
        MatriculasModel matriculasModel = (MatriculasModel) param;

        pstUpdate.setInt(1, matriculasModel.getCodigoMatricula());
        pstUpdate.setInt(2, matriculasModel.getCodigoAluno());
        pstUpdate.setDate(3, (Date) matriculasModel.getDataMatricula());
        pstUpdate.setInt(4, matriculasModel.getDiaVencimento());
        pstUpdate.setDate(5, (Date) matriculasModel.getDataEncerramento());

        try {
            pstUpdate.execute();
        } catch (SQLException e){
            System.out.println("Houve um erro ao atualizar matrícula!");
            e.printStackTrace();
        }
    }
}