package graphic.entidades.base;

import graphic.main.paineisPrincipais.painelBaixo.PainelBaixoMain;
import graphic.main.paineisPrincipais.painelCima.PainelCimaMain;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;

// Painel base para todos os outros. Por padrão virá com o botão voltar, que reiniciará o JFrame Main, recolocando os paineis padrão.
public abstract class EntidadesPanel extends JPanel {
    private JFrame cmpPai;

    public EntidadesPanel(JFrame cmpPai) {
        this.cmpPai = cmpPai;

        setBackground(new Color(255,255,255));
        setLayout(null);
        setBounds(0, 0, 1100, 820);

        criaPainelCima();
        criarPainelBaixo();

    }

    protected abstract String[] getColunasTabela();
    protected abstract void montaDadosTabela(JTable tabela, DefaultTableModel tableModel);

    private void criaPainelCima(){
        JPanel menuCima = new JPanel();
        menuCima.setBackground(new Color(223, 129, 57));
        menuCima.setLayout(null);
        menuCima.setBounds(0, 0, 1100, 140);
        add(menuCima);

        ImageIcon imagemLogo = new ImageIcon(this.getClass().getResource("/resources/icons/logoPequeno.png"));
        JLabel labelLogo = new JLabel(imagemLogo);
        labelLogo.setBounds(925, 15, 87, 105);
        menuCima.add(labelLogo);

        ImageIcon homeIcon = new ImageIcon(this.getClass().getResource("/resources/icons/homePage.png"));
        homeIcon = new ImageIcon(homeIcon.getImage().getScaledInstance(35,35, Image.SCALE_DEFAULT));
        JButton jButton = new JButton(homeIcon);
        jButton.setText(" Voltar");
        jButton.setFont(new Font("Helvetica", Font.BOLD, 16));
        jButton.setBackground(Color.WHITE);
        jButton.setBorder(BorderFactory.createEmptyBorder());
        jButton.setBounds(5, 15, 100, 30);
        jButton.setOpaque(false);
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

        menuCima.add(jButton);
    }

    private void criarPainelBaixo() {
        String[] colunasTabela = getColunasTabela();

        DefaultTableModel tableModel = new DefaultTableModel(colunasTabela, 0);

        JTable tabela = new JTable();

        montaDadosTabela(tabela, tableModel);

        JScrollPane scrollPane = new JScrollPane(tabela);
        scrollPane.setBounds(45, 230, 1000, 400);
        add(scrollPane);

        ImageIcon smbMais = new ImageIcon(this.getClass().getResource("/resources/icons/plusIcon.png"));
        JButton btnCadastrar = new JButton(smbMais);
        btnCadastrar.setBounds(45, 170, 40, 40);
        btnCadastrar.setBackground(Color.WHITE);
        btnCadastrar.setBorder(BorderFactory.createEmptyBorder());
        btnCadastrar.setOpaque(false);
        btnCadastrar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        add(btnCadastrar);

        btnCadastrar.addActionListener(e -> {
            onClickNovo();
        });

        ImageIcon smbProximo = new ImageIcon(this.getClass().getResource("/resources/icons/nextPage.png"));
        JButton btnProximo = new JButton(smbProximo);
        btnProximo.setBounds(760, 675, 40, 40);
        btnProximo.setBackground(Color.WHITE);
        btnProximo.setBorder(BorderFactory.createEmptyBorder());
        btnProximo.setOpaque(false);
        btnProximo.setCursor(new Cursor(Cursor.HAND_CURSOR));
        add(btnProximo);

        ImageIcon smbAnterior = new ImageIcon(this.getClass().getResource("/resources/icons/backPage.png"));
        JButton btnAnterior = new JButton(smbAnterior);
        btnAnterior.setBounds(300, 675, 40, 40);
        btnAnterior.setBackground(Color.WHITE);
        btnAnterior.setBorder(BorderFactory.createEmptyBorder());
        btnAnterior.setOpaque(false);
        btnAnterior.setCursor(new Cursor(Cursor.HAND_CURSOR));
        add(btnAnterior);
    }

    protected abstract void onClickNovo();

}
