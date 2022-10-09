package graphic.entidades.planos;

import controller.PlanosController;
import controller.PlanosController;
import graphic.entidades.planos.PlanosCadastro;
import graphic.entidades.base.EntidadesPanel;
import model.AlunosModel;
import model.PlanosModel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.List;

public class PlanosPanel extends EntidadesPanel {
    public PlanosPanel(JFrame cmpPai) {
        super(cmpPai);
    }

    @Override
    protected String getTitulo() {
        return "Planos";
    }

    @Override
    protected void deletar(String id) {
        PlanosModel planosModel = new PlanosModel();
        PlanosController planosController = new PlanosController();

        Integer idDeletar = Integer.parseInt(id);

        planosModel.setId(idDeletar);
        planosController.deletar(planosModel);

        this.recarregaLista();
    }

    @Override
    protected String[] getColunasTabela() {
        return new String[]{ "Id", "Modalidade", "Nome", "Valor mensal" };
    }

    @Override
    protected void montaDadosTabela(JTable tabela, DefaultTableModel tableModel) {
        PlanosController planosController = new PlanosController();
        List<Object> planosBanco = planosController.recuperarTodos();
        List<PlanosModel> listaPlanos = new ArrayList<>();

        planosBanco.forEach(plano -> listaPlanos.add((PlanosModel) plano));

        for (int i = 0; i < listaPlanos.size(); i++) {
            Integer id = listaPlanos.get(i).getId();
            String nomeModalidade = listaPlanos.get(i).getNomeModalidade();
            String nome = listaPlanos.get(i).getNome();
            Double valorMensal = listaPlanos.get(i).getValorMensal();

            Object[] linha = { id, nomeModalidade, nome, valorMensal };

            tableModel.addRow(linha);
        }

        tabela.setModel(tableModel);
    }

    @Override
    protected void onDoubleClickLinha(String id) {
        PlanosController planosController = new PlanosController();
        PlanosModel planoRecuperar = new PlanosModel();

        planoRecuperar.setId(Integer.parseInt(id));
        planoRecuperar = planosController.recuperarPorId(planoRecuperar);

        PlanosCadastro planosCadastro = new PlanosCadastro(planoRecuperar, this);

        planosCadastro.setVisible(true);
    }

    @Override
    protected void onClickNovo() {
        PlanosCadastro planosCadastro = new PlanosCadastro(this);

        planosCadastro.setVisible(true);
    }
}
