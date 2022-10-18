package graphic.entidades.matriculas;

import controller.FaturasMatriculasController;
import controller.MatriculasController;
import graphic.entidades.base.EntidadesPanel;
import model.FaturasMatriculasModel;
import model.MatriculasModel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MatriculasPanel extends EntidadesPanel {
    FaturasMatriculasController faturasMatriculasController = new FaturasMatriculasController();

    public MatriculasPanel(JFrame cmpPai) {
        super(cmpPai);
    }

    @Override
    protected void criarBotoes(JPanel panel, JTable tabela) {
        super.criarBotoes(panel, tabela);

        ImageIcon smbEncerrarMatricula = new ImageIcon(this.getClass().getResource("/resources/icons/encerrarMatriculaIcon.png"));
        JButton btnEncerrarMatricula = new JButton(smbEncerrarMatricula);
        btnEncerrarMatricula.setBounds(155, 33, 40, 40);
        btnEncerrarMatricula.setBackground(Color.WHITE);
        btnEncerrarMatricula.setBorder(BorderFactory.createEmptyBorder());
        btnEncerrarMatricula.setOpaque(false);
        btnEncerrarMatricula.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnEncerrarMatricula.setToolTipText("Encerrar Matricula");
        btnEncerrarMatricula.addActionListener(e -> {
            try {
                Integer linha = tabela.getSelectedRow();
                String id = tabela.getModel().getValueAt(linha, 0).toString();
                String data = tabela.getModel().getValueAt(linha, 4).toString();
                onClickEncerrarMatricula(id, data);
            }catch (ArrayIndexOutOfBoundsException ex){
                JOptionPane.showMessageDialog(null,"Selecione um registro para encerrar a matricula!", "Atenção", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        panel.add(btnEncerrarMatricula);
    }

    @Override
    protected String getTitulo() {
        return "Matrículas";
    }

    @Override
    protected void deletar(String id) {
        MatriculasModel matriculasModel = new MatriculasModel();
        MatriculasController matriculasController = new MatriculasController();

        Integer idDeletar = Integer.parseInt(id);

        List<Object> faturasBanco = faturasMatriculasController.recuperarTodos();
        List<FaturasMatriculasModel> listaFaturas = new ArrayList<>();

        faturasBanco.forEach(fatura -> listaFaturas.add((FaturasMatriculasModel) fatura));

        for (int i = 0; i < listaFaturas.size(); i++) {
            if(listaFaturas.get(i).getCodigoMatricula() == idDeletar){
                faturasMatriculasController.deletar(listaFaturas.get(i));
            }
        }

        matriculasModel.setCodigoMatricula(idDeletar);
        matriculasController.deletar(matriculasModel);

        this.recarregaLista();
    }

    @Override
    protected String[] getColunasTabela() {
        return new String[]{ "Id", "Aluno", "Data da Matrícula", "Dia de vencimento", "Data de Encerramento" };
    }

    @Override
    protected void montaDadosTabela(JTable tabela, DefaultTableModel tableModel) {
        MatriculasController matriculasController = new MatriculasController();
        List<Object> matriculasBanco = matriculasController.recuperarTodos();
        List<MatriculasModel> listaMatriculas = new ArrayList<>();

        matriculasBanco.forEach(matricula -> listaMatriculas.add((MatriculasModel) matricula));

        for (int i = 0; i < listaMatriculas.size(); i++) {
            Integer codigoMatricula = listaMatriculas.get(i).getCodigoMatricula();
            String nomeAluno = listaMatriculas.get(i).getNomeAluno();
            Integer diaVencimento = listaMatriculas.get(i).getDiaVencimento();
            String dataMatricula = listaMatriculas.get(i).getDataMatricula().toString();
            String dataEncerramento = "";

            if(listaMatriculas.get(i).getDataEncerramento() != null){
                dataEncerramento = listaMatriculas.get(i).getDataEncerramento().toString();
            }

            Object[] linha = { codigoMatricula, nomeAluno, dataMatricula, diaVencimento, dataEncerramento};

            tableModel.addRow(linha);
        }

        tabela.setModel(tableModel);
    }

    @Override
    protected void onDoubleClickLinha(String id) {
        MatriculasController matriculasController = new MatriculasController();
        MatriculasModel matriculaRecuperar = new MatriculasModel();

        matriculaRecuperar.setCodigoMatricula(Integer.parseInt(id));
        matriculaRecuperar = matriculasController.recuperarPorId(matriculaRecuperar);

        MatriculasCadastro matriculasCadastro = new MatriculasCadastro(matriculaRecuperar, this);

        matriculasCadastro.setVisible(true);
    }

    @Override
    protected void onClickNovo() {
        MatriculasCadastro matriculasCadastro = new MatriculasCadastro(null, this);
        matriculasCadastro.setVisible(true);
    }

    private void onClickEncerrarMatricula(String id, String data){
        if(!"".equals(data)){
            JOptionPane.showMessageDialog(null, "Matricula já encerrada!");
            return;
        }

        MatriculasController matriculasController = new MatriculasController();
        MatriculasModel matriculaRecuperar = new MatriculasModel();

        List<Object> faturasBanco = faturasMatriculasController.recuperarTodos();
        List<FaturasMatriculasModel> listaFaturas = new ArrayList<>();

        faturasBanco.forEach(fatura -> listaFaturas.add((FaturasMatriculasModel) fatura));

        for (int i = 0; i < listaFaturas.size(); i++) {
            if(listaFaturas.get(i).getCodigoMatricula() == Integer.parseInt(id)){
                FaturasMatriculasModel faturaRecuperar = new FaturasMatriculasModel();
                java.util.Date utilDate = new Date();
                java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
                faturaRecuperar.setDataCancelamento(sqlDate);
                faturaRecuperar.setCodigoMatricula(Integer.parseInt(id));
                faturasMatriculasController.encerrarPorMatricula(faturaRecuperar);
            }
        }

        matriculaRecuperar.setCodigoMatricula(Integer.parseInt(id));;
        matriculaRecuperar.setDataEncerramento(new Date());
        matriculasController.encerrar(matriculaRecuperar);

        recarregaLista();
    }

}
