package database.dao;

import database.connection.ConnectionFactory;
import database.connection.EntidadeConexao;
import model.UsuarioModel;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO extends SistemaDAO {
    private Connection conexao;

    private final String select = "SELECT * FROM public.usuarios;";
    private final String insert = "INSERT INTO public.usuarios(nome, senha, perfil) VALUES (?, ?, ?);";
    private final String delete = "DELETE FROM public.usuarios WHERE id = ?;";
    private final String update = "UPDATE public.usuarios SET nome = ?, senha = ?, perfil = ? WHERE id = ?;";
    private final String selectByNomeESenha = "SELECT * FROM public.usuarios WHERE nome = ? AND senha = ?;";

    private final PreparedStatement pstSelect;
    private final PreparedStatement pstInsert;
    private final PreparedStatement pstDelete;
    private final PreparedStatement pstUpdate;
    private final PreparedStatement pstSelectByNomeESenha;

    public UsuarioDAO() {
        try {
            this.conexao = ConnectionFactory.getConection(new EntidadeConexao());
            pstSelect = this.conexao.prepareStatement(select);
            pstInsert = this.conexao.prepareStatement(insert);
            pstDelete = this.conexao.prepareStatement(delete);
            pstUpdate = this.conexao.prepareStatement(update);
            pstSelectByNomeESenha = this.conexao.prepareStatement(selectByNomeESenha);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Houve um erro ao inicializar os comandos SQL.");
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<UsuarioModel> select() throws SQLException {
        ResultSet resultadoQuery = pstSelect.executeQuery();
        List<UsuarioModel> arrayListUsuarios = new ArrayList<UsuarioModel>();

        while (resultadoQuery.next()) {
            UsuarioModel usuarioModel = new UsuarioModel();
            usuarioModel.setNome(resultadoQuery.getString("nome"));
            usuarioModel.setSenha(resultadoQuery.getString("senha"));
            usuarioModel.setPerfil(resultadoQuery.getInt("perfil"));

            arrayListUsuarios.add(usuarioModel);
        }

        return arrayListUsuarios;
    }

    @Override
    public void insert(Object param) throws SQLException {
        UsuarioModel usuarioModel = (UsuarioModel) param;

        pstInsert.setString(1, usuarioModel.getNome());
        pstInsert.setString(2, usuarioModel.getSenha());
        pstInsert.setInt(3, usuarioModel.getPerfil());

        try {
            pstInsert.execute();
        } catch (SQLException e) {
            System.out.println("Houve um erro ao inserir usuário!");
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Object param) throws SQLException {
        UsuarioModel usuarioModel = (UsuarioModel) param;

        pstDelete.setInt(1, usuarioModel.getId());

        try {
            pstDelete.execute();
        } catch (SQLException e) {
            System.out.println("Houve um erro ao deletar usuário!");
            e.printStackTrace();
        }
    }

    @Override
    public void update(Object param) throws SQLException {
        UsuarioModel usuarioModel = (UsuarioModel) param;

        pstUpdate.setString(1, usuarioModel.getNome());
        pstUpdate.setString(2, usuarioModel.getSenha());
        pstUpdate.setInt(3, usuarioModel.getPerfil());
        pstUpdate.setInt(4, usuarioModel.getId());

        try {
            pstUpdate.execute();
        } catch (SQLException e) {
            System.out.println("Houve um erro ao atualizar usuário!");
            e.printStackTrace();
        }
    }

    public boolean selectByNomeESenha(String nome, String senha) throws SQLException {
        pstSelectByNomeESenha.setString(1, nome);
        pstSelectByNomeESenha.setString(2, senha);

        try {
            ResultSet resultadoQuery = pstSelectByNomeESenha.executeQuery();
            List<Object> arrayListUsuarios = new ArrayList<Object>();

            while (resultadoQuery.next()) {
                UsuarioModel u = new UsuarioModel();

                u.setNome(resultadoQuery.getString("nome"));
                u.setSenha(resultadoQuery.getString("senha"));
                u.setPerfil(resultadoQuery.getInt("perfil"));

                arrayListUsuarios.add(u);
            }

            return arrayListUsuarios.isEmpty() ? false : true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Houve um erro ao recuperar o usuário!");
            e.printStackTrace();
        }

        return false;
    }
}
