package graphic.entidades.modalidades;

import graphic.entidades.base.EntidadesPanel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

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

    }

    @Override
    protected String[] getColunasTabela() {
        return new String[0];
    }

    @Override
    protected void montaDadosTabela(JTable tabela, DefaultTableModel tableModel) {

    }

    @Override
    protected void onDoubleClickLinha(String id) {

    }

    @Override
    protected void onClickNovo() {

    }
}
