package graphic.controle;

import controller.ControleGeralController;
import model.AlunosModel;
import model.AssiduidadeModel;
import model.FaturasMatriculasModel;
import model.MatriculasModalidadesModel;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Date;
import java.util.List;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.ParseException;

public class ControleGeralWindow extends JDialog {
    private JTable tabelaMatriculas, tabelaFaturas, tabelaAssiduidade;
    private final ControleGeralController controleGeralController = new ControleGeralController();
    private AlunosModel alunosModel;

    public ControleGeralWindow() {
        setSize(900, 680);
        setLayout(null);
        setTitle("Controle Geral");
        setIconImage(Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/resources/icons/sistemaIcon.png")));
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        criaComponentes();
    }

    private void criaComponentes() {
        JPanel panel = new JPanel(new GridBagLayout());
        JPanel subPanel = new JPanel(new GridBagLayout());
        JPanel subPanel2 = new JPanel(new GridBagLayout());
        GridBagConstraints gbcSubPanel = new GridBagConstraints();
        GridBagConstraints gbcPanelPrincipal = new GridBagConstraints();
        panel.setSize(900,680);

        MaskFormatter mascaraNumero;

        try {
            mascaraNumero = new MaskFormatter("#####");
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        JFormattedTextField codigoAlunoTxf = new JFormattedTextField();
        codigoAlunoTxf.setColumns(10);
        mascaraNumero.install(codigoAlunoTxf);

        JTextField showNomeAlunoTxf = new JTextField(40);
        showNomeAlunoTxf.setEditable(false);
        showNomeAlunoTxf.setBorder(new LineBorder(Color.black));

        JTextField fotoAlunoTesteTxf = new JTextField();
        fotoAlunoTesteTxf.setEditable(false);
        fotoAlunoTesteTxf.setPreferredSize(new Dimension(220,220));
        fotoAlunoTesteTxf.setBorder(new LineBorder(Color.black));

        JTable tabelaMatriculas = criaTabelaMatriculas();
        JScrollPane scrollPaneMatriculas = new JScrollPane(tabelaMatriculas);
        scrollPaneMatriculas.setPreferredSize(new Dimension(0,100));

        JTextField showSituacaoTxf = new JTextField("SITUAÇÃO REGULAR");
        showSituacaoTxf.setEditable(false);
        showSituacaoTxf.setBorder(new LineBorder(Color.black));
        showSituacaoTxf.setPreferredSize(new Dimension(400,80));
        showSituacaoTxf.setHorizontalAlignment(JTextField.CENTER);

        JButton btnDadosAluno = new JButton("Acessar dados do aluno");
        btnDadosAluno.setFont(new Font("Helvetica", Font.BOLD, 16));
        btnDadosAluno.setBackground(Color.WHITE);
        btnDadosAluno.setOpaque(false);
        btnDadosAluno.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnDadosAluno.setBorder(BorderFactory.createLineBorder(Color.black));
        btnDadosAluno.setPreferredSize(new Dimension(280, 25));

        JButton btnDadosMatricula = new JButton("Acessar dados da matrícula");
        btnDadosMatricula.setFont(new Font("Helvetica", Font.BOLD, 16));
        btnDadosMatricula.setBackground(Color.WHITE);
        btnDadosMatricula.setOpaque(false);
        btnDadosMatricula.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnDadosMatricula.setBorder(BorderFactory.createLineBorder(Color.black));
        btnDadosMatricula.setPreferredSize(new Dimension(280, 25));

        JButton btnteste = new JButton("INSERIR BOX DE MES");
        btnteste.setPreferredSize(new Dimension(200, 25));

        JTable tabelaAssiduidade = criaTabelaAssiduidade();
        JScrollPane scrollPaneAssiduidade = new JScrollPane(tabelaAssiduidade);
        scrollPaneAssiduidade.setPreferredSize(new Dimension(220,310));

        JTable tabelaFaturas = criaTabelaFaturas();
        JScrollPane scrollPaneFaturas = new JScrollPane(tabelaFaturas);
        scrollPaneFaturas.setPreferredSize(new Dimension(0,310));

        this.tabelaMatriculas = tabelaMatriculas;
        this.tabelaFaturas = tabelaFaturas;
        this.tabelaAssiduidade = tabelaAssiduidade;

        codigoAlunoTxf.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String valorTxtField = codigoAlunoTxf.getText().trim();

                if (valorTxtField.length() < 5) return;

                Integer codigoDigitado = Integer.parseInt(valorTxtField);
                alunosModel = controleGeralController.recuperarAlunoPorCodigo(codigoDigitado);

                if (alunosModel != null) {
                    showNomeAlunoTxf.setText(alunosModel.getNome());
                    carregaDadosTabelaMatriculas();
                    carregaDadosTabelaFaturas();
                    marcaPresencaHoje();
                    carregaDadosTabelaAssiduidade();
                } else {
                    showNomeAlunoTxf.setText("Aluno não encontrado!");
                    limpaDadosDeTodasAsTabelas();
                }
            }
        });

