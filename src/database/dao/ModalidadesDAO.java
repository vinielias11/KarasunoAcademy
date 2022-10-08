package database.dao;

import database.connection.ConnectionFactory;
import database.connection.EntidadeConexao;
import database.util.DbUtil;
import graphic.entidades.modalidades.ModalidadesPanel;
import model.ModalidadesModel;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ModalidadesDAO extends SistemaDAO {

    private Connection conexao;
    private DbUtil dbUtil = new DbUtil();

    private final String select = "SELECT * from public.modalidades ORDER BY id;";
    private final String insert = "INSERT INTO public.modalidades(nome) VALUES (?);";
    private final String delete = "DELETE FROM public.modalidades WHERE id = ?;";
    private final String update = "UPDATE public.modalidades SET nome = ? WHERE id = ?;";
    private final String selectById = "SELECT * from public.modalidades WHERE id = ?;";

    private final PreparedStatement pstSelect;
    private final PreparedStatement pstInsert;
    private final PreparedStatement pstDelete;
    private final PreparedStatement pstUpdate;
    private final PreparedStatement pstSelectById;


    public ModalidadesDAO(){
        try {
            this.conexao = ConnectionFactory.getConection(new EntidadeConexao());
            pstSelect = this.conexao.prepareStatement(select);
            pstInsert = this.conexao.prepareStatement(insert);
            pstDelete = this.conexao.prepareStatement(delete);
            pstUpdate = this.conexao.prepareStatement(update);
            pstSelectById = this.conexao.prepareStatement(selectById);
        } catch (SQLException e){
            JOptionPane.showMessageDialog(null, "Houve um erro ao inicializar os comandos SQL.");
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Object> select() throws SQLException {
        List<Object> arrayListModalidades = new ArrayList<>();

        try {
            ResultSet resultadoQuery = pstSelect.executeQuery();
            while (resultadoQuery.next()){
                ModalidadesModel modalidadesModel = new ModalidadesModel();

                modalidadesModel.setId(resultadoQuery.getInt("id"));
                modalidadesModel.setNome(resultadoQuery.getString("nome"));

                arrayListModalidades.add(modalidadesModel);
            }


        } catch (SQLException e) {
            System.out.println("Houve um erro ao recuperar as modalidades!");
            e.printStackTrace();
        } finally {
            dbUtil.fecharConexaoEPrpdStatement(conexao, pstSelect);
        }

        return arrayListModalidades;
    }


    public ModalidadesModel selectById(Object param) throws SQLException {
        ModalidadesModel modalidadesRecuperar = (ModalidadesModel) param;
        List<ModalidadesModel> arrayModalidades = new ArrayList<>();

        pstSelectById.setInt(1, modalidadesRecuperar.getId());

        try {
            ResultSet resultadoQuery = pstSelectById.executeQuery();

            while (resultadoQuery.next()){
                ModalidadesModel modalidadesModel = new ModalidadesModel();

                modalidadesModel.setNome(resultadoQuery.getString("nome"));
                modalidadesModel.setId(resultadoQuery.getInt("id"));

                arrayModalidades.add(modalidadesModel);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ID não encontrado!");
            e.printStackTrace();
        } finally {
            dbUtil.fecharConexaoEPrpdStatement(conexao, pstSelectById);
        }

        return arrayModalidades.get(0);
    }

    @Override
    public void insert(Object param) throws SQLException {
        ModalidadesModel modalidadesModel = (ModalidadesModel) param;

        pstInsert.setString(1,modalidadesModel.getNome());

        try {
            pstInsert.execute();
        } catch (SQLException e){
            System.out.println("Houve um erro ao inserir modalidade!");
            e.printStackTrace();
        } finally {
            dbUtil.fecharConexaoEPrpdStatement(conexao, pstSelectById);
        }
    }

    @Override
    public void delete(Object param) throws SQLException {
        ModalidadesModel modalidadesModel = (ModalidadesModel) param;

        pstDelete.setInt(1, modalidadesModel.getId());

        try {
            pstDelete.execute();
        } catch (SQLException e) {
            System.out.println("Houve um erro ao excluir modalidade!");
            e.printStackTrace();
        } finally {
            dbUtil.fecharConexaoEPrpdStatement(conexao, pstDelete);
        }

    }

    @Override
    public void update(Object param) throws SQLException {
        ModalidadesModel modalidadesModel = (ModalidadesModel) param;

        pstUpdate.setString(1,modalidadesModel.getNome());
        pstUpdate.setInt(2, modalidadesModel.getId());

        try {
            pstUpdate.execute();
        } catch (SQLException e) {
            System.out.println("Houve um erro ao atualizar modalidade!");
            e.printStackTrace();
        } finally {
            dbUtil.fecharConexaoEPrpdStatement(conexao, pstSelectById);
        }
    }

}
