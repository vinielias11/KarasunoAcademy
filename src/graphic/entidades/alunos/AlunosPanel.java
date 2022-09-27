package graphic.entidades.alunos;

import graphic.entidades.base.EntidadesPanel;

import javax.swing.*;

public class AlunosPanel extends EntidadesPanel {
    public AlunosPanel(JFrame cmpPai) {
        super(cmpPai);
        criaComponentes();
    }

    @Override
    protected void onClickNovo() {
        AlunosCadastro alunosCadastro = new AlunosCadastro();

        alunosCadastro.setVisible(true);
    }

    private void criaComponentes() {
    }
}
