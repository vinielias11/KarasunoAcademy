package database.dao;

import database.connection.ConnectionFactory;
import database.connection.EntidadeConexao;
import model.AlunosModel;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AlunosDAO extends SistemaDAO {
    private Connection conexao;

    private final String select = "SELECT * FROM public.alunos;";
    private final String insert =
            "INSERT INTO public.alunos(codigo_aluno, nome, data_nascimento, sexo, telefone, celular, email, " +
            "observacao, endereco, numero, complemento, bairro, cidade, estado, pais, cep) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
    private final String delete = "DELETE FROM public.alunos WHERE id = ?;";
    private final String update = "UPDATE public.alunos SET nome = ?, data_nascimento = ?, sexo = ?," +
            " telefone = ?, celular = ?, email = ?, observacao = ?, endereco = ?, numero = ?, complemento = ?," +
            " bairro = ?, cidade = ?, estado = ?, pais = ?, cep = ? WHERE id = ?;";

    private final PreparedStatement pstSelect;
    private final PreparedStatement pstInsert;
    private final PreparedStatement pstDelete;
    private final PreparedStatement pstUpdate;

    public AlunosDAO() {
        try {
            this.conexao = ConnectionFactory.getConection(new EntidadeConexao());
            pstSelect = this.conexao.prepareStatement(select);
            pstInsert = this.conexao.prepareStatement(insert);
            pstDelete = this.conexao.prepareStatement(delete);
            pstUpdate = this.conexao.prepareStatement(update);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Houve um erro ao inicializar os comandos SQL.");
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Object> select() throws SQLException {
        ResultSet resultadoQuery = pstSelect.executeQuery();
        List<Object> arrayListAlunos = new ArrayList<>();

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

        return arrayListAlunos;
    }

    @Override
    public void insert(Object param) throws SQLException {
        AlunosModel alunosModel = (AlunosModel) param;

        pstInsert.setInt(1, alunosModel.getCodigoAluno());
        pstInsert.setString(2, alunosModel.getNome());
        pstInsert.setDate(3, alunosModel.getDataNascimento());
        pstInsert.setString(4, alunosModel.getSexo());
        pstInsert.setString(5, alunosModel.getTelefone());
        pstInsert.setString(6, alunosModel.getCelular());
        pstInsert.setString(7, alunosModel.getEmail());
        pstInsert.setString(8, alunosModel.getObservacao());
        pstInsert.setString(9, alunosModel.getEndereco());
        pstInsert.setString(10, alunosModel.getNumero());
        pstInsert.setString(11, alunosModel.getComplemento());
        pstInsert.setString(12, alunosModel.getBairro());
        pstInsert.setString(13, alunosModel.getCidade());
        pstInsert.setString(14, alunosModel.getEstado());
        pstInsert.setString(15, alunosModel.getPais());
        pstInsert.setString(16, alunosModel.getCep());

        try {
            pstInsert.execute();
        } catch (SQLException e) {
            System.out.println("Houve um erro ao inserir aluno!");
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Object param) throws SQLException {
        AlunosModel alunosModel = (AlunosModel) param;

        pstDelete.setInt(1, alunosModel.getId());

        try {
            pstDelete.execute();
        } catch (SQLException e) {
            System.out.println("Houve um erro ao deletar aluno!");
            e.printStackTrace();
        }
    }

    @Override
    public void update(Object param) throws SQLException {
        AlunosModel alunosModel = (AlunosModel) param;

        pstUpdate.setString(1, alunosModel.getNome());
        pstUpdate.setDate(2, alunosModel.getDataNascimento());
        pstUpdate.setString(3, alunosModel.getSexo());
        pstUpdate.setString(4, alunosModel.getTelefone());
        pstUpdate.setString(5, alunosModel.getCelular());
        pstUpdate.setString(6, alunosModel.getEmail());
        pstUpdate.setString(7, alunosModel.getObservacao());
        pstUpdate.setString(8, alunosModel.getEndereco());
        pstUpdate.setString(9, alunosModel.getNumero());
        pstUpdate.setString(10, alunosModel.getComplemento());
        pstUpdate.setString(11, alunosModel.getBairro());
        pstUpdate.setString(12, alunosModel.getCidade());
        pstUpdate.setString(13, alunosModel.getEstado());
        pstUpdate.setString(14, alunosModel.getPais());
        pstUpdate.setString(15, alunosModel.getCep());
        pstUpdate.setInt(16, alunosModel.getId());

        try {
            pstUpdate.execute();
        } catch (SQLException e) {
            System.out.println("Houve um erro ao atualizar aluno!");
            e.printStackTrace();
        }
    }
}
