package graphic.entidades.base;

import graphic.main.paineisPrincipais.painelBaixo.PainelBaixoMain;
import graphic.main.paineisPrincipais.painelCima.PainelCimaMain;

import javax.swing.*;
import java.awt.*;

// Painel base para todos os outros. Por padrão virá com o botão voltar, que reiniciará o JFrame Main, recolocando os paineis padrão.
public class EntidadesPanel extends JPanel {
    private JFrame cmpPai;
    public EntidadesPanel(JFrame cmpPai) {
        this.cmpPai = cmpPai;

        setBackground(new Color(0, 0, 0));
        setLayout(null);
        setBounds(0, 0, 1100, 820);

        JButton jButton = new JButton("Voltar");
        jButton.setBounds(10, 20, 200, 25);

        jButton.addActionListener(e -> {
            PainelCimaMain painelCimaMain = new PainelCimaMain();
            PainelBaixoMain painelBaixoMain = new PainelBaixoMain(cmpPai);

            cmpPai.getContentPane().removeAll();

            cmpPai.getContentPane().add(painelCimaMain);
            cmpPai.getContentPane().add(painelBaixoMain);

            cmpPai.getContentPane().revalidate();
            cmpPai.getContentPane().repaint();
        });

        add(jButton);
    }
}
