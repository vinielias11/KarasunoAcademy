package graphic.main.paineisPrincipais.painelBaixo.botoesEntidades;

import graphic.entidades.graduacoes.GraduacoesPanel;

import javax.swing.*;

public class BotaoGraduacoes extends BotaoEntidadeBase {
    public BotaoGraduacoes(int x, int y, String titulo, String caminhoImagem, JFrame cmpPai) {
        super(x, y, titulo, caminhoImagem, cmpPai);
    }

    @Override
    protected void onClick(JFrame cmpPai) {
        GraduacoesPanel graduacoesPanel = new GraduacoesPanel(cmpPai);
        cmpPai.getContentPane().removeAll();

        cmpPai.getContentPane().add(graduacoesPanel);

        cmpPai.getContentPane().revalidate();
        cmpPai.getContentPane().repaint();
    }
}
