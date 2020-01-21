package Hibernate;

public class car {
    int id;
    String name;
    int ib_engine;
    int id_kpp;
    int id_person;

    public int getId_person() {
        return id_person;
    }

    public void setId_person(int id_person) {
        this.id_person = id_person;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIb_engine() {
        return ib_engine;
    }

    public void setIb_engine(int ib_engine) {
        this.ib_engine = ib_engine;
    }

    public int getId_kpp() {
        return id_kpp;
    }

    public void setId_kpp(int id_kpp) {
        this.id_kpp = id_kpp;
    }

}