        gbcSubPanel.gridx = 0; gbcSubPanel.gridy = 0; gbcSubPanel.anchor = GridBagConstraints.NORTH; gbcSubPanel.insets = new Insets(0,0,25,0);
        subPanel.add(fotoAlunoTesteTxf, gbcSubPanel);
        gbcSubPanel.gridx = 0; gbcSubPanel.gridy = 1; gbcSubPanel.insets = new Insets(0,0,15,0);
        subPanel.add(btnteste, gbcSubPanel);
        gbcSubPanel.gridx = 0; gbcSubPanel.gridy = 2;
        subPanel.add(scrollPaneAssiduidade, gbcSubPanel);

        gbcPanelPrincipal.gridx = 0; gbcPanelPrincipal.gridy = 0;
        gbcPanelPrincipal.anchor = GridBagConstraints.NORTHWEST;
        panel.add(subPanel,gbcPanelPrincipal);

        gbcSubPanel.gridx = 0; gbcSubPanel.gridy = 0; gbcSubPanel.insets = new Insets(0,0,10,10);
        subPanel2.add(codigoAlunoTxf, gbcSubPanel);
        gbcSubPanel.gridx = 1; gbcSubPanel.gridy = 0;
        subPanel2.add(showNomeAlunoTxf, gbcSubPanel);
        gbcSubPanel.gridx = 0; gbcSubPanel.gridy = 1;
        gbcSubPanel.gridwidth = 2; gbcSubPanel.fill = GridBagConstraints.HORIZONTAL;
        subPanel2.add(scrollPaneMatriculas, gbcSubPanel);
        gbcSubPanel.gridx = 0; gbcSubPanel.gridy = 2; gbcSubPanel.insets = new Insets(0,0,25,10);
        subPanel2.add(showSituacaoTxf, gbcSubPanel);
        gbcSubPanel.gridx = 0; gbcSubPanel.gridy = 3; gbcSubPanel.fill = GridBagConstraints.NONE;
        gbcSubPanel.gridwidth = 0; gbcSubPanel.anchor = GridBagConstraints.WEST;  gbcSubPanel.insets = new Insets(0,0,15,10);
        subPanel2.add(btnDadosAluno,gbcSubPanel);
        gbcSubPanel.gridx = 1; gbcSubPanel.gridy = 3; gbcSubPanel.anchor = GridBagConstraints.EAST;
        subPanel2.add(btnDadosMatricula,gbcSubPanel);
        gbcSubPanel.gridx = 0; gbcSubPanel.gridy = 4;
        gbcSubPanel.fill = GridBagConstraints.BOTH;
        subPanel2.add(scrollPaneFaturas, gbcSubPanel);

        gbcPanelPrincipal.gridx = 1; gbcPanelPrincipal.gridy = 0; gbcPanelPrincipal.insets = new Insets(0,50,0,0);
        panel.add(subPanel2,gbcPanelPrincipal);

