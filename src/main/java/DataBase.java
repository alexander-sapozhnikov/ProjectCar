import java.sql.*;

public class DataBase {
    private Connection conn = null;
    private String userDB = "postgres";
    private String passwordDB = "";

    public DataBase(){
        String url = "jdbc:postgresql://localhost:5432/neo";
        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection(url, userDB, passwordDB);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public ResultSet getAll(){
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(
                    "SELECT c.name as name, e.name as eng_name, power, k.name as kpp_name, count_step" +
                    " FROM car c " +
                    " LEFT JOIN engine e on c.ib_engine = e.id " +
                    "LEFT JOIN  kpp k on c.id_kpp = k.id");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ResultSet rs = null;
        try {
            rs  = pst.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }
}
