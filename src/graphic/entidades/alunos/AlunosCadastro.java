package graphic.entidades.alunos;

import graphic.entidades.base.EntidadesCadastro;

import javax.swing.*;

public class AlunosCadastro extends EntidadesCadastro {
    public AlunosCadastro() {
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