        add(panel);
    }

    private JTable criaTabelaMatriculas() {

        JTable tabelaMatriculas = new JTable();
        tabelaMatriculas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabelaMatriculas.setRowHeight(20);

        String[] colunasTabela = { "Modalidade", "Graduação", "Plano", "Data Início", "Data Fim" };

        DefaultTableModel tableModel = new DefaultTableModel(colunasTabela, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex > 2) {
                    return java.util.Date.class;
                }
                return super.getColumnClass(columnIndex);
            }
        };

        tabelaMatriculas.setModel(tableModel);

        return tabelaMatriculas;
    }

    private JTable criaTabelaAssiduidade() {

        JTable tabelaAssiduidade = new JTable();
        tabelaAssiduidade.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabelaAssiduidade.setRowHeight(20);

        String[] colunasTabela = { "Assiduidade" };

        DefaultTableModel tableModel = new DefaultTableModel(colunasTabela, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabelaAssiduidade.setModel(tableModel);

        return tabelaAssiduidade;
    }

    private JTable criaTabelaFaturas() {

        JTable tabelaFaturas = new JTable();
        tabelaFaturas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabelaFaturas.setRowHeight(20);

        String[] colunasTabela = { "Vencimento", "Valor (R$)", "Pagamento", "Cancelamento" };

        DefaultTableModel tableModel = new DefaultTableModel(colunasTabela, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 0 || columnIndex > 1) {
                    return java.util.Date.class;
                }

                return super.getColumnClass(columnIndex);
            }
        };

        tabelaFaturas.setModel(tableModel);

        tabelaFaturas.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                if (tabelaFaturas.getValueAt(row, 2) == null) {
                    setBackground(Color.red);
                    setForeground(Color.white);
                } else {
                    setBackground(Color.green);
                    setForeground(Color.black);
                }

                return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            }
        });

        tabelaFaturas.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                int r = tabelaFaturas.rowAtPoint(e.getPoint());
                if (r >= 0 && r < tabelaFaturas.getRowCount()) {
                    tabelaFaturas.setRowSelectionInterval(r, r);
                } else {
                    tabelaFaturas.clearSelection();
                }

                int linhaSelecionada = tabelaFaturas.getSelectedRow();
                Date dataVencimento = (Date) tabelaFaturas.getValueAt(linhaSelecionada, 0),
                        dataPagamento = (Date) tabelaFaturas.getValueAt(linhaSelecionada, 2);

                if (linhaSelecionada < 0) return;

                if (e.isPopupTrigger() && e.getComponent() instanceof JTable ) {
                    JPopupMenu popup = criaMenuTabelaFaturas(dataVencimento, dataPagamento);
                    popup.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        });

        return tabelaFaturas;
    }

    private JPopupMenu criaMenuTabelaFaturas(Date dataVencimento, Date dataPagamento) {
        JPopupMenu jPopupMenu = new JPopupMenu();
        JMenuItem jMenuItem = new JMenuItem("Pagar fatura");

        jMenuItem.addActionListener(e -> {
            if (dataPagamento != null) {
                JOptionPane.showMessageDialog(null, "Fatura já está paga!");
                return;
            }

            controleGeralController.pagarFatura(dataVencimento, alunosModel.getId());
            carregaDadosTabelaFaturas();
        });

        jPopupMenu.add(jMenuItem);

        return jPopupMenu;
    }

    private void carregaDadosTabelaMatriculas() {
        DefaultTableModel tableModel = (DefaultTableModel) tabelaMatriculas.getModel();
        List<MatriculasModalidadesModel> matriculasModalidadesBanco = controleGeralController.recuperarMatriculasModalidadesPorCodigoAluno(alunosModel.getId());

        tableModel.setRowCount(0);

        if (matriculasModalidadesBanco.isEmpty()) return;

        matriculasModalidadesBanco.forEach(mm -> {
            Object[] linha = { mm.getNomeModalidade(), mm.getNomeGraduacao(), mm.getNomePlano(), mm.getDataInicio(), mm.getDataFim() };
            tableModel.addRow(linha);
        });
    }

    private void carregaDadosTabelaFaturas() {
        DefaultTableModel tableModel = (DefaultTableModel) tabelaFaturas.getModel();
        List<FaturasMatriculasModel> faturasMatriculasBanco = controleGeralController.recuperarFaturasMatriculasPorCodigoAluno(alunosModel.getId());

        tableModel.setRowCount(0);

        if (faturasMatriculasBanco.isEmpty()) return;

        faturasMatriculasBanco.forEach(fm -> {
           Object[] linha = { fm.getDataVencimento(), fm.getValor(), fm.getDataPagamento(), fm.getDataCancelamento() };
           tableModel.addRow(linha);
        });
    }

    private void carregaDadosTabelaAssiduidade() {
        DefaultTableModel tableModel = (DefaultTableModel) tabelaAssiduidade.getModel();
        List<AssiduidadeModel> assiduidadesBanco = controleGeralController.recuperarAssiduidadePorCodigoAluno(alunosModel.getId());

        tableModel.setRowCount(0);

        if (assiduidadesBanco.isEmpty()) return;

        assiduidadesBanco.forEach(a -> {
            Object[] linha = { a.getDataEntradaFormatada() };
            tableModel.addRow(linha);
        });
    }

    private void marcaPresencaHoje() {
        controleGeralController.marcarPresenca(alunosModel.getId());
    }

    private void limpaDadosDeTodasAsTabelas() {
        DefaultTableModel tableModelMatriculas = (DefaultTableModel) tabelaMatriculas.getModel(),
                tableModelFaturas = (DefaultTableModel) tabelaFaturas.getModel(),
                tableModelAssiduidade = (DefaultTableModel) tabelaAssiduidade.getModel();

        tableModelMatriculas.setRowCount(0);
        tableModelFaturas.setRowCount(0);
        tableModelAssiduidade.setRowCount(0);
    }

    public static void main(String[] args) {
        new ControleGeralWindow().setVisible(true);
    }
}
