package graphic.util;

import model.PlanosModel;

import javax.swing.*;
import java.awt.*;

public class PlanosComboRenderer extends DefaultListCellRenderer {
    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

        if (value instanceof PlanosModel) {
            PlanosModel planosModel = (PlanosModel) value;

            setText(planosModel.getNome());
        } else if (value == null) {
            setText("Selecione um plano...");
        }

        return this;
    }
}
