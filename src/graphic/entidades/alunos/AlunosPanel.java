package graphic.entidades.alunos;

import controller.UsuarioController;
import graphic.entidades.base.EntidadesPanel;
import model.UsuarioModel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class AlunosPanel extends EntidadesPanel {

    public AlunosPanel(JFrame cmpPai) {
        super(cmpPai);
        criaComponentes();

    }

    @Override
    protected String[] getColunasTabela() {
        return new String[]{"Nome", "Senha", "Perfil"};
    }

    @Override
    protected void montaDadosTabela(JTable tabela, DefaultTableModel tableModel) {
        UsuarioController usuarioController = new UsuarioController();
        List<UsuarioModel> dados = usuarioController.recuperarTodos();

        for (int i = 0; i < dados.size(); i++) {
            String nome = dados.get(i).getNome();
            String senha = dados.get(i).getSenha();
            Integer perfil = dados.get(i).getPerfil();

            Object[] linha = {nome, senha, perfil};

            tableModel.addRow(linha);
        }

        tabela.setModel(tableModel);
    }

    @Override
    protected void onClickNovo() {
        AlunosCadastro alunosCadastro = new AlunosCadastro();

        alunosCadastro.setVisible(true);
    }

    private void criaComponentes() {

    }
}
