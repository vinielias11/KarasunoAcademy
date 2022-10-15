package controller;

import database.dao.MatriculasModalidadesDAO;
import model.MatriculasModalidadesModel;

import javax.swing.*;
import java.sql.SQLException;
import java.util.List;

public class MatriculasModalidadesController {

    private MatriculasModalidadesDAO matriculasModalidadesDAO;

    public MatriculasModalidadesController() {
        matriculasModalidadesDAO = new MatriculasModalidadesDAO();
    }

    public List<Object> recuperarTodos() {
        try {
            return matriculasModalidadesDAO.select();
        }
        catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Houve um erro ao recuperar as matrículas!");
            throw new RuntimeException(e);
        }
    }

    public List<Object> recuperarMatriculasAluno(Integer codigoMatricula){
        try{
            return matriculasModalidadesDAO.selectFromAluno(codigoMatricula);
        }catch (SQLException e){
            JOptionPane.showMessageDialog(null, "Houve um erro ao recuperar as matrículas!");
            throw new RuntimeException(e);
        }
    }

    public void inserir(Object matriculaModalidade, JDialog cadastro) {
        try {
            matriculasModalidadesDAO.insert(matriculaModalidade);
            cadastro.dispose();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Houve um erro ao inserir a matrícula!");
        }
    }

    public void editar(Object matriculaModalidade, JDialog cadastro) {
        try {
            matriculasModalidadesDAO.update(matriculaModalidade);
            cadastro.dispose();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Houve um erro ao editar a matrícula!");
        }
    }

    public MatriculasModalidadesModel recuperarPorId(MatriculasModalidadesModel matriculaModalidade) {
        try {
            return matriculasModalidadesDAO.selectById(matriculaModalidade);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Houve um erro ao recuperar a matrícula!");
            throw new RuntimeException(e);
        }
    }

    public void deletar(MatriculasModalidadesModel matriculaModalidade){
        try {
            matriculasModalidadesDAO.delete(matriculaModalidade);
        }   catch (SQLException e){
            JOptionPane.showMessageDialog(null, "Houve um erro ao deletar a matrícula!");
        }
    }

//    public ArrayList<MatriculasModalidadesModel> recuperaMatriculasParaComboBox() {
//        ArrayList<MatriculasModalidadesModel> listaMatriculasModalidades = new ArrayList<>();
//
//        try {
//            List<Object> matriculasModalidadesRecuperar = matriculasModalidadesDAO.select();
//            matriculasModalidadesRecuperar.forEach(matriculasModalidades -> listaMatriculasModalidades.add((MatriculasModalidadesModel) matriculasModalidades));
//        } catch (SQLException e) {
//            JOptionPane.showMessageDialog(null, "Houve um erro ao recuperar as matrículas!");
//            throw new RuntimeException(e);
//        }
//
//        return listaMatriculasModalidades;
//    }
}
