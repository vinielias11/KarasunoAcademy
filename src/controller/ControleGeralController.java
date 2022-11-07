package controller;

import database.dao.ControleGeralDAO;
import model.AlunosModel;
import model.FaturasMatriculasModel;
import model.MatriculasModalidadesModel;

import java.sql.SQLException;
import java.util.List;

public class ControleGeralController {
    private ControleGeralDAO controleGeralDAO;

    public ControleGeralController() {
        controleGeralDAO = new ControleGeralDAO();
    }

    public AlunosModel recuperarAlunoPorCodigo(Integer codigo) {
        try {
            return controleGeralDAO.selectAlunoByCodigo(codigo);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<MatriculasModalidadesModel> recuperarMatriculasModalidadesPorCodigoAluno(Integer idAluno) {
        try {
            return controleGeralDAO.selectMatriculasModalidadesByIdAluno(idAluno);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<FaturasMatriculasModel> recuperarFaturasMatriculasPorCodigoAluno(Integer idAluno) {
        try {
            return controleGeralDAO.selectFaturasMatriculasByIdAluno(idAluno);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
