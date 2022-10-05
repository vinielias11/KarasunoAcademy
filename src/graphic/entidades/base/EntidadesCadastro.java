package graphic.entidades.base;

import javax.swing.*;
import java.awt.*;

public abstract class EntidadesCadastro extends JDialog {

    public EntidadesCadastro(){
        setBackground(new Color(255,255,255));
        setLayout(null);
        setTitle("Cadastro");
        setIconImage(Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/resources/icons/sistemaIcon.png")));
        setModal(true);
        setSize(520,700);
        setLocationRelativeTo(null);
        criarBotoes();
    }

    public void criarBotoes(){
        JButton btnFechar = new JButton("Cancelar");
        btnFechar.setBounds(100,580,120,30);
        add(btnFechar);

        btnFechar.addActionListener(e -> {
            dispose();
        });

        JButton btnSalvar = new JButton("Salvar");
        btnSalvar.setBounds(310,580,120,30);
        btnSalvar.addActionListener(e -> onClickSalvar());
        add(btnSalvar);
    }

    protected abstract void onClickSalvar();
}
