package graphic.entidades.matriculas;

import controller.AlunosController;
import controller.MatriculasController;
import controller.MatriculasModalidadesController;
import graphic.entidades.base.EntidadesCadastro;
import graphic.util.AlunosComboModel;
import graphic.util.AlunosComboRender;
import model.AlunosModel;
import model.MatriculasModalidadesModel;
import model.MatriculasModel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class MatriculasCadastro extends EntidadesCadastro {
    private MatriculasModel matriculasModel = new MatriculasModel();
    private MatriculasPanel matriculasPanel;

    private boolean isEditando = false;
    private Integer alunoEditando = 0;
    public MatriculasCadastro(MatriculasPanel matriculasPanel) {
        this.matriculasPanel = matriculasPanel;
        setSize(820,680);
        criaComponentes(null);
    }

    public MatriculasCadastro(MatriculasModel dados, MatriculasPanel matriculasPanel) {
        this.matriculasPanel = matriculasPanel;
        setSize(820,680);
        criaComponentes(dados);
    }

    public void criarBotoes() {
//        JButton btnFechar = new JButton("Cancelar");
//        btnFechar.setBounds(80,180,120,30);
//        btnFechar.setFont(new Font("Helvetica", Font.BOLD, 16));
//        btnFechar.setBackground(new Color(255,92,92));
//        btnFechar.setBorder(BorderFactory.createLineBorder(new Color(255, 92, 92)));
//        add(btnFechar);
//
//        btnFechar.addActionListener(e -> {
//            dispose();
//        });
//
//        JButton btnSalvar = new JButton("Salvar");
//        btnSalvar.setBounds(290,180,120,30);;
//        btnSalvar.addActionListener(e -> onClickSalvar());
//        btnSalvar.setFont(new Font("Helvetica", Font.BOLD, 16));
//        btnSalvar.setBackground(new Color(87, 241, 87, 210));
//        btnSalvar.setBorder(BorderFactory.createLineBorder(new Color(87, 241, 87, 210)));
//        add(btnSalvar);
    }

    private void criaComponentes(MatriculasModel dados) {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints c1 = new GridBagConstraints();
        panel.setSize(800,660);

        AlunosController alunosController = new AlunosController();
        ArrayList<AlunosModel> alunosRecuperados = alunosController.recuperaAlunosParaComboBox();
        AlunosComboModel alunosComboModel = new AlunosComboModel(alunosRecuperados);

        JLabel alunos = new JLabel("Aluno: ");
        JComboBox comboBoxAlunos = new JComboBox<>(alunosComboModel);
        comboBoxAlunos.setRenderer(new AlunosComboRender());
        comboBoxAlunos.setPreferredSize(new Dimension(224,20));

        JLabel diaVencimento = new JLabel("Dia de Vencimento: ");
        int min = 1, value = 1, max = 20, stepSize = 1;
        SpinnerNumberModel numberModel = new SpinnerNumberModel(value, min, max, stepSize);
        JSpinner diaVencimentoSpn = new JSpinner(numberModel);
        diaVencimentoSpn.setPreferredSize(new Dimension(50,20));
        matriculasModel.setDiaVencimento((Integer) diaVencimentoSpn.getValue());
        diaVencimentoSpn.addChangeListener(e -> matriculasModel.setDiaVencimento((Integer) diaVencimentoSpn.getValue()));

        // Cria a tabela listando as modalidades da matrícula
        JTable tabelaMatriculasModalidade = gerarTabela();

        comboBoxAlunos.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                AlunosModel item = (AlunosModel) comboBoxAlunos.getSelectedItem();
                matriculasModel.setCodigoAluno(item.getId());
                gerarTabela();
            }
        });

        c1.insets = new Insets(0, 0, 20, 35);
        c1.gridx = 0; c1.gridy = 0; c1.anchor = GridBagConstraints.EAST;
        panel.add(alunos, c1);
        c1.gridx = 1;
        panel.add(comboBoxAlunos, c1);
        c1.gridx = 0; c1.gridy = 1; c1.anchor = GridBagConstraints.EAST;
        panel.add(diaVencimento, c1);
        c1.gridx = 1; c1.anchor = GridBagConstraints.WEST;
        panel.add(diaVencimentoSpn, c1);


        c1.gridx = 0; c1.gridy = 3; c1.anchor = GridBagConstraints.WEST;
        c1.insets = new Insets(0, 0, 0, 0);
        panel.add(tabelaMatriculasModalidade, c1);


        if (dados != null) {
            isEditando = true;
            matriculasModel.setCodigoMatricula(dados.getCodigoMatricula());
            alunoEditando = dados.getCodigoAluno();
            diaVencimentoSpn.setValue(dados.getDiaVencimento());
            alunosRecuperados.forEach(alunoRecuperado -> {
                if (Objects.equals(alunoRecuperado.getId(), dados.getCodigoAluno())) {
                    comboBoxAlunos.setSelectedItem(alunoRecuperado);
                }
            });
        }

        add(panel);
    }


    @Override
    protected void onClickSalvar() {
        MatriculasController matriculasController = new MatriculasController();

        if(!validaCamposAntesDeSalvar()) return;

        if (!isEditando) {
            matriculasController.inserir(matriculasModel, this);
        } else {
            matriculasController.editar(matriculasModel, this);
        }

        matriculasPanel.recarregaLista();
    }

    private boolean validaCamposAntesDeSalvar(){
        MatriculasController matriculasController = new MatriculasController();
        List<Object> matriculasBanco = matriculasController.recuperarTodos();
        List<MatriculasModel> matriculasRecuperadas = new ArrayList<>();

        matriculasBanco.forEach(matricula -> matriculasRecuperadas.add((MatriculasModel) matricula));

        if (matriculasModel.getCodigoAluno() == null) {
            JOptionPane.showMessageDialog(null, "Insira um aluno!");
            return false;
        }

        boolean validacao = true;
        for (int i = 0; i < matriculasRecuperadas.size(); i++) {
            Integer codigoAtual = matriculasRecuperadas.get(i).getCodigoAluno();

            if (codigoAtual == matriculasModel.getCodigoAluno() && codigoAtual != alunoEditando) {
                JOptionPane.showMessageDialog(null, "Aluno já possui matricula!");
                validacao = false;
            }
        }

        return validacao;
    }

    private JTable gerarTabela() {
        JTable tabela = new JTable();
        tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabela.setRowHeight(22);
        tabela.setBackground(Color.BLUE);
        String[] colunasTabela = { "Código Matricula", "Modalidade", "Graduação", "Plano", "Data Início", "Data Fim" };

        DefaultTableModel tableModel = new DefaultTableModel(colunasTabela, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        montaDadosTabela(tabela, tableModel);

        return tabela;
    }

    protected void montaDadosTabela(JTable tabela, DefaultTableModel tableModel) {
        MatriculasModalidadesController matriculasModalidadesController = new MatriculasModalidadesController();

        Integer idAlunoSelecionado = matriculasModel.getCodigoAluno() == null ? 0 : matriculasModel.getCodigoAluno();

        List<Object> matriculasModalidadesBanco = matriculasModalidadesController.recuperarMatriculasAluno(idAlunoSelecionado);
        List<MatriculasModalidadesModel> listaMatriculasModalidades = new ArrayList<>();

        matriculasModalidadesBanco.forEach(matriculaModalidade -> listaMatriculasModalidades.add((MatriculasModalidadesModel) matriculaModalidade));

        for (int i = 0; i < listaMatriculasModalidades.size(); i++) {
            Integer codigoMatricula = listaMatriculasModalidades.get(i).getCodigoMatricula();
            Integer idModalidade = listaMatriculasModalidades.get(i).getModalidade();
            Integer idGraduacao = listaMatriculasModalidades.get(i).getGraduacao();
            Integer idPlano = listaMatriculasModalidades.get(i).getPlano();
            Date dataInicio  = listaMatriculasModalidades.get(i).getDataInicio();
            Date dataFim = listaMatriculasModalidades.get(i).getDataFim();
            Object[] linha = { codigoMatricula, idModalidade, idGraduacao, idPlano, dataInicio, dataFim };
            tableModel.addRow(linha);
        }

        tabela.setModel(tableModel);
    }

}
