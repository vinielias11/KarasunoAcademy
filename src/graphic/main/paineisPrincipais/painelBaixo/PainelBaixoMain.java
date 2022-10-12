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

        BotaoAlunos botaoAlunos = new BotaoAlunos(90, 50, "Alunos", "/resources/icons/alunosIcon.png", cmpPai);
        add(botaoAlunos);

        BotaoModalidades botaoModalidades = new BotaoModalidades(340, 50, "Modalidades", "/resources/icons/modalidadesIcon.png", cmpPai);
        add(botaoModalidades);

        BotaoGraduacoes botaoGraduacoes = new BotaoGraduacoes(590, 50, "Graduações", "/resources/icons/graduacoesIcon.png", cmpPai);
        add(botaoGraduacoes);

        BotaoFaturas botaoFaturas = new BotaoFaturas(840, 50, "Sobre", "/resources/icons/sobreIcon.png", cmpPai);
        add(botaoFaturas);

        BotaoPlanos botaoPlanos = new BotaoPlanos(90, 280, "Planos", "/resources/icons/planosIcon.png", cmpPai);
        add(botaoPlanos);

        BotaoMatriculas botaoMatriculas = new BotaoMatriculas(340, 280, "Matrículas", "/resources/icons/matriculasIcon.png", cmpPai);
        add(botaoMatriculas);

        BotaoMatriculasModalidades botaoMatriculasModalidades = new BotaoMatriculasModalidades(590,280, "Matriculas Modalidades", "/resources/icons/sobreIcon.png", cmpPai);
        add(botaoMatriculasModalidades);

        BotaoSobre botaoSobre = new BotaoSobre(840, 280, "Sobre", "/resources/icons/sobreIcon.png", cmpPai);
        add(botaoSobre);
    }
}
