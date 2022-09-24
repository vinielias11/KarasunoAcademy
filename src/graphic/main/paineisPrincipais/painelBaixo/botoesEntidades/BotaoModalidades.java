package graphic.main.paineisPrincipais.painelBaixo.botoesEntidades;

import graphic.entidades.modalidades.ModalidadesPanel;

import javax.swing.*;

public class BotaoModalidades extends BotaoEntidadeBase {
    public BotaoModalidades(int x, int y, String titulo, String caminhoImagem, JFrame cmpPai) {
        super(x, y, titulo, caminhoImagem, cmpPai);
    }

    @Override
    protected void onClick(JFrame cmpPai) {
        ModalidadesPanel modalidadesPanel = new ModalidadesPanel(cmpPai);
        cmpPai.getContentPane().removeAll();

        cmpPai.getContentPane().add(modalidadesPanel);

        cmpPai.getContentPane().revalidate();
        cmpPai.getContentPane().repaint();
    }
}
