package controller;

import database.dao.ModalidadesDAO;
import graphic.util.ModalidadesComboModel;
import model.ModalidadesModel;

import javax.swing.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ModalidadesController {
    private ModalidadesDAO modalidadesDAO;

    public ModalidadesController(){
        modalidadesDAO = new ModalidadesDAO();
    }

    public List<Object> recuperarTodos() {
        try {
            return modalidadesDAO.select();
        }
        catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Houve um erro ao recuperar as modalidades!");
            throw new RuntimeException(e);
        }
    }

    public ModalidadesModel recuperarPorID (ModalidadesModel modalidade){
        try {
            return modalidadesDAO.selectById(modalidade);
        } catch (SQLException e){
            JOptionPane.showMessageDialog(null, "Houve um erro ao recuperar a modalidade!");
            throw new RuntimeException(e);
        }
    }

    public void deletar(ModalidadesModel modalidade){
        try {
            modalidadesDAO.delete(modalidade);
        }   catch (SQLException e){
            JOptionPane.showMessageDialog(null, "Houve um erro ao deletar a modalidade!");
        }
    }

    public void inserir(Object modalidades, JDialog cadastro) {
        try {
            modalidadesDAO.insert(modalidades);
            cadastro.dispose();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Houve um erro ao inserir a modalidade!");
        }
    }

    public void editar(Object modalidades, JDialog cadastro) {
        try {
            modalidadesDAO.update(modalidades);
            cadastro.dispose();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Houve um erro ao editar a modalidade!");
        }
    }



    public ArrayList<ModalidadesModel> recuperaModalidadesParaComboBox() {
        ArrayList<ModalidadesModel> listaModalidades = new ArrayList<>();

        try {
            List<Object> modalidadesRecuperar = modalidadesDAO.select();
            modalidadesRecuperar.forEach(modalidade -> listaModalidades.add((ModalidadesModel) modalidade));
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Houve um erro ao recuperar as modalidades!");
            throw new RuntimeException(e);
        }

        return listaModalidades;
    }
}
