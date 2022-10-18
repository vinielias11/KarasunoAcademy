package graphic.main.paineisPrincipais.painelBaixo.botoesEntidades;

import graphic.entidades.faturasMatriculas.FaturasMatriculasPanel;

import javax.swing.*;

public class BotaoFaturasMatriculas extends BotaoEntidadeBase {
    public BotaoFaturasMatriculas(int x, int y, String titulo, String caminhoImagem, JFrame cmpPai) {
        super(x, y, titulo, caminhoImagem, cmpPai);
    }

    @Override
    protected void onClick(JFrame cmpPai) {
        FaturasMatriculasPanel faturasMatriculasPanel = new FaturasMatriculasPanel(cmpPai);
        cmpPai.getContentPane().removeAll();

        cmpPai.getContentPane().add(faturasMatriculasPanel);

        cmpPai.getContentPane().revalidate();
        cmpPai.getContentPane().repaint();
    }
}
