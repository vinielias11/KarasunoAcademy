package graphic.entidades.faturasMatriculas;

import controller.FaturasMatriculasController;
import graphic.entidades.base.EntidadesPanel;
import model.FaturasMatriculasModel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.List;

public class FaturasMatriculasPanel extends EntidadesPanel {

    public FaturasMatriculasPanel(JFrame cmpPai) { super(cmpPai); }

    @Override
    protected String getTitulo() {
        return "Faturas";
    }

    @Override
    protected void deletar(String id) {
        FaturasMatriculasModel faturasMatriculasModel = new FaturasMatriculasModel();
        FaturasMatriculasController faturasMatriculasController = new FaturasMatriculasController();

        Integer idDeletar = Integer.parseInt(id);

        faturasMatriculasModel.setId(idDeletar);
        faturasMatriculasController.deletar(faturasMatriculasModel);

        this.recarregaLista();
    }

    @Override
    protected String[] getColunasTabela() {
        return new String[]{"Id", "Código da Matrícula", "Data de Vencimento", "Valor", "Data de Pagamento", "Data de Cancelamento"};
    }

    @Override
    protected void montaDadosTabela(JTable tabela, DefaultTableModel tableModel) {
        FaturasMatriculasController faturasMatriculasController = new FaturasMatriculasController();
        List<Object> faturasMatriculasBanco = faturasMatriculasController.recuperarTodos();
        List<FaturasMatriculasModel> listaFaturasMatriculas = new ArrayList<>();

        faturasMatriculasBanco.forEach(faturasMatriculas -> listaFaturasMatriculas.add((FaturasMatriculasModel) faturasMatriculas));

        for (int i = 0; i < listaFaturasMatriculas.size(); i++) {
            Integer id = listaFaturasMatriculas.get(i).getId();
            Integer codigoMatricula = listaFaturasMatriculas.get(i).getCodigoMatricula();
            String dataVencimento = String.valueOf(listaFaturasMatriculas.get(i).getDataVencimento());
            Double valor = listaFaturasMatriculas.get(i).getValor();
            String dataPagamento = String.valueOf(listaFaturasMatriculas.get(i).getDataPagamento());
            String dataCancelamento = String.valueOf(listaFaturasMatriculas.get(i).getDataCancelamento());

            Object[] linha = { id, codigoMatricula, dataVencimento, valor, dataPagamento, dataCancelamento };

            tableModel.addRow(linha);
        }

        tabela.setModel(tableModel);
    }

    @Override
    protected void onDoubleClickLinha(String id) {
        FaturasMatriculasController faturasMatriculasController = new FaturasMatriculasController();
        FaturasMatriculasModel faturasMatriculasRecuperar = new FaturasMatriculasModel();

        faturasMatriculasRecuperar.setId(Integer.parseInt(id));
        faturasMatriculasRecuperar = faturasMatriculasController.recuperarPorId(faturasMatriculasRecuperar);

        FaturasMatriculasCadastro faturasMatriculasCadastro = new FaturasMatriculasCadastro(faturasMatriculasRecuperar, this);

        faturasMatriculasCadastro.setVisible(true);
    }

    @Override
    protected void onClickNovo() {
        FaturasMatriculasCadastro faturasMatriculasCadastro = new FaturasMatriculasCadastro(this);

        faturasMatriculasCadastro.setVisible(true);
    }

}

