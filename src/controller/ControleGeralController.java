package controller;

import database.dao.ControleGeralDAO;
import model.AlunosModel;
import model.AssiduidadeModel;
import model.FaturasMatriculasModel;
import model.MatriculasModalidadesModel;

import java.sql.SQLException;
import java.util.Date;
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

    public void pagarFatura(Date dataVencimento, Integer idAluno) {
        try {
            controleGeralDAO.updateDataPagamentoFatura(dataVencimento, idAluno);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void marcarPresenca(Integer idAluno) {
        try {
            controleGeralDAO.insertAssiduidade(idAluno);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<AssiduidadeModel> recuperarAssiduidadePorCodigoAluno(Integer idAluno) {
        try {
            return controleGeralDAO.selectAssiduidadeByIdAluno(idAluno);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
