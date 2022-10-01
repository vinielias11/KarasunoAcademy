package graphic.entidades.graduacoes;

import graphic.entidades.base.EntidadesPanel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class GraduacoesPanel extends EntidadesPanel {
    public GraduacoesPanel(JFrame cmpPai) {
        super(cmpPai);
    }

    @Override
    protected String getTitulo() {
        return "Graduações";
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
