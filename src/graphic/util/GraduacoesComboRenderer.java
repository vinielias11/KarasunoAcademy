package graphic.util;

import model.GraduacoesModel;

import javax.swing.*;
import java.awt.*;

public class GraduacoesComboRenderer extends DefaultListCellRenderer {
    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

        if (value instanceof GraduacoesModel) {
            GraduacoesModel graduacoesModel = (GraduacoesModel) value;

            setText(graduacoesModel.getNome());
        } else if (value == null) {
            setText("Selecione uma graduação...");
        }

        return this;
    }
}
