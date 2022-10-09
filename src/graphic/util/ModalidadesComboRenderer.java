package graphic.util;

import model.ModalidadesModel;

import javax.swing.*;
import java.awt.*;

public class ModalidadesComboRenderer extends DefaultListCellRenderer {
    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

        if (value instanceof ModalidadesModel) {
            ModalidadesModel modalidadesModel = (ModalidadesModel) value;

            setText(modalidadesModel.getNome());
        } else if (value == null) {
            setText("Selecione uma modalidade...");
        }

        return this;
    }
}
