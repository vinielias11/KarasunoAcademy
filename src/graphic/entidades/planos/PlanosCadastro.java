package graphic.entidades.planos;

import controller.ModalidadesController;
import controller.PlanosController;
import graphic.entidades.base.BindingListener;
import graphic.entidades.base.EntidadesCadastro;
import graphic.util.ModalidadesComboModel;
import graphic.util.ModalidadesComboRenderer;
import model.ModalidadesModel;
import model.PlanosModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;

public class PlanosCadastro extends EntidadesCadastro {
    private PlanosModel planosModel = new PlanosModel();
    private PlanosPanel planosPanel;

    private boolean isEditando = false;

    public PlanosCadastro(PlanosPanel planosPanel) {
        this.planosPanel = planosPanel;
        setSize(520, 300);
        criaComponentes(null);
    }

    public PlanosCadastro(PlanosModel dados, PlanosPanel planosPanel) {
        this.planosPanel = planosPanel;
        setSize(520, 300);
        criaComponentes(dados);
    }

    @Override
    public void criarBotoes() {
        JButton btnFechar = new JButton("Cancelar");
        btnFechar.setBounds(80, 200, 120, 30);
        btnFechar.setFont(new Font("Helvetica", Font.BOLD, 16));
        btnFechar.setBackground(new Color(255,92,92));
        btnFechar.setBorder(BorderFactory.createLineBorder(new Color(255, 92, 92)));
        add(btnFechar);

        btnFechar.addActionListener(e -> {
            dispose();
        });

        JButton btnSalvar = new JButton("Salvar");
        btnSalvar.setBounds(290, 200, 120, 30);;
        btnSalvar.addActionListener(e -> onClickSalvar());
        btnSalvar.setFont(new Font("Helvetica", Font.BOLD, 16));
        btnSalvar.setBackground(new Color(87, 241, 87, 210));
        btnSalvar.setBorder(BorderFactory.createLineBorder(new Color(87, 241, 87, 210)));
        add(btnSalvar);
    }

    private void criaComponentes(PlanosModel dados) {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints c1 = new GridBagConstraints();
        panel.setSize(520, 220);

        ModalidadesController modalidadesController = new ModalidadesController();
        ArrayList<ModalidadesModel> modalidadesRecuperadas = modalidadesController.recuperaModalidadesParaComboBox();
        ModalidadesComboModel modalidadesComboModel = new ModalidadesComboModel(modalidadesRecuperadas);

        JLabel modalidades = new JLabel("Modalidade: ");
        JComboBox comboBoxModalidades = new JComboBox(modalidadesComboModel);
        comboBoxModalidades.setRenderer(new ModalidadesComboRenderer());
        comboBoxModalidades.setPreferredSize(new Dimension(224, 20));

        comboBoxModalidades.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                ModalidadesModel item = (ModalidadesModel) comboBoxModalidades.getSelectedItem();

                planosModel.setIdModalidade(item.getId());
            }
        });

        JLabel nome = new JLabel("Nome: ");
        JTextField nomeTxf = new JTextField(20);
        nomeTxf.getDocument().addDocumentListener(new BindingListener(planosModel, "nome"));

        JLabel valorMensal = new JLabel("Valor mensal: ");
        double min = 0000.00, value = 50.00, max = 1000.00, stepSize = 1.00;
        SpinnerNumberModel numberModel = new SpinnerNumberModel(value, min, max, stepSize);
        JSpinner valorMensalSpinner = new JSpinner(numberModel);
        JSpinner.NumberEditor editor = (JSpinner.NumberEditor)valorMensalSpinner.getEditor();
        DecimalFormat format = editor.getFormat();
        format.setMinimumFractionDigits(2);
        format.setDecimalFormatSymbols(new DecimalFormatSymbols(new Locale("pt-BR")));
        editor.getTextField().setHorizontalAlignment(SwingConstants.CENTER);
        valorMensalSpinner.setPreferredSize(new Dimension(150, 20));
        valorMensalSpinner.addChangeListener(e -> planosModel.setValorMensal((Double) valorMensalSpinner.getValue()));
        planosModel.setValorMensal((Double) valorMensalSpinner.getValue());

        c1.insets = new Insets(0, 0, 10, 35);
        c1.gridx = 0; c1.gridy = 0; c1.anchor = GridBagConstraints.EAST;
        panel.add(modalidades, c1);
        c1.gridx = 1; c1.gridy = 0;
        panel.add(comboBoxModalidades, c1);
        c1.gridx = 0; c1.gridy = 1;
        panel.add(nome, c1);
        c1.gridx = 1; c1.gridy = 1;
        panel.add(nomeTxf, c1);
        c1.gridx = 0; c1.gridy = 2;
        panel.add(valorMensal, c1);
        c1.gridx = 1; c1.gridy = 2; c1.anchor = GridBagConstraints.WEST;
        panel.add(valorMensalSpinner, c1);

        if (dados != null) {
            isEditando = true;
            planosModel.setId(dados.getId());
            nomeTxf.setText(dados.getNome());
            valorMensalSpinner.setValue(dados.getValorMensal());

            modalidadesRecuperadas.forEach(modalidadeRecuperada -> {
                if (Objects.equals(modalidadeRecuperada.getId(), dados.getIdModalidade())) {
                    comboBoxModalidades.setSelectedItem(modalidadeRecuperada);
                }
            });
        }

        add(panel);
    }

    @Override
    protected void onClickSalvar() {
        PlanosController planosController = new PlanosController();

        if (!validaCamposAntesDeSalvar()) return;

        if (!isEditando) {
            planosController.inserir(planosModel, this);
        } else {
            planosController.editar(planosModel, this);
        }

        planosPanel.recarregaLista();
    }

    private boolean validaCamposAntesDeSalvar() {
        if (planosModel.getIdModalidade() == null) {
            JOptionPane.showMessageDialog(null, "Insira uma modalidade.");
            return false;
        }

        if (planosModel.getNome() == null || planosModel.getNome().trim().length() == 0) {
            JOptionPane.showMessageDialog(null, "Insira um nome válido.");
            return false;
        }

        if (planosModel.getValorMensal() == 0) {
            JOptionPane.showMessageDialog(null, "Insira um valor mensal válido.");
            return false;
        }

        return true;
    }
}
