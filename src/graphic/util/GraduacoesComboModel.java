package graphic.util;


import model.GraduacoesModel;

import javax.swing.*;
import java.util.ArrayList;

public class GraduacoesComboModel extends AbstractListModel implements ComboBoxModel {
    private ArrayList<GraduacoesModel> lista;
    private GraduacoesModel selecionado;

    public GraduacoesComboModel(ArrayList<GraduacoesModel> lista) {
        this.lista = lista;
    }

    @Override
    public void setSelectedItem(Object anItem) {
        this.selecionado = (GraduacoesModel) anItem;
    }

    @Override
    public Object getSelectedItem() {
        return this.selecionado;
    }

    @Override
    public int getSize() {
        return this.lista.size();
    }

    @Override
    public Object getElementAt(int index) {
        return this.lista.get(index);
    }

    @Override
    public String toString() {
        return selecionado.getNome();
    }
}