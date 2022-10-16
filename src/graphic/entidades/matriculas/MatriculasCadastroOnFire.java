package graphic.entidades.matriculas;

import controller.AlunosController;
import controller.MatriculasController;
import controller.MatriculasModalidadesController;
import graphic.entidades.base.EntidadesCadastro;
import graphic.entidades.matriculasModalidades.MatriculasModalidadesCadastroOnFire;
import graphic.util.AlunosComboModel;
import graphic.util.AlunosComboRender;
import model.AlunosModel;
import model.MatriculasModalidadesModel;
import model.MatriculasModel;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;

import java.awt.event.ItemEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class MatriculasCadastroOnFire extends EntidadesCadastro {

    private MatriculasModel matriculasModel = new MatriculasModel();
    private MatriculasController matriculasController = new MatriculasController();
    private AlunosController alunosController = new AlunosController();
    private MatriculasPanel matriculasPanel;
    private JPanel panel = new JPanel(new GridBagLayout());
    private GridBagConstraints c = new GridBagConstraints();
    private JTable tabela = new JTable();
    private DefaultTableModel tableModel = new DefaultTableModel();
    private boolean isEditando = false;
    private Integer alunoEditando = 0;

    public MatriculasCadastroOnFire(MatriculasModel matriculasModelEditando, MatriculasPanel matriculasPanel) {
        this.matriculasPanel = matriculasPanel;
        construirViewCadastro(matriculasModelEditando);
    }

    private void construirViewCadastro(MatriculasModel matriculasModelEditando){
        atualizaComponentesDuranteEdicao(matriculasModelEditando);
        configuracoesIniciaisPanel();
        criarBotaoSelecionarMatricula();
        criarInputVencimento();
        criarBotoesSalvarExcluirModalidadeMatricula();
        criarTabelaModalidadesMatricula();
        add(this.panel);
    }

    private void atualizaComponentesDuranteEdicao(MatriculasModel matriculasModelEditando){
        if(matriculasModelEditando != null) {
            isEditando = true;
            alunoEditando = matriculasModelEditando.getCodigoAluno();
            this.matriculasModel.setCodigoMatricula(matriculasModelEditando.getCodigoMatricula());
            this.matriculasModel.setCodigoAluno(matriculasModelEditando.getCodigoAluno());
            this.matriculasModel.setDataMatricula(matriculasModelEditando.getDataMatricula());
            this.matriculasModel.setDiaVencimento(matriculasModelEditando.getDiaVencimento());
            this.matriculasModel.setDataEncerramento(matriculasModelEditando.getDataEncerramento());
        }
    }

    private void criarBotoesSalvarExcluirModalidadeMatricula(){
        if(!isEditando){
            return;
        }

        ImageIcon smbMais = new ImageIcon(this.getClass().getResource("/resources/icons/plusIcon.png"));
        JButton btnCadastrar = new JButton(smbMais);
        btnCadastrar.setBounds(0, 30, 40, 40);
        btnCadastrar.setBackground(Color.WHITE);
        btnCadastrar.setBorder(BorderFactory.createEmptyBorder());
        btnCadastrar.setOpaque(false);
        btnCadastrar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnCadastrar.setToolTipText("Novo");
        btnCadastrar.addActionListener(e -> onClickNovaModalidade());
        setGridLayout(0,2, GridBagConstraints.WEST);
        panel.add(btnCadastrar, this.c);

        ImageIcon smbDelete = new ImageIcon(this.getClass().getResource("/resources/icons/deleteIcon.png"));
        JButton btnDelete = new JButton(smbDelete);
        btnDelete.setBackground(Color.WHITE);
        btnDelete.setOpaque(false);
        btnDelete.setBorder(BorderFactory.createEmptyBorder());
        btnDelete.setBounds(0,30,40,40);
        //btnDelete.setMargin(new Insets(0,20,0,0));
        btnDelete.setCursor(new Cursor((Cursor.HAND_CURSOR)));
        btnDelete.addActionListener(e -> {
            try {
                Integer linha = tabela.getSelectedRow();
                Integer codigoMatricula = (Integer) tabela.getModel().getValueAt(linha, 0);
                Integer idModalidade = (Integer) tabela.getModel().getValueAt(linha, 1);
                deletar(codigoMatricula, idModalidade);
            }catch (ArrayIndexOutOfBoundsException ex){
                JOptionPane.showMessageDialog(null,"Selecione um registro para deletar!", "Atenção", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        btnDelete.setToolTipText("Excluir");
        setGridLayout(0,2, GridBagConstraints.EAST);
        panel.add(btnDelete, this.c);
    }

    private void criarBotaoSelecionarMatricula(){
        ArrayList<AlunosModel> alunosRecuperados = alunosController.recuperaAlunosParaComboBox();
        AlunosComboModel alunosComboModel = new AlunosComboModel(alunosRecuperados);

        JLabel labelAlunos = new JLabel("Aluno: ");
        setGridLayout(0,0, GridBagConstraints.EAST);
        panel.add(labelAlunos, this.c);

        JComboBox comboBoxAlunos = new JComboBox<>(alunosComboModel);
        comboBoxAlunos.setRenderer(new AlunosComboRender());
        comboBoxAlunos.setPreferredSize(new Dimension(224,20));
        setGridLayout(1,0, GridBagConstraints.WEST);

        if(isEditando && this.matriculasModel.getCodigoAluno() != null){
            alunosRecuperados.forEach(alunoRecuperado -> {
                if (Objects.equals(alunoRecuperado.getId(), this.matriculasModel.getCodigoAluno())) {
                    comboBoxAlunos.setSelectedItem(alunoRecuperado);
                }
            });
            comboBoxAlunos.setEnabled(false);
        }

        panel.add(comboBoxAlunos, this.c);

        comboBoxAlunos.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                AlunosModel item = (AlunosModel) comboBoxAlunos.getSelectedItem();
                matriculasModel.setCodigoAluno(item.getId());
                atualizarDadosTabela();
            }
        });
    }

    private void criarInputVencimento(){
        JLabel labelDiaVencimento = new JLabel("Dia de Vencimento: ");
        int min = 1, value = 1, max = 20, stepSize = 1;
        setGridLayout(0,1, GridBagConstraints.EAST);
        panel.add(labelDiaVencimento, this.c);

        SpinnerNumberModel numberModel = new SpinnerNumberModel(value, min, max, stepSize);
        JSpinner diaVencimentoSpn = new JSpinner(numberModel);
        diaVencimentoSpn.setPreferredSize(new Dimension(50,20));


        if(isEditando && this.matriculasModel.getDiaVencimento() != null) {
            diaVencimentoSpn.setValue(this.matriculasModel.getDiaVencimento());
        } else {
            matriculasModel.setDiaVencimento((Integer) diaVencimentoSpn.getValue());
        }

        diaVencimentoSpn.addChangeListener(e ->
                matriculasModel.setDiaVencimento((Integer) diaVencimentoSpn.getValue())
        );
        setGridLayout(1,1, GridBagConstraints.WEST);
        panel.add(diaVencimentoSpn, this.c);
    }

    private void criarTabelaModalidadesMatricula(){
        if(!isEditando){
            return;
        }
        this.tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        this.tabela.setRowHeight(22);
        this.tabela.setBackground(Color.white);
        this.tabela.setSize(new Dimension(860, 300));
        this.tabela.setPreferredSize(new Dimension(860, 300));
        this.tabela.setShowGrid(true);

        String[] colunasTabela = { "Código Matricula", "Modalidade", "Graduação", "Plano", "Data Início", "Data Fim" };

        this.tableModel.setColumnIdentifiers(colunasTabela);
        this.tableModel.setColumnCount(6);

        atualizarDadosTabela();

        TableColumnModel colunas = tabela.getColumnModel();
        colunas.getColumn(0).setMaxWidth(860);

        DefaultTableCellRenderer centerRender = new DefaultTableCellRenderer();
        centerRender.setHorizontalAlignment(JLabel.CENTER);
        centerRender.setBackground(Color.pink);

        for (int i = 0; i < tabela.getColumnCount(); i++){
            tabela.getColumnModel().getColumn(i).setCellRenderer(centerRender);
        }

        this.c.gridwidth= 3;
        setGridLayout(0,3, GridBagConstraints.WEST);
        this.panel.add(new JScrollPane(this.tabela), this.c);
    }

    public void atualizarDadosTabela(){
        MatriculasModalidadesController matriculasModalidadesController = new MatriculasModalidadesController();
        Integer idCodigoMatricula = this.matriculasModel.getCodigoMatricula() == null ? 0 : this.matriculasModel.getCodigoMatricula();
        List<Object> matriculasModalidadesBanco = matriculasModalidadesController.recuperarMatriculasAluno(idCodigoMatricula);
        List<MatriculasModalidadesModel> listaMatriculasModalidades = new ArrayList<>();
        matriculasModalidadesBanco.forEach(matriculaModalidade -> listaMatriculasModalidades.add((MatriculasModalidadesModel) matriculaModalidade));
        for(int i = 0; i < this.tableModel.getRowCount(); i++){
            this.tableModel.removeRow(i);
        }
        for (int i = 0; i < listaMatriculasModalidades.size(); i++) {
            Integer codigoMatricula = listaMatriculasModalidades.get(i).getCodigoMatricula();
            Integer idModalidade = listaMatriculasModalidades.get(i).getModalidade();
            Integer idGraduacao = listaMatriculasModalidades.get(i).getGraduacao();
            Integer idPlano = listaMatriculasModalidades.get(i).getPlano();
            Date dataInicio  = listaMatriculasModalidades.get(i).getDataInicio();
            Date dataFim = listaMatriculasModalidades.get(i).getDataFim();
            Object[] linha = { codigoMatricula, idModalidade, idGraduacao, idPlano, dataInicio, dataFim };
            this.tableModel.addRow(linha);
        }
        this.tabela.setModel(this.tableModel);
        this.tabela.repaint();
    }

    private void setGridLayout(Integer x, Integer y, Integer orientation){
        this.c.gridx = x;
        this.c.gridy = y;
        this.c.anchor = orientation;
    }

    private void configuracoesIniciaisPanel(){
        Integer frameWidth = isEditando ? 500 : 500;
        Integer frameHeight  = isEditando ? 700 : 700;
        setSize(frameWidth,frameHeight);
        this.panel.setSize(new Dimension(frameWidth, frameHeight));
        this.c.insets = new Insets(0, 0, 20, 10);
    }

    @Override
    protected void onClickSalvar() {
        MatriculasController matriculasController = new MatriculasController();
        if(!validaCamposAntesDeSalvar()) return;
        if (!isEditando) {
            matriculasModel.setDataMatricula(new java.sql.Date(System.currentTimeMillis()));
            matriculasController.inserir(matriculasModel, this);
        } else {
            matriculasController.editar(matriculasModel, this);
        }
        matriculasPanel.recarregaLista();
    }

    private boolean validaCamposAntesDeSalvar(){
        List<Object> matriculasBanco = this.matriculasController.recuperarTodos();
        List<MatriculasModel> matriculasRecuperadas = new ArrayList<>();
        matriculasBanco.forEach(matricula -> matriculasRecuperadas.add((MatriculasModel) matricula));
        if (this.matriculasModel.getCodigoAluno() == null) {
            JOptionPane.showMessageDialog(null, "Insira um aluno!");
            return false;
        }
        boolean validacao = true;
        for (int i = 0; i < matriculasRecuperadas.size(); i++) {
            Integer codigoAtual = matriculasRecuperadas.get(i).getCodigoAluno();
            if (codigoAtual == this.matriculasModel.getCodigoAluno() && codigoAtual != alunoEditando) {
                JOptionPane.showMessageDialog(null, "Aluno já possui matricula!");
                validacao = false;
            }
        }
        return validacao;
    }

    protected void onClickNovaModalidade() {
        if(matriculasModel.getDataEncerramento() == null){
            MatriculasModalidadesCadastroOnFire matriculasModalidadesCadastroOnFire = new MatriculasModalidadesCadastroOnFire(null, this, matriculasModel);
            matriculasModalidadesCadastroOnFire.setVisible(true);
        }else{
            JOptionPane.showMessageDialog(null, "Matrícula já encerrada!");
        }

    }

    @Override
    public void criarBotoes(){

        Integer posicaoY = isEditando ? 580 : 580;

        JButton btnFechar = new JButton("Cancelar");
        btnFechar.setBounds(100,posicaoY,120,30);
        btnFechar.setName("btnFechar");
        btnFechar.setFont(new Font("Helvetica", Font.BOLD, 16));
        btnFechar.setBackground(new Color(255,92,92));
        btnFechar.setBorder(BorderFactory.createLineBorder(new Color(255, 92, 92)));
        add(btnFechar);

        btnFechar.addActionListener(e -> {
            dispose();
        });

        JButton btnSalvar = new JButton("Salvar");
        btnSalvar.setBounds(310,posicaoY,120,30);
        btnSalvar.addActionListener(e -> onClickSalvar());
        btnFechar.setName("btnSalvar");
        btnSalvar.setFont(new Font("Helvetica", Font.BOLD, 16));
        btnSalvar.setBackground(new Color(87, 241, 87, 210));
        btnSalvar.setBorder(BorderFactory.createLineBorder(new Color(87, 241, 87, 210)));
        add(btnSalvar);
    }

    private void removeComponente(String name){
        Component[] componentList = panel.getComponents();
        for(Component c : componentList){
            if(c instanceof JComboBox){
                if(c.getName() == name){
                    panel.remove(c);
                    break;
                }
            }
        }
        panel.revalidate();
        panel.repaint();
    }

    protected void deletar(Integer codigoMatricula, Integer idModalidade) {
        MatriculasModalidadesModel matriculasModalidadesModel = new MatriculasModalidadesModel();
        MatriculasModalidadesController matriculasModalidadesController = new MatriculasModalidadesController();

        matriculasModalidadesModel.setCodigoMatricula(codigoMatricula);
        matriculasModalidadesModel.setModalidade(idModalidade);
        matriculasModalidadesController.deletar(matriculasModalidadesModel);

        atualizarDadosTabela();
    }


}
