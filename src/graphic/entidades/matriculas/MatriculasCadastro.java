package graphic.entidades.matriculas;

import controller.MatriculasController;
import graphic.entidades.base.EntidadesCadastro;
import model.MatriculasModel;

import javax.swing.*;
import java.awt.*;

public class MatriculasCadastro extends EntidadesCadastro {
    private MatriculasModel matriculasModel = new MatriculasModel();
    private MatriculasPanel matriculasPanel;

    private boolean isEditando = false;
    public MatriculasCadastro(MatriculasPanel matriculasPanel) {
        this.matriculasPanel = matriculasPanel;
        criaComponentes(null);
    }

    public MatriculasCadastro(MatriculasModel dados, MatriculasPanel matriculasPanel) {
        this.matriculasPanel = matriculasPanel;
        criaComponentes(dados);
    }

    private void criaComponentes(MatriculasModel dados) {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints c1 = new GridBagConstraints();
        panel.setSize(520,580);


        if (dados != null) {
            isEditando = true;

        }

        add(panel);
    }

    @Override
    protected void onClickSalvar() {
        MatriculasController matriculasController = new MatriculasController();

        if (!isEditando) {
            matriculasController.inserir(matriculasModel, this);
        } else {
            matriculasController.editar(matriculasModel, this);
        }

        matriculasPanel.recarregaLista();
    }
}
