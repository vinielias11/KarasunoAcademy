package database.dao;

import database.connection.ConnectionFactory;
import database.connection.EntidadeConexao;
import database.util.DbUtil;
import model.MatriculasModalidadesModel;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MatriculasModalidadesDAO extends SistemaDAO {
    private Connection conexao;
    private DbUtil dbUtil = new DbUtil();

    private final String select = "SELECT * FROM public.matriculas_modalidades;";
    private final String selectFromMatricula = "SELECT * FROM public.matriculas_modalidades WHERE codigo_matricula = ?;";
    private final String insert = "INSERT INTO public.matriculas_modalidades(nome) VALUES (?);";
    private final String delete = "DELETE FROM public.matriculas_modalidades WHERE id = ?;";
    private final String update = "UPDATE public.matriculas_modalidades SET nome = ? WHERE id = ?;";
    private final String selectById = "SELECT * from public.matriculas_modalidades WHERE id = ?;";

    private final PreparedStatement pstSelect;
    private final PreparedStatement pstInsert;
    private final PreparedStatement pstDelete;
    private final PreparedStatement pstUpdate;
    private final PreparedStatement pstSelectById;
    private final PreparedStatement pstSelectFromMatricula;


    public MatriculasModalidadesDAO(){
        try {
            this.conexao = ConnectionFactory.getConection(new EntidadeConexao());
            pstSelect = this.conexao.prepareStatement(select);
            pstInsert = this.conexao.prepareStatement(insert);
            pstDelete = this.conexao.prepareStatement(delete);
            pstUpdate = this.conexao.prepareStatement(update);
            pstSelectById = this.conexao.prepareStatement(selectById);
            pstSelectFromMatricula = this.conexao.prepareStatement(selectFromMatricula);
        } catch (SQLException e){
            JOptionPane.showMessageDialog(null, "Houve um erro ao inicializar os comandos SQL.");
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Object> select() throws SQLException {
        List<Object> arrayListMatriculasModalidades = new ArrayList<>();

        try {
            ResultSet resultadoQuery = pstSelect.executeQuery();
            while (resultadoQuery.next()){
                MatriculasModalidadesModel matriculasModalidadesModel = new MatriculasModalidadesModel();

                matriculasModalidadesModel.setCodigoMatricula(resultadoQuery.getInt("codigo_matricula"));
                matriculasModalidadesModel.setModalidade(resultadoQuery.getInt("id_modalidade"));
                matriculasModalidadesModel.setGraduacao(resultadoQuery.getInt("id_graduacao"));
                matriculasModalidadesModel.setPlano(resultadoQuery.getInt("id_plano"));
                matriculasModalidadesModel.setDataInicio(resultadoQuery.getDate("data_inicio"));
                matriculasModalidadesModel.setDataFim(resultadoQuery.getDate("data_fim"));

                arrayListMatriculasModalidades.add(matriculasModalidadesModel);
            }


        } catch (SQLException e) {
            System.out.println("Houve um erro ao recuperar as matrículas!");
            e.printStackTrace();
        } finally {
            dbUtil.fecharConexaoEPrpdStatement(conexao, pstSelect);
        }

        return arrayListMatriculasModalidades;
    }

    public List<Object> selectFromAluno(Integer param) throws SQLException {
        //MatriculasModalidadesModel matriculasModalidadesRecuperar = (MatriculasModalidadesModel) param;
        List<Object> arrayListMatriculasModalidades = new ArrayList<>();

        try {
            pstSelectFromMatricula.setInt(1, param);
            ResultSet resultadoQuery = pstSelectFromMatricula.executeQuery();
            while (resultadoQuery.next()){
                MatriculasModalidadesModel matriculasModalidadesModel = new MatriculasModalidadesModel();

                matriculasModalidadesModel.setCodigoMatricula(resultadoQuery.getInt("codigo_matricula"));
                matriculasModalidadesModel.setModalidade(resultadoQuery.getInt("id_modalidade"));
                matriculasModalidadesModel.setGraduacao(resultadoQuery.getInt("id_graduacao"));
                matriculasModalidadesModel.setPlano(resultadoQuery.getInt("id_plano"));
                matriculasModalidadesModel.setDataInicio(resultadoQuery.getDate("data_inicio"));
                matriculasModalidadesModel.setDataFim(resultadoQuery.getDate("data_fim"));

                arrayListMatriculasModalidades.add(matriculasModalidadesModel);
            }


        } catch (SQLException e) {
            System.out.println("Houve um erro ao recuperar as matrículas!");
            e.printStackTrace();
        } finally {
            dbUtil.fecharConexaoEPrpdStatement(conexao, pstSelect);
        }

        return arrayListMatriculasModalidades;
    }


    @Override
    public MatriculasModalidadesModel selectById(Object param) throws SQLException {
        MatriculasModalidadesModel matriculasModalidadesRecuperar = (MatriculasModalidadesModel) param;
        List<MatriculasModalidadesModel> arrayMatriculasModalidades = new ArrayList<>();

        pstSelectById.setInt(1, matriculasModalidadesRecuperar.getCodigoMatricula());

        try {
            ResultSet resultadoQuery = pstSelectById.executeQuery();

            while (resultadoQuery.next()){
                MatriculasModalidadesModel matriculasModalidadesModel = new MatriculasModalidadesModel();

                matriculasModalidadesModel.setModalidade(resultadoQuery.getInt("id_modalidade"));
                matriculasModalidadesModel.setGraduacao(resultadoQuery.getInt("id_graduacao"));
                matriculasModalidadesModel.setPlano(resultadoQuery.getInt("id_plano"));
                matriculasModalidadesModel.setDataInicio(resultadoQuery.getDate("data_inicio"));
                matriculasModalidadesModel.setDataFim(resultadoQuery.getDate("data_fim"));


                arrayMatriculasModalidades.add(matriculasModalidadesModel);
            }

        } catch (SQLException e) {
            System.out.println("Houve um erro ao selecionar o id!");
            e.printStackTrace();
        } finally {
            dbUtil.fecharConexaoEPrpdStatement(conexao, pstSelectById);
        }

        return arrayMatriculasModalidades.get(0);
    }

    @Override
    public void insert(Object param) throws SQLException {
        MatriculasModalidadesModel matriculasModalidadesModel = (MatriculasModalidadesModel) param;

        pstInsert.setInt(1,matriculasModalidadesModel.getCodigoMatricula());
        pstInsert.setInt(1,matriculasModalidadesModel.getModalidade());
        pstInsert.setInt(1,matriculasModalidadesModel.getGraduacao());
        pstInsert.setInt(1,matriculasModalidadesModel.getPlano());
        pstInsert.setDate(1, (Date) matriculasModalidadesModel.getDataInicio());
        pstInsert.setDate(1, (Date) matriculasModalidadesModel.getDataFim());

        try {
            pstInsert.execute();
        } catch (SQLException e){
            System.out.println("Houve um erro ao inserir matrícula!");
            e.printStackTrace();
        } finally {
            dbUtil.fecharConexaoEPrpdStatement(conexao, pstSelectById);
        }
    }

    @Override
    public void delete(Object param) throws SQLException {
        MatriculasModalidadesModel matriculasModalidadesModel = (MatriculasModalidadesModel) param;

        pstDelete.setInt(1, matriculasModalidadesModel.getCodigoMatricula());

        try {
            pstDelete.execute();
        } catch (SQLException e) {
            dbUtil.trataExcecoesDeAcordoComState(e.getSQLState());
        } finally {
            dbUtil.fecharConexaoEPrpdStatement(conexao, pstDelete);
        }

    }

    @Override
    public void update(Object param) throws SQLException {
        MatriculasModalidadesModel matriculasModalidadesModel = (MatriculasModalidadesModel) param;

        pstUpdate.setInt(1,matriculasModalidadesModel.getCodigoMatricula());
        pstUpdate.setInt(2, matriculasModalidadesModel.getModalidade());
        pstUpdate.setInt(2, matriculasModalidadesModel.getGraduacao());
        pstUpdate.setInt(2, matriculasModalidadesModel.getPlano());
        pstUpdate.setDate(2, (Date) matriculasModalidadesModel.getDataInicio());
        pstUpdate.setDate(2, (Date) matriculasModalidadesModel.getDataFim());

        try {
            pstUpdate.execute();
        } catch (SQLException e) {
            System.out.println("Houve um erro ao atualizar matrícula!");
            e.printStackTrace();
        } finally {
            dbUtil.fecharConexaoEPrpdStatement(conexao, pstSelectById);
        }
    }
}
