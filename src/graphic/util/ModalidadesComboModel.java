package graphic.util;

import model.ModalidadesModel;

import javax.swing.*;
import java.util.ArrayList;

public class ModalidadesComboModel extends AbstractListModel implements ComboBoxModel {
    private ArrayList<ModalidadesModel> lista;
    private ModalidadesModel selecionado;

    public ModalidadesComboModel(ArrayList<ModalidadesModel> lista) {
        this.lista = lista;
    }

    @Override
    public void setSelectedItem(Object anItem) {
        this.selecionado = (ModalidadesModel) anItem;
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

}
