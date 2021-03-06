
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import java.sql.*;
import java.util.*;

/**
 * Основной класс обрабатывающий все действия со страницы
 * и заполняет её
 */
@ManagedBean
public class Information {

    @EJB
    private DataBase DB = null;
    private List<Car> listCar = new ArrayList<Car>();
    private List<Capsule> listPerson = new ArrayList<Capsule>();
    private List<Capsule> listEng = new ArrayList<Capsule>();
    private List<Capsule> listKpp = new ArrayList<Capsule>();
    private char sort = 'D';
    private String order = " ASC";


    public Information(){

        DB = new DataBase();
        getInfoPersonEngKpp();
        UPDInfromation();
    }

    public void addNewCar(Car car){
        //addNewCar - Добавляет новую машину в базу изменяет и устанавливает id person и car
        //assembleCar - Оставшиеся поля изменяет в нужный вид
        listCar.add(DB.assembleCar(DB.addNewCar(car)));
    }

    public void deleteCar(Car delCar){
        DB.deleteCar(delCar);
        listCar.remove(delCar);
    }

    //Обновление информации в таблице
    public void UPDInfromation(){
        listCar.clear();
        ResultSet rs = DB.getAll(sort, order);
        try {
            while(rs.next()) {
                Car car = new Car();
                car.setId_person(rs.getInt("id_person"));
                car.setPerson(rs.getString("person"));
                car.setId_car(rs.getInt("id_car"));
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

    //Получаем информацию для выпадающенго списка при добавление авто
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





    //Дальше идут служебны функции для jsf
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

    public char getSort() {
        return sort;
    }

    public void setSort(char sort) {
        this.sort = sort;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }
}