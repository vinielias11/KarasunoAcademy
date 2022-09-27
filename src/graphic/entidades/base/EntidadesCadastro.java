package graphic.entidades.base;

import javax.swing.*;
import java.awt.*;

public abstract class EntidadesCadastro extends JDialog {

    public EntidadesCadastro(){
        setBackground(new Color(255,255,255));
        setLayout(null);
        setModal(true);
        setSize(620,760);
        setLocationRelativeTo(null);
        criarBotoes();
    }

    public void criarBotoes(){
        JButton btnFechar = new JButton("Cancelar");
        btnFechar.setBounds(110,640,120,30);
        add(btnFechar);

        btnFechar.addActionListener(e -> {
            dispose();
        });

        JButton btnSalvar = new JButton("Salvar");
        btnSalvar.setBounds(380,640,120,30);
        btnSalvar.addActionListener(e -> {
            onClickSalvar();
        });
        add(btnSalvar);
    }

    protected abstract void onClickSalvar();
}
