package graphic.entidades.alunos;

import controller.AlunosController;
import graphic.entidades.base.EntidadesPanel;
import model.AlunosModel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.List;

public class AlunosPanel extends EntidadesPanel {
    public AlunosPanel(JFrame cmpPai) {
        super(cmpPai);
    }

    @Override
    protected String getTitulo() {
        return "Alunos";
    }

    @Override
    protected String[] getColunasTabela() {
        return new String[]{ "Id", "Código", "Nome", "Celular", "Email", "Cidade" };
    }

    @Override
    protected void montaDadosTabela(JTable tabela, DefaultTableModel tableModel) {
        AlunosController alunosController = new AlunosController();
        List<Object> alunosBanco = alunosController.recuperarTodos();
        List<AlunosModel> listaAlunos = new ArrayList<>();

        alunosBanco.forEach(aluno -> listaAlunos.add((AlunosModel) aluno));

        for (int i = 0; i < listaAlunos.size(); i++) {
            Integer id = listaAlunos.get(i).getId();
            Integer codigoAluno = listaAlunos.get(i).getCodigoAluno();
            String nome = listaAlunos.get(i).getNome();
            String celular = listaAlunos.get(i).getCelular();
            String email = listaAlunos.get(i).getEmail();
            String cidade = listaAlunos.get(i).getCidade();

            Object[] linha = { id, codigoAluno, nome, celular, email, cidade };

            tableModel.addRow(linha);
        }

        tabela.setModel(tableModel);
    }

    @Override
    protected void onDoubleClickLinha(String id) {
        System.out.println(id);
    }

    @Override
    protected void onClickNovo() {
        AlunosCadastro alunosCadastro = new AlunosCadastro();

        alunosCadastro.setVisible(true);
    }
}
