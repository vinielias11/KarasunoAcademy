package graphic.entidades.alunos;

import controller.AlunosController;
import graphic.entidades.base.BindingListener;
import graphic.entidades.base.EntidadesCadastro;
import model.AlunosModel;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

public class AlunosCadastro extends EntidadesCadastro {
    private AlunosModel alunosModel = new AlunosModel();
    private AlunosPanel alunosPanel;

    private boolean isEditando = false;
    public AlunosCadastro(AlunosPanel alunosPanel) {
        this.alunosPanel = alunosPanel;
        criaComponentes(null);
    }

    public AlunosCadastro(AlunosModel dados, AlunosPanel alunosPanel) {
        this.alunosPanel = alunosPanel;
        criaComponentes(dados);
    }

    public void criaComponentes(AlunosModel dados) {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints c1 = new GridBagConstraints();
        panel.setSize(520,580);

        MaskFormatter mascaraCelular = null;
        MaskFormatter mascaraCep = null;
        try {
            mascaraCelular = new MaskFormatter("(##) #####-####");
            mascaraCep = new MaskFormatter("#####-###");
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        JLabel nome = new JLabel("Nome: ");
        JTextField nomeTxf = new JTextField(20);
        nomeTxf.getDocument().addDocumentListener(new BindingListener(alunosModel, "nome"));

        JLabel dataNacimento = new JLabel("Data de Nascimento: ");
        SpinnerDateModel dateModel = new SpinnerDateModel(new Date(), null, null, Calendar.DAY_OF_MONTH);
        JSpinner dateSpinner = new JSpinner(dateModel);
        dateSpinner.setEditor(new JSpinner.DateEditor(dateSpinner, "dd/MM/yyyy"));
        JComponent editor = dateSpinner.getEditor();
        JSpinner.DefaultEditor spinnerEditor = (JSpinner.DefaultEditor)editor;
        spinnerEditor.getTextField().setHorizontalAlignment(JTextField.CENTER);
        Dimension d = new Dimension(120,20);
        dateSpinner.setPreferredSize(d);
        dateSpinner.addChangeListener(e -> alunosModel.setDataNascimento((Date) dateSpinner.getValue()));
        alunosModel.setDataNascimento(new Date());

        JLabel sexo = new JLabel("Sexo: ");
        String[] sexoString = { "Masculino", "Feminino" };
        JComboBox sexoCmbox = new JComboBox<>(sexoString);
        sexoCmbox.addActionListener(e -> {
            String valor = sexoCmbox.getSelectedItem().toString();
            if (valor.equals("Masculino")) {
                alunosModel.setSexo("M");
            } else {
                alunosModel.setSexo("F");
            }

        });
        alunosModel.setSexo("M");

        JLabel celular = new JLabel("Celular: ");
        JFormattedTextField celularTxf = new JFormattedTextField();
        mascaraCelular.install(celularTxf);
        celularTxf.setColumns(9);
        celularTxf.getDocument().addDocumentListener(new BindingListener(alunosModel, "celular"));

        JLabel email = new JLabel("Email: ");
        JTextField emailTxf = new JTextField(20);
        emailTxf.getDocument().addDocumentListener(new BindingListener(alunosModel, "email"));

        JLabel endereco = new JLabel("Endereco: ");
        JTextField enderecoTxf = new JTextField(20);
        enderecoTxf.getDocument().addDocumentListener(new BindingListener(alunosModel, "endereco"));

        JLabel numero = new JLabel("Numero: ");
        JTextField numeroTxf = new JTextField(20);
        numeroTxf.getDocument().addDocumentListener(new BindingListener(alunosModel, "numero"));

        JLabel complemento = new JLabel("Complemento: ");
        JTextField complementoTxf = new JTextField(20);
        complementoTxf.getDocument().addDocumentListener(new BindingListener(alunosModel, "complemento"));

        JLabel bairro = new JLabel("Bairro: ");
        JTextField bairroTxf = new JTextField(20);
        bairroTxf.getDocument().addDocumentListener(new BindingListener(alunosModel, "bairro"));

        JLabel cidade = new JLabel("Cidade: ");
        JTextField cidadeTxf = new JTextField(20);
        cidadeTxf.getDocument().addDocumentListener(new BindingListener(alunosModel, "cidade"));

        JLabel estado = new JLabel("Estado: ");
        JTextField estadoTxf = new JTextField(20);
        estadoTxf.getDocument().addDocumentListener(new BindingListener(alunosModel, "estado"));

        JLabel pais = new JLabel("Pais: ");
        JTextField paisTxf = new JTextField(20);
        paisTxf.getDocument().addDocumentListener(new BindingListener(alunosModel, "pais"));

        JLabel cep = new JLabel("CEP: ");
        JFormattedTextField cepTxf = new JFormattedTextField();
        cepTxf.setColumns(20);
        cepTxf.getDocument().addDocumentListener(new BindingListener(alunosModel, "cep"));
        mascaraCep.install(cepTxf);

        JLabel observacao = new JLabel("Observação: ");
        JTextArea observacaoTxa = new JTextArea(5, 20);
        JScrollPane scpObservacao = new JScrollPane(observacaoTxa);
        observacaoTxa.setWrapStyleWord(true);
        observacaoTxa.setLineWrap(true);
        observacaoTxa.getDocument().addDocumentListener(new BindingListener(alunosModel, "observacao"));

        c1.insets = new Insets(0, 0, 10, 35);
        c1.gridx = 0; c1.gridy = 0; c1.anchor = GridBagConstraints.EAST;
        panel.add(nome, c1);
        c1.gridx = 1; c1.gridy = 0;
        panel.add(nomeTxf, c1);
        c1.gridx = 0; c1.gridy = 1;
        panel.add(email, c1);
        c1.gridx = 1; c1.gridy = 1;
        panel.add(emailTxf, c1);
        c1.gridx = 0; c1.gridy = 3;
        panel.add(dataNacimento, c1);
        c1.gridx = 1; c1.gridy = 3; c1.anchor = GridBagConstraints.WEST;
        panel.add(dateSpinner, c1);
        c1.anchor = GridBagConstraints.EAST; c1.gridx = 0; c1.gridy = 4;
        panel.add(sexo, c1);
        c1.gridx = 1; c1.gridy = 4; c1.anchor = GridBagConstraints.WEST;
        panel.add(sexoCmbox, c1);
        c1.gridx = 0; c1.gridy = 5; c1.anchor = GridBagConstraints.EAST;
        panel.add(celular, c1);
        c1.gridx = 1; c1.gridy = 5; c1.anchor = GridBagConstraints.WEST;
        panel.add(celularTxf, c1);
        c1.gridx = 0; c1.gridy = 6; c1.anchor = GridBagConstraints.EAST;
        panel.add(endereco, c1);
        c1.gridx = 1; c1.gridy = 6;
        panel.add(enderecoTxf, c1);
        c1.gridx = 0; c1.gridy = 7;
        panel.add(numero, c1);
        c1.gridx = 1; c1.gridy = 7;
        panel.add(numeroTxf, c1);
        c1.gridx = 0; c1.gridy = 8;
        panel.add(complemento, c1);
        c1.gridx = 1; c1.gridy = 8;
        panel.add(complementoTxf, c1);
        c1.gridx = 0; c1.gridy = 9;
        panel.add(bairro, c1);
        c1.gridx = 1; c1.gridy = 9;
        panel.add(bairroTxf, c1);
        c1.gridx = 0; c1.gridy = 10;
        panel.add(cidade, c1);
        c1.gridx = 1; c1.gridy = 10;
        panel.add(cidadeTxf, c1);
        c1.gridx = 0; c1.gridy = 11;
        panel.add(estado, c1);
        c1.gridx = 1; c1.gridy = 11;
        panel.add(estadoTxf, c1);
        c1.gridx = 0; c1.gridy = 12;
        panel.add(pais, c1);
        c1.gridx = 1; c1.gridy = 12;
        panel.add(paisTxf, c1);
        c1.gridx = 0; c1.gridy = 13;
        panel.add(cep, c1);
        c1.gridx = 1; c1.gridy = 13;
        panel.add(cepTxf, c1);
        c1.gridx = 0; c1.gridy = 14;
        panel.add(observacao, c1);
        c1.gridx = 1; c1.gridy = 14;
        panel.add(scpObservacao, c1);

        if (dados != null) {
            isEditando = true;
            alunosModel.setId(dados.getId());
            nomeTxf.setText(dados.getNome());
            dateSpinner.setValue(dados.getDataNascimento());
            if (dados.getSexo().equals("M")) {
                sexoCmbox.setSelectedIndex(0);
            } else if (dados.getSexo().equals("F")) {
                sexoCmbox.setSelectedIndex(1);
            }
            celularTxf.setText(dados.getCelular());
            emailTxf.setText(dados.getEmail());
            enderecoTxf.setText(dados.getEndereco());
            numeroTxf.setText(dados.getNumero());
            complementoTxf.setText(dados.getComplemento());
            bairroTxf.setText(dados.getBairro());
            cidadeTxf.setText(dados.getCidade());
            estadoTxf.setText(dados.getEstado());
            paisTxf.setText(dados.getPais());
            cepTxf.setText(dados.getCep());
            observacaoTxa.setText(dados.getObservacao());
        }

        add(panel);
    }

    @Override
    protected void onClickSalvar() {
        AlunosController alunosController = new AlunosController();

        if (!isEditando) {
            alunosController.inserir(alunosModel, this);
        } else {
            alunosController.editar(alunosModel, this);
        }

        alunosPanel.recarregaLista();
    }
}
