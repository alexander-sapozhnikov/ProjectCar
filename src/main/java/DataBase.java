import java.sql.*;

/**
 * Класс работает с базой данных
 * обеспечитвает подключение к ней и всю работу с ней
 */
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
        ResultSet rs = null;
        try {
            pst = conn.prepareStatement(
                    "SELECT p.name person, c.name as name, e.name as eng_name, power, k.name as kpp_name, count_step\n" +
                            "FROM car c\n" +
                            "    LEFT JOIN engine e on c.ib_engine = e.id\n" +
                            "    LEFT JOIN  kpp k on c.id_kpp = k.id\n" +
                            "    RIGHT JOIN car_person cp on cp.id_car = c.id\n" +
                            "    LEFT JOIN person p on cp.id_person = p.id;");
            rs  = pst.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    public ResultSet getSmt(String base){
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            pst = conn.prepareStatement(
                    "SELECT * FROM " + base);
            rs  = pst.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    public void addNewCar(Car car){

        try {
            //Work with table car
            conn.prepareStatement(
                    "INSERT INTO car VALUES(default, \'" +car.getName() +
                            "\', "+ car.getEng_name() +
                            ", "+ car.getKpp_name() +
                            ")").executeUpdate();

            ResultSet rs = conn.prepareStatement("SELECT * FROM car ORDER  BY id DESC LIMIT 1").executeQuery();
            rs.next();
            int id_car = rs.getInt("id");

            conn.prepareStatement(
                    "INSERT INTO car_person VALUES(" +
                            id_car + ", " +
                            car.getPerson() + ")").executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Car assembleCar(Car car){
        Car readyCar = new Car();
        readyCar.setName(car.getName());
        PreparedStatement pst = null;
        try {
            //Eng set
            pst = conn.prepareStatement(
                    "SELECT * FROM engine where id = " + car.getEng_name());
            ResultSet rs = pst.executeQuery();
            rs.next();
            readyCar.setEng_name(rs.getString("name"));
            readyCar.setPower(rs.getString("power"));

            //Kpp set
            pst = conn.prepareStatement(
                    "SELECT * FROM kpp where id = " + car.getKpp_name());
            rs = pst.executeQuery();
            rs.next();
            readyCar.setKpp_name(rs.getString("name"));
            readyCar.setCount_step(rs.getInt("count_step"));

            //Person set
            pst = conn.prepareStatement(
                    "SELECT * FROM person where id = " + car.getPerson());
            rs = pst.executeQuery();
            rs.next();
            readyCar.setPerson(rs.getString("name"));

            rs.close();
            pst.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return readyCar;
    }
}
