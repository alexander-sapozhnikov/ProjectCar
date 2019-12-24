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
            checkExistsTables();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        //Проверяем все таблицы есть, еслти нет то создаем
    }

    public ResultSet getAll(char sort, String order){
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            String sortString = "c.id";
            switch (sort){
                case 'E' : sortString = "e.name";
                    break;
                case 'K' :  sortString = "k.name";
                    break;
            }
            pst = conn.prepareStatement(
                    "SELECT p.name person, p.id id_person, c.name as name, c.id as id_car," +
                            " e.name as eng_name, power, k.name as kpp_name, count_step\n" +
                            "FROM car c\n" +
                            "    LEFT JOIN engine e on c.ib_engine = e.id\n" +
                            "    LEFT JOIN  kpp k on c.id_kpp = k.id\n" +
                            "    RIGHT JOIN car_person cp on cp.id_car = c.id\n" +
                            "    LEFT JOIN person p on cp.id_person = p.id ORDER  BY " +
                            sortString + order);
            /*switch (sort){
                case 'E' : pst.setString(1, "e.name");
                break;
                case 'K' : pst.setString(1, "k.name");
                break;
                default: pst.setString(1, "c.id");
            }*/

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

    public Car addNewCar(Car car){
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
            car.setId_car(id_car);

            conn.prepareStatement(
                    "INSERT INTO car_person VALUES(" +
                            id_car + ", " +
                            car.getPerson() + ")").executeUpdate();
            car.setId_person(Integer.parseInt(car.getPerson()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return car;
    }

    public void deleteCar(Car car){
        try {
            PreparedStatement pst = conn.prepareStatement(
                    "DELETE FROM car_person WHERE id_car = ? AND id_person = ?");
            pst.setInt(1, car.getId_car());
            pst.setInt(2, car.getId_person());
            pst.executeUpdate();

            pst = conn.prepareStatement(
                    "DELETE FROM car WHERE id = ?");
            pst.setInt(1, car.getId_car());
            pst.executeUpdate();
            pst.close();

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
            readyCar.setId_person(rs.getInt("id"));

            rs.close();
            pst.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return readyCar;
    }

    private void checkExistsTables(){
        try {
            DatabaseMetaData metaData = conn.getMetaData();
            //Person
            ResultSet have = metaData.getTables(null, null, "person", null);
            if (!have.next()) {
                conn.prepareStatement("CREATE TABLE person (\n" +
                        "id SERIAL PRIMARY KEY,\n" +
                        "name VARCHAR(60)\n" +
                        ")").execute();

                PreparedStatement pst = conn.prepareStatement("INSERT into  person  values (default, ?)");
                pst.setString(1, "Вася");
                pst.execute();
                pst.setString(1, "Жася");
                pst.execute();
                pst.setString(1, "Муся");
                pst.execute();
                pst.setString(1, "Леша");
                pst.execute();
            }
            //Engine
            have = metaData.getTables(null, null, "engine", null);
            if (!have.next()) {
                conn.prepareStatement("CREATE TABLE engine (\n" +
                        " id SERIAL PRIMARY KEY,\n" +
                        " name VARCHAR(70) not null,\n" +
                        " power VARCHAR(60))").execute();
                PreparedStatement pst = conn.prepareStatement("INSERT into  engine  values (default, ?, ?)");
                pst.setString(1, "21081");
                pst.setString(2, "1.1");
                pst.execute();
                pst.setString(1, "H854125");
                pst.setString(2, "2.3");
                pst.execute();
                pst.setString(1, "456999");
                pst.setString(2, "8.9");
                pst.execute();
                pst.setString(1, "M891");
                pst.setString(2, "0.5");
                pst.execute();
            }
            //Kpp
            have = metaData.getTables(null, null, "kpp", null);
            if (!have.next()) {
                conn.prepareStatement("CREATE TABLE kpp (\n" +
                        "id SERIAL PRIMARY KEY,\n" +
                        "name VARCHAR(70) not null,\n" +
                        "count_step INT)").execute();
                PreparedStatement pst = conn.prepareStatement("INSERT into  kpp  values (default, ?, ?)");
                pst.setString(1, "JH3 540");
                pst.setInt(2, 7);
                pst.execute();
                pst.setString(1, "TRE 999");
                pst.setInt(2, 5);
                pst.execute();
                pst.setString(1, "JH3 879");
                pst.setInt(2, 6);
                pst.execute();
                pst.setString(1, "PSW 208");
                pst.setInt(2, 5);
                pst.execute();
            }
            //Car
            have = metaData.getTables(null, null, "car", null);
            if (!have.next()) {
                conn.prepareStatement("CREATE TABLE car (   \n" +
                        "id SERIAL PRIMARY KEY,\n" +
                        "name VARCHAR(60) not null,\n" +
                        "ib_engine INT,\n" +
                        "id_kpp INT,\n" +
                        "foreign key(ib_engine) references engine(id),\n" +
                        "foreign key (id_kpp) references kpp(id))").execute();
            }
            //Car_person
            have = metaData.getTables(null, null, "car_person", null);
            if (!have.next()){
                conn.prepareStatement("CREATE TABLE car_person (\n" +
                        "id_car INT,\n" +
                        "id_person INT,\n" +
                        "PRIMARY KEY (id_car, id_person))").execute();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
