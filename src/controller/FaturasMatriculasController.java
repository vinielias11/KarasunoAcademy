package controller;

import database.dao.FaturasMatriculasDAO;
import model.FaturasMatriculasModel;

import javax.swing.*;
import java.sql.SQLException;
import java.util.List;

public class FaturasMatriculasController {

    private FaturasMatriculasDAO faturasMatriculasDAO;

    public FaturasMatriculasController() {faturasMatriculasDAO = new FaturasMatriculasDAO();}

    public List<Object> recuperarTodos() {
        try {
            return faturasMatriculasDAO.select();
        }
        catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Houve um erro ao recuperar as faturas!");
            throw new RuntimeException(e);
        }
    }

    public void inserir(Object faturasMatriculas, JDialog cadastro) {
        try {
            faturasMatriculasDAO.insert(faturasMatriculas);
            cadastro.dispose();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Houve um erro ao inserir a fatura!");
        }
    }

    public void editar(Object faturasMatriculas, JDialog cadastro) {
        try {
            faturasMatriculasDAO.update(faturasMatriculas);
            cadastro.dispose();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Houve um erro ao editar a fatura!");
        }
    }

    public FaturasMatriculasModel recuperarPorId(FaturasMatriculasModel faturasMatriculas) {
        try {
            return (FaturasMatriculasModel) faturasMatriculasDAO.selectById(faturasMatriculas);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Houve um erro ao recuperar a fatura!");
            throw new RuntimeException(e);
        }
    }

    public void deletar(FaturasMatriculasModel faturasMatriculas){
        try {
            faturasMatriculasDAO.delete(faturasMatriculas);
        }   catch (SQLException e){
            JOptionPane.showMessageDialog(null, "Houve um erro ao deletar a fatura!");
        }
    }

    public void encerrarPorMatricula(FaturasMatriculasModel faturasMatriculas){
        try {
            faturasMatriculasDAO.updateDataCancelamento(faturasMatriculas);
        }   catch (SQLException e){
            JOptionPane.showMessageDialog(null, "Houve um erro ao deletar a fatura!");
        }
    }

}
