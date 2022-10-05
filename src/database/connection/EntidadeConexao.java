package database.connection;

public class EntidadeConexao {
    private String endereco = "localhost";
    private String porta = "5432";
    private String nomeBanco = "KarasunoAcademy";
    private String usuario = "postgres";
    private String senha = "postgres";

    public String getEndereco() {
        return endereco;
    }

    public String getPorta() {
        return porta;
    }

    public String getNomeBanco() {
        return nomeBanco;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getSenha() {
        return senha;
    }

}
