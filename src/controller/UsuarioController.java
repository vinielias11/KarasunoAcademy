package controller;

import database.dao.UsuarioDAO;
import model.UsuarioModel;

import java.sql.SQLException;
import java.util.List;

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

    public List<UsuarioModel> recuperarTodos(){
        try {
            return usuarioDAO.select();
        }
        catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
}