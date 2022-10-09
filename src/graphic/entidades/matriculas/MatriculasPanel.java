package graphic.entidades.matriculas;

import graphic.entidades.base.EntidadesPanel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

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

    }

    @Override
    protected String[] getColunasTabela() {
        return new String[0];
    }

    @Override
    protected void montaDadosTabela(JTable tabelam, DefaultTableModel tableModel) {

    }

    @Override
    protected void onDoubleClickLinha(String id) {

    }

    @Override
    protected void onClickNovo() {

    }

}
