package graphic.entidades.alunos;

import graphic.entidades.base.EntidadesPanel;
import graphic.entidades.base.FrameCadastro;

import javax.swing.*;

public class AlunosPanel extends EntidadesPanel {
    public AlunosPanel(JFrame cmpPai) {
        super(cmpPai);
        criaComponentes();

    }

    @Override
    protected void onClickNovo() {
        CadastroAlunos cadastroAlunos = new CadastroAlunos();

        cadastroAlunos.setVisible(true);
    }

    private void criaComponentes() {
    }
}
