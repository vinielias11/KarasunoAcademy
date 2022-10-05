package controller;

import database.dao.AlunosDAO;
import model.AlunosModel;

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
        catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Houve um erro ao recuperar os alunos!");
            throw new RuntimeException(e);
        }
    }

    public void inserir(Object aluno, JDialog cadastro) {
        try {
            alunosDAO.insert(aluno);
            cadastro.dispose();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Houve um erro ao inserir o aluno!");
            throw new RuntimeException(e);
        }
    }

    public void editar(Object aluno, JDialog cadastro) {
        try {
            alunosDAO.update(aluno);
            cadastro.dispose();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Houve um erro ao editar o aluno!");
            throw new RuntimeException(e);
        }
    }

    public AlunosModel recuperarPorId(AlunosModel aluno) {
        try {
            return alunosDAO.selectById(aluno);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Houve um erro ao recuperar o aluno!");
            throw new RuntimeException(e);
        }
    }
}
