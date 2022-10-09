package graphic.entidades.modalidades;

import controller.AlunosController;
import controller.ModalidadesController;
import graphic.entidades.alunos.AlunosPanel;
import graphic.entidades.base.BindingListener;
import graphic.entidades.base.EntidadesCadastro;
import model.AlunosModel;
import model.ModalidadesModel;

import javax.swing.*;
import java.awt.*;

public class ModalidadesCadastro extends EntidadesCadastro {
    private ModalidadesModel modalidadesModel = new ModalidadesModel();
    private ModalidadesPanel modalidadesPanel;

    private boolean isEditando = false;
    public ModalidadesCadastro(ModalidadesPanel modalidadesPanel) {
        this.modalidadesPanel = modalidadesPanel;
        setSize(520, 200);
        criaComponentes(null);
    }

    public ModalidadesCadastro(ModalidadesModel dados, ModalidadesPanel modalidadesPanel) {
        this.modalidadesPanel = modalidadesPanel;
        criaComponentes(dados);
        setSize(520, 200);
    }

    @Override
    public void criarBotoes() {

        JButton btnFechar = new JButton("Cancelar");
        btnFechar.setBounds(80,100,120,30);
        btnFechar.setFont(new Font("Helvetica", Font.BOLD, 16));
        btnFechar.setBackground(new Color(255,92,92));
        btnFechar.setBorder(BorderFactory.createLineBorder(new Color(255, 92, 92)));
        add(btnFechar);

        btnFechar.addActionListener(e -> {
            dispose();
        });

        JButton btnSalvar = new JButton("Salvar");
        btnSalvar.setBounds(290,100,120,30);;
        btnSalvar.addActionListener(e -> onClickSalvar());
        btnSalvar.setFont(new Font("Helvetica", Font.BOLD, 16));
        btnSalvar.setBackground(new Color(87, 241, 87, 210));
        btnSalvar.setBorder(BorderFactory.createLineBorder(new Color(87, 241, 87, 210)));
        add(btnSalvar);
    }

    private void criaComponentes(ModalidadesModel dados){
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints c1 = new GridBagConstraints();
        panel.setSize(520, 200);

        JLabel nome = new JLabel("Nome: ");
        JTextField nomeTxf = new JTextField(20);
        nomeTxf.getDocument().addDocumentListener(new BindingListener(modalidadesModel, "nome"));

        c1.insets = new Insets(0, 0, 100, 35);
        c1.gridx = 0; c1.gridy = 0; c1.anchor = GridBagConstraints.NORTHEAST;
        panel.add(nome, c1);
        c1.gridx = 1; c1.gridy = 0;
        panel.add(nomeTxf, c1);

        if (dados != null) {
            isEditando = true;
            modalidadesModel.setId(dados.getId());
            nomeTxf.setText(dados.getNome());
        }

        add(panel);

    }

    @Override
    protected void onClickSalvar() {
        ModalidadesController modalidadesController = new ModalidadesController();

        if (!isEditando) {
            modalidadesController.inserir(modalidadesModel, this);
        } else {
            modalidadesController.editar(modalidadesModel, this);
        }

        modalidadesPanel.recarregaLista();
    }
}
