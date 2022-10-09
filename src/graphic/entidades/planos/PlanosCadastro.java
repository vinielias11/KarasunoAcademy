package graphic.entidades.planos;

import controller.ModalidadesController;
import graphic.entidades.base.EntidadesCadastro;
import graphic.util.ModalidadesComboModel;
import graphic.util.ModalidadesComboRenderer;
import model.AlunosModel;
import model.ModalidadesModel;
import model.PlanosModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.util.List;
import java.util.Objects;

public class PlanosCadastro extends EntidadesCadastro {
    private PlanosModel planosModel = new PlanosModel();
    private PlanosPanel planosPanel;

    public PlanosCadastro(PlanosPanel planosPanel) {
        this.planosPanel = planosPanel;
        criaComponentes(null);
    }

    public PlanosCadastro(AlunosModel dados, PlanosPanel planosPanel) {
        this.planosPanel = planosPanel;
        criaComponentes(dados);
    }

    private void criaComponentes(AlunosModel dados) {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints c1 = new GridBagConstraints();
        panel.setSize(520,580);

        ModalidadesController modalidadesController = new ModalidadesController();
        ModalidadesComboModel modalidadesComboModel = modalidadesController.recuperaModalidadesParaComboBox();

        JLabel modalidades = new JLabel("Modalidades");
        JComboBox comboBoxModalidades = new JComboBox(modalidadesComboModel);
        comboBoxModalidades.setRenderer(new ModalidadesComboRenderer());
        comboBoxModalidades.setPreferredSize(new Dimension(224, 20));

        comboBoxModalidades.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                ModalidadesModel item = (ModalidadesModel) comboBoxModalidades.getSelectedItem();

                planosModel.setIdModalidade(item.getId());
            }
        });

        c1.insets = new Insets(0, 0, 10, 35);
        c1.gridx = 0; c1.gridy = 0; c1.anchor = GridBagConstraints.EAST;
        panel.add(modalidades, c1);
        c1.gridx = 1; c1.gridy = 0;
        panel.add(comboBoxModalidades, c1);

        add(panel);
    }

    @Override
    protected void onClickSalvar() {

    }
}
