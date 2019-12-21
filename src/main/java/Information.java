import com.sun.javafx.util.Logging;
import javafx.scene.chart.PieChart;
import sun.rmi.runtime.Log;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import java.sql.*;
import java.util.*;
import java.util.logging.Logger;

import static java.lang.Math.max;

@ManagedBean
public class Information {

    @EJB
    private DataBase DB = null;
    private List<Car> listCar = new ArrayList<Car>();
    private List<Capsule> listPerson = new ArrayList<Capsule>();
    private List<Capsule> listEng = new ArrayList<Capsule>();
    private List<Capsule> listKpp = new ArrayList<Capsule>();
    private String name = "sa";


    public Information(){

        DB = new DataBase();
        getInfoPersonEngKpp();
        UPDInfromation();
    }

    public void addNewCar(Car car){
        listCar.add(DB.assembleCar(car));
        DB.addNewCar(car);
    }

    public void deleteCar(Car car){
        name = car.getName();
    }
    private void UPDInfromation(){
        listCar.clear();
        ResultSet rs = DB.getAll();
        try {
            while(rs.next()) {
                Car car = new Car();
                car.setPerson(rs.getString("person"));
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
    private void getInfoPersonEngKpp(){
        //Add information in list
        ResultSet rs = DB.getSmt("person");
        try {
            while(rs.next()) {
                listPerson.add(
                        new Capsule(rs.getString("name"), rs.getInt("id")) );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        rs = DB.getSmt("engine");
        try {
            while(rs.next()) {
                listEng.add( new Capsule(rs.getString("name") + " - " +
                        rs.getString("power"), rs.getInt("id")));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        rs = DB.getSmt("kpp");
        try {
            while(rs.next()) {
                listKpp.add(new Capsule(rs.getString("name") + "  - " +
                        rs.getString("count_step"), rs.getInt("id")));

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

    public List<Capsule> getListPerson() {
        return listPerson;
    }

    public void setListPerson(List<Capsule> listPerson) {
        this.listPerson = listPerson;
    }

    public List<Capsule> getListEng() {
        return listEng;
    }

    public void setListEng(List<Capsule> listEng) {
        this.listEng = listEng;
    }

    public List<Capsule> getListKpp() {
        return listKpp;
    }

    public void setListKpp(List<Capsule> listKpp) {
        this.listKpp = listKpp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}