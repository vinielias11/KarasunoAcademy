package graphic.entidades.matriculasModalidades;

import controller.*;
import graphic.entidades.base.EntidadesCadastro;
import graphic.util.*;
import model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class MatriculasModalidadesCadastro extends EntidadesCadastro {

    private MatriculasModalidadesModel matriculasModalidadesModel = new MatriculasModalidadesModel();
    private MatriculasModalidadesPanel matriculasModalidadesPanel;

    private boolean isEditando = false;
    public MatriculasModalidadesCadastro(MatriculasModalidadesPanel matriculasPanel) {
        this.matriculasModalidadesPanel = matriculasModalidadesPanel;
        setSize(520,280);
        criaComponentes(null);
    }

    public MatriculasModalidadesCadastro(MatriculasModalidadesModel dados, MatriculasModalidadesPanel matriculasModalidadesPanel) {
        this.matriculasModalidadesPanel = matriculasModalidadesPanel;
        setSize(520,280);
        criaComponentes(dados);
    }

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

    private void criaComponentes(MatriculasModalidadesModel dados) {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints c1 = new GridBagConstraints();
        panel.setSize(520,280);

        MatriculasModalidadesController matriculasModalidadesController = new MatriculasModalidadesController();

        AlunosController alunosController = new AlunosController();
        ArrayList<AlunosModel> alunosRecuperados = alunosController.recuperaAlunosParaComboBox();
        AlunosComboModel alunosComboModel = new AlunosComboModel(alunosRecuperados);

        JLabel alunos = new JLabel("Aluno: ");
        JComboBox comboBoxAlunos = new JComboBox<>(alunosComboModel);
        comboBoxAlunos.setRenderer(new AlunosComboRender());
        comboBoxAlunos.setPreferredSize(new Dimension(224,20));

//        JLabel codigoMatriculaLbl = new JLabel("Código Matrícula: ");
//        JTextField codigoMatriculaTxf = new JTextField();
//        codigoMatriculaTxf.setEditable(false);
//
//        comboBoxAlunos.addItemListener(e -> {
//            if (e.getStateChange() == ItemEvent.SELECTED) {
//                AlunosModel item = (AlunosModel) comboBoxAlunos.getSelectedItem();
//
//                Integer codigoMatricula = matriculasController.recuperaCodigoPorCodigoAluno(item.getId());
//                matriculasModalidadesModel.setCodigoMatricula(codigoMatricula);
//                codigoMatriculaTxf.setText(String.valueOf(codigoMatricula));
//            }
//        });
//
//        ModalidadesController modalidadesController = new ModalidadesController();
//        ArrayList<ModalidadesModel> modalidadesRecuperadas = modalidadesController.recuperaModalidadesParaComboBox();
//        ModalidadesComboModel modalidadesComboModel = new ModalidadesComboModel(modalidadesRecuperadas);
//
//        JLabel modalidades = new JLabel("Modalidade: ");
//        JComboBox comboBoxModalidades = new JComboBox(modalidadesComboModel);
//        comboBoxModalidades.setRenderer(new ModalidadesComboRenderer());
//        comboBoxModalidades.setPreferredSize(new Dimension(224, 20));
//
//        comboBoxModalidades.setSelectedItem(modalidadesRecuperadas.get(0));
//        comboBoxModalidades.addItemListener(e -> {
//            if (e.getStateChange() == ItemEvent.SELECTED) {
//                ModalidadesModel item = (ModalidadesModel) comboBoxModalidades.getSelectedItem();
//
//                matriculasModalidadesModel.setModalidade(item.getId());
//
//            }
//        });
//
//        ModalidadesModel modalidadeSelecionada = (ModalidadesModel) comboBoxModalidades.getSelectedItem();
//        GraduacoesController graduacoesController = new GraduacoesController();
//        ArrayList<GraduacoesModel> graduacoesRecuperadas = graduacoesController.recuperaGraduacoesParaComboBox(modalidadeSelecionada.getId());
//        GraduacoesComboModel graduacoesComboModel = new GraduacoesComboModel(graduacoesRecuperadas);
//
//        JLabel graduacao = new JLabel("Graduação: ");
//        JComboBox comboBoxGraduacao = new JComboBox(graduacoesComboModel);
//        comboBoxGraduacao.setRenderer(new GraduacoesComboRenderer());
//        comboBoxGraduacao.setPreferredSize(new Dimension(224, 20));
//
//        comboBoxGraduacao.setSelectedItem(graduacoesRecuperadas.get(0));
//        comboBoxGraduacao.addItemListener(e -> {
//            if (e.getStateChange() == ItemEvent.SELECTED) {
//                GraduacoesModel item = (GraduacoesModel) comboBoxGraduacao.getSelectedItem();
//
//                matriculasModalidadesModel.setGraduacao(item.getId());
//            }
//        });
//
//
//        JLabel graduacao = new JLabel("Graduação: ");
//        JComboBox comboBoxGraduacao = new JComboBox(graduacoesComboModel);
//        comboBoxGraduacao.setRenderer(new GraduacoesComboRenderer());
//        comboBoxGraduacao.setPreferredSize(new Dimension(224, 20));
//
//        comboBoxGraduacao.addItemListener(e -> {
//            if (e.getStateChange() == ItemEvent.SELECTED) {
//                GraduacoesModel item = (GraduacoesModel) comboBoxGraduacao.getSelectedItem();
//
//                matriculasModalidadesModel.setGraduacao(item.getId());
//            }
//        });
//
//
//        JLabel dataInicio = new JLabel("Data Início: ");
//        SpinnerDateModel dateModel = new SpinnerDateModel(new Date(), null, null, Calendar.DAY_OF_MONTH);
//        JSpinner dateSpinner = new JSpinner(dateModel);
//        dateSpinner.setEditor(new JSpinner.DateEditor(dateSpinner, "dd/MM/yyyy"));
//        JComponent editor = dateSpinner.getEditor();
//        JSpinner.DefaultEditor spinnerEditor = (JSpinner.DefaultEditor)editor;
//        spinnerEditor.getTextField().setHorizontalAlignment(JTextField.CENTER);
//        Dimension d = new Dimension(120,20);
//        dateSpinner.setPreferredSize(d);
//        dateSpinner.addChangeListener(e -> matriculasModalidadesModel.setDataInicio((Date) dateSpinner.getValue()));
//        matriculasModalidadesModel.setDataInicio(new Date());
//
//
//        c1.insets = new Insets(0, 0, 30, 35);
//        c1.gridx = 0; c1.gridy = 0; c1.anchor = GridBagConstraints.EAST;
//        panel.add(alunos, c1);
//        c1.gridx = 1;
//        panel.add(comboBoxAlunos, c1);
//        c1.gridx = 0; c1.gridy = 1; c1.anchor = GridBagConstraints.EAST;
//        panel.add(diaVencimento, c1);
//        c1.gridx = 1; c1.anchor = GridBagConstraints.WEST;
//        panel.add(diaVencimentoSpn, c1);
//
//
//        if (dados != null) {
//            isEditando = true;
//            matriculasModalidadesModel.setCodigoMatricula(dados.getCodigoMatricula());
//            diaVencimentoSpn.setValue(dados.getDiaVencimento());
//            alunosRecuperados.forEach(alunoRecuperado -> {
//                if (Objects.equals(alunoRecuperado.getId(), dados.getCodigoAluno())) {
//                    comboBoxAlunos.setSelectedItem(alunoRecuperado);
//                }
//            });
//        }

        add(panel);
    }

    @Override
    protected void onClickSalvar() {
        MatriculasModalidadesController matriculasModalidadesController = new MatriculasModalidadesController();

        if (!isEditando) {
            matriculasModalidadesController.inserir(matriculasModalidadesModel, this);
        } else {
            matriculasModalidadesController.editar(matriculasModalidadesModel, this);
        }

        matriculasModalidadesPanel.recarregaLista();
    }
}
