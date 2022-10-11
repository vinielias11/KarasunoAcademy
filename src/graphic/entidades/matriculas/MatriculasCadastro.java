package graphic.entidades.matriculas;

import controller.AlunosController;
import controller.MatriculasController;
import graphic.entidades.base.EntidadesCadastro;
import graphic.util.AlunosComboModel;
import graphic.util.AlunosComboRender;
import model.AlunosModel;
import model.MatriculasModel;
import model.ModalidadesModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MatriculasCadastro extends EntidadesCadastro {
    private MatriculasModel matriculasModel = new MatriculasModel();
    private MatriculasPanel matriculasPanel;

    private boolean isEditando = false;
    public MatriculasCadastro(MatriculasPanel matriculasPanel) {
        this.matriculasPanel = matriculasPanel;
        setSize(520,280);
        criaComponentes(null);
    }

    public MatriculasCadastro(MatriculasModel dados, MatriculasPanel matriculasPanel) {
        this.matriculasPanel = matriculasPanel;
        setSize(520,280);
        criaComponentes(dados);
    }

    public void criarBotoes() {

        JButton btnFechar = new JButton("Cancelar");
        btnFechar.setBounds(80,180,120,30);
        btnFechar.setFont(new Font("Helvetica", Font.BOLD, 16));
        btnFechar.setBackground(new Color(255,92,92));
        btnFechar.setBorder(BorderFactory.createLineBorder(new Color(255, 92, 92)));
        add(btnFechar);

        btnFechar.addActionListener(e -> {
            dispose();
        });

        JButton btnSalvar = new JButton("Salvar");
        btnSalvar.setBounds(290,180,120,30);;
        btnSalvar.addActionListener(e -> onClickSalvar());
        btnSalvar.setFont(new Font("Helvetica", Font.BOLD, 16));
        btnSalvar.setBackground(new Color(87, 241, 87, 210));
        btnSalvar.setBorder(BorderFactory.createLineBorder(new Color(87, 241, 87, 210)));
        add(btnSalvar);
    }

    private void criaComponentes(MatriculasModel dados) {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints c1 = new GridBagConstraints();
        panel.setSize(520,210);

        AlunosController alunosController = new AlunosController();
        ArrayList<AlunosModel> alunosRecuperados = alunosController.recuperaAlunosParaComboBox();
        AlunosComboModel alunosComboModel = new AlunosComboModel(alunosRecuperados);

        JLabel alunos = new JLabel("Aluno: ");
        JComboBox comboBoxAlunos = new JComboBox<>(alunosComboModel);
        comboBoxAlunos.setRenderer(new AlunosComboRender());
        comboBoxAlunos.setPreferredSize(new Dimension(224,20));

        comboBoxAlunos.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                AlunosModel item = (AlunosModel) comboBoxAlunos.getSelectedItem();

                matriculasModel.setCodigoAluno(item.getId());
            }
        });

        JLabel diaVencimento = new JLabel("Dia de Vencimento: ");
        int min = 1, value = 1, max = 20, stepSize = 1;
        SpinnerNumberModel numberModel = new SpinnerNumberModel(value, min, max, stepSize);
        JSpinner diaVencimentoSpn = new JSpinner(numberModel);
        diaVencimentoSpn.setPreferredSize(new Dimension(50,20));
        matriculasModel.setDiaVencimento((Integer) diaVencimentoSpn.getValue());
        diaVencimentoSpn.addChangeListener(e -> matriculasModel.setDiaVencimento((Integer) diaVencimentoSpn.getValue()));

        c1.insets = new Insets(0, 0, 20, 35);
        c1.gridx = 0; c1.gridy = 0; c1.anchor = GridBagConstraints.EAST;
        panel.add(alunos, c1);
        c1.gridx = 1;
        panel.add(comboBoxAlunos, c1);
        c1.gridx = 0; c1.gridy = 1; c1.anchor = GridBagConstraints.EAST;
        panel.add(diaVencimento, c1);
        c1.gridx = 1; c1.anchor = GridBagConstraints.WEST;
        panel.add(diaVencimentoSpn, c1);


        if (dados != null) {
            isEditando = true;
            matriculasModel.setCodigoMatricula(dados.getCodigoMatricula());
            diaVencimentoSpn.setValue(dados.getDiaVencimento());
            alunosRecuperados.forEach(alunoRecuperado -> {
                if (Objects.equals(alunoRecuperado.getId(), dados.getCodigoAluno())) {
                    comboBoxAlunos.setSelectedItem(alunoRecuperado);
                }
            });
        }

        add(panel);
    }

    @Override
    protected void onClickSalvar() {
        MatriculasController matriculasController = new MatriculasController();

        if(!validaCamposAntesDeSalvar()) return;

        if (!isEditando) {
            matriculasController.inserir(matriculasModel, this);
        } else {
            matriculasController.editar(matriculasModel, this);
        }

        matriculasPanel.recarregaLista();
    }

    private boolean validaCamposAntesDeSalvar(){
        MatriculasController matriculasController = new MatriculasController();
        List<Object> matriculasBanco = matriculasController.recuperarTodos();

        List<MatriculasModel> matriculasRecuperadas = new ArrayList<>();

        matriculasBanco.forEach(matricula -> matriculasRecuperadas.add((MatriculasModel) matricula));

        boolean validacao = true;

        if(matriculasModel.getCodigoAluno() == null){
            JOptionPane.showMessageDialog(null, "Insira um aluno!");
            validacao = false;
            return validacao;
        }

        for (int i = 0; i < matriculasRecuperadas.size(); i++) {
            if(matriculasRecuperadas.get(i).getCodigoAluno() == matriculasModel.getCodigoAluno()){
                JOptionPane.showMessageDialog(null, "Aluno já possui matricula!");
                validacao = false;
            }
        }
        return validacao;
    }

}
