package database.dao;

import database.connection.ConnectionFactory;
import database.connection.EntidadeConexao;
import model.AlunosModel;
import model.FaturasMatriculasModel;
import model.MatriculasModalidadesModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ControleGeralDAO {
    private Connection conexao;

    private final String selectAlunoByCodigo = "SELECT * FROM public.alunos WHERE codigo_aluno = ?";

    private final String selectMatriculasModalidadesByIdAluno = "SELECT MO.NOME AS modalidade, G.NOME AS graduacao, P.NOME AS plano, MM.DATA_INICIO, MM.DATA_FIM FROM ALUNOS A INNER JOIN MATRICULAS M ON (M.ID_ALUNO = A.ID) " +
            "INNER JOIN MATRICULAS_MODALIDADES MM ON (MM.CODIGO_MATRICULA = M.CODIGO_MATRICULA) INNER JOIN MODALIDADES MO ON (MO.ID = MM.ID_MODALIDADE) " +
            "INNER JOIN GRADUACOES G ON (G.ID = MM.ID_GRADUACAO) INNER JOIN PLANOS P ON (P.ID = MM.ID_PLANO) WHERE A.ID = ?;";

    private final String selectFaturasMatriculasByIdAluno = "SELECT FM.DATA_VENCIMENTO, FM.VALOR, FM.DATA_PAGAMENTO, FM.DATA_CANCELAMENTO FROM ALUNOS A \n" +
            "INNER JOIN MATRICULAS M ON (M.ID_ALUNO = A.ID) " +
            "INNER JOIN FATURAS_MATRICULAS FM ON (FM.CODIGO_MATRICULA = M.CODIGO_MATRICULA) " +
            "WHERE A.ID = ?;";

    private final PreparedStatement pstSelectAlunoByCodigo;
    private final PreparedStatement pstSelectMatriculasModalidadesByIdAluno;
    private final PreparedStatement pstSelectFaturasMatriculasByIdAluno;

    public ControleGeralDAO() {
        try {
            this.conexao = ConnectionFactory.getConection(new EntidadeConexao());
            pstSelectAlunoByCodigo = this.conexao.prepareStatement(selectAlunoByCodigo);
            pstSelectMatriculasModalidadesByIdAluno = this.conexao.prepareStatement(selectMatriculasModalidadesByIdAluno);
            pstSelectFaturasMatriculasByIdAluno = this.conexao.prepareStatement(selectFaturasMatriculasByIdAluno);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public AlunosModel selectAlunoByCodigo(Integer codigo) throws SQLException {
        List<AlunosModel> arrayListAlunos = new ArrayList<>();

        pstSelectAlunoByCodigo.setInt(1, codigo);

        try {
            ResultSet resultadoQuery = pstSelectAlunoByCodigo.executeQuery();

            while (resultadoQuery.next()) {
                AlunosModel alunosModel = new AlunosModel();

                alunosModel.setId(resultadoQuery.getInt("id"));
                alunosModel.setCodigoAluno(resultadoQuery.getInt("codigo_aluno"));
                alunosModel.setNome(resultadoQuery.getString("nome"));

                arrayListAlunos.add(alunosModel);
            }
        } catch (SQLException e) {
            System.out.println("Houve um erro ao recuperar aluno!");
        }

        return !arrayListAlunos.isEmpty() ? arrayListAlunos.get(0) : null;
    }

    public List<MatriculasModalidadesModel> selectMatriculasModalidadesByIdAluno(Integer idAluno) throws SQLException {
        List<MatriculasModalidadesModel> arrayListMatriculasModalidades = new ArrayList<>();

        pstSelectMatriculasModalidadesByIdAluno.setInt(1, idAluno);

        try {
            ResultSet resultadoQuery = pstSelectMatriculasModalidadesByIdAluno.executeQuery();

            while (resultadoQuery.next()) {
                MatriculasModalidadesModel matriculasModalidadesModel = new MatriculasModalidadesModel();

                matriculasModalidadesModel.setNomeModalidade(resultadoQuery.getString("modalidade"));
                matriculasModalidadesModel.setNomeGraduacao(resultadoQuery.getString("graduacao"));
                matriculasModalidadesModel.setNomePlano(resultadoQuery.getString("plano"));
                matriculasModalidadesModel.setDataInicio(resultadoQuery.getDate("data_inicio"));
                matriculasModalidadesModel.setDataFim(resultadoQuery.getDate("data_fim"));

                arrayListMatriculasModalidades.add(matriculasModalidadesModel);
            }
        } catch (SQLException e) {
            System.out.println("Houve um erro ao recuperar matriculas modalidades!");
        }

        return arrayListMatriculasModalidades;
    }

    public List<FaturasMatriculasModel> selectFaturasMatriculasByIdAluno(Integer idAluno) throws SQLException {
        List<FaturasMatriculasModel> arrayListFaturasMatriculas = new ArrayList<>();

        pstSelectFaturasMatriculasByIdAluno.setInt(1, idAluno);

        try {
            ResultSet resultadoQuery = pstSelectFaturasMatriculasByIdAluno.executeQuery();

            while (resultadoQuery.next()) {
                FaturasMatriculasModel faturasMatriculasModel = new FaturasMatriculasModel();

                faturasMatriculasModel.setDataVencimento(resultadoQuery.getDate("data_vencimento"));
                faturasMatriculasModel.setValor(resultadoQuery.getDouble("valor"));
                faturasMatriculasModel.setDataPagamento(resultadoQuery.getTimestamp("data_pagamento"));
                faturasMatriculasModel.setDataCancelamento(resultadoQuery.getDate("data_cancelamento"));

                arrayListFaturasMatriculas.add(faturasMatriculasModel);
            }
        } catch (SQLException e) {
            System.out.println("Houve um erro ao recuperar as faturas!");
        }

        return arrayListFaturasMatriculas;
    }
}
