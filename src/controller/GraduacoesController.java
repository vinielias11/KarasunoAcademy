package controller;

import database.dao.AlunosDAO;
import database.dao.GraduacoesDAO;
import model.AlunosModel;
import model.GraduacoesModel;
import model.ModalidadesModel;

import javax.swing.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GraduacoesController {
    private GraduacoesDAO graduacoesDAO;

    public GraduacoesController() {graduacoesDAO = new GraduacoesDAO();}

    public List<Object> recuperarTodos() {
        try {
            return graduacoesDAO.select();
        }
        catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Houve um erro ao recuperar as graduações!");
            throw new RuntimeException(e);
        }
    }

    public void inserir(Object graduacoes, JDialog cadastro) {
        try {
            graduacoesDAO.insert(graduacoes);
            cadastro.dispose();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Houve um erro ao inserir a graduação!");
        }
    }

    public void editar(Object graduacoes, JDialog cadastro) {
        try {
            graduacoesDAO.update(graduacoes);
            cadastro.dispose();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Houve um erro ao editar a graduação!");
        }
    }

    public GraduacoesModel recuperarPorId(GraduacoesModel graduacoes) {
        try {
            return graduacoesDAO.selectById(graduacoes);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Houve um erro ao recuperar a graduação!");
            throw new RuntimeException(e);
        }
    }

    public void deletar(GraduacoesModel graduacoes){
        try {
            graduacoesDAO.delete(graduacoes);
        }   catch (SQLException e){
            JOptionPane.showMessageDialog(null, "Houve um erro ao deletar a graduação!");
        }
    }

    public ArrayList<GraduacoesModel> recuperaModalidadesParaComboBox() {
        ArrayList<GraduacoesModel> listaGraduacoes = new ArrayList<>();

        try {
            List<Object> graduacoesRecuperar = graduacoesDAO.select();
            graduacoesRecuperar.forEach(modalidade -> listaGraduacoes.add((GraduacoesModel) modalidade));
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Houve um erro ao recuperar as graduações!");
            throw new RuntimeException(e);
        }

        return listaGraduacoes;
    }
}

