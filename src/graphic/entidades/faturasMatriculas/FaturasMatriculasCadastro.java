package graphic.entidades.faturasMatriculas;

import controller.FaturasMatriculasController;
import controller.MatriculasController;
import graphic.entidades.base.EntidadesCadastro;
import graphic.util.MatriculasComboModel;
import graphic.util.MatriculasComboRenderer;
import model.FaturasMatriculasModel;
import model.MatriculasModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.util.ArrayList;
import java.util.Objects;

public class FaturasMatriculasCadastro extends EntidadesCadastro {

    private FaturasMatriculasModel faturasMatriculasModel = new FaturasMatriculasModel();
    private FaturasMatriculasPanel faturasMatriculasPanel;

    private boolean isEditando = false;
    public FaturasMatriculasCadastro(FaturasMatriculasPanel faturasMatriculasPanel) {
        this.faturasMatriculasPanel = faturasMatriculasPanel;
        setSize(520, 280);
        criaComponentes(null);
    }
    public FaturasMatriculasCadastro(FaturasMatriculasModel dados, FaturasMatriculasPanel faturasMatriculasPanel) {
        this.faturasMatriculasPanel = faturasMatriculasPanel;
        setSize(520, 280);
        criaComponentes(dados);
    }

    @Override
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

    private void criaComponentes(FaturasMatriculasModel dados){
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints c1 = new GridBagConstraints();
        panel.setSize(520,230);
//
//        MatriculasController matriculasController = new MatriculasController();
//        ArrayList<MatriculasModel> matriculasRecuperadas = matriculasController.recuperaMatriculasParaComboBox();
//        MatriculasComboModel matriculasComboModel = new MatriculasComboModel(matriculasRecuperadas);
//
//        JLabel matriculas = new JLabel("Código de Matrícula");
//        JComboBox comboBoxMatriculas = new JComboBox(matriculasComboModel);
//        comboBoxMatriculas.setRenderer(new MatriculasComboRenderer());
//        comboBoxMatriculas.setPreferredSize(new Dimension(224, 20));
//
//        comboBoxMatriculas.addItemListener(e -> {
//            if (e.getStateChange() == ItemEvent.SELECTED) {
//                MatriculasModel item = (MatriculasModel) comboBoxMatriculas.getSelectedItem();
//
//                faturasMatriculasModel.setCodigoMatricula(item.getCodigoMatricula());
//            }
//        });
//
//        c1.insets = new Insets(0, 0, 30, 35);
//        c1.gridx = 0; c1.gridy = 0;
//        panel.add(matriculas,c1);
//        c1.gridx = 1; c1.gridy = 0;
//        panel.add(comboBoxMatriculas,c1);
//
//        if (dados != null) {
//            isEditando = true;
//            faturasMatriculasModel.setId(dados.getId());
//            matriculasRecuperadas.forEach(matriculaRecuperada -> {
//                if (Objects.equals(matriculaRecuperada.getCodigoMatricula(), dados.getCodigoMatricula())) {
//                    comboBoxMatriculas.setSelectedItem(matriculaRecuperada);
//                }
//            });
//        }

        add(panel);
    }

    @Override
    protected void onClickSalvar() {
        FaturasMatriculasController faturasMatriculasController = new FaturasMatriculasController();

        if (!isEditando) {
            faturasMatriculasController.inserir(faturasMatriculasModel, this);
        } else {
            faturasMatriculasController.editar(faturasMatriculasModel, this);
        }

        faturasMatriculasPanel.recarregaLista();
    }
}
