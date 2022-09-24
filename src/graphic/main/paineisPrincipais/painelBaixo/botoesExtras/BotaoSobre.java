package graphic.main.paineisPrincipais.painelBaixo.botoesExtras;

import graphic.main.paineisPrincipais.painelBaixo.botoesEntidades.BotaoEntidadeBase;
import graphic.sobre.SobrePanel;

import javax.swing.*;

public class BotaoSobre extends BotaoEntidadeBase {
    public BotaoSobre(int x, int y, String titulo, String caminhoImagem, JFrame cmpPai) {
        super(x, y, titulo, caminhoImagem, cmpPai);
    }

    @Override
    protected void onClick(JFrame cmpPai) {
        SobrePanel sobrePanel = new SobrePanel(cmpPai);
        cmpPai.getContentPane().removeAll();

        cmpPai.getContentPane().add(sobrePanel);

        cmpPai.getContentPane().revalidate();
        cmpPai.getContentPane().repaint();
    }
}
