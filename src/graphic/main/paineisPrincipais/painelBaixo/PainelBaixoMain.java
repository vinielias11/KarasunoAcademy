package graphic.main.paineisPrincipais.painelBaixo;

import graphic.main.paineisPrincipais.painelBaixo.botoesEntidades.*;
import graphic.main.paineisPrincipais.painelBaixo.botoesExtras.BotaoSobre;

import javax.swing.*;
import java.awt.*;

public class PainelBaixoMain extends JPanel {
    public PainelBaixoMain(JFrame cmpPai) {
        setLayout(null);
        setBackground(new Color(255, 255, 255));
        setBounds(0, 250, 1100, 570);

        BotaoAlunos botaoAlunos = new BotaoAlunos(120, 50, "Alunos", "/resources/icons/alunosIcon.png", cmpPai);
        add(botaoAlunos);

        BotaoModalidades botaoModalidades = new BotaoModalidades(450, 50, "Modalidades", "/resources/icons/modalidadesIcon.png", cmpPai);
        add(botaoModalidades);

        BotaoGraduacoes botaoGraduacoes = new BotaoGraduacoes(780, 50, "Graduações", "/resources/icons/graduacoesIcon.png", cmpPai);
        add(botaoGraduacoes);

        BotaoPlanos botaoPlanos = new BotaoPlanos(120, 280, "Planos", "/resources/icons/planosIcon.png", cmpPai);
        add(botaoPlanos);

        BotaoMatriculas botaoMatriculas = new BotaoMatriculas(450, 280, "Matrículas", "/resources/icons/matriculasIcon.png", cmpPai);
        add(botaoMatriculas);

        BotaoFaturasMatriculas botaoFaturasMatriculas = new BotaoFaturasMatriculas(780, 280, "Financeiro", "/resources/icons/faturasIcon.png", cmpPai);
        add(botaoFaturasMatriculas);
    }
}
