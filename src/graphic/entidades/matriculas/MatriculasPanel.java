package graphic.entidades.matriculas;

import controller.MatriculasController;
import graphic.entidades.base.EntidadesPanel;
import model.MatriculasModel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.List;

public class MatriculasPanel extends EntidadesPanel {
    public MatriculasPanel(JFrame cmpPai) {
        super(cmpPai);
    }

    @Override
    protected String getTitulo() {
        return "Matrículas";
    }

    @Override
    protected void deletar(String id) {
        MatriculasModel matriculasModel = new MatriculasModel();
        MatriculasController matriculasController = new MatriculasController();

        Integer idDeletar = Integer.parseInt(id);

        matriculasModel.setCodigoMatricula(idDeletar);
        matriculasController.deletar(matriculasModel);

        this.recarregaLista();
    }

    @Override
    protected String[] getColunasTabela() {
    return new String[]{"Id", "Aluno", "Data da Matrícula", "Dia de vencimento", "Data de Encerramento" };
    }

    @Override
    protected void montaDadosTabela(JTable tabela, DefaultTableModel tableModel) {
        MatriculasController matriculasController = new MatriculasController();
        List<Object> matriculasBanco = matriculasController.recuperarTodos();
        List<MatriculasModel> listaMatriculas = new ArrayList<>();

        matriculasBanco.forEach(matricula -> listaMatriculas.add((MatriculasModel) matricula));

        for (int i = 0; i < listaMatriculas.size(); i++) {
            Integer codigoMatricula = listaMatriculas.get(i).getCodigoMatricula();
            Integer codigoAluno = listaMatriculas.get(i).getCodigoAluno();
            Integer diaVencimento = listaMatriculas.get(i).getDiaVencimento();
            String dataMatricula = listaMatriculas.get(i).getDataMatricula().toString();
            String dataEncerramento = listaMatriculas.get(i).getDataEncerramento().toString();

            Object[] linha = { codigoMatricula, codigoAluno, dataMatricula, diaVencimento, dataEncerramento};

            tableModel.addRow(linha);
        }

        tabela.setModel(tableModel);
    }

    @Override
    protected void onDoubleClickLinha(String id) {
        MatriculasController matriculasController = new MatriculasController();
        MatriculasModel matriculaRecuperar = new MatriculasModel();

        matriculaRecuperar.setCodigoMatricula(Integer.parseInt(id));
        matriculaRecuperar = matriculasController.recuperarPorId(matriculaRecuperar);

        MatriculasCadastro matriculasCadastro = new MatriculasCadastro(matriculaRecuperar, this);

        matriculasCadastro.setVisible(true);
    }

    @Override
    protected void onClickNovo() {
        MatriculasCadastro matriculasCadastro = new MatriculasCadastro(this);

        matriculasCadastro.setVisible(true);
    }

}
