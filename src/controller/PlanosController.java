package controller;

import database.dao.PlanosDAO;
import model.PlanosModel;

import javax.swing.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PlanosController {
    private PlanosDAO planosDAO;

    public PlanosController() {
        planosDAO = new PlanosDAO();
    }

    public List<Object> recuperarTodos() {
        try {
            return planosDAO.select();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Houve um erro ao recuperar os planos!");
            throw new RuntimeException(e);
        }
    }

    public void inserir(Object plano, JDialog cadastro) {
        try {
            planosDAO.insert(plano);
            cadastro.dispose();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Houve um erro ao inserir o plano!");
        }
    }

    public void editar(Object plano, JDialog cadastro) {
        try {
            planosDAO.update(plano);
            cadastro.dispose();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Houve um erro ao editar o plano!");
        }
    }

    public PlanosModel recuperarPorId(PlanosModel plano) {
        try {
            return planosDAO.selectById(plano);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Houve um erro ao recuperar o plano!");
            throw new RuntimeException(e);
        }
    }

    public void deletar (PlanosModel plano) {
        try {
            planosDAO.delete(plano);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Houve um erro ao excluir o plano!");
            throw new RuntimeException(e);
        }
    }

    public ArrayList<PlanosModel> recuperaPlanosParaComboBox() {
        ArrayList<PlanosModel> listaPlanos = new ArrayList<>();

        try {
            List<Object> planosRecuperar = planosDAO.select();
            planosRecuperar.forEach(plano -> listaPlanos.add((PlanosModel) plano));
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Houve um erro ao recuperar os planos!");
            throw new RuntimeException(e);
        }

        return listaPlanos;
    }
}
