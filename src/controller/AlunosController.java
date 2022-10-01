package controller;

import database.dao.AlunosDAO;

import javax.swing.*;
import java.sql.SQLException;
import java.util.List;

public class AlunosController {
    private AlunosDAO alunosDAO;

    public AlunosController() {
        alunosDAO = new AlunosDAO();
    }

    public List<Object> recuperarTodos() {
        try {
            return alunosDAO.select();
        }
        catch (SQLException e){
            JOptionPane.showMessageDialog(null, "Houve um erro ao recuperar os alunos!");
            throw new RuntimeException(e);
        }
    }
}
