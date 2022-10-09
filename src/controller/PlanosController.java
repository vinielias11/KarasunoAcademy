package controller;

import database.dao.PlanosDAO;

import javax.swing.*;
import java.sql.SQLException;
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
}
