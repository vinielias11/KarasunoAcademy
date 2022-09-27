package graphic.sobre;

import graphic.entidades.base.EntidadesPanel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class SobrePanel extends EntidadesPanel {
    public SobrePanel(JFrame cmpPai) {
        super(cmpPai);
    }

    @Override
    protected String[] getColunasTabela() {
        return new String[0];
    }

    @Override
    protected void montaDadosTabela(JTable tabela, DefaultTableModel tableModel) {

    }

    @Override
    protected void onClickNovo() {

    }
}
