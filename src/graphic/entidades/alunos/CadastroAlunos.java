package graphic.entidades.alunos;

import graphic.entidades.base.FrameCadastro;

import javax.swing.*;

public class CadastroAlunos extends FrameCadastro {
    public CadastroAlunos(){
        JButton btn = new JButton("OIII");
        btn.setBounds(150,150,50,50);
        add(btn);
    }

    @Override
    protected void onClickSalvar() {

    }

    private void criaComponentes() {

    }
}
