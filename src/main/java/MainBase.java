import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

/**
 * Основной класс для работы с базой данных
 * @author alexandr
 *
 */
public class MainBase {

    private String url = "";
    private String user = "postgres";
    private String password = "";
    private Connection conn = null;

    public MainBase() {
        String url = "jdbc:postgresql://localhost:5432/neo";
        Properties props = new Properties();
        props.setProperty("user","postgres");
        props.setProperty("password","");
        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection(url, "postgres", "");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public ResultSet get(){
        ResultSet rs = null;
        try {
            PreparedStatement pst =  conn.prepareStatement("SELECT * FROM car c " +
                    " LEFT JOIN engine e on c.ib_engine = e.id " +
                    "LEFt JOIN  kpp k on c.id_kpp = k.id");
            return  pst.executeQuery();
        } catch (SQLException e) {
            return null;
        }
    }




}