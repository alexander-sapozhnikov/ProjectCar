import javafx.scene.chart.PieChart;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import java.sql.*;
import java.util.*;

@ManagedBean
public class Information {

    @EJB
    private DataBase DB = null;
    private List<Car> listCar = new ArrayList<Car>();

    public Information(){

        DB = new DataBase();
        UPDInfromation();
    }

    private void UPDInfromation(){

        listCar.clear();

        ResultSet rs = DB.getAll();
        try {
            while(rs.next()) {
                Car car = new Car();
                car.setName(rs.getString("name"));
                car.setEng_name(rs.getString("eng_name"));
                car.setPower(rs.getString("power"));
                car.setKpp_name(rs.getString("kpp_name"));
                car.setCount_step(rs.getInt("count_step"));
                listCar.add(car);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Car> getListCar() {
        return listCar;
    }

    public void setListCar(List<Car> listCar) {
        this.listCar = listCar;
    }


}