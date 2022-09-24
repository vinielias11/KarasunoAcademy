package graphic.main.paineisPrincipais.painelBaixo.botoesEntidades;

import graphic.entidades.planos.PlanosPanel;

import javax.swing.*;

public class BotaoPlanos extends BotaoEntidadeBase {
    public BotaoPlanos(int x, int y, String titulo, String caminhoImagem, JFrame cmpPai) {
        super(x, y, titulo, caminhoImagem, cmpPai);
    }

    @Override
    protected void onClick(JFrame cmpPai) {
        PlanosPanel planosPanel = new PlanosPanel(cmpPai);
        cmpPai.getContentPane().removeAll();

        cmpPai.getContentPane().add(planosPanel);

        cmpPai.getContentPane().revalidate();
        cmpPai.getContentPane().repaint();
    }
}
