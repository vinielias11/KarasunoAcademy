package graphic.entidades.modalidades;

import controller.AlunosController;
import controller.ModalidadesController;
import graphic.entidades.alunos.AlunosCadastro;
import graphic.entidades.base.EntidadesPanel;
import model.AlunosModel;
import model.ModalidadesModel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.List;

public class ModalidadesPanel extends EntidadesPanel {
    public ModalidadesPanel(JFrame cmpPai) {
        super(cmpPai);
    }

    @Override
    protected String getTitulo() {
        return "Modalidades";
    }

    @Override
    protected void deletar(String id) {
        ModalidadesModel modalidadesModel = new ModalidadesModel();
        ModalidadesController modalidadesController = new ModalidadesController();

        Integer idDeletar = Integer.parseInt(id);

        modalidadesModel.setId(idDeletar);
        modalidadesController.deletar(modalidadesModel);

        this.recarregaLista();
    }

    @Override
    protected String[] getColunasTabela() {
        return new String[]{"Id", "Nome"};
    }

    @Override
    protected void montaDadosTabela(JTable tabela, DefaultTableModel tableModel) {
        ModalidadesController modalidadesController = new ModalidadesController();
        List<Object> modalidadesBanco = modalidadesController.recuperarTodos();
        List<ModalidadesModel> listaModalidades = new ArrayList<>();

        modalidadesBanco.forEach(modalidade -> listaModalidades.add((ModalidadesModel) modalidade ));

        for(int i = 0; i < listaModalidades.size(); i++){
            Integer id = listaModalidades.get(i).getId();
            String nome = listaModalidades.get(i).getNome();

            Object[] linha = {id, nome};

            tableModel.addRow(linha);
        }

        tabela.setModel(tableModel);
    }

    @Override
    protected void onDoubleClickLinha(String id) {
        ModalidadesController modalidadesController = new ModalidadesController();
        ModalidadesModel modalidadesRecuperar = new ModalidadesModel();

        modalidadesRecuperar.setId(Integer.parseInt(id));
        modalidadesRecuperar = modalidadesController.recuperarPorID(modalidadesRecuperar);

        ModalidadesCadastro modalidadesCadastro = new ModalidadesCadastro(modalidadesRecuperar, this);
        modalidadesCadastro.setVisible(true);
    }

    @Override
    protected void onClickNovo() {
        ModalidadesCadastro modalidadesCadastro = new ModalidadesCadastro(this);

        modalidadesCadastro.setVisible(true);
    }
}
