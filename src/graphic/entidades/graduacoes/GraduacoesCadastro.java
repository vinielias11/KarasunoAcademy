package graphic.entidades.graduacoes;

import controller.GraduacoesController;
import controller.ModalidadesController;
import graphic.entidades.alunos.AlunosPanel;
import graphic.entidades.base.BindingListener;
import graphic.entidades.base.EntidadesCadastro;
import graphic.util.ModalidadesComboModel;
import graphic.util.ModalidadesComboRenderer;
import model.AlunosModel;
import model.GraduacoesModel;
import model.ModalidadesModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.util.List;
import java.util.Objects;

public class GraduacoesCadastro extends EntidadesCadastro {
    private GraduacoesModel graduacoesModel = new GraduacoesModel();
    private GraduacoesPanel graduacoesPanel;

    private boolean isEditando = false;
    public GraduacoesCadastro(GraduacoesPanel graduacoesPanel) {
        this.graduacoesPanel = graduacoesPanel;
        setSize(520, 280);
        criaComponentes(null);
    }
    public GraduacoesCadastro(GraduacoesModel dados, GraduacoesPanel graduacoesPanel) {
        this.graduacoesPanel = graduacoesPanel;
        setSize(520, 280);
        criaComponentes(dados);
    }

    @Override
    public void criarBotoes() {

        JButton btnFechar = new JButton("Cancelar");
        btnFechar.setBounds(80,180,120,30);
        btnFechar.setFont(new Font("Helvetica", Font.BOLD, 16));
        btnFechar.setBackground(new Color(255,92,92));
        btnFechar.setBorder(BorderFactory.createLineBorder(new Color(255, 92, 92)));
        add(btnFechar);

        btnFechar.addActionListener(e -> {
            dispose();
        });

        JButton btnSalvar = new JButton("Salvar");
        btnSalvar.setBounds(290,180,120,30);;
        btnSalvar.addActionListener(e -> onClickSalvar());
        btnSalvar.setFont(new Font("Helvetica", Font.BOLD, 16));
        btnSalvar.setBackground(new Color(87, 241, 87, 210));
        btnSalvar.setBorder(BorderFactory.createLineBorder(new Color(87, 241, 87, 210)));
        add(btnSalvar);
    }

    private void criaComponentes(GraduacoesModel dados){
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints c1 = new GridBagConstraints();
        panel.setSize(520,280);

        ModalidadesController modalidadesController = new ModalidadesController();
        ModalidadesComboModel modalidadesComboModel = modalidadesController.recuperaModalidadesParaComboBox();

        JLabel nome = new JLabel("Nome: ");
        JTextField nomeTxf = new JTextField(20);
        nomeTxf.getDocument().addDocumentListener(new BindingListener(graduacoesModel, "nome"));

        JLabel modalidades = new JLabel("Modalidades");
        JComboBox comboBoxModalidades = new JComboBox(modalidadesComboModel);
        comboBoxModalidades.setRenderer(new ModalidadesComboRenderer());
        comboBoxModalidades.setPreferredSize(new Dimension(224, 20));

        comboBoxModalidades.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                ModalidadesModel item = (ModalidadesModel) comboBoxModalidades.getSelectedItem();

                graduacoesModel.setIdModalidade(item.getId());
            }
        });

        c1.insets = new Insets(0, 0, 30, 35);
        c1.gridx = 0; c1.gridy = 0;
        panel.add(modalidades,c1);
        c1.gridx = 1; c1.gridy = 0;
        panel.add(comboBoxModalidades,c1);
        c1.gridx = 0; c1.gridy = 1;
        panel.add(nome, c1);
        c1.gridx = 1; c1.gridy = 1;
        panel.add(nomeTxf, c1);

        if (dados != null) {
            isEditando = true;

        }

        add(panel);
    }

    @Override
    protected void onClickSalvar() {
        GraduacoesController graduacoesController = new GraduacoesController();

        if (!isEditando) {
            graduacoesController.inserir(graduacoesModel, this);
        } else {
            graduacoesController.editar(graduacoesModel, this);
        }

        graduacoesPanel.recarregaLista();
    }
}
