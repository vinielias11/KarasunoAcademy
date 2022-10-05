package graphic.entidades.alunos;

import graphic.entidades.base.EntidadesCadastro;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

public class AlunosCadastro extends EntidadesCadastro {
    public AlunosCadastro() {
        criaComponentes();
    }

    public void criaComponentes(){
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints c1 = new GridBagConstraints();
        panel.setSize(520,580);

        MaskFormatter mascaraCelular = null;
        try {
            mascaraCelular = new MaskFormatter("(##) #####-####");
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        JLabel nome = new JLabel("Nome: ");
        JTextField nomeTxf = new JTextField(20);

        JLabel dataNacimento = new JLabel("Data de Nascimento: ");
        SpinnerDateModel dateModel = new SpinnerDateModel(new Date(), null, null, Calendar.DAY_OF_MONTH);
        JSpinner dateSpinner = new JSpinner(dateModel);
        dateSpinner.setEditor(new JSpinner.DateEditor(dateSpinner, "dd/MM/yyyy"));
        JComponent editor = dateSpinner.getEditor();
        JSpinner.DefaultEditor spinnerEditor = (JSpinner.DefaultEditor)editor;
        spinnerEditor.getTextField().setHorizontalAlignment(JTextField.CENTER);
        Dimension d = new Dimension(120,20);
        dateSpinner.setPreferredSize(d);

        JLabel sexo = new JLabel("Sexo: ");
        String[] sexoString = {"Masculino", "Feminino"};
        JComboBox sexoCmbox = new JComboBox<>(sexoString);

        JLabel celular = new JLabel("Celular: ");
        JFormattedTextField celularTxf = new JFormattedTextField();
        mascaraCelular.install(celularTxf);
        celularTxf.setColumns(9);

        JLabel email = new JLabel("Email: ");
        JTextField emailTxf = new JTextField(20);

        JLabel endereco = new JLabel("Endereco: ");
        JTextField enderecoTxf = new JTextField(20);

        JLabel numero = new JLabel("Numero: ");
        JTextField numeroTxf = new JTextField(20);

        JLabel complemento = new JLabel("Complemento: ");
        JTextField complementoTxf = new JTextField(20);

        JLabel bairro = new JLabel("Bairro: ");
        JTextField bairroTxf = new JTextField(20);

        JLabel cidade = new JLabel("Cidade: ");
        JTextField cidadeTxf = new JTextField(20);

        JLabel estado = new JLabel("Estado: ");
        JTextField estadoTxf = new JTextField(20);

        JLabel pais = new JLabel("Pais: ");
        JTextField paisTxf = new JTextField(20);

        JLabel cep = new JLabel("CEP: ");
        JTextField cepTxf = new JTextField(20);

        JLabel observacao = new JLabel("Observação: ");
        JTextArea observacaoTxa = new JTextArea(5, 20);
        JScrollPane scpObservacao = new JScrollPane(observacaoTxa);
        observacaoTxa.setWrapStyleWord(true);
        observacaoTxa.setLineWrap(true);

        c1.insets = new Insets(0, 0, 10, 35);
        c1.gridx = 0;
        c1.gridy = 0;
        c1.anchor = GridBagConstraints.EAST;
        panel.add(nome, c1);
        c1.gridx = 1;
        c1.gridy = 0;
        panel.add(nomeTxf, c1);
        c1.gridx = 0;
        c1.gridy = 1;
        panel.add(email, c1);
        c1.gridx = 1;
        c1.gridy = 1;
        panel.add(emailTxf, c1);
        c1.gridx = 0;
        c1.gridy = 3;
        panel.add(dataNacimento, c1);
        c1.gridx = 1;
        c1.gridy = 3;
        c1.anchor = GridBagConstraints.WEST;
        panel.add(dateSpinner, c1);
        c1.anchor = GridBagConstraints.EAST;
        c1.gridx = 0;
        c1.gridy = 4;
        panel.add(sexo, c1);
        c1.gridx = 1;
        c1.gridy = 4;
        c1.anchor = GridBagConstraints.WEST;
        panel.add(sexoCmbox, c1);
        c1.gridx = 0;
        c1.gridy = 5;
        c1.anchor = GridBagConstraints.EAST;
        panel.add(celular, c1);
        c1.gridx = 1;
        c1.gridy = 5;
        c1.anchor = GridBagConstraints.WEST;
        panel.add(celularTxf, c1);
        c1.gridx = 0;
        c1.gridy = 6;
        c1.anchor = GridBagConstraints.EAST;
        panel.add(endereco, c1);
        c1.gridx = 1;
        c1.gridy = 6;
        panel.add(enderecoTxf, c1);
        c1.gridx = 0;
        c1.gridy = 7;
        panel.add(numero, c1);
        c1.gridx = 1;
        c1.gridy = 7;
        panel.add(numeroTxf, c1);
        c1.gridx = 0;
        c1.gridy = 8;
        panel.add(complemento, c1);
        c1.gridx = 1;
        c1.gridy = 8;
        panel.add(complementoTxf, c1);
        c1.gridx = 0;
        c1.gridy = 9;
        panel.add(bairro, c1);
        c1.gridx = 1;
        c1.gridy = 9;
        panel.add(bairroTxf, c1);
        c1.gridx = 0;
        c1.gridy = 10;
        panel.add(cidade, c1);
        c1.gridx = 1;
        c1.gridy = 10;
        panel.add(cidadeTxf, c1);
        c1.gridx = 0;
        c1.gridy = 11;
        panel.add(estado, c1);
        c1.gridx = 1;
        c1.gridy = 11;
        panel.add(estadoTxf, c1);
        c1.gridx = 0;
        c1.gridy = 12;
        panel.add(pais, c1);
        c1.gridx = 1;
        c1.gridy = 12;
        panel.add(paisTxf, c1);
        c1.gridx = 0;
        c1.gridy = 13;
        panel.add(cep, c1);
        c1.gridx = 1;
        c1.gridy = 13;
        panel.add(cepTxf, c1);
        c1.gridx = 0;
        c1.gridy = 14;
        panel.add(observacao, c1);
        c1.gridx = 1;
        c1.gridy = 14;
        panel.add(scpObservacao, c1);

        add(panel);
    }


    @Override
    protected void onClickSalvar() {

    }
}
