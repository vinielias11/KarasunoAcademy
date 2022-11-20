package database.dao;

import database.connection.ConnectionFactory;
import database.connection.EntidadeConexao;
import model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ControleGeralDAO {
    private Connection conexao;

    private final String selectAlunoByCodigo = "SELECT * FROM public.alunos WHERE codigo_aluno = ?";

    private final String selectMatriculasModalidadesByIdAluno = "SELECT MO.NOME AS modalidade, G.NOME AS graduacao, P.NOME AS plano, MM.DATA_INICIO, MM.DATA_FIM FROM ALUNOS A INNER JOIN MATRICULAS M ON (M.ID_ALUNO = A.ID) " +
            "INNER JOIN MATRICULAS_MODALIDADES MM ON (MM.CODIGO_MATRICULA = M.CODIGO_MATRICULA) INNER JOIN MODALIDADES MO ON (MO.ID = MM.ID_MODALIDADE) " +
            "INNER JOIN GRADUACOES G ON (G.ID = MM.ID_GRADUACAO) INNER JOIN PLANOS P ON (P.ID = MM.ID_PLANO) WHERE A.ID = ?;";

    private final String selectMatriculasByCodigo = "SELECT M.*, A.nome AS nome_aluno FROM public.matriculas M INNER JOIN alunos A ON M.id_aluno = A.id WHERE M.id_aluno = (SELECT ID FROM ALUNOS WHERE CODIGO_ALUNO = ?) ORDER BY M.codigo_matricula;";

    private final String selectFaturasMatriculasByIdAluno = "SELECT FM.DATA_VENCIMENTO, FM.VALOR, FM.DATA_PAGAMENTO, FM.DATA_CANCELAMENTO FROM ALUNOS A \n" +
            "INNER JOIN MATRICULAS M ON (M.ID_ALUNO = A.ID) " +
            "INNER JOIN FATURAS_MATRICULAS FM ON (FM.CODIGO_MATRICULA = M.CODIGO_MATRICULA) " +
            "WHERE A.ID = ? ORDER BY DATA_VENCIMENTO;";

    private final String updateDataPagamentoFatura = "UPDATE FATURAS_MATRICULAS SET DATA_PAGAMENTO = CURRENT_DATE " +
            "WHERE CODIGO_MATRICULA = (SELECT CODIGO_MATRICULA FROM MATRICULAS WHERE ID_ALUNO = ?) AND DATA_VENCIMENTO = ?;";

    private final String insertAssiduidade = "INSERT INTO ASSIDUIDADE(CODIGO_MATRICULA) VALUES ((SELECT CODIGO_MATRICULA FROM MATRICULAS WHERE ID_ALUNO = ?))";

    private final String selectAssiduidadeByIdAluno = "SELECT DATA_ENTRADA FROM ASSIDUIDADE WHERE CODIGO_MATRICULA = ((SELECT CODIGO_MATRICULA FROM MATRICULAS WHERE ID_ALUNO = ?)) AND EXTRACT (MONTH FROM DATA_ENTRADA) = ? AND EXTRACT (YEAR FROM DATA_ENTRADA) = ?";

    private final PreparedStatement pstSelectAlunoByCodigo;
    private final PreparedStatement pstSelectMatriculasModalidadesByIdAluno;
    private final PreparedStatement pstSelectFaturasMatriculasByIdAluno;
    private final PreparedStatement pstUpdateDataPagamentoFatura;
    private final PreparedStatement pstInsertAssiduidade;
    private final PreparedStatement pstSelectAssiduidadeByIdAluno;
    private final PreparedStatement pstSelectMatriculasByCodigo;

    public ControleGeralDAO() {
        try {
            this.conexao = ConnectionFactory.getConection(new EntidadeConexao());
            pstSelectAlunoByCodigo = this.conexao.prepareStatement(selectAlunoByCodigo);
            pstSelectMatriculasModalidadesByIdAluno = this.conexao.prepareStatement(selectMatriculasModalidadesByIdAluno);
            pstSelectFaturasMatriculasByIdAluno = this.conexao.prepareStatement(selectFaturasMatriculasByIdAluno);
            pstUpdateDataPagamentoFatura = this.conexao.prepareStatement(updateDataPagamentoFatura);
            pstInsertAssiduidade = this.conexao.prepareStatement(insertAssiduidade);
            pstSelectAssiduidadeByIdAluno = this.conexao.prepareStatement(selectAssiduidadeByIdAluno);
            pstSelectMatriculasByCodigo = this.conexao.prepareStatement(selectMatriculasByCodigo);
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
                alunosModel.setDataNascimento(resultadoQuery.getDate("data_nascimento"));
                alunosModel.setSexo(resultadoQuery.getString("sexo"));
                alunosModel.setTelefone(resultadoQuery.getString("telefone"));
                alunosModel.setCelular(resultadoQuery.getString("celular"));
                alunosModel.setEmail(resultadoQuery.getString("email"));
                alunosModel.setObservacao(resultadoQuery.getString("observacao"));
                alunosModel.setEndereco(resultadoQuery.getString("endereco"));
                alunosModel.setNumero(resultadoQuery.getString("numero"));
                alunosModel.setComplemento(resultadoQuery.getString("complemento"));
                alunosModel.setBairro(resultadoQuery.getString("bairro"));
                alunosModel.setCidade(resultadoQuery.getString("cidade"));
                alunosModel.setEstado(resultadoQuery.getString("estado"));
                alunosModel.setPais(resultadoQuery.getString("pais"));
                alunosModel.setCep(resultadoQuery.getString("cep"));

                arrayListAlunos.add(alunosModel);
            }
        } catch (SQLException e) {
            System.out.println("Houve um erro ao recuperar aluno!");
        }

        return !arrayListAlunos.isEmpty() ? arrayListAlunos.get(0) : null;
    }

    public MatriculasModel selectMatriculasByCodigo(Integer codigo) throws SQLException {
        List<MatriculasModel> arrayListMatriculas = new ArrayList<>();

        pstSelectMatriculasByCodigo.setInt(1, codigo);

        try {
            ResultSet resultadoQuery = pstSelectMatriculasByCodigo.executeQuery();

            while (resultadoQuery.next()) {
                MatriculasModel matriculasModel = new MatriculasModel();

                matriculasModel.setCodigoMatricula(resultadoQuery.getInt("codigo_matricula"));
                matriculasModel.setCodigoAluno(resultadoQuery.getInt("id_aluno"));
                matriculasModel.setNomeAluno(resultadoQuery.getString("nome_aluno"));
                matriculasModel.setDataMatricula(resultadoQuery.getDate("data_matricula"));
                matriculasModel.setDiaVencimento(resultadoQuery.getInt("dia_vencimento"));
                matriculasModel.setDataEncerramento(resultadoQuery.getDate("data_encerramento"));

                arrayListMatriculas.add(matriculasModel);
            }
        } catch (SQLException e) {
            System.out.println("Houve um erro ao recuperar matrícula!");
        }

        return !arrayListMatriculas.isEmpty() ? arrayListMatriculas.get(0) : null;
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

    public void updateDataPagamentoFatura(Date dataVencimento, Integer idAluno) throws SQLException {
        java.sql.Date dataVencimentoSql = new java.sql.Date(dataVencimento.getTime());

        pstUpdateDataPagamentoFatura.setInt(1, idAluno);
        pstUpdateDataPagamentoFatura.setDate(2, dataVencimentoSql);

        try {
            pstUpdateDataPagamentoFatura.execute();
        } catch (SQLException e) {
            System.out.println("Houve um problema ao pagar fatura");
        }
    }

    public void insertAssiduidade(Integer idAluno) throws SQLException {
        pstInsertAssiduidade.setInt(1, idAluno);

        try {
            pstInsertAssiduidade.execute();
        } catch (SQLException e) {
            System.out.println("Houve um problema ao inserir dia na assiduidade!");
        }
    }

    public List<AssiduidadeModel> selectAssiduidadeByIdAluno(Integer idAluno, Integer mes, Integer ano) throws SQLException {
        List<AssiduidadeModel> arrayListAssiduidade = new ArrayList<>();

        pstSelectAssiduidadeByIdAluno.setInt(1, idAluno);
        pstSelectAssiduidadeByIdAluno.setInt(2, mes);
        pstSelectAssiduidadeByIdAluno.setInt(3, ano);

        try {
            ResultSet resultadoQuery = pstSelectAssiduidadeByIdAluno.executeQuery();

            while (resultadoQuery.next()) {
                AssiduidadeModel assiduidadeModel = new AssiduidadeModel();

                assiduidadeModel.setDataEntrada(resultadoQuery.getTimestamp("data_entrada"));

                arrayListAssiduidade.add(assiduidadeModel);
            }
        } catch (SQLException e) {
            System.out.println("Houve um erro ao recuperar as assiduidades!");
        }

        return arrayListAssiduidade;
    }
}
