package graphic.entidades.base;

import graphic.main.paineisPrincipais.painelBaixo.PainelBaixoMain;
import graphic.main.paineisPrincipais.painelCima.PainelCimaMain;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

// Painel base para todos os outros. Por padrão virá com o botão voltar, que reiniciará o JFrame Main, recolocando os paineis padrão.
public abstract class EntidadesPanel extends JPanel {
    private JFrame cmpPai;

    public EntidadesPanel(JFrame cmpPai) {
        this.cmpPai = cmpPai;
        setBackground(new Color(255,255,255));
        setLayout(null);
        setBounds(0, 0, 1100, 820);
        criaPainelDeCima();
        criaPainelDeBaixo();
    }

    protected abstract String getTitulo();

    private void criaPainelDeCima() {
        JPanel painelCima = new JPanel();
        painelCima.setBackground(new Color(223, 129, 57));
        painelCima.setLayout(null);
        painelCima.setBounds(0, 0, 1100, 140);
        add(painelCima);

        ImageIcon imagemLogo = new ImageIcon(this.getClass().getResource("/resources/icons/logoPequeno.png"));
        JLabel labelLogo = new JLabel(imagemLogo);
        labelLogo.setBounds(925, 15, 87, 105);
        painelCima.add(labelLogo);

        JButton btnVoltar = criaBotaoVoltar();
        painelCima.add(btnVoltar);

        JLabel titulo = new JLabel(getTitulo());
        titulo.setFont(new Font("Helvetica", Font.BOLD, 36));
        titulo.setBounds(30, 90, 300, 40);
        painelCima.add(titulo);
    }

    private JButton criaBotaoVoltar() {
        ImageIcon homeIcon = new ImageIcon(this.getClass().getResource("/resources/icons/homePage.png"));
        JButton jButton = new JButton(homeIcon);
        jButton.setText("Voltar");
        jButton.setFont(new Font("Helvetica", Font.BOLD, 16));
        jButton.setBackground(Color.WHITE);
        jButton.setBorder(BorderFactory.createEmptyBorder());
        jButton.setBounds(5, 15, 100, 30);
        jButton.setOpaque(false);
        jButton.setRolloverEnabled(false);
        jButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        jButton.addActionListener(e -> {
            PainelCimaMain painelCimaMain = new PainelCimaMain();
            PainelBaixoMain painelBaixoMain = new PainelBaixoMain(cmpPai);

            cmpPai.getContentPane().removeAll();

            cmpPai.getContentPane().add(painelCimaMain);
            cmpPai.getContentPane().add(painelBaixoMain);

            cmpPai.getContentPane().revalidate();
            cmpPai.getContentPane().repaint();
        });

        return jButton;
    }

    private void criaPainelDeBaixo() {
        JPanel painelBaixo = new JPanel();
        painelBaixo.setBackground(new Color(255, 255, 255));
        painelBaixo.setLayout(null);
        painelBaixo.setBounds(0, 140, 1100, 680);
        add(painelBaixo);

        JTable tabela = criaTabela();

        JScrollPane jScrollPane = new JScrollPane(tabela);
        jScrollPane.setBounds(45, 90, 1000, 500);
        painelBaixo.add(jScrollPane);

        ImageIcon smbMais = new ImageIcon(this.getClass().getResource("/resources/icons/plusIcon.png"));
        JButton btnCadastrar = new JButton(smbMais);
        btnCadastrar.setBounds(45, 30, 40, 40);
        btnCadastrar.setBackground(Color.WHITE);
        btnCadastrar.setBorder(BorderFactory.createEmptyBorder());
        btnCadastrar.setOpaque(false);
        btnCadastrar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnCadastrar.addActionListener(e -> onClickNovo());
        painelBaixo.add(btnCadastrar);
    }

    private JTable criaTabela() {
        JTable tabela = new JTable();
        tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabela.setRowHeight(22);
        
        String[] colunasTabela = getColunasTabela();
        DefaultTableModel tableModel = new DefaultTableModel(colunasTabela, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        montaDadosTabela(tabela, tableModel);
        implementaDoubleClickNasLinhas(tabela);

        return tabela;
    }

    public void recarregaLista() {
        this.removeAll();
        criaPainelDeCima();
        criaPainelDeBaixo();
        this.revalidate();
        this.repaint();
    }

    private void implementaDoubleClickNasLinhas(JTable tabela) {
        tabela.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    JTable target = (JTable) e.getSource();
                    int linha = target.getSelectedRow();
                    int coluna = 0;
                    String id = tabela.getModel().getValueAt(linha, coluna).toString();

                    onDoubleClickLinha(id);
                }
            }
        });
    }

    protected abstract String[] getColunasTabela();

    protected abstract void montaDadosTabela(JTable tabela, DefaultTableModel tableModel);

    protected abstract void onDoubleClickLinha(String id);

    protected abstract void onClickNovo();

}