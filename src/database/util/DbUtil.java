package database.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DbUtil {
    public DbUtil() {

    }

    public void fecharConexaoEPrpdStatement(Connection conexao, PreparedStatement pst) throws SQLException {
        pst.close();
        conexao.close();
    }
}
