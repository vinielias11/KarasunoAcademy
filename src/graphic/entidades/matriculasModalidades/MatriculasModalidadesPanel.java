package graphic.entidades.matriculasModalidades;

import controller.MatriculasModalidadesController;
import graphic.entidades.base.EntidadesPanel;
import model.MatriculasModalidadesModel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MatriculasModalidadesPanel extends EntidadesPanel {

    public MatriculasModalidadesPanel(JFrame cmpPai) {
        super(cmpPai);
    }

    @Override
    protected String getTitulo() {
        return "Modalidades Matrículas";
    }

    @Override
    protected void deletar(String id) {
        MatriculasModalidadesModel matriculasModalidadesModel = new MatriculasModalidadesModel();
        MatriculasModalidadesController matriculasModalidadesController = new MatriculasModalidadesController();

        Integer idDeletar = Integer.parseInt(id);

        matriculasModalidadesModel.setCodigoMatricula(idDeletar);
        matriculasModalidadesController.deletar(matriculasModalidadesModel);

        this.recarregaLista();
    }

    @Override
    protected String[] getColunasTabela() {
        return new String[]{"Código Matrícula", "Modalidade", "Graduação", "Plano", "Data Início", "Data Fim" };
    }

    @Override
    protected void montaDadosTabela(JTable tabela, DefaultTableModel tableModel) {
        MatriculasModalidadesController matriculasModalidadesController = new MatriculasModalidadesController();
        List<Object> matriculasModalidadesBanco = matriculasModalidadesController.recuperarTodos();
        List<MatriculasModalidadesModel> listaMatriculasModalidades = new ArrayList<>();

        matriculasModalidadesBanco.forEach(matriculaModalidade -> listaMatriculasModalidades.add((MatriculasModalidadesModel) matriculaModalidade));

        for (int i = 0; i < listaMatriculasModalidades.size(); i++) {
            Integer codigoMatricula = listaMatriculasModalidades.get(i).getCodigoMatricula();
            String nomeModalidade = listaMatriculasModalidades.get(i).getNomeModalidade();
            String nomeGraduacao = listaMatriculasModalidades.get(i).getNomeGraduacao();
            String nomePlano = listaMatriculasModalidades.get(i).getNomePlano();
            Date dataInicio = listaMatriculasModalidades.get(i).getDataInicio();
            Date dataFim = listaMatriculasModalidades.get(i).getDataFim();

            Object[] linha = { codigoMatricula, nomeModalidade, nomeGraduacao, nomePlano, dataInicio, dataFim };

            tableModel.addRow(linha);
        }

        tabela.setModel(tableModel);
    }

    @Override
    protected void onDoubleClickLinha(String id) {
        MatriculasModalidadesController matriculasModalidadesController = new MatriculasModalidadesController();
        MatriculasModalidadesModel matriculaModalidadeRecuperar = new MatriculasModalidadesModel();

        matriculaModalidadeRecuperar.setCodigoMatricula(Integer.parseInt(id));
        matriculaModalidadeRecuperar = matriculasModalidadesController.recuperarPorId(matriculaModalidadeRecuperar);

        MatriculasModalidadesCadastro matriculasModalidadesCadastro = new MatriculasModalidadesCadastro(matriculaModalidadeRecuperar, this);

        matriculasModalidadesCadastro.setVisible(true);
    }

    @Override
    protected void onClickNovo() {
        MatriculasModalidadesCadastro matriculasModalidadesCadastro = new MatriculasModalidadesCadastro(this);

        matriculasModalidadesCadastro.setVisible(true);
    }
}
