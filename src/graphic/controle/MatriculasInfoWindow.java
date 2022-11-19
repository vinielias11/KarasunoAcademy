package graphic.controle;

import controller.ControleGeralController;
import model.AlunosModel;
import model.MatriculasModel;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class MatriculasInfoWindow extends JDialog {

    public MatriculasInfoWindow(MatriculasModel matriculasModel){
        setSize(400, 150);
        setLayout(null);
        setTitle("Informações Matricula");
        setIconImage(Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/resources/icons/sistemaIcon.png")));
        setResizable(false);
        setLocationRelativeTo(null);
        setModal(true);
        criaComponentes(matriculasModel);
    }

    private void criaComponentes(MatriculasModel matriculasModel){
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setSize(400,150);
        GridBagConstraints gbc = new GridBagConstraints();

        JLabel labelNome = new JLabel();
        labelNome.setText("Nome: ");

        JTextField showNomeAlunoTxf = new JTextField();
        showNomeAlunoTxf.setPreferredSize(new Dimension(200,20));
        showNomeAlunoTxf.setEditable(false);
        showNomeAlunoTxf.setText(matriculasModel.getNomeAluno());
        showNomeAlunoTxf.setBorder(javax.swing.BorderFactory.createEmptyBorder());

        JLabel labelDataMatricula = new JLabel();
        labelDataMatricula.setText("Data da Matrícula: ");

        JTextField showDataMatricula = new JTextField();
        showDataMatricula.setPreferredSize(new Dimension(200,20));
        showDataMatricula.setEditable(false);
        showDataMatricula.setText(matriculasModel.getDataMatricula().toString());
        showDataMatricula.setBorder(javax.swing.BorderFactory.createEmptyBorder());

        JLabel labelDiaVencimentoPagamento = new JLabel();
        labelDiaVencimentoPagamento.setText("Dia de Vencimento: ");

        JTextField showDiaVencimento = new JTextField();
        showDiaVencimento.setPreferredSize(new Dimension(200,20));
        showDiaVencimento.setEditable(false);
        showDiaVencimento.setText(matriculasModel.getDiaVencimento().toString());
        showDiaVencimento.setBorder(javax.swing.BorderFactory.createEmptyBorder());

        JLabel labelDataEncerramento = new JLabel();
        labelDataEncerramento.setText("Data de Encerramento: ");

        JTextField showDataEncerramento = new JTextField();
        showDataEncerramento.setPreferredSize(new Dimension(200,20));
        showDataEncerramento.setEditable(false);
        if(matriculasModel.getDataEncerramento() == null){
            showDataEncerramento.setText("Matricula Ativa");
        }
        else{
            showDataEncerramento.setText(matriculasModel.getDataEncerramento().toString());
        }
        showDataEncerramento.setBorder(javax.swing.BorderFactory.createEmptyBorder());


        gbc.gridx = 0; gbc.gridy = 0; gbc.anchor = GridBagConstraints.NORTHEAST;
        panel.add(labelNome, gbc);
        gbc.gridx = 1; gbc.gridy = 0;  gbc.insets = new Insets (0,15,0,0);
        panel.add(showNomeAlunoTxf, gbc);
        gbc.gridx = 0; gbc.gridy = 1; gbc.insets = new Insets (0,0,0,0);
        panel.add(labelDataMatricula, gbc);
        gbc.gridx = 1; gbc.gridy = 1; gbc.insets = new Insets (0,15,0,0);
        panel.add(showDataMatricula, gbc);
        gbc.gridx = 0; gbc.gridy = 2; gbc.insets = new Insets (0,0,0,0);
        panel.add(labelDiaVencimentoPagamento, gbc);
        gbc.gridx = 1; gbc.gridy = 2; gbc.insets = new Insets (0,15,0,0);
        panel.add(showDiaVencimento, gbc);
        gbc.gridx = 0; gbc.gridy = 3; gbc.insets = new Insets (0,0,0,0);
        panel.add(labelDataEncerramento, gbc);
        gbc.gridx = 1; gbc.gridy = 3; gbc.insets = new Insets (0,15,35,0);
        panel.add(showDataEncerramento, gbc);

        add(panel);
    }
}
