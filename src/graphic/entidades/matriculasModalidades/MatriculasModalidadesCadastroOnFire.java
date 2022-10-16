package graphic.entidades.matriculasModalidades;

import controller.*;
import graphic.entidades.base.EntidadesCadastro;
import graphic.entidades.matriculas.MatriculasCadastroOnFire;
import graphic.entidades.matriculas.MatriculasPanel;
import graphic.util.*;
import model.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class MatriculasModalidadesCadastroOnFire extends EntidadesCadastro {
    private MatriculasCadastroOnFire matriculasCadastroOnFire;
    private MatriculasModel matriculasModel = new MatriculasModel();
    private MatriculasModalidadesModel matriculasModalidadesModel = new MatriculasModalidadesModel();
    private MatriculasController matriculasController = new MatriculasController();
    private AlunosController alunosController = new AlunosController();
    private ModalidadesController modalidadesController = new ModalidadesController();
    private GraduacoesController graduacoesController = new GraduacoesController();
    private PlanosController planosController = new PlanosController();
    private MatriculasPanel matriculasPanel;
    private JPanel panel = new JPanel(new GridBagLayout());
    private JTable tabela = new JTable();
    private GridBagConstraints c = new GridBagConstraints();
    private DefaultTableModel tableModel = new DefaultTableModel();
    private boolean isEditando = false;
    private Integer matriculaEditando = 0;

    public MatriculasModalidadesCadastroOnFire(MatriculasModalidadesModel matriculasModalidadesModelEditando,
                                               MatriculasCadastroOnFire matriculasCadastroOnFire,
                                               MatriculasModel matriculasModel) {
        this.matriculasCadastroOnFire = matriculasCadastroOnFire;
        this.matriculasModel = matriculasModel;
        this.construirViewCadastro(matriculasModalidadesModelEditando);
    }

    public void construirViewCadastro(MatriculasModalidadesModel matriculasModalidadesModelEditando){
        configuracoesIniciaisPanel();
        criarBotaoCodigomatricula();
        criarComboBoxModalidade();
        criarComboBoxGraduacao();
        criarComboBoxPlano();
        add(this.panel);
    }

    public void criarBotaoCodigomatricula(){
        JLabel labelCodigoMatricula = new JLabel("Código Matrícula: ");
        setGridLayout(0,0, GridBagConstraints.EAST);
        panel.add(labelCodigoMatricula, this.c);

        JLabel labelValorCodigoMatricula = new JLabel(this.matriculasModel.getCodigoMatricula().toString());
        this.matriculasModalidadesModel.setCodigoMatricula(this.matriculasModel.getCodigoMatricula());
        setGridLayout(1,0, GridBagConstraints.WEST);
        panel.add(labelValorCodigoMatricula, this.c);
    }

    public void criarComboBoxPlano(){
        ArrayList<PlanosModel> planosRecuperados = planosController.recuperaPlanosParaComboBox();
        PlanosComboModel planosComboModel = new PlanosComboModel(planosRecuperados);

        JLabel labelPlano = new JLabel("Plano: ");
        setGridLayout(0,3, GridBagConstraints.EAST);
        panel.add(labelPlano, this.c);

        JComboBox comboBoxPlano = new JComboBox<>(planosComboModel);
        comboBoxPlano.setRenderer(new PlanosComboRenderer());
        comboBoxPlano.setPreferredSize(new Dimension(224,20));
        comboBoxPlano.setEnabled(false);
        comboBoxPlano.setName("CBPlano");
        setGridLayout(1,3, GridBagConstraints.WEST);

        comboBoxPlano.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                PlanosModel item = (PlanosModel) comboBoxPlano.getSelectedItem();
                matriculasModalidadesModel.setPlano(item.getId());
            }
        });

        panel.add(comboBoxPlano, this.c);
    }

    public void criarComboBoxModalidade(){
        ArrayList<ModalidadesModel> modalidadesRecuperadas = modalidadesController.recuperaModalidadesParaComboBox();
        ModalidadesComboModel alunosComboModel = new ModalidadesComboModel(modalidadesRecuperadas);

        JLabel labelModalidade = new JLabel("Modalidade: ");
        setGridLayout(0,1, GridBagConstraints.EAST);
        panel.add(labelModalidade, this.c);

        JComboBox comboBoxModalidade = new JComboBox<>(alunosComboModel);
        comboBoxModalidade.setRenderer(new ModalidadesComboRenderer());
        comboBoxModalidade.setPreferredSize(new Dimension(224,20));
        setGridLayout(1,1, GridBagConstraints.WEST);

        comboBoxModalidade.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                ModalidadesModel item = (ModalidadesModel) comboBoxModalidade.getSelectedItem();
                matriculasModalidadesModel.setModalidade(item.getId());
                atualizarComboBoxGraduacao();
                atualizarComboBoxPlano();
            }
        });

        panel.add(comboBoxModalidade, this.c);
    }

    private void removeComponente(String name){
        Component[] componentList = panel.getComponents();
        for(Component c : componentList){
            if(c instanceof JComboBox){
                if(c.getName() == name){
                    panel.remove(c);
                    break;
                }
            }
        }
        panel.revalidate();
        panel.repaint();
    }

    private void atualizarComboBoxPlano(){
        removeComponente("CBPlano");
        PlanosController planosControllerAtt = new PlanosController();
        ArrayList<PlanosModel> planosRecuperados = planosControllerAtt.recuperaPlanosParaComboBox();

        ArrayList<PlanosModel> planosPorModalidade = (ArrayList<PlanosModel>) planosRecuperados.stream()
                .filter(i -> i.getIdModalidade() == this.matriculasModalidadesModel.getModalidade())
                .collect(Collectors.toList());

        PlanosComboModel planosComboModel = new PlanosComboModel(planosPorModalidade);

        JComboBox comboBoxPlano = new JComboBox<>(planosComboModel);
        comboBoxPlano.setRenderer(new PlanosComboRenderer());
        comboBoxPlano.setPreferredSize(new Dimension(224,20));
        comboBoxPlano.setName("CBPlano");
        setGridLayout(1,3, GridBagConstraints.WEST);

        comboBoxPlano.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                PlanosModel item = (PlanosModel) comboBoxPlano.getSelectedItem();
                matriculasModalidadesModel.setPlano(item.getId());
            }
        });

        panel.add(comboBoxPlano, this.c);
    }

    private void atualizarComboBoxGraduacao(){
        removeComponente("CBGraduacao");
        GraduacoesController graduacoesControllerAtt = new GraduacoesController();
        ArrayList<GraduacoesModel> graduacoesRecuperadas = graduacoesControllerAtt.recuperaGraduacoesParaComboBox();

        ArrayList<GraduacoesModel> graduacoesPorModalidade = (ArrayList<GraduacoesModel>) graduacoesRecuperadas.stream()
                .filter(i -> i.getIdModalidade() == this.matriculasModalidadesModel.getModalidade())
                .collect(Collectors.toList());

        GraduacoesComboModel graduacoesComboModel = new GraduacoesComboModel(graduacoesPorModalidade);

        JComboBox comboBoxGraduacao = new JComboBox<>(graduacoesComboModel);
        comboBoxGraduacao.setRenderer(new GraduacoesComboRenderer());
        comboBoxGraduacao.setName("CBGraduacao");
        comboBoxGraduacao.setPreferredSize(new Dimension(224,20));
        setGridLayout(1,2, GridBagConstraints.WEST);
        comboBoxGraduacao.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                GraduacoesModel item = (GraduacoesModel) comboBoxGraduacao.getSelectedItem();
                matriculasModalidadesModel.setGraduacao(item.getId());
            }
        });
        panel.add(comboBoxGraduacao, this.c);
    }

    public void criarComboBoxGraduacao(){
        JLabel labelGraduacao = new JLabel("Graduação: ");
        setGridLayout(0,2, GridBagConstraints.EAST);
        panel.add(labelGraduacao, this.c);

        ArrayList<GraduacoesModel> graduacoesRecuperadas = graduacoesController.recuperaGraduacoesParaComboBox();
        GraduacoesComboModel graduacoesComboModel = new GraduacoesComboModel(graduacoesRecuperadas);
        JComboBox comboBoxGraduacao = new JComboBox<>(graduacoesComboModel);
        comboBoxGraduacao.setRenderer(new GraduacoesComboRenderer());
        comboBoxGraduacao.setName("CBGraduacao");
        comboBoxGraduacao.setPreferredSize(new Dimension(224,20));
        comboBoxGraduacao.setEnabled(false);
        setGridLayout(1,2, GridBagConstraints.WEST);
        comboBoxGraduacao.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                GraduacoesModel item = (GraduacoesModel) comboBoxGraduacao.getSelectedItem();
                matriculasModalidadesModel.setGraduacao(item.getId());
            }
        });
        panel.add(comboBoxGraduacao, this.c);
    }

    private void configuracoesIniciaisPanel(){
        Integer frameWidth = 500;
        Integer frameHeight = 700;
        setSize(frameWidth,frameHeight);
        this.panel.setSize(new Dimension(frameWidth, frameHeight));
        this.c.insets = new Insets(0, 0, 20, 10);
    }

    @Override
    protected void onClickSalvar() {
        MatriculasModalidadesController matriculasModalidadesController = new MatriculasModalidadesController();
        if (!isEditando) {
            matriculasModalidadesController.inserir(matriculasModalidadesModel, this);
        } else {
            matriculasModalidadesController.editar(matriculasModalidadesModel, this);
        }
        matriculasCadastroOnFire.atualizarDadosTabela();
    }

    private void setGridLayout(Integer x, Integer y, Integer orientation){
        this.c.gridx = x;
        this.c.gridy = y;
        this.c.anchor = orientation;
    }

    protected void deletar(String id) {
        MatriculasModalidadesModel matriculasModalidadesModel = new MatriculasModalidadesModel();
        MatriculasModalidadesController matriculasModalidadesController = new MatriculasModalidadesController();

        Integer idDeletar = Integer.parseInt(id);

        matriculasModalidadesModel.setCodigoMatricula(idDeletar);
        matriculasModalidadesController.deletar(matriculasModalidadesModel);

        matriculasCadastroOnFire.atualizarDadosTabela();
    }
}
