package graphic.entidades.graduacoes;

import controller.AlunosController;
import controller.GraduacoesController;
import graphic.entidades.alunos.AlunosCadastro;
import graphic.entidades.base.EntidadesPanel;
import model.AlunosModel;
import model.GraduacoesModel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.List;

public class GraduacoesPanel extends EntidadesPanel {
    public GraduacoesPanel(JFrame cmpPai) {
        super(cmpPai);
    }

    @Override
    protected String getTitulo() {
        return "Graduações";
    }

    @Override
    protected void deletar(String id) {
        GraduacoesModel graduacoesModel = new GraduacoesModel();
        GraduacoesController graduacoesController = new GraduacoesController();

        Integer idDeletar = Integer.parseInt(id);

        graduacoesModel.setId(idDeletar);
        graduacoesController.deletar(graduacoesModel);

        this.recarregaLista();
    }

    @Override
    protected String[] getColunasTabela() {
        return new String[]{"Id", "Nome", "Modalidade"};
    }

    @Override
    protected void montaDadosTabela(JTable tabela, DefaultTableModel tableModel) {
        GraduacoesController graduacoesController = new GraduacoesController();
        List<Object> graduacoesBanco = graduacoesController.recuperarTodos();
        List<GraduacoesModel> listaGraduacoes = new ArrayList<>();

        graduacoesBanco.forEach(graduacoes -> listaGraduacoes.add((GraduacoesModel) graduacoes));

        for (int i = 0; i < listaGraduacoes.size(); i++) {
            Integer id = listaGraduacoes.get(i).getId();
            String nome = listaGraduacoes.get(i).getNome();
            String modalidade = listaGraduacoes.get(i).getNomeModalidade();
;

            Object[] linha = { id, nome, modalidade };

            tableModel.addRow(linha);
        }

        tabela.setModel(tableModel);
    }

    @Override
    protected void onDoubleClickLinha(String id) {
        GraduacoesController graduacoesController = new GraduacoesController();
        GraduacoesModel graduacoesRecuperar = new GraduacoesModel();

        graduacoesRecuperar.setId(Integer.parseInt(id));
        graduacoesRecuperar = graduacoesController.recuperarPorId(graduacoesRecuperar);

        GraduacoesCadastro graduacoesCadastro = new GraduacoesCadastro(graduacoesRecuperar, this);

        graduacoesCadastro.setVisible(true);
    }

    @Override
    protected void onClickNovo() {
        GraduacoesCadastro graduacoesCadastro = new GraduacoesCadastro(this);

        graduacoesCadastro.setVisible(true);
    }
}
