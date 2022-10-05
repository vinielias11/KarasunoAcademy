package graphic.login;

import controller.UsuariosController;
import graphic.main.Main;

import javax.swing.*;
import java.awt.*;

public class LoginWindow extends JFrame {

    public LoginWindow() {
        setSize(350, 220);
        setTitle("Login");
        setIconImage(Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/resources/icons/sistemaIcon.png")));
        setResizable(false);
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        criaComponentes();
    }

    private void criaComponentes() {
        JPanel jpanel = new JPanel();
        jpanel.setLayout(null);
        jpanel.setBounds(0, 0, 350, 220);

        JLabel titulo = new JLabel("Karasuno Academy");
        titulo.setBounds(116, 20, 115, 20);
        jpanel.add(titulo);

        JLabel labelUsuario = new JLabel("Usuário:");
        labelUsuario.setBounds(55, 60, 100, 20);
        jpanel.add(labelUsuario);

        JTextField textFieldUsuario = new JTextField();
        textFieldUsuario.setBounds(125, 60, 150, 20);
        jpanel.add(textFieldUsuario);

        JLabel labelSenha = new JLabel("Senha:");
        labelSenha.setBounds(55, 90, 100, 20);
        jpanel.add(labelSenha);

        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(125, 90, 150, 20);
        jpanel.add(passwordField);

        JButton botaoFechar = new JButton("Fechar");
        botaoFechar.setBounds(55, 130, 100, 25);
        jpanel.add(botaoFechar);

        JButton botaoEntrar = new JButton("Entrar");
        botaoEntrar.setBounds(175, 130, 100, 25);
        jpanel.add(botaoEntrar);

        botaoFechar.addActionListener(e -> {
            dispose();
        });

        botaoEntrar.addActionListener(e -> {
            String inputUsuario = textFieldUsuario.getText();
            String inputSenha = String.valueOf(passwordField.getPassword());

            if ("".equals(inputUsuario) || "".equals(inputSenha)) {
                JOptionPane.showMessageDialog(null, "Insira um nome e senha válidos!");
                return;
            }

            onClickEntrar(inputUsuario, inputSenha);
        });

        getContentPane().add(jpanel);
    }

    private void onClickEntrar(String nome, String senha) {
        UsuariosController usuariosController = new UsuariosController();

        if (usuariosController.logar(senha, nome)) {
            Main main = new Main();
            main.setVisible(true);
            dispose();
        } else {
            JOptionPane.showMessageDialog(null, "Usuário ou senha inválidos!");
        }
    }

    public static void main(String[] args) {
        new LoginWindow().setVisible(true);
    }
}