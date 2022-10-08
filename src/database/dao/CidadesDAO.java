package database.dao;

import database.connection.ConnectionFactory;
import database.connection.EntidadeConexao;
import database.util.DbUtil;
import model.AlunosModel;
import model.CidadesModel;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CidadesDAO extends SistemaDAO {
    private Connection conexao;
    private DbUtil dbUtil = new DbUtil();

    private final String select = "SELECT * FROM public.cidades ORDER BY id;";
    private final String selectCidadesByEstado = "SELECT cidade FROM public.cidades WHERE estado = ? ORDER BY cidade ASC";

    private final PreparedStatement pstSelect;
    private final PreparedStatement pstSelectCidadesByEstado;

    public CidadesDAO() {
        try {
            this.conexao = ConnectionFactory.getConection(new EntidadeConexao());
            pstSelect = this.conexao.prepareStatement(select);
            pstSelectCidadesByEstado = this.conexao.prepareStatement(selectCidadesByEstado);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Houve um erro ao inicializar os comandos SQL.");
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Object> select() throws SQLException {
        List<Object> arrayListCidades = new ArrayList<>();

        try {
            ResultSet resultadoQuery = pstSelect.executeQuery();

            while (resultadoQuery.next()) {
                CidadesModel cidadesModel = new CidadesModel();

                cidadesModel.setId(resultadoQuery.getInt("id"));
                cidadesModel.setCidade(resultadoQuery.getString("cidade"));
                cidadesModel.setEstado(resultadoQuery.getString("estado"));
                cidadesModel.setPais(resultadoQuery.getString("pais"));

                arrayListCidades.add(cidadesModel);
            }

        } catch (SQLException e) {
            System.out.println("Houve um erro ao recuperar as cidades!");
            e.printStackTrace();
        } finally {
            dbUtil.fecharConexaoEPrpdStatement(conexao, pstSelect);
        }

        return arrayListCidades;
    }

    public List<String> selectCidadesByEstado(String estado) throws SQLException {
        List<String> cidadesRecuperar = new ArrayList<>();

        pstSelectCidadesByEstado.setString(1, estado);

        try {
            ResultSet resultadoQuery = pstSelectCidadesByEstado.executeQuery();

            while (resultadoQuery.next()) {
                String cidade = "";

                cidade = resultadoQuery.getString("cidade");

                cidadesRecuperar.add(cidade);
            }
        } catch (SQLException e) {
            System.out.println("Houve um erro ao recuperar as cidades do Estado!");
            e.printStackTrace();
        }

        return cidadesRecuperar;
    }

    @Override
    public void insert(Object param) throws SQLException {

    }

    @Override
    public void delete(Object param) throws SQLException {

    }

    @Override
    public void update(Object param) throws SQLException {

    }
}
