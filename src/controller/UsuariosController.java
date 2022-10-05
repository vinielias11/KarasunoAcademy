package controller;

import database.dao.UsuariosDAO;

import java.sql.SQLException;
import java.util.List;

public class UsuariosController {
    private UsuariosDAO usuariosDAO;

    public UsuariosController() {
        usuariosDAO = new UsuariosDAO();
    }

    public boolean logar(String nome, String senha) {
        try {
            return usuariosDAO.selectByNomeESenha(nome, senha);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}