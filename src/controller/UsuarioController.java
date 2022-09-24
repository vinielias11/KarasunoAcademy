package controller;

import database.dao.UsuarioDAO;
import model.UsuarioModel;

import javax.swing.*;
import java.sql.SQLException;

public class UsuarioController {
    private UsuarioDAO usuarioDAO;

    public UsuarioController() {
        usuarioDAO = new UsuarioDAO();
    }

    public boolean logar(String nome, String senha) {
        try {
            return usuarioDAO.selectByNomeESenha(nome, senha);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}