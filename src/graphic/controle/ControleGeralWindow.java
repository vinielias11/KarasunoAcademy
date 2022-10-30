package graphic.controle;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ControleGeralWindow extends JDialog {
    public ControleGeralWindow(){
        setSize(900, 680);
        setLayout(null);
        setTitle("Controle Geral");
        setIconImage(Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/resources/icons/sistemaIcon.png")));
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        criaComponentes();
    }

    private void criaComponentes(){
        JPanel panel = new JPanel(new GridBagLayout());
        JPanel subPanel = new JPanel(new GridBagLayout());
        JPanel subPanel2 = new JPanel(new GridBagLayout());
        GridBagConstraints gbcSubPanel = new GridBagConstraints();
        GridBagConstraints gbcPanelPrincipal = new GridBagConstraints();
        panel.setSize(900,680);

        JTextField codigoAlunoTxf = new JTextField(10);

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

        JButton btnteste= new JButton("INSERIR BOX DE MES");
        btnteste.setPreferredSize(new Dimension(200, 25));

        JTable tabelaAssiduidade = criaTabelaAssiduidade();
        JScrollPane scrollPaneAssiduidade = new JScrollPane(tabelaAssiduidade);
        scrollPaneAssiduidade.setPreferredSize(new Dimension(220,310));

        JTable tabelaFaturas = criaTabelaFaturas();
        JScrollPane scrollPaneFaturas = new JScrollPane(tabelaFaturas);
        scrollPaneFaturas.setPreferredSize(new Dimension(0,310));

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

    private JTable criaTabelaMatriculas(){

        JTable tabelaMatriculas = new JTable();
        tabelaMatriculas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabelaMatriculas.setRowHeight(22);

        String[] colunasTabela = {"Modalidade", "Graduação", "Plano", "Data Início", "Data Fim"};

        DefaultTableModel tableModel = new DefaultTableModel(colunasTabela, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabelaMatriculas.setModel(tableModel);

        return tabelaMatriculas;
    }

    private JTable criaTabelaAssiduidade(){

        JTable tabelaAssiduidade = new JTable();
        tabelaAssiduidade.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabelaAssiduidade.setRowHeight(22);

        String[] colunasTabela = {"Assiduidade"};

        DefaultTableModel tableModel = new DefaultTableModel(colunasTabela, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabelaAssiduidade.setModel(tableModel);

        return tabelaAssiduidade;
    }

    private JTable criaTabelaFaturas(){

        JTable tabelaFaturas = new JTable();
        tabelaFaturas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabelaFaturas.setRowHeight(22);

        String[] colunasTabela = {"Vencimento", "Valor", "Pagamento", "Cancelamento"};

        DefaultTableModel tableModel = new DefaultTableModel(colunasTabela, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabelaFaturas.setModel(tableModel);

        return tabelaFaturas;
    }

    public static void main(String[] args) {
        new ControleGeralWindow().setVisible(true);
    }
}
