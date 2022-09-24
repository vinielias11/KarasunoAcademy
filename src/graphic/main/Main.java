package graphic.main;

import graphic.main.paineisPrincipais.painelBaixo.PainelBaixoMain;
import graphic.main.paineisPrincipais.painelCima.PainelCimaMain;

import javax.swing.*;

public class Main extends JFrame {

    private JMenuBar menuBar;
    private JMenu menuSistema, menuCadastro, menuFinanceiro, menuUtilitario;
    private JMenuItem itemSistemaUsuario, itemCadastroAluno, itemCadastroModalidade;

    public Main() {
        setSize(1100,820);
        setTitle("Karasuno Academy");
        setResizable(false);
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        criaMenusSuspensos();
        criaPainelDeCima();
        criaPainelDeBaixo();
    }

    private void criaMenusSuspensos() {
        menuBar = new JMenuBar();

        menuSistema = new JMenu("Sistema");
        itemSistemaUsuario = new JMenuItem("Usuário");
        menuSistema.add(itemSistemaUsuario);

        menuCadastro = new JMenu("Cadastro");
        itemCadastroAluno = new JMenuItem("Aluno");
        itemCadastroModalidade = new JMenuItem("Modalidade");
        menuCadastro.add(itemCadastroAluno);
        menuCadastro.add(itemCadastroModalidade);

        menuBar.add(menuSistema);
        menuBar.add(menuCadastro);

        setJMenuBar(menuBar);
    }

    private void criaPainelDeCima() {
        PainelCimaMain painelCimaMain = new PainelCimaMain();
        getContentPane().add(painelCimaMain);
    }

    private void criaPainelDeBaixo() {
        PainelBaixoMain painelBaixoMain = new PainelBaixoMain(this);
        getContentPane().add(painelBaixoMain);
    }

    public static void main(String[] args) {
        new Main().setVisible(true);
    }
}
