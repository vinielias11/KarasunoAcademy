package graphic.util;

import model.PlanosModel;

import javax.swing.*;
import java.util.ArrayList;

public class PlanosComboModel extends AbstractListModel implements ComboBoxModel{
    private ArrayList<PlanosModel> lista;
    private PlanosModel selecionado;
    public PlanosComboModel(ArrayList<PlanosModel> lista) {
        this.lista = lista;
    }

    @Override
    public void setSelectedItem(Object anItem) {
        this.selecionado = (PlanosModel) anItem;
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
