package graphic.entidades.faturasMatriculas;

import controller.FaturasMatriculasController;
import graphic.entidades.base.EntidadesPanel;
import model.FaturasMatriculasModel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FaturasMatriculasPanel extends EntidadesPanel {

    public FaturasMatriculasPanel(JFrame cmpPai) { super(cmpPai); }

    @Override
    protected String getTitulo() {
        return "Financeiro";
    }

    @Override
    protected void deletar(String id) {

    }

    protected void deletar(Integer codigoMatricula, Date dataVencimento) {
        FaturasMatriculasModel faturasMatriculasModel = new FaturasMatriculasModel();
        FaturasMatriculasController faturasMatriculasController = new FaturasMatriculasController();

        faturasMatriculasModel.setCodigoMatricula(codigoMatricula);
        faturasMatriculasModel.setDataVencimento(dataVencimento);
        faturasMatriculasController.deletar(faturasMatriculasModel);

        this.recarregaLista();
    }

    @Override
    protected void criarBotoes(JPanel panel, JTable tabela){
        ImageIcon smbMais = new ImageIcon(this.getClass().getResource("/resources/icons/plusIcon.png"));
        JButton btnCadastrar = new JButton(smbMais);
        btnCadastrar.setBounds(45, 30, 40, 40);
        btnCadastrar.setBackground(Color.WHITE);
        btnCadastrar.setBorder(BorderFactory.createEmptyBorder());
        btnCadastrar.setOpaque(false);
        btnCadastrar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnCadastrar.setToolTipText("Novo");
        btnCadastrar.addActionListener(e -> onClickNovo());

        ImageIcon smbDelete = new ImageIcon(this.getClass().getResource("/resources/icons/deleteIcon.png"));
        JButton btnDelete = new JButton(smbDelete);
        btnDelete.setBackground(Color.WHITE);
        btnDelete.setOpaque(false);
        btnDelete.setBorder(BorderFactory.createEmptyBorder());
        btnDelete.setBounds(100,30,40,40);
        btnDelete.setCursor(new Cursor((Cursor.HAND_CURSOR)));
        btnDelete.setToolTipText("Excluir");
        btnDelete.addActionListener(e -> {
            try {
                Integer linha = tabela.getSelectedRow();
                Integer codigoMatricula = (Integer) tabela.getModel().getValueAt(linha, 0);
                Date dataVencimento = new Date();
                try {
                    SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
                    dataVencimento = (Date) f.parse((String) tabela.getModel().getValueAt(linha,1));
                } catch (ParseException e2) {
                    e2.printStackTrace();
                }

                deletar(codigoMatricula, dataVencimento);
            }catch (ArrayIndexOutOfBoundsException ex){
                JOptionPane.showMessageDialog(null,"Selecione um registro para deletar!", "Atenção", JOptionPane.INFORMATION_MESSAGE);
            }
        });


        panel.add(btnDelete);
        panel.add(btnCadastrar);
    }

    @Override
    protected String[] getColunasTabela() {
        return new String[]{"Matrícula", "Data de Vencimento", "Valor", "Data de Pagamento", "Data de Cancelamento"};
    }

    @Override
    protected void montaDadosTabela(JTable tabela, DefaultTableModel tableModel) {
        FaturasMatriculasController faturasMatriculasController = new FaturasMatriculasController();
        List<Object> faturasMatriculasBanco = faturasMatriculasController.recuperarTodos();
        List<FaturasMatriculasModel> listaFaturasMatriculas = new ArrayList<>();

        faturasMatriculasBanco.forEach(faturasMatriculas -> listaFaturasMatriculas.add((FaturasMatriculasModel) faturasMatriculas));

        for (int i = 0; i < listaFaturasMatriculas.size(); i++) {
            Integer codigoMatricula = listaFaturasMatriculas.get(i).getCodigoMatricula();
            String dataVencimento = String.valueOf(listaFaturasMatriculas.get(i).getDataVencimento());
            Double valor = listaFaturasMatriculas.get(i).getValor();
            Date dataPagamento = (listaFaturasMatriculas.get(i).getDataPagamento());
            Date dataCancelamento = (listaFaturasMatriculas.get(i).getDataCancelamento());

            Object[] linha = { codigoMatricula, dataVencimento, valor, dataPagamento, dataCancelamento };

            tableModel.addRow(linha);
        }

        tabela.setModel(tableModel);
    }

    @Override
    protected void onDoubleClickLinha(String id) {

    }

    protected void onDoubleClickLinha(Integer codigoMatricula, Date dataVencimento) {
        FaturasMatriculasController faturasMatriculasController = new FaturasMatriculasController();
        FaturasMatriculasModel faturasMatriculasRecuperar = new FaturasMatriculasModel();

        faturasMatriculasRecuperar.setCodigoMatricula(codigoMatricula);
        faturasMatriculasRecuperar.setDataVencimento(dataVencimento);
        faturasMatriculasRecuperar = faturasMatriculasController.recuperarPorId(faturasMatriculasRecuperar);

        FaturasMatriculasCadastro faturasMatriculasCadastro = new FaturasMatriculasCadastro(faturasMatriculasRecuperar, this);

        faturasMatriculasCadastro.setVisible(true);
    }

    @Override
    protected void onClickNovo() {
        FaturasMatriculasCadastro faturasMatriculasCadastro = new FaturasMatriculasCadastro(this);
        faturasMatriculasCadastro.setVisible(true);
    }

}

