package database.dao;

import database.connection.ConnectionFactory;
import database.connection.EntidadeConexao;
import database.util.DbUtil;
import model.MatriculasModel;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MatriculasDAO extends SistemaDAO {
    private Connection conexao;
    private DbUtil dbUtil = new DbUtil();

    private final String select = "SELECT M.*, A.nome AS nome_aluno FROM public.matriculas M INNER JOIN alunos A ON M.id_aluno = A.id ORDER BY M.codigo_matricula;";;
    private final String insert = "INSERT INTO public.matriculas(id_aluno,dia_vencimento,data_encerramento)" +
            "VALUES (?,?,?);";
    private final String delete = "DELETE FROM public.matriculas WHERE codigo_matricula = ?;";
    private final String update = "UPDATE public.matriculas SET id_aluno = ?, dia_vencimento = ? WHERE codigo_matricula = ?";
    private final String selectById = "SELECT M.*, A.nome AS nome_aluno FROM public.matriculas M INNER JOIN alunos A ON M.id_aluno = A.id WHERE codigo_matricula = ? ORDER BY M.codigo_matricula;";
    private final String encerrarMatricula = "UPDATE public.matriculas SET data_encerramento = ? WHERE codigo_matricula = ?";

    private final PreparedStatement pstSelect;
    private final PreparedStatement pstInsert;
    private final PreparedStatement pstDelete;
    private final PreparedStatement pstUpdate;
    private final PreparedStatement pstSelectById;
    private final PreparedStatement pstEncerrarMatricula;


    public MatriculasDAO() {
        try {
            this.conexao = ConnectionFactory.getConection(new EntidadeConexao());
            pstSelect = this.conexao.prepareStatement(select);
            pstInsert = this.conexao.prepareStatement(insert);
            pstDelete = this.conexao.prepareStatement(delete);
            pstUpdate = this.conexao.prepareStatement(update);
            pstSelectById = this.conexao.prepareStatement(selectById);
            pstEncerrarMatricula = this.conexao.prepareStatement(encerrarMatricula);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Houve um erro ao inicializar os comandos SQL.");
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Object> select() throws SQLException {
        List <Object> arrayListMatriculas = new ArrayList<>();

        try {
            ResultSet resultadoQuery = pstSelect.executeQuery();
            while (resultadoQuery.next()){
                MatriculasModel matriculasModel = new MatriculasModel();

                matriculasModel.setCodigoMatricula(resultadoQuery.getInt("codigo_matricula"));
                matriculasModel.setCodigoAluno(resultadoQuery.getInt("id_aluno"));
                matriculasModel.setDataMatricula(resultadoQuery.getDate("data_matricula"));
                matriculasModel.setDiaVencimento(resultadoQuery.getInt("dia_vencimento"));
                matriculasModel.setDataEncerramento(resultadoQuery.getDate("data_encerramento"));
                matriculasModel.setNomeAluno(resultadoQuery.getString("nome_aluno"));

                arrayListMatriculas.add(matriculasModel);
            }
        } catch (SQLException e){
            System.out.println("Houve um erro ao recuperar as matriculas!");
        }
        finally {
            dbUtil.fecharConexaoEPrpdStatement(conexao, pstSelect);
        }


        return arrayListMatriculas;
    }

    @Override
    public MatriculasModel selectById(Object param) throws SQLException {
        MatriculasModel matriculasModel = (MatriculasModel) param;
        List<MatriculasModel> arrayListMatriculas = new ArrayList<>();

        pstSelectById.setInt(1, matriculasModel.getCodigoMatricula());

        try {
            ResultSet resultadoQuery = pstSelectById.executeQuery();

            while(resultadoQuery.next()){
                matriculasModel.setCodigoMatricula(resultadoQuery.getInt("codigo_matricula"));
                matriculasModel.setCodigoAluno(resultadoQuery.getInt("id_aluno"));
                matriculasModel.setDataMatricula(resultadoQuery.getDate("data_matricula"));
                matriculasModel.setDiaVencimento(resultadoQuery.getInt("dia_vencimento"));
                matriculasModel.setDataEncerramento(resultadoQuery.getDate("data_encerramento"));
                matriculasModel.setNomeAluno(resultadoQuery.getString("nome_aluno"));

                arrayListMatriculas.add(matriculasModel);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ID não encontrado!");
            e.printStackTrace();
        }finally {
            dbUtil.fecharConexaoEPrpdStatement(conexao, pstSelect);
        }
        return matriculasModel;
    }

    @Override
    public void insert(Object param) throws SQLException {
        MatriculasModel matriculasModel = (MatriculasModel) param;

        pstInsert.setInt(1, matriculasModel.getCodigoAluno());;
        pstInsert.setInt(2, matriculasModel.getDiaVencimento());
        pstInsert.setDate(3, (Date) matriculasModel.getDataEncerramento());

        try {
            pstInsert.execute();
        } catch (SQLException e) {
            System.out.println("Houve um erro ao inserir a matrícula!");
            e.printStackTrace();
        }finally {
            dbUtil.fecharConexaoEPrpdStatement(conexao, pstSelect);
        }
    }

    @Override
    public void delete(Object param) throws SQLException {
        MatriculasModel matriculasModel = (MatriculasModel) param;

        pstDelete.setInt(1,matriculasModel.getCodigoMatricula());

        try {
            pstDelete.execute();
        } catch (SQLException e) {
            System.out.println("Houve um erro ao excluir matrícula!");
            e.printStackTrace();
        }finally {
            dbUtil.fecharConexaoEPrpdStatement(conexao, pstSelect);
        }
    }

    @Override
    public void update(Object param) throws SQLException {
        MatriculasModel matriculasModel = (MatriculasModel) param;


        pstUpdate.setInt(1, matriculasModel.getCodigoAluno());
        pstUpdate.setInt(2, matriculasModel.getDiaVencimento());
        pstUpdate.setInt(3, matriculasModel.getCodigoMatricula());

        try {
            pstUpdate.execute();
        } catch (SQLException e){
            System.out.println("Houve um erro ao atualizar matrícula!");
            e.printStackTrace();
        }finally {
            dbUtil.fecharConexaoEPrpdStatement(conexao, pstSelect);
        }
    }

    public void encerrarMatricula(Object param) throws SQLException{
        MatriculasModel matriculasModel = (MatriculasModel) param;

        java.util.Date utilDate = matriculasModel.getDataEncerramento();
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());

        pstEncerrarMatricula.setDate(1, sqlDate);
        pstEncerrarMatricula.setInt(2, matriculasModel.getCodigoMatricula());

        try {
            pstEncerrarMatricula.execute();
        } catch (SQLException e){
            System.out.println("Houve um erro ao atualizar matrícula!");
            e.printStackTrace();
        }finally {
            dbUtil.fecharConexaoEPrpdStatement(conexao, pstEncerrarMatricula);
        }
    }
}
